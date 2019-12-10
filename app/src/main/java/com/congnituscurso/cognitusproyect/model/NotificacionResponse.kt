package com.congnituscurso.cognitusproyect.model

import com.congnituscurso.cognitusproyect.model.response.Notificacion
import com.google.gson.annotations.SerializedName

class NotificacionResponse(
                           @SerializedName("notificaciones")val notificaciones: ArrayList<Notificacion>,
                           @SerializedName("valido")val validoNotificacion:String,
                           @SerializedName("mensaje")val mensajenotificacion:String)


/* Response Notificacin
* {
    "notificaciones": [
        {
            "not_id": "58",
            "not_titulo": "Bienvenido",
            "not_update": "2019-12-09 17:26:20",
            "not_desc": "Hola Agustin , gracias por registrarte en nuestra plataforma.",
            "not_status": "1"
        }
    ],
    "valido": "1",
    "mensaje": "correcto"
}
* */