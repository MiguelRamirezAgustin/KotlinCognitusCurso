package com.congnituscurso.cognitusproyect.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Base64
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.congnituscurso.cognitusproyect.databinding.ActivityRegistroBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.congnituscurso.cognitusproyect.R
import com.congnituscurso.cognitusproyect.dao.APIService
import com.congnituscurso.cognitusproyect.model.RegisterResponse
import com.congnituscurso.cognitusproyect.model.TerminosResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class RegistroActivity : AppCompatActivity(), Validator.ValidationListener {

    var statusCheck:Boolean?= false

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityRegistroBinding>(
            this, R.layout.activity_registro
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_registro)

        // show backbutton and set custom title on actionbar
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Registro"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }

        Log.d("TAG", "inicial check "+statusCheck )

        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)
        binding.setClickListener {
            when (it!!.id) {
                binding.imgMostrarPaswword.id -> {
                    if (binding.eTpassword.transformationMethod is PasswordTransformationMethod) {
                        binding.eTpassword.transformationMethod = null
                    } else {
                        binding.eTpassword.transformationMethod = PasswordTransformationMethod()
                    }
                    binding.eTpassword.setSelection(binding.eTpassword.length())
                }
                binding.imgMostrarPaswwordConfir.id -> {
                    if (binding.eTpasswordConfir.transformationMethod is PasswordTransformationMethod) {
                        binding.eTpasswordConfir.transformationMethod = null
                    } else {
                        binding.eTpasswordConfir.transformationMethod =
                            PasswordTransformationMethod()
                    }
                    binding.eTpasswordConfir.setSelection(binding.eTpasswordConfir.length())
                }
                binding.btnRegistrar.id -> {
                    validator!!.toValidate()
                }
                binding.checkTerminos.id->{
                    statusCheck = binding.checkTerminos.isChecked

                    if (statusCheck == true){
                        Log.d("TAG", "check "+statusCheck )
                        binding.tVLeerTerminos.setFocusable(false)

                    }else{
                        Log.d("TAG", "check "+statusCheck )
                        binding.tVLeerTerminos.setFocusable(true)
                    }
                }
                binding.tVLeerTerminos.id->{
                    doAsync {
                        val testValue: String = "99"
                        var encodeTermios = Base64.encodeToString(testValue.toByteArray(), Base64.DEFAULT)
                        val call = registroRetrofit().create(APIService::class.java).terminos(encodeTermios).execute()
                        val result = call.body() as TerminosResponse
                        Log.d("TAG","Resul "+ result.valido)
                        uiThread {
                            if (result.valido=="1"){
                                val urlTermios = result.url_pdf

                                val intent = Intent(this@RegistroActivity, WebViewTerminosActivity::class.java)
                                intent.putExtra("urlTerminos", urlTermios)
                                startActivity(intent)
                            }else if(result.valido =="0"){
                                val alerDialog = AlertDialog.Builder(this@RegistroActivity)
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



    fun registroRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getString(com.congnituscurso.cognitusproyect.R.string.urlBase))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onValidationSuccess() {
        if (binding.eTpassword.text.toString().equals(binding.eTpasswordConfir.text.toString())) {
            if (statusCheck != false){
            doAsync {
                val nombre = binding.eTname.text.toString()
                val correo = binding.eTemail.text.toString()
                val nip = binding.eTpassword.text.toString()
                //101|miguel|ramirez@gmail.com|12345
                val base: String = "101|" + nombre + "|" + correo + "|" + nip
                Log.d("TAG", base)
                val encodebase = Base64.encodeToString(base.toByteArray(), Base64.DEFAULT)
                Log.i("TAG", "Base param " + encodebase)

                val call =
                    registroRetrofit().create(APIService::class.java).registerUser(encodebase)
                        .execute()
                val result = call.body() as RegisterResponse
                Log.i("Result", result.validoRegister)
                uiThread {
                    if (result.validoRegister == "1") {
                        val alertDialog = AlertDialog.Builder(this@RegistroActivity)
                        alertDialog.setTitle("Alerta")
                        alertDialog.setMessage("" + result.mensajeRegister)
                        alertDialog.setPositiveButton("Si") { dialog, which ->

                            //variables para sharedPreferences
                            val usrIds = result.usuarioRegister.usu_idRegister
                            val usrNombre = result.usuarioRegister.usr_nombreRegister
                            val usrEmail = result.usuarioRegister.usr_emailRegister
                            val usrImg = result.usuarioRegister.usr_rutafotoRegister

                            Log.d("UserPreferenses ", usrIds + " - " + usrNombre + " " + usrEmail)

                            val sharedPreferences =
                                getSharedPreferences("my_aplicacion_binding", Context.MODE_PRIVATE)
                            var editor = sharedPreferences.edit()
                            editor.putString("usr_id", usrIds)
                            editor.putString("usr_name", usrNombre)
                            editor.putString("usr_email", usrEmail)
                            editor.putString("usr_Img", usrImg)
                            editor.commit()

                            val intent = Intent(this@RegistroActivity, MenuActivity::class.java)
                            startActivity(intent)
                        }
                        val dialog = alertDialog.create()
                        dialog.show()

                    } else if (result.validoRegister == "0") {
                        val alerDialog = AlertDialog.Builder(this@RegistroActivity)
                        alerDialog.setTitle("Alerta")
                        alerDialog.setMessage("" + result.mensajeRegister)
                        alerDialog.setPositiveButton("Si") { dialog, which ->

                        }
                        val dialog = alerDialog.create()
                        dialog.show()
                    }
                }
            }

            }else{
                Toast.makeText(this, "Es necesario aceptar los\n terminos y condiciones", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Las contraseñas son diferenes", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onValidationError() {
        //Toast.makeText(this@RegistroActivity, "Dados inválidos!", Toast.LENGTH_SHORT).show()
    }
}
