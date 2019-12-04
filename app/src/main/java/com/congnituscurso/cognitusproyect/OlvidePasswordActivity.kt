package com.congnituscurso.cognitusproyect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.congnituscurso.cognitusproyect.databinding.ActivityOlvidePasswordBinding
import com.congnituscurso.cognitusproyect.databinding.ActivityRegistroBinding

class OlvidePasswordActivity : AppCompatActivity(), Validator.ValidationListener {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityOlvidePasswordBinding>(this, R.layout.activity_olvide_password)
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
                    validator!!.toValidate()
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
