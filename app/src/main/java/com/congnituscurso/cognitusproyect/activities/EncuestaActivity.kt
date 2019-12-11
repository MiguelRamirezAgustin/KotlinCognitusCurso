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
import br.com.ilhasoft.support.validation.Validator
import com.congnituscurso.cognitusproyect.R
import com.congnituscurso.cognitusproyect.adapter.EncuestaAdapter
import com.congnituscurso.cognitusproyect.dao.APIService
import com.congnituscurso.cognitusproyect.databinding.ActivityEncuestaBinding
import com.congnituscurso.cognitusproyect.databinding.ActivityNotificacionBinding
import com.congnituscurso.cognitusproyect.model.EncuestaEnviarResponse
import com.congnituscurso.cognitusproyect.model.EncuestaResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EncuestaActivity : AppCompatActivity(), Validator.ValidationListener {

    private lateinit var linearLayoutManager: LinearLayoutManager

    var idUsr: String? = null

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityEncuestaBinding>(this, R.layout.activity_encuesta)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_encuesta)

        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)

        //recupera id de sharedPreferences
        val sharedPreferences = getSharedPreferences("my_aplicacion_binding", Context.MODE_PRIVATE)
        val usrId = sharedPreferences.getString("usr_id", "")
        idUsr = usrId
        Log.i("TAG", "id user " + "->${idUsr}")

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setTitle("Encuesta")
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
        getEncuesta(idUsr.toString())

        binding.setClickListener {
            when(it!!.id){
                binding.btnSiguiente.id -> {
                    validator!!.validate()
                    doAsync {
                        //recupera id de sharedPreferences
                        val sharedPreferences =
                            getSharedPreferences("my_aplicacion_binding", Context.MODE_PRIVATE)
                        val pre_1 = sharedPreferences.getString("pregunta1", "")
                        val pre_2 = sharedPreferences.getString("pregunta2", "")
                        val pre_3 = sharedPreferences.getString("pregunta3", "")
                        val pre_4_1 = sharedPreferences.getString("pregunta4_1", "")
                        val pre_4_2 = sharedPreferences.getString("pregunta4_2", "")
                        val pre_4_3 = sharedPreferences.getString("pregunta4_3", "")
                        val pre_4_4 = sharedPreferences.getString("pregunta4_4", "")

                        val param =
                            pre_1 + "|" + pre_2 + "|" + pre_3 + "|" + pre_4_1 + "|" + pre_4_2 + "|" + pre_4_3 + "|" + pre_4_4
                        Log.d("TAG", "ParamEnviar " + param)

                        val call =
                            encuestaRetrofit().create(APIService::class.java).encuestaEnviar(param)
                                .execute()
                        val result = call.body() as EncuestaEnviarResponse
                        Log.d("TAG", "Result encuesta " + result.valido)
                        uiThread {
                            if (result.valido == "1") {
                                val alerDialog =
                                    android.app.AlertDialog.Builder(this@EncuestaActivity)
                                alerDialog.setTitle("Alerta")
                                alerDialog.setMessage(""+ result.mensaje)
                                alerDialog.setPositiveButton("Si") { dialog, which ->

                                }
                                val dialog = alerDialog.create()
                                dialog.show()
                            }
                        }
                    }
                }
            }
        }

    }

    private fun getEncuesta(idUsr: String) {
        doAsync {
            val base: String = "600"
            val encodebase = Base64.encodeToString(base.toByteArray(), Base64.DEFAULT)
            Log.i("TAG", "Base64ConvetidoEncuesta  " + encodebase)
            val call =
                encuestaRetrofit().create(APIService::class.java).encuesta(encodebase).execute()
            val result = call.body() as EncuestaResponse
            Log.d("TAG", "Result encuestas " + result.valido)
            uiThread {
                if (result.valido == "1") {
                    linearLayoutManager = LinearLayoutManager(this@EncuestaActivity)
                    binding.rvEncuesta.layoutManager = linearLayoutManager
                    binding.rvEncuesta.adapter = EncuestaAdapter(result.encuesta, idUsr)

                } else if (result.valido == "2") {
                    val alerDialog = AlertDialog.Builder(this@EncuestaActivity)
                    alerDialog.setTitle("Alerta")
                    alerDialog.setMessage("" + result.mensaje)
                    alerDialog.setPositiveButton("Si") { dialog, which ->

                    }
                    val dialog = alerDialog.create()
                    dialog.show()
                }
            }
        }
    }

    override fun onValidationError() {
    }

    override fun onValidationSuccess() {

    }

    fun encuestaRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getString(R.string.urlBase))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
