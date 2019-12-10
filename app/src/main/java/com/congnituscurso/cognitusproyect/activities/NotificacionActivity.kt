package com.congnituscurso.cognitusproyect.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.congnituscurso.cognitusproyect.R
import com.congnituscurso.cognitusproyect.adapter.NotificaionAdaper
import com.congnituscurso.cognitusproyect.dao.APIService
import com.congnituscurso.cognitusproyect.databinding.ActivityNotificacionBinding
import com.congnituscurso.cognitusproyect.databinding.ActivityTareasBinding
import com.congnituscurso.cognitusproyect.model.NotificacionResponse
import kotlinx.android.synthetic.main.item_notificacion.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotificacionActivity : AppCompatActivity() {

    var idUsr:String?= null
    private lateinit var linearLayoutManager: LinearLayoutManager


    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityNotificacionBinding>(this, R.layout.activity_notificacion)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_notificacion)

        //recupera id de sharedPreferences
        val sharedPreferences=getSharedPreferences("my_aplicacion_binding", Context.MODE_PRIVATE)
        val usrId = sharedPreferences.getString("usr_id","")

        idUsr = usrId
        Log.i("TAG","id user Notificaciones "+"->${idUsr}")

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.title="Notificaciones"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
        getnotificacion(idUsr.toString())


    }

    private fun getnotificacion(idUsr:String){
     doAsync {
         val testValue:String ="300|" + idUsr
         val encodeValue = Base64.encodeToString(testValue.toByteArray(), Base64.DEFAULT)
         Log.i("TAG","Base64Notificacion "+"->${encodeValue}")
         val call = notificaionRetrofit().create(APIService::class.java).notificacion(encodeValue)?.execute()
         val result = call.body() as NotificacionResponse
         uiThread {
             if (result.validoNotificacion =="1"){
                 linearLayoutManager = LinearLayoutManager(applicationContext)
                 binding.rvNotificacion.layoutManager = linearLayoutManager
                 binding.rvNotificacion.adapter = NotificaionAdaper(result.notificaciones, idUsr)
             }
             else if(result.validoNotificacion =="0"){
               val alerDialog = AlertDialog.Builder(applicationContext)
                 alerDialog.setTitle("Alerta")
                 alerDialog.setMessage(""+result.mensajenotificacion)
                 alerDialog.setPositiveButton("Si"){dialog, which ->

                 }
                 val dialog = alerDialog.create()
                 dialog.show()
             }
         }
     }
    }

    fun notificaionRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getString(R.string.urlBase))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
