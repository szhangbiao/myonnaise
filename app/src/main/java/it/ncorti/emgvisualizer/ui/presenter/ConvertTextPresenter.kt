package it.ncorti.emgvisualizer.ui.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import it.ncorti.emgvisualizer.network.ApiDataSource
import it.ncorti.emgvisualizer.ui.contract.ConvertTextContract
import javax.inject.Inject

class ConvertTextPresenter @Inject constructor(private val view: ConvertTextContract.View, val apiDataSource: ApiDataSource) : ConvertTextContract.Presenter {

    private val disposableContainer by lazy {
        CompositeDisposable()
    }

    override fun onTextConvert(text: String) {
        apiDataSource.synSystem(text)
            .doOnSubscribe {
                view.showLoading()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                view.hideLoading()
            }
            .subscribe({
                view.convertSuccess(it)
            }, {
                view.hideLoading()
                view.showToast(it.message ?: "未知错误")
            })
            .apply {
                disposableContainer.add(this)
            }
    }

    override fun create() {}

    override fun start() {}

    override fun stop() {
        disposableContainer.clear()
    }
}