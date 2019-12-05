package com.congnituscurso.cognitusproyect.model

import com.google.gson.annotations.SerializedName

class PasswordResponse (@SerializedName("valido")val validoPassword:String,
                        @SerializedName("mensaje")val mensajePassword:String)


/*{
    "valido": "1",
    "mensaje": "Se envió una liga de recuperación a su correo"
}*/