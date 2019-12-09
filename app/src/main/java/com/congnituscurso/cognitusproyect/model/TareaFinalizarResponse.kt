package com.congnituscurso.cognitusproyect.model

import com.google.gson.annotations.SerializedName

class TareaFinalizarResponse (@SerializedName("valido")val valido:String,
                              @SerializedName("mensaje")val mensaje:String)

/*Response
* {
    "valido": "1",
    "mensaje": "finalizada"
}
* */