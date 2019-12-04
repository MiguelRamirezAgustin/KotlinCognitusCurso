package com.congnituscurso.cognitusproyect.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
     val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
