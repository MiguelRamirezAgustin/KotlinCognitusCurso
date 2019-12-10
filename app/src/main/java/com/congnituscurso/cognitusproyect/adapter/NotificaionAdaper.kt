package com.congnituscurso.cognitusproyect.adapter

import android.content.Context
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.congnituscurso.cognitusproyect.R
import com.congnituscurso.cognitusproyect.dao.APIService
import com.congnituscurso.cognitusproyect.databinding.ItemNotificacionBinding
import com.congnituscurso.cognitusproyect.model.NotificacionLeerResponse
import com.congnituscurso.cognitusproyect.model.response.Notificacion
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotificaionAdaper(private val items:List<Notificacion>, private val idUsr:String):RecyclerView.Adapter<NotificaionAdaper.ViewHolder>() {
    private var contexto: Context? =null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        contexto=parent.context
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNotificacionBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        val idNotificacion= items[position].not_id

        if(items[position].not_status == "0"){
            holder.binding.iMNotificacion.setImageResource(R.drawable.ellipse_1)
        }

        holder.itemView.setOnClickListener {
            doAsync {
                val testValue: String = "301|" + idNotificacion + "|" + idUsr
                val encodeValue = Base64.encodeToString(testValue.toByteArray(), Base64.DEFAULT)
                Log.i("TAG", "Base64NotificacionLeer " + "->${encodeValue}")
                val call = notificaionLeerRetrofit().create(APIService::class.java)
                    .notificacionLeer(encodeValue).execute()
                val result = call.body() as NotificacionLeerResponse
                Log.d("TAG", "RestulLeerNotificacion " + result.valido)
                uiThread {
                    if (result.valido =="1"){
                        holder.binding.iMNotificacion.setImageResource(R.drawable.ellipse_1)
                    }
                }
            }
        }

    }


    class ViewHolder(val binding: ItemNotificacionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notificacionModel: Notificacion) {
            binding.elementos= notificacionModel
            binding.executePendingBindings()
        }
    }

    fun notificaionLeerRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://35.155.161.8:8080/WSExample/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}