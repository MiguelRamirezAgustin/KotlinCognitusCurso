package com.congnituscurso.cognitusproyect.model.response

import com.google.gson.annotations.SerializedName

class ResponseLogin (@SerializedName("cli_id")var cli_idLogin:String,
                     @SerializedName("emp_id")var emp_idLogin:String)