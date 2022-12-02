package it.ncorti.emgvisualizer.ui.contract

import it.ncorti.emgvisualizer.ui.base.BasePresenter
import it.ncorti.emgvisualizer.ui.base.BaseView
import it.ncorti.emgvisualizer.model.Device

interface ScanDeviceContract {

    interface View : BaseView {

        fun showStartMessage()

        fun showEmptyListMessage()

        fun hideEmptyListMessage()

        fun wipeDeviceList()

        fun addDeviceToList(device: Device)

        fun populateDeviceList(list: List<Device>)

        fun showScanLoading()

        fun hideScanLoading()

        fun showScanError()

        fun showScanCompleted()

        fun navigateToControlDevice()
    }

    interface Presenter : BasePresenter {

        fun onScanToggleClicked()

        fun onDeviceSelected(index: Int)
    }
}
