package com.congnituscurso.cognitusproyect.model.response

import com.google.gson.annotations.SerializedName

class Encuesta (@SerializedName("cpreg_id")val cpreg_id:String,
                @SerializedName("cpreg_tipo")val cpreg_tipo:String,
                @SerializedName("cpreg_titulo")val cpreg_titulo:String,
                @SerializedName("respuestas")val respuestas:ArrayList<Respuestas>)