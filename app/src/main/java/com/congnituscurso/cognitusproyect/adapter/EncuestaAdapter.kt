package com.congnituscurso.cognitusproyect.adapter

import android.content.Context
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.congnituscurso.cognitusproyect.databinding.ItemEncuestaBinding
import com.congnituscurso.cognitusproyect.model.response.Encuesta

class EncuestaAdapter(private val items: List<Encuesta>, private val idUsr: String) :
    RecyclerView.Adapter<EncuestaAdapter.ViewHolder>() {
    private var contexto: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EncuestaAdapter.ViewHolder {
        contexto = parent.context
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEncuestaBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

        if (items[position].cpreg_id == "1") {
            holder.binding.tVPregunta.setText("Pregunta 1")
            holder.binding.rBar.visibility = View.INVISIBLE
            holder.binding.radioGroup.visibility = View.INVISIBLE
            holder.binding.liCheckBox.visibility = View.INVISIBLE
        }
        if (items[position].cpreg_id == "2") {
            holder.binding.tVPregunta.setText("Pregunta 2")
            holder.binding.liLPregunta.visibility = View.INVISIBLE
            holder.binding.radioGroup.visibility = View.INVISIBLE
            holder.binding.liCheckBox.visibility = View.INVISIBLE
        }
        if (items[position].cpreg_id == "3") {
            holder.binding.tVPregunta.setText("Pregunta 3")
            holder.binding.liLPregunta.visibility = View.INVISIBLE
            holder.binding.rBar.visibility = View.INVISIBLE
            holder.binding.liCheckBox.visibility = View.INVISIBLE
        }
        if (items[position].cpreg_id == "4") {
            holder.binding.tVPregunta.setText("Pregunta 4")
            holder.binding.liLPregunta.visibility = View.INVISIBLE
            holder.binding.rBar.visibility = View.INVISIBLE
            holder.binding.radioGroup.visibility = View.INVISIBLE
        }

        holder.binding.eTPregunta.addTextChangedListener {
            val idPregunta = items[position].cpreg_id
            val valor = holder.binding.eTPregunta.text
            val testValue: String ="601|"+ idPregunta + "@@@0@@@" + valor + "@@@" + idUsr
            Log.d("TAG", "idPre--1 " + idPregunta + " valorE_ " + valor + "idUsuario_ " + idUsr)
            var encodePregunta1 = Base64.encodeToString(testValue.toByteArray(), Base64.DEFAULT)

            Log.d("TAG", "encodeBase1 " + encodePregunta1)

            val sharedPreferences =
                contexto?.getSharedPreferences("my_aplicacion_binding", Context.MODE_PRIVATE)
            var editor = sharedPreferences?.edit()
            editor?.putString("pregunta1", encodePregunta1)
            editor?.commit()
        }


        holder.binding.rBar.setOnRatingBarChangeListener { ratingBar, fl, b ->
            //2@@@0@@@2@@@353
            val idPregunta = items[position].cpreg_id
            val valor = holder.binding.rBar.rating.toString()
            val testValue: String = idPregunta + "@@@0@@@" + valor + "@@@" + idUsr
            Log.d("TAG", "idPre--2 " + idPregunta + " valorE_ " + valor + "idUsuario_ " + idUsr)
            var encodePregunta2 = Base64.encodeToString(testValue.toByteArray(), Base64.DEFAULT)


            Log.d("TAG", "encodeBase2 " + encodePregunta2)

            val sharedPreferences =
                contexto?.getSharedPreferences("my_aplicacion_binding", Context.MODE_PRIVATE)
            var editor = sharedPreferences?.edit()
            editor?.putString("pregunta2", encodePregunta2)
            editor?.commit()
        }

        holder.binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            //2@@@0@@@2@@@353
            var valor:Int = 0
            if (holder.binding.raBSI.isChecked){
                valor = 1
            }
            if(holder.binding.raBNO.isChecked){
                valor = 2
            }
            val idPregunta = items[position].cpreg_id
            val testValue: String = idPregunta + "@@@0@@@" + valor + "@@@" + idUsr
            Log.d("TAG", "idPre--3 " + idPregunta + " valorE_ " + valor + " idUsuario_ " + idUsr)
            var encodePregunta3 = Base64.encodeToString(testValue.toByteArray(), Base64.DEFAULT)


            Log.d("TAG", "encodeBase3 " + encodePregunta3)

            val sharedPreferences =
                contexto?.getSharedPreferences("my_aplicacion_binding", Context.MODE_PRIVATE)
            var editor = sharedPreferences?.edit()
            editor?.putString("pregunta3", encodePregunta3)
            editor?.commit()
        }

        holder.binding.checkBox1.setOnCheckedChangeListener { compoundButton, b ->
            //4@@@1@@@0@@@353
            if (holder.binding.checkBox1.isChecked){
                holder.binding.checkBox4.isChecked = false
                val idPregunta = items[position].cpreg_id
                val idRespuesta = items[position].respuestas[0].cresp_id
                val testValue: String = idPregunta + "@@@" +idRespuesta+"@@@0@@@" + idUsr
                Log.d("TAG", "idPre--4 " + idPregunta + " valorE_ " + idRespuesta + " idUsuario_ " + idUsr)
                var encodePregunta4_1 = Base64.encodeToString(testValue.toByteArray(), Base64.DEFAULT)


                Log.d("TAG", "encodeBase4_1 " + encodePregunta4_1)

                val sharedPreferences =
                    contexto?.getSharedPreferences("my_aplicacion_binding", Context.MODE_PRIVATE)
                var editor = sharedPreferences?.edit()
                editor?.putString("pregunta4_1", encodePregunta4_1)
                editor?.commit()
            }else{

            }
        }

        holder.binding.checkBox2.setOnCheckedChangeListener { compoundButton, b ->
            //4@@@1@@@0@@@353
            if (holder.binding.checkBox2.isChecked){
                holder.binding.checkBox4.isChecked = false
                val idPregunta = items[position].cpreg_id
                val idRespuesta = items[position].respuestas[1].cresp_id
                val testValue: String = idPregunta + "@@@" +idRespuesta+"@@@0@@@" + idUsr
                Log.d("TAG", "idPre--4.1 " + idPregunta + " valorE_ " + idRespuesta + " idUsuario_ " + idUsr)
                var encodePregunta4_2 = Base64.encodeToString(testValue.toByteArray(), Base64.DEFAULT)


                Log.d("TAG", "encodeBase4_2 " + encodePregunta4_2)

                val sharedPreferences =
                    contexto?.getSharedPreferences("my_aplicacion_binding", Context.MODE_PRIVATE)
                var editor = sharedPreferences?.edit()
                editor?.putString("pregunta4_2", encodePregunta4_2)
                editor?.commit()
            }else{

            }
        }

        holder.binding.checkBox3.setOnCheckedChangeListener { compoundButton, b ->
            //4@@@1@@@0@@@353
            if (holder.binding.checkBox3.isChecked){
                holder.binding.checkBox4.isChecked = false
                val idPregunta = items[position].cpreg_id
                val idRespuesta = items[position].respuestas[2].cresp_id
                val testValue: String = idPregunta + "@@@" +idRespuesta+"@@@0@@@" + idUsr
                Log.d("TAG", "idPre--4.2 " + idPregunta + " valorE_ " + idRespuesta + " idUsuario_ " + idUsr)
                var encodePregunta4_3 = Base64.encodeToString(testValue.toByteArray(), Base64.DEFAULT)


                Log.d("TAG", "encodeBase4 " + encodePregunta4_3)

                val sharedPreferences =
                    contexto?.getSharedPreferences("my_aplicacion_binding", Context.MODE_PRIVATE)
                var editor = sharedPreferences?.edit()
                editor?.putString("pregunta4_3", encodePregunta4_3)
                editor?.commit()
            }else{

            }
        }


        holder.binding.checkBox4.setOnCheckedChangeListener { compoundButton, b ->
            //4@@@1@@@0@@@353
            if (holder.binding.checkBox4.isChecked){
                holder.binding.checkBox1.isChecked = false
                holder.binding.checkBox2.isChecked = false
                holder.binding.checkBox3.isChecked = false
                val idPregunta = items[position].cpreg_id
                val idRespuesta = items[position].respuestas[3].cresp_id
                val testValue: String = idPregunta + "@@@" +idRespuesta+"@@@0@@@" + idUsr
                Log.d("TAG", "idPre--4.3 " + idPregunta + " valorE_ " + idRespuesta + " idUsuario_ " + idUsr)
                var encodePregunta4_4 = Base64.encodeToString(testValue.toByteArray(), Base64.DEFAULT)


                Log.d("TAG", "encodeBase4 " + encodePregunta4_4)

                val sharedPreferences =
                    contexto?.getSharedPreferences("my_aplicacion_binding", Context.MODE_PRIVATE)
                var editor = sharedPreferences?.edit()
                editor?.putString("pregunta4_4", encodePregunta4_4)
                editor?.commit()
            }else{

            }
        }


    }


    class ViewHolder(val binding: ItemEncuestaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(encuestaModel: Encuesta) {
            binding.elemento = encuestaModel
            binding.executePendingBindings()
        }
    }
}