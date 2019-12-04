package com.congnituscurso.cognitusproyect.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.congnituscurso.cognitusproyect.R
import com.congnituscurso.cognitusproyect.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity(), Validator.ValidationListener  {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMenuBinding>(this,
            R.layout.activity_menu
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_menu)
        supportActionBar?.hide()

        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)
        binding.setClickListener {
          when(it!!.id){
              binding.btnCerrarSesion.id->{
                  //Toast.makeText(this, "Cerrar sesion", Toast.LENGTH_SHORT).show()
                  val alertBuiler = AlertDialog.Builder(this)
                  alertBuiler.setTitle("Alerta")
                  alertBuiler.setMessage("Decea cerrar sesión? ")
                  alertBuiler.setNegativeButton("No"){dialog, which ->

                  }
                  alertBuiler.setPositiveButton("Si"){dialog, which ->
                      val shareActionProvider= getSharedPreferences("my_aplicacion_binding",Context.MODE_PRIVATE)
                      val editor = shareActionProvider.edit()
                      editor.putString("usr_id","" )
                      editor.commit()
                      val intent = Intent(this, MainActivity::class.java)
                      startActivity(intent)
                  }
                  val dialog = alertBuiler.create()
                  dialog.show()
              }
              binding.reLcheckIn.id -> {
                  //Toast.makeText(this, "Check in", Toast.LENGTH_SHORT).show()
                  val inteten = Intent(this, CheckInActivity::class.java)
                  startActivity(inteten)
              }
              binding.reLNotificaciones.id -> {
                  Toast.makeText(this, "Notificacion", Toast.LENGTH_SHORT).show()
              }
              binding.reLPerfil.id -> {
                  //Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
                  val inteten = Intent(this, PerfilActivity::class.java)
                  startActivity(inteten)
              }
              binding.reLEncuesta.id -> {
                  Toast.makeText(this, "Encuesta", Toast.LENGTH_SHORT).show()
              }
              binding.reLTareas.id -> {
                  Toast.makeText(this, "Tareas", Toast.LENGTH_SHORT).show()
              }
          }

        }

    }

    override fun onValidationSuccess() {
        Toast.makeText(this,"Todo ok ", Toast.LENGTH_LONG).show()
    }

    override fun onValidationError() {
        Toast.makeText(this@MenuActivity, "Dados inválidos!", Toast.LENGTH_SHORT).show()
    }

}