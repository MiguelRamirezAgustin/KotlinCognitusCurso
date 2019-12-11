package com.congnituscurso.cognitusproyect.model

import com.google.gson.annotations.SerializedName

class EncuestaEnviarResponse (@SerializedName("valido")val valido:String,
                              @SerializedName("mensaje")val mensaje:String)

/*
* {
    "valido": "1",
    "mensaje": "Gracias por su compartir su punto de vista."
}
* */