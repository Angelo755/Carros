package com.example.carros

import android.database.Cursor
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class AdapterCarros(val fragment: ListaCarrosFragment) : RecyclerView.Adapter<AdapterCarros.ViewHolderCarro>() {
    var cursor: Cursor? = null
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolderCarro(contentor: View) : ViewHolder(contentor) {
        private val textViewNome = contentor.findViewById<TextView>(R.id.textViewNome)
        private val textViewTipoDeMarcas = contentor.findViewById<TextView>(R.id.textViewMarca)

        internal var carro:carros?=null
            set(value){
                field = value
                textViewNome.text=carro?.nome
                textViewTipoDeMarcas.text=carro?.id_TipoDeMarcas.toString()?:""
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCarro {
        return ViewHolderCarro(
            fragment.layoutInflater.inflate(R.layout.item_carro,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return cursor?.count ?:0
    }

    override fun onBindViewHolder(holder: ViewHolderCarro, position: Int) {
        cursor!!.move(position)
        holder.carro=carros.fromCursor(cursor!!)
    }

}