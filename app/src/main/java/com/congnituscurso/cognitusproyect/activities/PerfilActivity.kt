package com.congnituscurso.cognitusproyect.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.congnituscurso.cognitusproyect.databinding.ActivityPerfilBinding
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.congnituscurso.cognitusproyect.R
import kotlinx.android.synthetic.main.activity_perfil.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class PerfilActivity : AppCompatActivity(), Validator.ValidationListener {

    private val TAKE_PHOTO_REQUEST = 101
    private val PERMISSION_CODE = 1001
    private val GALLERY = 1
    private val CAMERA = 2
    private var mediaPath: String? = null
    private var postPath: String? = null
    private val IMAGE_DIRECTORY = "/demosImg"

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityPerfilBinding>(
            this,
            R.layout.activity_perfil
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_perfil)

        //show actionbar
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Perfil"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }

        //recupera id de sharedPreferences
        val sharedPreferences=getSharedPreferences("my_aplicacion_binding",Context.MODE_PRIVATE)
        val usrEmail = sharedPreferences.getString("usr_name","")

        binding.email.setText(usrEmail)

        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)
        binding.setClickListener {
            when (it!!.id) {
                binding.imgMostrarPaswwordActual.id -> {
                    if (binding.eTpasswordActual.transformationMethod is PasswordTransformationMethod) {
                        binding.eTpasswordActual.transformationMethod = null
                    } else {
                        binding.eTpasswordActual.transformationMethod =
                            PasswordTransformationMethod()
                    }
                    binding.eTpasswordActual.setSelection(binding.eTpasswordActual.length())
                }
                binding.imgMostrarPaswwordNuevo.id -> {
                    if (binding.eTpasswordNuevo.transformationMethod is PasswordTransformationMethod) {
                        binding.eTpasswordNuevo.transformationMethod = null
                    } else {
                        binding.eTpasswordNuevo.transformationMethod =
                            PasswordTransformationMethod()
                    }
                    binding.eTpasswordNuevo.setSelection(binding.eTpasswordNuevo.length())
                }
                binding.imgMostrarPaswwordConfir.id -> {
                    if (binding.eTpasswordConfir.transformationMethod is PasswordTransformationMethod) {
                        binding.eTpasswordConfir.transformationMethod = null
                    } else {
                        binding.eTpasswordConfir.transformationMethod =
                            PasswordTransformationMethod()
                    }
                    binding.eTpasswordConfir.setSelection(binding.eTpasswordConfir.length())
                }
                binding.imgPerfil.id -> {
                    //Toast.makeText(this, "Perfil ", Toast.LENGTH_LONG).show()
                    val alerDialog = AlertDialog.Builder(this)
                    alerDialog.setTitle(getString(R.string.txtAlerCamaraGaleria))
                    val dialoItem = arrayOf("Acceder a camara", "Acceder a galeria")
                    alerDialog.setItems(
                        dialoItem
                    ) { dialog, which ->
                        when (which) {
                            0 -> permisoCamara()
                            1 -> permisoGaleria()
                        }
                    }
                    alerDialog.show()
                }
                binding.btnGuardarCambios.id -> {
                    Toast.makeText(this, "Button ", Toast.LENGTH_LONG).show()
                    validator.toValidate()
                }

            }
        }
    }

    fun permisoCamara() {
        revisapermiso()
    }

    fun permisoGaleria() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED){
                //permission requerido
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                //show popup
                requestPermissions(permissions, PERMISSION_CODE)
            }
            else{
                //permission otorgados
                choosePhotoFromGallary()
            }
        }
        else{
            //system OS  < Marshmallow
            choosePhotoFromGallary()
        }
    }

    fun takePhotoFromCamara(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    fun choosePhotoFromGallary(){
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, GALLERY)
    }


    fun revisapermiso() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), TAKE_PHOTO_REQUEST
            )
            Log.i("TAG", "Permisos Pedir---")
        }else{
            //Ya tiene permisos
            takePhotoFromCamara()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY){
            if (data != null){
                val contentURI = data!!.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                try {
                    val cursor = contentResolver.query(contentURI!!, filePathColumn, null, null, null)
                    assert(cursor != null)
                    cursor!!.moveToFirst()
                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    mediaPath = cursor.getString(columnIndex)
                    // Set the Image in ImageView for Previewing the Media
                    imgPerfil.setImageBitmap(BitmapFactory.decodeFile(mediaPath))
                    cursor.close()
                    cursor.close()
                    //postPath contiene la ruta de la imagen
                    postPath = mediaPath
                }catch (e:IOException){
                    e.printStackTrace()
                    Toast.makeText(this@PerfilActivity, "Fallo", Toast.LENGTH_SHORT).show()
                }
            }
        }else if( requestCode == CAMERA){
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            imgPerfil.setImageBitmap(thumbnail)
            saveImage(thumbnail)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            TAKE_PHOTO_REQUEST -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhotoFromCamara()
            }else{
                Log.i("TAG", "No dio permiso")
            }
        }
    }


    //Save imagen
    fun saveImage(myButmap:Bitmap):String{
        val bytes = ByteArrayOutputStream()
        myButmap.compress(Bitmap.CompressFormat.JPEG, 90 , bytes)
        val wallpaperDirectory = File((Environment.getExternalStorageDirectory().toString() + IMAGE_DIRECTORY)
        )

        //buil the directory structure
        Log.d("file", wallpaperDirectory.toString())
        if(!wallpaperDirectory.exists()){
            wallpaperDirectory.mkdir()
        }
        try {
            val f = File(
                wallpaperDirectory, ((Calendar.getInstance()
                    .getTimeInMillis()).toString() + ".jpg")
            )
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(
                this,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null
            )
            fo.close()
            postPath = f.getAbsolutePath()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())
        }catch (e: IOException){
            e.printStackTrace()
        }
        return  ""
    }


    override fun onValidationError() {
        Toast.makeText(this, "Dattos incorrectos ", Toast.LENGTH_LONG).show()
    }

    override fun onValidationSuccess() {
        Toast.makeText(this, "Todo ok ", Toast.LENGTH_LONG).show()
    }
}
