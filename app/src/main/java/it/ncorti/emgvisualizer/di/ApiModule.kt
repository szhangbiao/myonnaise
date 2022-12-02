package it.ncorti.emgvisualizer.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.schedulers.Schedulers
import it.ncorti.emgvisualizer.network.service.ApiService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val NETWORK_TIMEOUT = 30000L
    private const val BASE_URL = "http://101.36.230.236:5010/"

    @Singleton
    @Provides
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(): GsonConverterFactory = GsonConverterFactory.create(Gson())

    @Singleton
    @Provides
    fun provideOkhttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
        builder.writeTimeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
        builder.readTimeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
        builder.addInterceptor(HttpLoggingInterceptor())
        builder.retryOnConnectionFailure(false)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient, rxJava2CallAdapterFactory: RxJava2CallAdapterFactory, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .addConverterFactory(gsonConverterFactory)
            .client(httpClient)
            .build()
    }

    @Singleton
    @Provides
    internal fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create()
}