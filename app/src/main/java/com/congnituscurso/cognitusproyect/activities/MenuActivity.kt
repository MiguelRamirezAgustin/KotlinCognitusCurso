package com.congnituscurso.cognitusproyect.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.congnituscurso.cognitusproyect.R
import com.congnituscurso.cognitusproyect.databinding.ActivityMenuBinding
import com.congnituscurso.cognitusproyect.model.DemoCrash
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class MenuActivity : AppCompatActivity(), Validator.ValidationListener  {

    var idUsr:String? = null

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
                      val shareActionProvider = getSharedPreferences("my_aplicacion_binding", Context.MODE_PRIVATE)
                      var editor = shareActionProvider.edit()
                      editor.putString("usr_id", "")
                      editor.commit()
                      val intent = Intent(this, MainActivity::class.java)
                      startActivity(intent)
                      finish()
                  }
                  val dialog = alertBuiler.create()
                  dialog.show()
              }
              binding.reLcheckIn.id -> {
                  //Toast.makeText(this, "Check in", Toast.LENGTH_SHORT).show()
                  val inteten = Intent(this, CheckInActivity::class.java)
                  inteten.putExtra("ID",idUsr)
                  startActivity(inteten)
              }
              binding.reLNotificaciones.id -> {
                  //Toast.makeText(this, "Notificacion", Toast.LENGTH_SHORT).show()
                  val intent = Intent(this, NotificacionActivity::class.java)
                  startActivity(intent)
              }
              binding.reLPerfil.id -> {
                  //Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
                  val inteten = Intent(this, PerfilActivity::class.java)
                  startActivity(inteten)
              }
              binding.reLEncuesta.id -> {
                  val inteten = Intent(this, EncuestaActivity::class.java)
                  startActivity(inteten)
              }
              binding.reLTareas.id -> {
                  //Toast.makeText(this, "Tareas", Toast.LENGTH_SHORT).show()
                  val inatent = Intent(this, TareasActivity::class.java)
                  startActivity(inatent)
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

    private fun setKeysBasic(key: String) {
        // [START crash_set_keys_basic]
        Crashlytics.setString(key, "foo" /* string value */)

        Crashlytics.setBool(key, true /* boolean value */)

        Crashlytics.setDouble(key, 1.0 /* double value */)

        Crashlytics.setFloat(key, 1.0f /* float value */)

        Crashlytics.setInt(key, 1 /* int value */)
        // [END crash_set_keys_basic]
    }

    private fun resetKey() {
        // [START crash_re_set_key]
        Crashlytics.setInt("current_level", 3)
        Crashlytics.setString("last_UI_action", "logged_in")
        // [END crash_re_set_key]
    }

    private fun logReportAndPrint() {
        // [START crash_log_report_and_print]
        Crashlytics.log(Log.DEBUG, "tag", "message")
        // [END crash_log_report_and_print]
    }

    private fun logReportOnly() {
        // [START crash_log_report_only]
        Crashlytics.log("message")
        // [END crash_log_report_only]
    }

    private fun enableAtRuntime() {
        // [START crash_enable_at_runtime]
        Fabric.with(this, Crashlytics())
        // [END crash_enable_at_runtime]
    }

    private fun setUserId() {
        // [START crash_set_user_id]
        Crashlytics.setUserIdentifier("user123456789")
        // [END crash_set_user_id]
    }

    @Throws(Exception::class)
    private fun methodThatThrows() {
        throw Exception()
    }

    private fun logCaughtEx() {
        // [START crash_log_caught_ex]
        try {
            methodThatThrows()
        } catch (e: Exception) {
            Crashlytics.logException(e)
            // handle your exception here
        }
        // [END crash_log_caught_ex]
    }

    private fun enableDebugMode() {
        // [START crash_enable_debug_mode]
        val fabric = Fabric.Builder(this)
            .kits(Crashlytics())
            .debuggable(true) // Enables Crashlytics debugger
            .build()
        Fabric.with(fabric)
        // [END crash_enable_debug_mode]
    }

    private fun forceACrash() {
        // [START crash_force_crash]
        val crashButton = Button(this)
        crashButton.text = "Crash!"
        crashButton.setOnClickListener {
            Crashlytics.getInstance().crash() // Force a crash
        }

        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))
        // [END crash_force_crash]
    }

}
