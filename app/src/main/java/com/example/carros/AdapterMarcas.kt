package com.example.carros

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class AdapterMarcas(val fragment: ListaMarcasFragment) : RecyclerView.Adapter<AdapterMarcas.ViewHolderMarca>() {
    var cursor: Cursor? = null
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolderMarca(contentor: View) : RecyclerView.ViewHolder(contentor) {
        private val textViewMarca = contentor.findViewById<TextView>(R.id.textViewMarcaMarca)

        init {
            contentor.setOnClickListener{
                viewHolderSeleccionado?.desSeleciona()
                seleciona()
            }
        }
        internal var marca:TipoDeMarcas?=null
            set(value){
                field = value
                textViewMarca.text=marca?.nome_marca ?: ""
            }


        fun seleciona(){
            viewHolderSeleccionado = this
            fragment.marcaSelecionado = marca
            itemView.setBackgroundResource(R.color.item_selecionado)
        }

        fun desSeleciona(){
            itemView.setBackgroundResource(android.R.color.white)
        }
    }
    private var viewHolderSeleccionado : ViewHolderMarca?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMarcas.ViewHolderMarca {
        return ViewHolderMarca(
            fragment.layoutInflater.inflate(R.layout.item_marca,parent,false)
        )

    }
    override fun getItemCount(): Int {
        return cursor?.count ?:0
    }

    override fun onBindViewHolder(holder: ViewHolderMarca, position: Int) {
        cursor!!.moveToPosition(position)
        holder.marca=TipoDeMarcas.fromCursor(cursor!!)
    }

}