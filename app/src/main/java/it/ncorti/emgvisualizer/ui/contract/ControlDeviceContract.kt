package it.ncorti.emgvisualizer.ui.contract

import it.ncorti.emgvisualizer.ui.base.BasePresenter
import it.ncorti.emgvisualizer.ui.base.BaseView

interface ControlDeviceContract {

    interface View : BaseView {

        fun showDeviceInformation(name: String?, address: String)

        fun showConnectionProgress()

        fun hideConnectionProgress()

        fun showConnected()

        fun showDisconnected()

        fun showConnecting()

        fun showConnectionError()

        fun enableConnectButton()

        fun disableConnectButton()

        fun enableControlPanel()

        fun disableControlPanel()

        fun showStreaming()

        fun showNotStreaming()

        fun showFrequency(frequency: Int)
    }

    interface Presenter : BasePresenter {

        fun onConnectionToggleClicked()

        fun onStreamingToggleClicked()

        fun onVibrateClicked(duration: Int)

        fun onProgressSelected(progress: Int)
    }
}
