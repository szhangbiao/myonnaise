package it.ncorti.emgvisualizer.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import it.ncorti.emgvisualizer.ui.contract.ControlDeviceContract
import it.ncorti.emgvisualizer.ui.contract.ExportContract
import it.ncorti.emgvisualizer.ui.fragment.ControlDeviceFragment
import it.ncorti.emgvisualizer.ui.presenter.ExportPresenter
import it.ncorti.emgvisualizer.ui.contract.GraphContract
import it.ncorti.emgvisualizer.ui.fragment.GraphFragment
import it.ncorti.emgvisualizer.ui.presenter.GraphPresenter
import it.ncorti.emgvisualizer.ui.contract.ScanDeviceContract
import it.ncorti.emgvisualizer.ui.fragment.ExportFragment
import it.ncorti.emgvisualizer.ui.fragment.ScanDeviceFragment
import it.ncorti.emgvisualizer.ui.presenter.ControlDevicePresenter
import it.ncorti.emgvisualizer.ui.presenter.ScanDevicePresenter
import javax.inject.Singleton

@Module
@InstallIn(FragmentComponent::class)
interface PresenterModule {

    @Binds
    fun bindScanDevicePresenter(scanDevicePresenter: ScanDevicePresenter): ScanDeviceContract.Presenter

    @Binds
    fun bindGraphPresenter(graphPresenter: GraphPresenter): GraphContract.Presenter

    @Binds
    fun bindExportPresenter(exportPresenter: ExportPresenter): ExportContract.Presenter

    @Binds
    fun bindControlDevicePresenter(controlDevicePresenter: ControlDevicePresenter): ControlDeviceContract.Presenter
}

@Module
@InstallIn(SingletonComponent::class)
interface ViewModule {

    @Binds
    fun bindControlDeviceView(exportFragment: ControlDeviceFragment): ControlDeviceContract.View

    @Binds
    fun bindExportView(exportFragment: ExportFragment): ExportContract.View

    @Binds
    fun bindGraphView(exportFragment: GraphFragment): GraphContract.View

    @Binds
    fun bindScanDeviceView(scanFragment: ScanDeviceFragment): ScanDeviceContract.View

    companion object {

        @Provides
        @Singleton
        fun provideExportFragment(): ExportFragment {
            return ExportFragment.newInstance()
        }

        @Provides
        @Singleton
        fun provideControlDeviceFragment(): ControlDeviceFragment {
            return ControlDeviceFragment.newInstance()
        }

        @Provides
        @Singleton
        fun provideControlGraphFragment(): GraphFragment {
            return GraphFragment.newInstance()
        }

        @Provides
        @Singleton
        fun provideScanDeviceFragment(): ScanDeviceFragment {
            return ScanDeviceFragment.newInstance()
        }
    }

}