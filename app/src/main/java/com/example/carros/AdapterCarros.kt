package com.example.carros

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class AdapterCarros(val fragment: ListaCarrosFragment) : RecyclerView.Adapter<AdapterCarros.ViewHolderCarro>() {
    var cursor: Cursor? = null
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolderCarro(itemView: View) : ViewHolder(itemView) {

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
        TODO("Not yet implemented")
    }

}