package com.congnituscurso.cognitusproyect.model.response

import com.google.gson.annotations.SerializedName

class Respuestas (@SerializedName("cresp_desc")val cresp_desc:String,
                  @SerializedName("cresp_id")val cresp_id:String)