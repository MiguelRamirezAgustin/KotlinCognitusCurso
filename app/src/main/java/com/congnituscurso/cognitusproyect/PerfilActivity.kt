package com.congnituscurso.cognitusproyect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import com.congnituscurso.cognitusproyect.databinding.ActivityPerfilBinding
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator

class PerfilActivity : AppCompatActivity(), Validator.ValidationListener {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityPerfilBinding>(this, R.layout.activity_perfil)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_perfil)

        //show actionbar
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.title = "Perfil"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }

        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)
        binding.setClickListener {
            when(it!!.id){
                binding.imgMostrarPaswwordActual.id->{
                    if (binding.eTpasswordActual.transformationMethod is PasswordTransformationMethod){
                        binding.eTpasswordActual.transformationMethod = null
                    }else{
                        binding.eTpasswordActual.transformationMethod = PasswordTransformationMethod()
                    }
                    binding.eTpasswordActual.setSelection(binding.eTpasswordActual.length())
                }
                binding.imgMostrarPaswwordNuevo.id->{
                    if (binding.eTpasswordNuevo.transformationMethod is PasswordTransformationMethod){
                        binding.eTpasswordNuevo.transformationMethod = null
                    }else{
                        binding.eTpasswordNuevo.transformationMethod = PasswordTransformationMethod()
                    }
                    binding.eTpasswordNuevo.setSelection(binding.eTpasswordNuevo.length())
                }
                binding.imgMostrarPaswwordConfir.id->{
                    if(binding.eTpasswordConfir.transformationMethod is PasswordTransformationMethod){
                        binding.eTpasswordConfir.transformationMethod = null
                    }else{
                        binding.eTpasswordConfir.transformationMethod = PasswordTransformationMethod()
                    }
                    binding.eTpasswordConfir.setSelection(binding.eTpasswordConfir.length())
                }
                binding.imgPerfil.id->{
                    Toast.makeText(this, "Perfil ", Toast.LENGTH_LONG).show()
                }
                binding.btnGuardarCambios.id->{
                    Toast.makeText(this, "Button ", Toast.LENGTH_LONG).show()
                    validator.toValidate()
                }

            }
        }
    }

    override fun onValidationError() {
        Toast.makeText(this, "Dattos incorrectos ", Toast.LENGTH_LONG).show()
    }

    override fun onValidationSuccess() {
        Toast.makeText(this, "Todo ok ", Toast.LENGTH_LONG).show()
    }
}
