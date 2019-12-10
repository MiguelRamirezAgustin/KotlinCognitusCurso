package com.congnituscurso.cognitusproyect.model

import com.google.gson.annotations.SerializedName

class NotificacionLeerResponse (@SerializedName("valido")val valido:String,
                                @SerializedName("mensaje")val mensaje:String)

/* Response leerNotificacion
{
    "valido": "1",
    "mensaje": "Se actualizo correctamente"
}*/