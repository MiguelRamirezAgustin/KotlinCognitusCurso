package com.congnituscurso.cognitusproyect.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.congnituscurso.cognitusproyect.R
import com.congnituscurso.cognitusproyect.databinding.ActivityRegistroBinding

class RegistroActivity : AppCompatActivity(), Validator.ValidationListener  {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityRegistroBinding>(this,
            R.layout.activity_registro
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

        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)
        binding.setClickListener {
            when(it!!.id){
                binding.imgMostrarPaswword.id->{
                    if (binding.eTpassword.transformationMethod is PasswordTransformationMethod){
                        binding.eTpassword.transformationMethod = null
                    }else{
                        binding.eTpassword.transformationMethod = PasswordTransformationMethod()
                    }
                    binding.eTpassword.setSelection(binding.eTpassword.length())
                }
                binding.imgMostrarPaswwordConfir.id->{
                    if (binding.eTpasswordConfir.transformationMethod is PasswordTransformationMethod){
                        binding.eTpasswordConfir.transformationMethod = null
                    }else{
                        binding.eTpasswordConfir.transformationMethod = PasswordTransformationMethod()
                    }
                    binding.eTpasswordConfir.setSelection(binding.eTpasswordConfir.length())
                }
                binding.btnRegistrar.id->{
                    if ( validator!!.toValidate() != null){
                        if (binding.eTpassword.text.toString().equals(binding.eTpasswordConfir.text.toString())){
                            Toast.makeText(this, "Las contraseñas no son diferentes", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this, "Todo bien ", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onValidationSuccess() {
        //Toast.makeText(this,"Todo ok ", Toast.LENGTH_LONG).show()
    }

    override fun onValidationError() {
        Toast.makeText(this@RegistroActivity, "Dados inválidos!", Toast.LENGTH_SHORT).show()
    }
}
