package it.ncorti.emgvisualizer.ui.contract

import it.ncorti.emgvisualizer.ui.base.BasePresenter
import it.ncorti.emgvisualizer.ui.base.BaseView

interface ExportContract {

    interface View : BaseView {
        fun enableStartCollectingButton()

        fun disableStartCollectingButton()

        fun showNotStreamingErrorMessage()

        fun showCollectionStarted()

        fun showCollectionStopped()

        fun showCollectedPoints(totalPoints: Int)

        fun enableResetButton()

        fun disableResetButton()

        fun hideSaveArea()

        fun showSaveArea()

        fun saveCsvFile(content: String)

        fun sharePlainText(content: String)

        fun showToast(message: String)

        fun showLoading()

        fun hideLoading()
    }

    interface Presenter : BasePresenter {

        fun onCollectionTogglePressed()

        fun onResetPressed()

        fun onSavePressed()

        fun onSharePressed()

        fun onUploadPressed()
    }
}
