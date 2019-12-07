package com.congnituscurso.cognitusproyect.model

import com.google.gson.annotations.SerializedName

class PerfilResponse (@SerializedName("ruta_foto")var rutaFotoPerfil:String,
                      @SerializedName("valido")var validoPerfil:String,
                      @SerializedName("mensaje") var mensajePerfil:String
                     )