package com.congnituscurso.cognitusproyect.model

import com.congnituscurso.cognitusproyect.model.response.ResponseRegister
import com.google.gson.annotations.SerializedName

class RegisterResponse(@SerializedName("valido")val validoRegister:String,
                       @SerializedName("usuario")val usuarioRegister:ResponseRegister,
                       @SerializedName("mensaje")val mensajeRegister:String)


/*{  Response register
    "valido": "1",
    "usuario": {
    "usu_id": "298",
    "usr_rutafoto": "media/usuario/12_20191128130512.jpeg",
    "usr_email": "ramirez@gmail.com",
    "usr_nombre": "miguel"
},
    "mensaje": "Bienvenido"
}*/