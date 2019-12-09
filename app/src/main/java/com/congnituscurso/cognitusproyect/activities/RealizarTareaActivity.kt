package com.congnituscurso.cognitusproyect.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.congnituscurso.cognitusproyect.R
import com.congnituscurso.cognitusproyect.dao.APIService
import com.congnituscurso.cognitusproyect.databinding.ActivityRealizarTareaBinding
import com.congnituscurso.cognitusproyect.databinding.ActivityTareasBinding
import com.congnituscurso.cognitusproyect.model.TareaFinalizarResponse
import com.congnituscurso.cognitusproyect.model.TareasGuardarResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RealizarTareaActivity : AppCompatActivity(), Validator.ValidationListener {

    var idTarea:String?= null
    var idUser:String?= null
    var status:Int= 0

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityRealizarTareaBinding>(this, R.layout.activity_realizar_tarea)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_realizar_tarea)


        //recupera id de sharedPreferences
        val sharedPreferences=getSharedPreferences("my_aplicacion_binding", Context.MODE_PRIVATE)
        val usrId = sharedPreferences.getString("usr_id","")
        idUser= usrId

        val tareaT = intent.getStringExtra("tareaT")
        val tareaS = intent.getStringExtra("tareaS")
        val tareaD = intent.getStringExtra("tareaD")
        val tareaH= intent.getStringExtra("tareaH")
        val tareaI = intent.getStringExtra("tareaI")
        val tareaF = intent.getStringExtra("tareaF")
        idTarea =tareaI

        if (tareaF == "0"){
            binding.btnGuardar.visibility = View.INVISIBLE
            binding.btnFinalizar.visibility = View.INVISIBLE
            Toast.makeText(this,"La tarea ya esta finalizada", Toast.LENGTH_SHORT).show()
        }

        binding.tvTituloTarea.setText(tareaT)
        binding.tvSubTituloTarea.setText(tareaS)
        binding.tVHorasRealizar.setText(tareaH)
        binding.tVTareaDescripcion.setText(tareaD)

        Log.d("TAG", "Tarea Realizar "+ tareaT+ " _subTitulo "+tareaS +" _Horas "+ tareaH +" _Des "+tareaD +" _id "+idTarea+" _idUser "+idUser )

        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.title="Tareas"
            actionBar.setDisplayShowHomeEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)
        //id_usuario|hora|checkInApp

        binding.setClickListener {
            when(it!!.id){
                binding.btnGuardar.id->{
                    validator!!.toValidate()
                    doAsync {
                        val idTarea= idTarea
                        val idUser = idUser
                        val horas= binding.eThora.text.toString()
                        val nota= binding.eTNota.text.toString()
                        Log.d("TAG", "Datos "+idTarea +" _ "+ idUser + " _ "+horas + " - "+nota)
                        val base:String= "501|"+idTarea+"|"+idUser+"|"+horas+"|"+nota
                        Log.d("TAG", "Datos---- "+base)
                        val encodebase = Base64.encodeToString(base.toByteArray(), Base64.DEFAULT)
                        Log.d("TAG", "DatosEncodebase64---- "+encodebase)
                        val call = tareaRetrofit().create(APIService::class.java).tareaGuardar(encodebase)?.execute()
                        val result = call.body() as TareasGuardarResponse
                        Log.d("TAG", "Datos result---- "+result.valido)
                        uiThread {
                            if (result.valido =="1"){
                                val alerDialog = AlertDialog.Builder(this@RealizarTareaActivity)
                                alerDialog.setTitle("Alerta")
                                alerDialog.setMessage(""+result.mensaje)
                                alerDialog.setPositiveButton("Si"){dialog, which ->
                                    binding.eThora.setText("")
                                    binding.eTNota.setText("")
                                }
                                val dialog = alerDialog.create()
                                dialog.show()
                                Log.d("TAG", "Datos result tarea---- "+result.tarea.usrtar_fin)
                                if (result.tarea.usrtar_fin == "2"){
                                    binding.btnGuardar.visibility = View.INVISIBLE
                                }
                            }else if (result.valido == "0"){
                                val alerDialog = AlertDialog.Builder(this@RealizarTareaActivity)
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
                binding.btnFinalizar.id->{
                    validator!!.toValidate()
                    doAsync {
                        val idTarea= idTarea
                        val idUser = idUser
                        val horas= binding.eThora.text.toString()
                        val nota= binding.eTNota.text.toString()
                        Log.d("TAG", "Datos "+idTarea +" _ "+ idUser + " _ "+horas + " - "+nota)
                        val base:String= "502|"+idTarea+"|"+idUser+"|"+horas+"|"+nota
                        Log.d("TAG", "Datos---- "+base)
                        val encodebase = Base64.encodeToString(base.toByteArray(), Base64.DEFAULT)
                        Log.d("TAG", "DatosEncodebase64---- "+encodebase)
                        val call = tareaRetrofit().create(APIService::class.java).tareaFinalizar(encodebase)?.execute()
                        val result = call.body() as TareaFinalizarResponse
                        Log.d("TAG", "Datos result finalizar---- "+result.valido)
                        uiThread {
                            if (result.valido =="1"){
                                val alerDialog = AlertDialog.Builder(this@RealizarTareaActivity)
                                alerDialog.setTitle("Alerta")
                                alerDialog.setMessage(""+result.mensaje)
                                alerDialog.setPositiveButton("Si"){dialog, which ->

                                    binding.btnGuardar.visibility = View.INVISIBLE
                                    binding.btnFinalizar.visibility = View.INVISIBLE
                                    binding.eThora.setText("")
                                    binding.eTNota.setText("")
                                }
                                val dialog = alerDialog.create()
                                dialog.show()

                            }else if (result.valido == "0"){
                                val alerDialog = AlertDialog.Builder(this@RealizarTareaActivity)
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
            }
        }

    }

    override fun onValidationError() {

    }

    override fun onValidationSuccess() {
    }


    fun tareaRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getString(R.string.urlBase))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}
