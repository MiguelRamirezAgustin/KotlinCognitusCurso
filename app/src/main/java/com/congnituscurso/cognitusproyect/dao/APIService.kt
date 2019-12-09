package com.congnituscurso.cognitusproyect.dao
import com.congnituscurso.cognitusproyect.model.*
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

    @POST("GeneralData")
    fun terminos(@Query("word")paramTerminos:String):Call<TerminosResponse>

    @Multipart
    @POST("GeneralData")
    fun actualzarPerfil(@Part partMaps: List<MultipartBody.Part>):Call<PerfilResponse>

    @POST("GeneralData")
    fun tareasGet(@Query("word")paramGetTareas:String):Call<TareasResponse>

    @POST("GeneralData")
    fun tareaGuardar(@Query("word")pararGuardarTarea:String):Call<TareasGuardarResponse>


    @POST("GeneralData")
    fun tareaFinalizar(@Query("word")pararGuardarTarea:String):Call<TareaFinalizarResponse>
}