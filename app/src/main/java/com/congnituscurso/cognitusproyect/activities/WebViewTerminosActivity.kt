package com.congnituscurso.cognitusproyect.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil.setContentView
import com.congnituscurso.cognitusproyect.R
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import java.io.File


class WebViewTerminosActivity : AppCompatActivity() {

    var mywebview: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.congnituscurso.cognitusproyect.R.layout.activity_web_view_terminos)

        mywebview = findViewById<WebView>(com.congnituscurso.cognitusproyect.R.id.webview)

        val actionBar = supportActionBar
        if (actionBar !=  null){
            actionBar.title= "Terminos y condiciones"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)

        }

        var paramUrl:String = intent.getStringExtra("urlTerminos")
        Log.d("TAG", " paramUrl "+ paramUrl)

        mywebview!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        val myPdfUrl = paramUrl
        val url = "http://35.155.161.8:8080/WSExample/$myPdfUrl"
        Log.d("TAG", " url "+ url)
        val file = File(url)
        mywebview!!.loadUrl(url)
        //loadUrl(file.toString())

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
