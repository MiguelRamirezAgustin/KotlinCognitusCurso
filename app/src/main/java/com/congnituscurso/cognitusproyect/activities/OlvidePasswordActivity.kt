package com.congnituscurso.cognitusproyect.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.congnituscurso.cognitusproyect.R
import com.congnituscurso.cognitusproyect.dao.APIService
import com.congnituscurso.cognitusproyect.databinding.ActivityOlvidePasswordBinding
import com.congnituscurso.cognitusproyect.model.PasswordResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OlvidePasswordActivity : AppCompatActivity(), Validator.ValidationListener {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityOlvidePasswordBinding>(this,
            R.layout.activity_olvide_password
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       //setContentView(R.layout.activity_olvide_password)

        //show actionbar
        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.title="Olvide contraseña"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        //binding
        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)
        binding.setClickListener {
            when(it!!.id){
                binding.btnRecuperar.id->{
                    if (validator!!.toValidate() !=  null){
                        doAsync {
                            val email = binding.eTemail.text.toString()
                            val base = "102|" +email
                            Log.i("TAG", "base "+base)
                            val encodebase = Base64.encodeToString(base.toByteArray(), Base64.DEFAULT)
                            Log.i("TAG", "Base generada - "+encodebase)
                            val call = passwordRetrofit().create(APIService::class.java).password(encodebase).execute()
                            val result = call.body() as PasswordResponse
                            Log.i("TAG", "Resul password "+result.validoPassword)
                            uiThread {
                                if (result.validoPassword == "1"){
                                    val alerDialog = AlertDialog.Builder(this@OlvidePasswordActivity)
                                    alerDialog.setTitle("Alerta")
                                    alerDialog.setMessage(""+result.mensajePassword)
                                    alerDialog.setPositiveButton("Si"){dialog, which ->
                                        binding.eTemail.setText("")
                                    }
                                    val dialog = alerDialog.create()
                                    dialog.show()
                                }else if(result.validoPassword == "0"){
                                    val alerDialog = AlertDialog.Builder(this@OlvidePasswordActivity)
                                    alerDialog.setTitle("Alerta")
                                    alerDialog.setMessage(""+result.mensajePassword)
                                    alerDialog.setPositiveButton("Si"){dialog, which ->

                                    }
                                    val dialog = alerDialog.create()
                                    dialog.show()
                                }
                            }
                        }
                    }else{
                        onValidationError()
                    }
                }
            }
        }

    }

    fun passwordRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(getString(R.string.urlBase))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onValidationError() {
        Toast.makeText(this, "Dattos incorrectos ", Toast.LENGTH_LONG).show()
    }

    override fun onValidationSuccess() {

    }
}
