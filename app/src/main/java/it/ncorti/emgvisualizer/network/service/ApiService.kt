package it.ncorti.emgvisualizer.network.service

import io.reactivex.Observable
import io.reactivex.Single
import it.ncorti.emgvisualizer.model.ApiResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST("syn_system")
    @FormUrlEncoded
    fun synSystem(@Field("sentence") sentence: String): Observable<ApiResponse<String>>

    @POST("translate_system")
    @FormUrlEncoded
    fun translateSystem(@Field("emg") emg: String, @Field("imu") imu: String): Observable<ApiResponse<String>>
}