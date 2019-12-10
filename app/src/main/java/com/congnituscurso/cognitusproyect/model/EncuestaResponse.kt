package com.congnituscurso.cognitusproyect.model

import com.congnituscurso.cognitusproyect.model.response.Encuesta
import com.google.gson.annotations.SerializedName

class EncuestaResponse (@SerializedName("encuesta")val encuesta:ArrayList<Encuesta>,
                        @SerializedName("valido")val valido:String,
                        @SerializedName("mensaje")val mensaje:String)



/*
* {
    "encuesta": [
        {
            "cpreg_id": "1",
            "cpreg_tipo": "1",
            "cpreg_titulo": "Describe la actividad que mas te gusto"
        },
        {
            "cpreg_id": "2",
            "cpreg_tipo": "2",
            "cpreg_titulo": "Como evaluarias el tiempo asignado a tus tareas"
        },
        {
            "cpreg_id": "3",
            "cpreg_tipo": "3",
            "cpreg_titulo": "El tiempo asigando para tus tareas fue adecuado"
        },
        {
            "respuestas": [
                {
                    "cresp_desc": "Inglés",
                    "cresp_id": "1"
                },
                {
                    "cresp_desc": "Italiano",
                    "cresp_id": "2"
                },
                {
                    "cresp_desc": "Frances",
                    "cresp_id": "3"
                },
                {
                    "cresp_desc": "Ninguno",
                    "cresp_id": "4"
                }
            ],
            "cpreg_id": "4",
            "cpreg_tipo": "4",
            "cpreg_titulo": "¿Qué otro idioma a parte de castellano hablas?"
        }
    ],
    "valido": "1",
    "mensaje": "preguntas encuesta"
}
* */