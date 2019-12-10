package com.congnituscurso.cognitusproyect.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.congnituscurso.cognitusproyect.R
import com.congnituscurso.cognitusproyect.adapter.EncuestaAdapter
import com.congnituscurso.cognitusproyect.dao.APIService
import com.congnituscurso.cognitusproyect.databinding.ActivityEncuestaBinding
import com.congnituscurso.cognitusproyect.databinding.ActivityNotificacionBinding
import com.congnituscurso.cognitusproyect.model.EncuestaResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EncuestaActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityEncuestaBinding>(this, R.layout.activity_encuesta)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_encuesta)

        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setTitle("Encuesta")
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
        getEncuesta()
    }

    private fun getEncuesta(){
        doAsync {
            val base: String = "600"
            Log.d("TAG", "base64DatosEncuesta " + base)
            val encodebase = Base64.encodeToString(base.toByteArray(), Base64.DEFAULT)
            Log.i("TAG", "Base64ConvetidoEncuesta  " + encodebase)
            val call =  encuestaRetrofit().create(APIService::class.java).encuesta(encodebase).execute()
            val result = call.body() as EncuestaResponse
            Log.d("TAG", "Result encuestas "+result.valido)
            uiThread {
                if (result.valido =="1"){
                    linearLayoutManager = LinearLayoutManager(this@EncuestaActivity)
                    binding.rvEncuesta.layoutManager = linearLayoutManager
                    binding.rvEncuesta.adapter = EncuestaAdapter(result.encuesta)

                }else if (result.valido =="2"){
                    val alerDialog = AlertDialog.Builder(this@EncuestaActivity)
                    alerDialog.setTitle("Alerta")
                    alerDialog.setMessage(""+result.mensaje)
                    alerDialog.setPositiveButton("Si"){dialog, which ->

                    }
                    val dialog = alerDialog.create()
                    dialog.show()
                }
            }
        }
    }

    fun encuestaRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getString(R.string.urlBase))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
