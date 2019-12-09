package com.congnituscurso.cognitusproyect.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Tarea  {
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


/*
*
*  "tareas": [
    {
        "tarea_subtit": "Retrofit",
        "usrtar_nota": "",
        "usrtar_horas": "",
        "tarea_titulo": "Implementación de WS",
        "usrtar_id": "55",
        "tarea_hrs": "16",
        "usrtar_fin": "1",
        "tarea_desc": "Realizar el consumo de los servicios para cada una de las pantallas del aplicación Recursos Humanos para el cusro de android en Cognitus ",
        "usrtar_status": "1"
    },
* */