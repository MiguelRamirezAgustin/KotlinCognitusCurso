package com.congnituscurso.cognitusproyect.model

import com.google.gson.annotations.SerializedName

class TerminosResponse (@SerializedName("url_pdf")var url_pdf:String,
                        @SerializedName("valido")var valido: String,
                        @SerializedName("mensaje")var mensaje:String)

/*  response
{
    "url_pdf": "media/termycond/terminos_condiciones_uso.pdf",
    "valido": "1",
    "mensaje": "revise url"
}*/