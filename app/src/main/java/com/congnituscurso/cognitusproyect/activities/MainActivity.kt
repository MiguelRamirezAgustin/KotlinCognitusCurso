package com.congnituscurso.cognitusproyect.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.congnituscurso.cognitusproyect.R
import com.congnituscurso.cognitusproyect.dao.APIService
import com.congnituscurso.cognitusproyect.databinding.ActivityMainBinding
import com.congnituscurso.cognitusproyect.model.UsuarioResponse
import org.jetbrains.anko.doAsync
import retrofit2.Retrofit
import android.util.Base64
import androidx.appcompat.app.AlertDialog
import org.jetbrains.anko.uiThread
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), Validator.ValidationListener {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        /* binding.tVRegistrar.setOnClickListener {
             Toast.makeText(this, "Texto ", Toast.LENGTH_LONG).show()
         }*/


        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)

        binding.setClickListener {
            when (it!!.id) {
                binding.btnIniciar.id -> {
                    validator.toValidate()
                    doAsync {
                        val em = binding.eTemail.text.toString()
                        val ps = binding.eTpassword.text.toString()
                        // Log.i("TAG", "Parama--  " + em + " " + ps)
                        val testValue: String = "100|" + em + "|" + ps
                        //Log.i("TAG", "Parama--base  " + testValue)
                        var encodeLogin = Base64.encodeToString(testValue.toByteArray(), Base64.DEFAULT)
                        //Log.i("TAG", "base64  " + encodeLogin)
                        val call =loginRetrofit().create(APIService::class.java).loginUser(encodeLogin).execute()
                        val result = call.body() as UsuarioResponse
                        Log.i("TAG", "Result login " + result.validoLogin)
                        uiThread {
                            if (result.validoLogin == "1") {
                                val builder = AlertDialog.Builder(this@MainActivity)
                                builder.setTitle("Alerta")
                                builder.setMessage("" + result.mensajeLogin)
                                builder.setPositiveButton("Si") { dialog, which ->

                                    val usrIds = result.usuarioLogin.cli_idLogin
                                    val usrNombre = result.usuarioLogin.emp_idLogin

                                    Log.d("UserPreferenses ", usrIds + " " + usrNombre)
                                    //Guardar datos de sesion
                                    val sharedPreferences = getSharedPreferences("my_aplicacion_binding",Context.MODE_PRIVATE)
                                    var editor = sharedPreferences.edit()
                                    editor.putString("usr_id", usrIds)
                                    editor.putString("usr_name", usrNombre)
                                    editor.commit()

                                    val intent =
                                        Intent(applicationContext, MenuActivity::class.java)
                                    intent.putExtra("usr_id", usrIds)
                                    intent.putExtra("usr_nombre", usrNombre)
                                    startActivity(intent)

                                }
                                val dialog: AlertDialog = builder.create()
                                dialog.show()

                            } else if (result.validoLogin == "0") {
                                val builder = AlertDialog.Builder(this@MainActivity)
                                builder.setTitle("Alerta")
                                builder.setMessage("" + result.mensajeLogin)
                                builder.setPositiveButton("Si") { dialog, which ->
                                }
                                val dialog: AlertDialog = builder.create()
                                dialog.show()
                            }
                        }
                    }
                }
                binding.imgMostrarPaswword.id -> {
                    if (binding.eTpassword.transformationMethod is PasswordTransformationMethod) {
                        binding.eTpassword.transformationMethod = null
                    } else {
                        binding.eTpassword.transformationMethod = PasswordTransformationMethod()
                    }
                    binding.eTpassword.setSelection(binding.eTpassword.length())
                }
                binding.tVRegistrar.id -> {
                    val intent = Intent(this@MainActivity, RegistroActivity::class.java)
                    startActivity(intent)
                }
                binding.tVOlvidoPasssword.id -> {
                    val intent = Intent(this@MainActivity, OlvidePasswordActivity::class.java)
                    startActivity(intent)
                }
            }
        }


    }

    override fun onValidationSuccess() {

        //val intent = Intent(this, MenuActivity::class.java)
        //startActivity(intent)
    }

    override fun onValidationError() {
        Toast.makeText(this@MainActivity, "Dados inválidos!", Toast.LENGTH_SHORT).show()
    }

    private fun loginRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getString(R.string.urlBase))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
