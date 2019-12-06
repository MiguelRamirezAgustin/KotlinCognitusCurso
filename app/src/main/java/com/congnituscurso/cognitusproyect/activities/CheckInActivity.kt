package com.congnituscurso.cognitusproyect.activities

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.congnituscurso.cognitusproyect.R
import com.congnituscurso.cognitusproyect.dao.APIService
import com.congnituscurso.cognitusproyect.databinding.ActivityCheckInBinding
import com.congnituscurso.cognitusproyect.model.CheckInResponse
import com.github.gcacace.signaturepad.views.SignaturePad
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.text.DateFormat


class CheckInActivity : AppCompatActivity(), Validator.ValidationListener {
    private var mediaPath: String? = null
    private var postPath: String? = null
    var idUs :String?=null
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityCheckInBinding>(
            this, com.congnituscurso.cognitusproyect.R.layout.activity_check_in
        )
    }

    companion object{
        val TAG = "Permissos--"
        private const val  REQUEST_INTERNET = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_check_in)

        //show action bar
        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.title="Check In"
            actionBar.setDisplayShowHomeEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        //recuperar Id
        val idUser = intent.getStringExtra("ID")
        idUs = idUser
        Log.i("TAG", "Id User Check In "+idUs)

        //permisos
        revisarPermisos()

        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)
        //id_usuario|hora|checkInApp

        binding.setClickListener {
            when(it!!.id){
                binding.eTHoraEntrada.id->{
                    /*val dates = Date()
                    val dateFormats = android.text.format.DateFormat.getDateFormat(applicationContext)
                    Log.i("TAG", "Time: " + dateFormats.format(dates)*/

                    val date = Date()
                    val stringDate = DateFormat.getTimeInstance().format(date)
                    binding.eTHoraEntrada.setText(stringDate)

                }
                binding.imgBorrar.id->{
                    binding.signaturePad.clear()
                    binding.tVFirma.visibility = View.VISIBLE
                    postPath=""
                }
                binding.btnConfirmar.id->{
                        validator!!.toValidate()
                }

            }
        }

        binding.signaturePad.setOnSignedListener(object :SignaturePad.OnSignedListener{
            override fun onStartSigning() {
                /*cuando comienza a dibujar
                 Toast.makeText(applicationContext,"Comineza firma", Toast.LENGTH_SHORT).show()*/
                binding.tVFirma.visibility = View.INVISIBLE
                binding.imgBorrar.visibility = View.INVISIBLE
            }

            override fun onClear() {
                binding.imgBorrar.visibility = View.INVISIBLE
                //deshabilita
                //mSaveButton.isEnabled = false
                //mClearButton.isEnabled = false
            }

            override fun onSigned() {
                binding.imgBorrar.visibility = View.VISIBLE
                guardarFirma()
            }

        })
    }

    override fun onValidationError() {

    }

    override fun onValidationSuccess() {
        if (binding.signaturePad.isEmpty()){
            Toast.makeText(this@CheckInActivity,"Es necesario realizar la firma", Toast.LENGTH_LONG).show()
        }else{
            doAsync {
                val base:String= "200|"+idUs+"|"+binding.eTHoraEntrada.text.toString()+"|checkInApp"
                val encodebase = Base64.encodeToString(base.toByteArray(), Base64.DEFAULT)
                var photoFile: File? = null
                photoFile = File(postPath)

                val partes = ArrayList<MultipartBody.Part>()
                partes.add(MultipartBody.Part.createFormData("word", encodebase ))
                partes.add(MultipartBody.Part.createFormData("archivo", photoFile?.name, RequestBody.create(MediaType.parse("images/*"),photoFile)))

                val call = checkInRetrofit().create(APIService::class.java).checkIn(partes)?.execute()
                val result = call.body() as CheckInResponse
                Log.i("TAG", "ResulCheck--"+result.validoCheck)
                  uiThread {
                      if (result.validoCheck == "1"){
                          val alerDialog = AlertDialog.Builder(this@CheckInActivity)
                          alerDialog.setTitle("Alerta")
                          alerDialog.setMessage("Registro: "+result.mensajeCheck +"\n\n" + "Hora de registro: "+result.registro_ck.ck_tiemporeal)
                          alerDialog.setPositiveButton("Si"){dialog, which ->
                         
                              binding.eTHoraEntrada.setText("")
                              binding.signaturePad.clear()
                          }
                          val dialog = alerDialog.create()
                          dialog.show()
                      }else if(result.validoCheck =="0"){
                          val alerDialog = AlertDialog.Builder(this@CheckInActivity)
                          alerDialog.setTitle("Alerta")
                          alerDialog.setMessage(""+result.mensajeCheck)
                          alerDialog.setPositiveButton("Si"){dialog, which ->

                          }
                          val dialog = alerDialog.create()
                          dialog.show()
                      }
                  }

            }
        }
    }

    fun checkInRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(getString(R.string.urlBase))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun File.crearMultiparte(): RequestBody {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(this).toString())
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        Log.d("ImagenUtil", "uri: " + Uri.fromFile(this))
        Log.d("ImagenUtil", "type: " + type!!)
        return RequestBody.create(
            MediaType.parse("image/*"), this
        )
    }

    fun guardarFirma(){
        val signatureBitmap: Bitmap = binding.signaturePad.transparentSignatureBitmap
        if(addJPGSignatureToGalerry(signatureBitmap)){
            /*Toast.makeText(
                this,
                "Firma Guardada",
                Toast.LENGTH_SHORT
            ).show()*/
            //signaturePad.clear()
        }else{
            Toast.makeText(
                this,
                "No se puede almacenar la firma",
                Toast.LENGTH_SHORT
            ).show()
        }
        Log.d("TAG", "Ruta-- "+addJPGSignatureToGalerry(signatureBitmap))
    }

    //Guardar en galeria
    fun addJPGSignatureToGalerry(signature: Bitmap):Boolean{
        var result = false
        try {
            val path = Environment.getExternalStorageDirectory().toString() + "/empleado"
            Log.d("Files", "path $path")
            val fileFirm = File(path)
            fileFirm.mkdir()
            val photo =
                File(fileFirm, "Firma.png")
            Log.d("FilesFirma", "path $photo")
            postPath = photo.toString()
            Log.i("TAG", "post-----guardada----- "+postPath)
            saveBitmapToPNG(signature, photo)
            result = true
        }catch (e: IOException){
            e.printStackTrace()
        }
        return result
    }

    @Throws(IOException::class)
    fun saveBitmapToPNG(bitmap: Bitmap, photo:File){
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(photo)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            try {
                out?.close()
            }catch (e:IOException){
                e.printStackTrace()
            }
        }
    }



    //Permisos
    fun revisarPermisos(){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_INTERNET
            )
            Log.i(TAG, "Pide permiso")
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_INTERNET -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Si dio permiso")
            }else{
                Log.i(TAG, "No dio permiso")
                //Permisos necesarios
                revisarPermisos()
            }
        }
    }

}
