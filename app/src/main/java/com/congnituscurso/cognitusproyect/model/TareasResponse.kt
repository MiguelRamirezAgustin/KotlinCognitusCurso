package com.congnituscurso.cognitusproyect.model

import com.congnituscurso.cognitusproyect.model.response.Tarea
import com.google.gson.annotations.SerializedName

class TareasResponse (@SerializedName("valido")val valido:String,
                      @SerializedName("mensaje")val mensaje:String,
                      @SerializedName("tareas")val tareas:ArrayList<Tarea>
                    )

/* response tareas
{
    "valido": "1",
    "mensaje": "Tareas",
    "tareas": [
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
    {
        "tarea_subtit": "Build apk",
        "usrtar_nota": "",
        "usrtar_horas": "",
        "tarea_titulo": "Generación de App",
        "usrtar_id": "56",
        "tarea_hrs": "1",
        "usrtar_fin": "1",
        "tarea_desc": "Colocar en un carpeta el apk generada para la versión final de la aplicación y el código fuente generado como evidencia de su creación... Código Feliz!!! ",
        "usrtar_status": "2"
    },
    {
        "tarea_subtit": "Retrofit",
        "usrtar_nota": "",
        "usrtar_horas": "",
        "tarea_titulo": "Implementación de WS",
        "usrtar_id": "57",
        "tarea_hrs": "16",
        "usrtar_fin": "1",
        "tarea_desc": "Realizar el consumo de los servicios para cada una de las pantallas del aplicación Recursos Humanos para el cusro de android en Cognitus ",
        "usrtar_status": "1"
    }
    ]
}*/