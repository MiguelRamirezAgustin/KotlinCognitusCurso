package com.congnituscurso.cognitusproyect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.congnituscurso.cognitusproyect.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Validator.ValidationListener {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        binding.tVRegistrar.setOnClickListener {
            Toast.makeText(this, "Texto ", Toast.LENGTH_LONG).show()
        }


        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)

        binding.setClickListener {
            when(it!!.id){
                binding.btnIniciar.id ->{
                    validator.toValidate()
                }
                binding.imgMostrarPaswword.id -> {
                    if (binding.eTpassword.transformationMethod is PasswordTransformationMethod){
                        binding.eTpassword.transformationMethod = null
                    }else{
                        binding.eTpassword.transformationMethod = PasswordTransformationMethod()
                    }
                    binding.eTpassword.setSelection(binding.eTpassword.length())
                }
                binding.tVRegistrar.id -> {
                    val intent = Intent(this@MainActivity, RegistroActivity::class.java)
                    startActivity(intent)
                }
            }
        }


    }
    override fun onValidationSuccess() {
        Toast.makeText(this, "Todo ok ", Toast.LENGTH_LONG).show()
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }

    override fun onValidationError() {
        Toast.makeText(this@MainActivity, "Dados inválidos!", Toast.LENGTH_SHORT).show()
    }
}
