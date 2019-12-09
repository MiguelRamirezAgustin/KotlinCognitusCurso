package com.congnituscurso.cognitusproyect.model

import com.congnituscurso.cognitusproyect.model.response.Tarea
import com.congnituscurso.cognitusproyect.model.response.TareaGuardar
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TareasGuardarResponse (@SerializedName("tarea") val tarea:TareaGuardar,
                             @SerializedName("valido")val valido:String,
                             @SerializedName("mensaje")val mensaje:String)
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