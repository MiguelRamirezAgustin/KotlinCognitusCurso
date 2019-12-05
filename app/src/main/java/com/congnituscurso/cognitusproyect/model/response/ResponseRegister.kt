package com.congnituscurso.cognitusproyect.model.response

import com.google.gson.annotations.SerializedName

class ResponseRegister (@SerializedName("usu_id")val usu_idRegister:String,
                        @SerializedName("usr_rutafoto")val usr_rutafotoRegister:String,
                        @SerializedName("usr_email")val usr_emailRegister:String,
                        @SerializedName("usr_nombre")val usr_nombreRegister:String)



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