package com.congnituscurso.cognitusproyect.model.response

import com.google.gson.annotations.SerializedName

class ResponseLogin (@SerializedName("usr_rutafoto")var usr_rutafotoLogin:String,
                     @SerializedName("usr_email")var usr_emailLogin:String,
                     @SerializedName("usr_id")var usr_idLogin:String,
                     @SerializedName("usr_nombre")var usr_nombreLogin:String)




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