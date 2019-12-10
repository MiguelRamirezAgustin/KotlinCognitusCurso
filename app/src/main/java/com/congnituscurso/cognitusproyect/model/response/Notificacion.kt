package com.congnituscurso.cognitusproyect.model.response

import com.google.gson.annotations.SerializedName

class Notificacion (@SerializedName("not_id")val not_id:String,
                    @SerializedName("not_titulo")val not_titulo:String,
                    @SerializedName("not_update")val not_update:String,
                    @SerializedName("not_desc")val not_desc:String,
                    @SerializedName("not_status")val not_status:String
                    )

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