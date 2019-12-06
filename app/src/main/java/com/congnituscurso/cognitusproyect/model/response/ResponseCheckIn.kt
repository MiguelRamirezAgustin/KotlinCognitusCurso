package com.congnituscurso.cognitusproyect.model.response

import com.google.gson.annotations.SerializedName

class ResponseCheckIn (@SerializedName("ck_tiemporeal")var ck_tiemporeal:String,
                       @SerializedName("ck_firmaurl")var ck_firmaurl:String,
                       @SerializedName("ck_id")var ck_id:String,
                       @SerializedName("ck_hraenv")var ck_hraenv:String)



/*{    Response check in
    "valido": "1",
    "registro_ck": {
    "ck_tiemporeal": "2019-12-05 16:11:19",
    "ck_firmaurl": "media/firmas/4_20191205161119.jpg",
    "ck_id": "4",
    "ck_hraenv": "4:09:27 p.m."
},
    "mensaje": "exitoso"
}
*/