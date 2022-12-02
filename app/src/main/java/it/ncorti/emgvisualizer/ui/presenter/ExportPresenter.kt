package it.ncorti.emgvisualizer.ui.presenter

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.ncorti.myonnaise.EMG_ARRAY_SIZE
import com.ncorti.myonnaise.IMU_ARRAY_SIZE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import it.ncorti.emgvisualizer.ui.contract.ExportContract
import it.ncorti.emgvisualizer.device.DeviceManager
import it.ncorti.emgvisualizer.network.ApiDataSource
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class ExportPresenter @Inject constructor(private val view: ExportContract.View, private val deviceManager: DeviceManager, val apiDataSource: ApiDataSource) : ExportContract.Presenter {

    private val counter: AtomicInteger = AtomicInteger()
    private val buffer: ArrayList<FloatArray> = arrayListOf()

    internal var dataSubscription: Disposable? = null

    private val disposableContainer by lazy {
        CompositeDisposable()
    }

    override fun create() {}

    override fun start() {
        view.showCollectedPoints(counter.get())
        deviceManager.myo?.apply {
            if (this.isStreaming()) {
                view.enableStartCollectingButton()
            } else {
                view.disableStartCollectingButton()
            }
        }
    }

    override fun stop() {
        dataSubscription?.dispose()
        disposableContainer.clear()
        view.showCollectionStopped()
    }

    override fun onCollectionTogglePressed() {
        deviceManager.myo?.apply {
            if (this.isStreaming()) {
                if (dataSubscription == null || dataSubscription?.isDisposed == true) {
                    dataSubscription = this.dataFlowable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe {
                            view.showCollectionStarted()
                            view.disableResetButton()
                        }
                        .subscribe {
                            buffer.add(it)
                            var text: String = ""
                            it.forEach { fl -> text += ", $fl" }
                            Log.d("mytest", "single point data = $text")
                            view.showCollectedPoints(counter.incrementAndGet())
                        }
                } else {
                    dataSubscription?.dispose()
                    view.enableResetButton()
                    view.showSaveArea()
                    view.showCollectionStopped()
                }
            } else {
                view.showNotStreamingErrorMessage()
            }
        }
    }

    override fun onResetPressed() {
        counter.set(0)
        buffer.clear()
        view.showCollectedPoints(0)
        dataSubscription?.dispose()
        view.hideSaveArea()
        view.disableResetButton()
    }

    override fun onSavePressed() {
        view.saveCsvFile(createCsv(buffer))
    }

    override fun onSharePressed() {
        view.sharePlainText(createCsv(buffer))
    }

    override fun onUploadPressed() {
        val emg = StringBuffer()
        val imu = StringBuffer()
        buffer.forEach { floats ->
            if (floats.size == EMG_ARRAY_SIZE / 2) {
                floats.forEachIndexed { index, fl ->
                    if (index != (EMG_ARRAY_SIZE / 2) - 1) {
                        emg.append(fl).append(" ")
                    } else {
                        emg.append(fl)
                    }
                }
                emg.append("\n")
            } else {
                floats.forEachIndexed { index, fl ->
                    if (index != IMU_ARRAY_SIZE - 1) {
                        imu.append(fl).append(" ")
                    } else {
                        imu.append(fl)
                    }
                }
                imu.append("\n")
            }
        }
        apiDataSource.translateSystem(emg.toString(), imu.toString())
            .doOnSubscribe {
                view.showLoading()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                view.hideLoading()
            }
            .subscribe({
                view.showToast(it)
            }, {
                view.hideLoading()
                view.showToast(it.message ?: "未知错误")
            })
            .apply {
                disposableContainer.add(this)
            }
    }

    @VisibleForTesting
    internal fun createCsv(buffer: ArrayList<FloatArray>): String {
        val stringBuilder = StringBuilder()
        buffer.forEach {
            it.forEach {
                stringBuilder.append(it)
                stringBuilder.append(";")
            }
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }
}
