package com.congnituscurso.cognitusproyect.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import br.com.ilhasoft.support.validation.Validator
import com.congnituscurso.cognitusproyect.R
import com.congnituscurso.cognitusproyect.databinding.ActivityCheckInBinding
import com.congnituscurso.cognitusproyect.databinding.ActivityMainBinding
import com.github.gcacace.signaturepad.views.SignaturePad
import java.util.*


class CheckInActivity : AppCompatActivity(), Validator.ValidationListener {

    private val CERO = "0"
    private val DOS_PUNTOS = ":"

    //Calendario para obtener fecha & hora
    var c = java.util.Calendar.getInstance()

    //Variables para obtener la hora hora
    var hora = c.get(Calendar.HOUR_OF_DAY)
    var minuto = c.get(Calendar.MINUTE)

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityCheckInBinding>(
            this, R.layout.activity_check_in
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

        //permisos
        revisarPermisos()

        val validator: Validator = Validator(binding)
        validator.setValidationListener(this)

        binding.setClickListener {
            when(it!!.id){
                binding.eTHoraEntrada.id->{
                    val recogerHora = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                            //Formateo el hora obtenido: antepone el 0 si son menores de 10
                            val horaFormateada =
                                if (hourOfDay < 10) CERO + hourOfDay else hourOfDay.toString()
                            //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                            val minutoFormateado =
                                if (minute < 10) CERO + minute else minute.toString()
                            //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                            val AM_PM: String
                            if (hourOfDay < 12) {
                                AM_PM = "a.m."
                            } else {
                                AM_PM = "p.m."
                            }
                            //Muestro la hora con el formato deseado
                           binding.eTHoraEntrada.setText("$horaFormateada$DOS_PUNTOS$minutoFormateado $AM_PM")
                        },
                        //Estos valores deben ir en ese orden
                        //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
                        //Pero el sistema devuelve la hora en formato 24 horas
                        hora, minuto, false
                    )
                    recogerHora.show()
                }
                binding.imgBorrar.id->{
                    binding.signaturePad.clear()
                    binding.tVFirma.visibility = View.VISIBLE
                }

            }
        }

        binding.signaturePad.setOnSignedListener(object :SignaturePad.OnSignedListener{
            override fun onStartSigning() {
                /*cuando comienza a dibujar
                 Toast.makeText(
                     applicationContext,
                     "Comineza firma",
                     Toast.LENGTH_SHORT
                 ).show()*/
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
                //Al termianr la firnma
                //mSaveButton.isEnabled = true
                //mClearButton.isEnabled= true
                binding.imgBorrar.visibility = View.VISIBLE
            }

        })
    }


    override fun onValidationError() {
    }

    override fun onValidationSuccess() {
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
