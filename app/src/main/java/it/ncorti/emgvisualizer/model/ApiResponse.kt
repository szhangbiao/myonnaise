package it.ncorti.emgvisualizer.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("code", alternate = ["error_code"])
    val code: Int?,
    @SerializedName("msg", alternate = ["message", "error_msg"])
    val message: String?,
    val data: T? = null
)