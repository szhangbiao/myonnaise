package it.ncorti.emgvisualizer.network

import io.reactivex.Observable
import io.reactivex.Single
import it.ncorti.emgvisualizer.network.exception.ApiException
import it.ncorti.emgvisualizer.network.service.ApiService
import javax.inject.Inject

class ApiDataSource @Inject constructor(private val apiService: ApiService) {

    fun synSystem(sentence: String): Observable<String> {
        return apiService.synSystem(sentence)
            .map {
                it.data ?: throw ApiException(it.message ?: "Api返回出错！")
            }
            .map {
                // convert to oss video url
                "https://hfutshouyu.oss-cn-hangzhou.aliyuncs.com/syn/$it.avi"
            }
    }

    fun translateSystem(emg: String, imu: String): Observable<String> {
        return apiService.translateSystem(emg, imu)
            .map {
                it.data ?: throw ApiException(it.message ?: "Api返回出错！")
            }
    }
}