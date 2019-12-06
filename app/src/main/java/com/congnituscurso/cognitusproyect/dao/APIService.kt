package com.congnituscurso.cognitusproyect.dao
import com.congnituscurso.cognitusproyect.model.CheckInResponse
import com.congnituscurso.cognitusproyect.model.PasswordResponse
import com.congnituscurso.cognitusproyect.model.RegisterResponse
import com.congnituscurso.cognitusproyect.model.UsuarioResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @POST("GeneralData")
    fun loginUser(@Query("word")paramLogin:String):Call<UsuarioResponse>

    @POST("GeneralData")
    fun registerUser(@Query("word")paramRegister:String):Call<RegisterResponse>

    @POST("GeneralData")
    fun password(@Query("word")paramPassword:String):Call<PasswordResponse>

    @Multipart
    @POST("GeneralData")
    fun checkIn(@Part partMap: List<MultipartBody.Part> ):Call<CheckInResponse>
    //fun registroPerfil(@Part partMap: List<MultipartBody.Part> ): Call<ServerResponse>
}