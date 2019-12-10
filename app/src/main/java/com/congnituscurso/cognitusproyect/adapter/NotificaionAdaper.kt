package com.congnituscurso.cognitusproyect.adapter

import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.congnituscurso.cognitusproyect.R
import com.congnituscurso.cognitusproyect.dao.APIService
import com.congnituscurso.cognitusproyect.databinding.ItemNotificacionBinding
import com.congnituscurso.cognitusproyect.model.response.Notificacion
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotificaionAdaper(private val items:List<Notificacion>):RecyclerView.Adapter<NotificaionAdaper.ViewHolder>() {
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

        holder.itemView.setOnClickListener {
           /* Toast.makeText(holder.itemView.context, "click", Toast.LENGTH_SHORT).show()
            val testValue:String ="300|"
            val encodeValue = Base64.encodeToString(testValue.toByteArray(), Base64.DEFAULT)
            Log.i("TAG","Base64Notificacion "+"->${encodeValue}")
            val call = notificaionLeerRetrofit().create(APIService::class.java).notificacionLeer(encodeValue).execute()
        */
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