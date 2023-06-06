package com.example.carros

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class carros(
    var nome:String,
    var TipoDeMarcas: TipoDeMarcas,
    var descricao:String,
    var ano: String,
    var id: Long = -1){
    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaCarros.CAMPO_NOME,nome)
        valores.put(TabelaCarros.CAMPO_DESCRICAO,descricao)
        valores.put(TabelaCarros.CAMPO_ANO,ano)
        valores.put(TabelaCarros.CAMPO_FK_IDMARCA,TipoDeMarcas.id)

        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor):carros {
            val posID = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaCarros.CAMPO_NOME)
            val posDescricao = cursor.getColumnIndex(TabelaCarros.CAMPO_DESCRICAO)
            val posAno = cursor.getColumnIndex(TabelaCarros.CAMPO_ANO)
            val posTipoDeMarcasFK = cursor.getColumnIndex(TabelaCarros.CAMPO_FK_IDMARCA)
            val posNomeMarca = cursor.getColumnIndex(TabelaCarros.CAMPO_NOME_MARCA)

            val id=cursor.getLong(posID)
            val nome=cursor.getString(posNome)
            val descricao = cursor.getString(posDescricao)
            val ano = cursor.getString(posAno)
            val marcaID = cursor.getLong(posTipoDeMarcasFK)
            val nomeMarca = cursor.getString(posNomeMarca)

            return carros(nome, TipoDeMarcas(nomeMarca, marcaID),descricao, ano, id)
        }
    }
}