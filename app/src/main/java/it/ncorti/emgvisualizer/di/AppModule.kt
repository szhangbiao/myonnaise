package it.ncorti.emgvisualizer.di

import android.content.Context
import com.ncorti.myonnaise.Myonnaise
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import it.ncorti.emgvisualizer.device.DeviceManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDeviceManager(): DeviceManager {
        return DeviceManager()
    }

    @Provides
    @Singleton
    fun provideMyonnaise(@ApplicationContext context: Context): Myonnaise {
        return Myonnaise(context)
    }
}