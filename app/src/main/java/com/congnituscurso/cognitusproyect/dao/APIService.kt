package com.congnituscurso.cognitusproyect.dao
import com.congnituscurso.cognitusproyect.model.UsuarioResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @POST("GeneralData")
    fun loginUser(@Query("word")paramLogin:String):Call<UsuarioResponse>
}