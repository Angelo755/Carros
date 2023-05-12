package com.example.carros

import android.content.ContentValues

data class TipoDeMarcas (
    var nome:String,
    var id:Long = -1
){
    fun toContentValues():ContentValues{
        val valores = ContentValues()

        valores.put(TabelaTipoDeMarcas.CAMPO_NOME, nome)


        return valores
    }
}