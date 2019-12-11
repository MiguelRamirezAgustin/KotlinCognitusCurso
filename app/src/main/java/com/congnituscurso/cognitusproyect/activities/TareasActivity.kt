package com.congnituscurso.cognitusproyect.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.congnituscurso.cognitusproyect.R
import com.congnituscurso.cognitusproyect.adapter.TareasAdapter
import com.congnituscurso.cognitusproyect.dao.APIService
import com.congnituscurso.cognitusproyect.databinding.ActivityTareasBinding
import com.congnituscurso.cognitusproyect.model.TareasResponse
import com.congnituscurso.cognitusproyect.model.response.Tarea
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TareasActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var noticias: List<Tarea>
    var encodeValue: String? = null
    var idUsr: String? = null


    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityTareasBinding>(this, R.layout.activity_tareas)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_tareas)


        //recupera id de sharedPreferences
        val sharedPreferences = getSharedPreferences("my_aplicacion_binding", Context.MODE_PRIVATE)
        val usrId = sharedPreferences.getString("usr_id", "")
        idUsr = usrId
        Log.i("TAG", "id user " + "->${idUsr}")

        val testValue: String = "500|" + idUsr
        encodeValue = Base64.encodeToString(testValue.toByteArray(), Base64.DEFAULT)
        Log.i("TAG", "Base64Tareas " + "->${encodeValue}")

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Tareas"
            actionBar.setDisplayShowHomeEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        getValueWS()
    }

    private fun getValueWS() {
        doAsync {
            val call =
                tareasRetrofit().create(APIService::class.java).tareasGet(encodeValue!!).execute()
            val message = call.body() as TareasResponse
            Log.d("TAG", "response tareas " + message.valido)
            uiThread {
                linearLayoutManager = LinearLayoutManager(applicationContext)
                binding.rvTareas.layoutManager = linearLayoutManager
                binding.rvTareas.adapter = TareasAdapter(message.tareas)
            }
        }
    }

    fun tareasRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getString(R.string.urlBase))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
