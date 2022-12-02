package it.ncorti.emgvisualizer.ui.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import it.ncorti.emgvisualizer.ui.contract.GraphContract
import it.ncorti.emgvisualizer.device.DeviceManager
import javax.inject.Inject

class GraphPresenter @Inject constructor(private val view: GraphContract.View, private val deviceManager: DeviceManager) : GraphContract.Presenter {

    private var dataSubscription: Disposable? = null

    override fun create() {}

    override fun start() {
        deviceManager.myo?.apply {
            if (this.isStreaming()) {
                view.hideNoStreamingMessage()
                dataSubscription?.apply {
                    if (!this.isDisposed) this.dispose()
                }
                dataSubscription = this.dataFlowable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        view.startGraph(true)
                    }
                    .subscribe {
                        view.showData(it)
                    }
            } else {
                view.showNoStreamingMessage()
            }
        }
    }

    override fun stop() {
        view.startGraph(false)
        dataSubscription?.dispose()
    }
}
