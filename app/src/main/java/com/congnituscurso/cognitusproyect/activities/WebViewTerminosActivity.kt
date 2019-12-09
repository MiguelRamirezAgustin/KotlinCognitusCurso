package com.congnituscurso.cognitusproyect.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil.setContentView
import com.congnituscurso.cognitusproyect.R

class WebViewTerminosActivity : AppCompatActivity() {

    lateinit var mywebview: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_terminos)

        mywebview = findViewById<WebView>(R.id.webview)
        mywebview.setWebViewClient(WebViewClient())
        mywebview.getSettings().setSupportZoom(true)
        mywebview.getSettings().setJavaScriptEnabled(true)

        val actionBar = supportActionBar
        if (actionBar !=  null){
            actionBar.title= "Terminos y condiciones"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)

        }

        var paramUrl:String = intent.getStringExtra("urlTerminos")
        Log.d("TAG", " paramUrl "+ paramUrl)


        val myPdfUrl = paramUrl
        val url = "http://35.155.161.8:8080/WSExample/$myPdfUrl"
        mywebview.getSettings().setJavaScriptEnabled(true)
        mywebview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=$url")

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
