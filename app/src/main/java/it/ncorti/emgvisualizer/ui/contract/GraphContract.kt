package it.ncorti.emgvisualizer.ui.contract

import it.ncorti.emgvisualizer.ui.base.BasePresenter
import it.ncorti.emgvisualizer.ui.base.BaseView

interface GraphContract {

    interface View : BaseView {

        fun showData(data: FloatArray)

        fun startGraph(running: Boolean)

        fun hideNoStreamingMessage()

        fun showNoStreamingMessage()
    }

    interface Presenter : BasePresenter
}
