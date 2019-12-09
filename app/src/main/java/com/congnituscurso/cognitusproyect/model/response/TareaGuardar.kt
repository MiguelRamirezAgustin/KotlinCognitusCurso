package com.congnituscurso.cognitusproyect.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TareaGuardar {
                    @SerializedName("tarea_subtit") @Expose val tarea_subtit: String?=null
                    @SerializedName("usrtar_nota") @Expose val usrtar_nota: String?=null
                    @SerializedName("usrtar_horas") @Expose val usrtar_horas: String?=null
                    @SerializedName("tarea_titulo") @Expose val tarea_titulo: String?=null
                    @SerializedName("usrtar_id") @Expose val usrtar_id: String?=null
                    @SerializedName("tarea_hrs") @Expose val tarea_hrs: String?=null
                    @SerializedName("usrtar_fin") @Expose val usrtar_fin: String?=null
                    @SerializedName("tarea_desc") @Expose val tarea_desc: String?=null
                    @SerializedName("usrtar_status") @Expose val usrtar_status: String?=null
                }


/*Response Notas
* {
    "tarea": {
        "tarea_subtit": "Make up",
        "usrtar_nota": " - primera nota",
        "usrtar_horas": "1",
        "tarea_titulo": "Navegación de App Cognitus",
        "usrtar_id": "58",
        "tarea_hrs": "24",
        "usrtar_fin": "1",
        "tarea_desc": "Realizar el modelado de las pantallas y navegación de la aplicación V2 para el cusro de android en Cognitus ",
        "usrtar_status": "2"
    },
    "valido": "1",
    "mensaje": "Se actualizo correctamente"
}
* */