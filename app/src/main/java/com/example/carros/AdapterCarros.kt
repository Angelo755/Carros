package com.example.carros

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class AdapterCarros(val fragment: ListaCarrosFragment) : RecyclerView.Adapter<AdapterCarros.ViewHolderCarro>() {
    var cursor: Cursor? = null
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolderCarro(contentor: View) : ViewHolder(contentor) {
        private val textViewNome = contentor.findViewById<TextView>(R.id.textViewCarros)
        private val textViewTipoDeMarcas = contentor.findViewById<TextView>(R.id.textViewMarca)
        private val textViewAno = contentor.findViewById<TextView>(R.id.textViewAno)

        init {
            contentor.setOnClickListener{
               viewHolderSeleccionado?.desSeleciona()
                seleciona()
                 }
         }
        internal var carro:carros?=null
            set(value){
                field = value
                textViewNome.text = carro?.nome_carro ?:""
                textViewTipoDeMarcas.text=carro?.TipoDeMarcas?.nome_marca ?: ""
                textViewAno.text = carro?.ano?:""
            }


     fun seleciona(){
       viewHolderSeleccionado = this
         fragment.carroSelecionado = carro
       itemView.setBackgroundResource(R.color.item_selecionado)
     }

    fun desSeleciona(){
         itemView.setBackgroundResource(android.R.color.white)
     }
    }
    private var viewHolderSeleccionado : ViewHolderCarro?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCarro {
        return ViewHolderCarro(
            fragment.layoutInflater.inflate(R.layout.item_carro,parent,false)
        )

    }
    override fun getItemCount(): Int {
        return cursor?.count ?:0
    }

    override fun onBindViewHolder(holder: ViewHolderCarro, position: Int) {
        cursor!!.moveToPosition(position)
        holder.carro=carros.fromCursor(cursor!!)
    }

}