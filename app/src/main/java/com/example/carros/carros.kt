package com.example.carros

import android.content.ContentValues

data class carros(
    var nome:String,
    var id_TipoDeMarcas: Long,
    var descricao:String,
    var ano: String,
    var id: Long = -1){
    fun toContentValues(): ContentValues {
        val valores = ContentValues()

        valores.put(TabelaCarros.CAMPO_NOME,nome)
        valores.put(TabelaCarros.CAMPO_DESCRICAO,descricao)
        valores.put(TabelaCarros.CAMPO_ANO,ano)
        valores.put(TabelaCarros.CAMPO_FK_IDMARCA,id_TipoDeMarcas)

        return valores
    }
}