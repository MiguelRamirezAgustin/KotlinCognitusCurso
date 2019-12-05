package com.congnituscurso.cognitusproyect.model

import com.congnituscurso.cognitusproyect.model.response.ResponseLogin
import com.google.gson.annotations.SerializedName

class UsuarioResponse (@SerializedName("valido")var validoLogin:String,
                       @SerializedName("usuario")var usuarioLogin:ResponseLogin,
                       @SerializedName("mensaje")var mensajeLogin:String)


/* Response login
{
    "valido": "1",
    "usuario": {
    "usr_rutafoto": "media/usuario/12_20191128130512.jpeg",
    "usr_email": "ramirez@gmail.com",
    "usr_id": "298",
    "usr_nombre": "miguel"
},
    "mensaje": "Ingreso correcto"
}*/