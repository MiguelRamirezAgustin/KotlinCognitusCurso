package com.congnituscurso.cognitusproyect.model

import com.congnituscurso.cognitusproyect.model.response.ResponseLogin
import com.google.gson.annotations.SerializedName

class UsuarioResponse (@SerializedName("valido")var validoLogin:String,
                       @SerializedName("usuario")var usuarioLogin:ResponseLogin,
                       @SerializedName("mensaje")var mensajeLogin:String)