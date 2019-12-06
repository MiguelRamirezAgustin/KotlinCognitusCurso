package com.congnituscurso.cognitusproyect.model

import com.congnituscurso.cognitusproyect.model.response.ResponseCheckIn
import com.google.gson.annotations.SerializedName

class CheckInResponse (@SerializedName("valido")var validoCheck:String,
                       @SerializedName("registro_ck")var registro_ck:ResponseCheckIn,
                       @SerializedName("mensaje")var mensajeCheck:String)

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