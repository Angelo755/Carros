package com.example.carros

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class TipoDeMarcas (
    var nome_marca:String,
    var id:Long = -1
){
    fun toContentValues():ContentValues{
        val valores = ContentValues()

        valores.put(TabelaTipoDeMarcas.CAMPO_NOME, nome_marca)


        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor):TipoDeMarcas {
            val posID = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaTipoDeMarcas.CAMPO_NOME)

            val id=cursor.getLong(posID)
            val nome=cursor.getString(posNome)

            return TipoDeMarcas(nome, id)
        }
    }
}