package it.ncorti.emgvisualizer.ui.presenter

import android.bluetooth.BluetoothDevice
import android.util.Log
import com.ncorti.myonnaise.Myonnaise
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import it.ncorti.emgvisualizer.ui.contract.ScanDeviceContract
import it.ncorti.emgvisualizer.device.DeviceManager
import it.ncorti.emgvisualizer.model.Device
import javax.inject.Inject

class ScanDevicePresenter @Inject constructor(private val view: ScanDeviceContract.View, private val myonnaise: Myonnaise, private val deviceManager: DeviceManager) : ScanDeviceContract.Presenter {

    private lateinit var scanFlowable: Flowable<BluetoothDevice>

    private var scanSubscription: Disposable? = null

    override fun create() {
        scanFlowable = myonnaise.startScan()
    }

    override fun start() {
        view.wipeDeviceList()
        if (deviceManager.scannedDeviceList.isEmpty()) {
            view.showStartMessage()
        } else {
            view.populateDeviceList(deviceManager.scannedDeviceList.map { Device(it.name, it.address) })
        }
    }

    override fun stop() {
        scanSubscription?.dispose()
        view.hideScanLoading()
    }

    override fun onScanToggleClicked() {
        if (scanSubscription?.isDisposed == false) {
            scanSubscription?.dispose()
            view.hideScanLoading()
            if (deviceManager.scannedDeviceList.isEmpty()) {
                view.showEmptyListMessage()
            }
        } else {
            view.hideEmptyListMessage()
            view.showScanLoading()
            scanSubscription = scanFlowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it !in deviceManager.scannedDeviceList) {
                            view.addDeviceToList(Device(it.name, it.address))
                            deviceManager.scannedDeviceList.add(it)
                        }
                        Log.d("ScanDevice", "${it.name},::${it.address}")
                    },
                    {
                        view.hideScanLoading()
                        view.showScanError()
                        if (deviceManager.scannedDeviceList.isEmpty()) {
                            view.showEmptyListMessage()
                        }
                    },
                    {
                        view.hideScanLoading()
                        view.showScanCompleted()
                        if (deviceManager.scannedDeviceList.isEmpty()) {
                            view.showEmptyListMessage()
                        }
                    }
                )
        }
    }

    override fun onDeviceSelected(index: Int) {
        deviceManager.selectedIndex = index
        view.navigateToControlDevice()
    }
}
