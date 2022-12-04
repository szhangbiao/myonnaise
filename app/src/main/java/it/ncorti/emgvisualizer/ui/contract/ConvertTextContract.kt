package it.ncorti.emgvisualizer.ui.contract

import it.ncorti.emgvisualizer.ui.base.BasePresenter
import it.ncorti.emgvisualizer.ui.base.BaseView

class ConvertTextContract {
    interface View : BaseView {
        fun showLoading()

        fun hideLoading()

        fun showToast(message: String)

        fun convertSuccess(videoUrl: String)
    }

    interface Presenter : BasePresenter {
        fun onTextConvert(text: String)
    }
}