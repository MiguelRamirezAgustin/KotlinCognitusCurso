package com.congnituscurso.cognitusproyect.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.congnituscurso.cognitusproyect.R

class Splash : AppCompatActivity(), Runnable {

    private lateinit var handler:Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        simularCarga()
    }

    fun simularCarga(){
        handler = Handler()
        handler.postDelayed(this, 3000)
    }

    override fun run() {

        //validar si existe sesion
        val sharedPreferences= getSharedPreferences("my_aplicacion_binding",Context.MODE_PRIVATE)

        val usrId = sharedPreferences.getString("usr_id", "")
        Log.d("--->", usrId)

        if (!usrId.equals("")){
            // si existe sesion
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("nombre_usr", usrId)
            startActivity(intent)
            finish()
        }else{
            //No existe sesion
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

}
