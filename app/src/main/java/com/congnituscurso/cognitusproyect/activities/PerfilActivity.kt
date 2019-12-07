package com.congnituscurso.cognitusproyect.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.method.PasswordTransformationMethod
import android.util.Base64
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.congnituscurso.cognitusproyect.databinding.ActivityPerfilBinding
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.bumptech.glide.Glide
import com.congnituscurso.cognitusproyect.R
import com.congnituscurso.cognitusproyect.dao.APIService
import com.congnituscurso.cognitusproyect.model.PerfilResponse
import kotlinx.android.synthetic.main.activity_perfil.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    var idUser:String? = null

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
        val usrEmail = sharedPreferences.getString("usr_email","")
        val usrName = sharedPreferences.getString("usr_name","")
        val usrImg = sharedPreferences.getString("usr_Img","")
        idUser = sharedPreferences.getString("usr_id","")

        Log.i("TAG", "idUsuario--- "+ idUser +" correo "+usrEmail)

        binding.eTemail.setText(usrEmail)
        binding.tVName.setText(usrName)
        binding.eTnameUsuario.setText(usrName)
        val requesManager = Glide.with(this)
        val requestBuilder = requesManager.load(getString(R.string.urlBase)+usrImg)
        Log.i("TAG", "Url img_mostrar_perfil "+ requestBuilder)
        requestBuilder.into(binding.imgPerfil)

        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)
        binding.setClickListener {
            when (it!!.id) {
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
        }catch (e:IOException){
            e.printStackTrace()
        }
        return  ""
    }


    fun perfilRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getString(R.string.urlBase))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    override fun onValidationError() {
    }

    override fun onValidationSuccess() {
        if (binding.eTpasswordNuevo.text.toString().equals(binding.eTpasswordConfir.text.toString())) {
            doAsync {
            val nombre = binding.eTnameUsuario.text.toString()
            val correo = binding.eTemail.text.toString()
            val nip = binding.eTpasswordNuevo.text.toString()
            val id = idUser
            val base: String = "400|" + id + "|" + nombre + "|" + correo + "|" + nip
            Log.d("TAG", "base64Datos " + base)
            val encodebase = Base64.encodeToString(base.toByteArray(), Base64.DEFAULT)
            Log.i("TAG", "Base64Convetido  " + encodebase)

            var photoFile: File? = null
            photoFile = File(postPath)
            Log.i("TAG", "urlFinal-- " + postPath)
            val partes = ArrayList<MultipartBody.Part>()
            partes.add(MultipartBody.Part.createFormData("word", encodebase))
            partes.add(MultipartBody.Part.createFormData("archivo", photoFile?.name, photoFile?.crearMultiparte()))

            val call =
                perfilRetrofit().create(APIService::class.java).actualzarPerfil(partes)?.execute()
                Log.d("TAG", "Call--- " + call)
            val result = call.body() as PerfilResponse

            Log.d("TAG", "Resul--- " + result.validoPerfil)
                uiThread {
                    if (result.validoPerfil == "1"){
                        val alertDialog = AlertDialog.Builder(this@PerfilActivity)
                        alertDialog.setTitle("Alerta")
                        alertDialog.setMessage(""+result.mensajePerfil)
                        alertDialog.setPositiveButton("Si"){dialog, which ->

                            val usrImg = result.rutaFotoPerfil
                            //Guardar datos de datos
                            val sharedPreferences = getSharedPreferences("my_aplicacion_binding",Context.MODE_PRIVATE)
                            var editor = sharedPreferences.edit()
                            editor.putString("usr_Img", usrImg)
                            editor.commit()

                            binding.eTpasswordNuevo.setText("")
                            binding.eTpasswordConfir.setText("")
                        }
                        val dialog = alertDialog.create()
                        dialog.show()

                    }else if(result.validoPerfil == "0"){
                        val alerDialog = AlertDialog.Builder(this@PerfilActivity)
                        alerDialog.setTitle("Alerta")
                        alerDialog.setMessage(""+result.mensajePerfil)
                        alerDialog.setPositiveButton("Si"){dialog, which ->

                        }
                        val dialog = alerDialog.create()
                        dialog.show()
                     }
                }
        }

        }else {
            Toast.makeText(this, "Las contraseñas son diferenes", Toast.LENGTH_SHORT).show()
        }
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
}
