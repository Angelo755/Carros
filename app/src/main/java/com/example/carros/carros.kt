package com.example.carros

import android.content.ContentValues

data class carros (
    var id_TipoDeMarcas: Int,
    var descricao:String,
    var nome:String,
    var ano: Long,
    var id: Long = -1){
    fun toContentValues(){
        val valores = ContentValues()

        valores.put(TabelaCarros.CAMPO_NOME,nome)
        valores.put(TabelaCarros.CAMPO_DESCRICAO,descricao)
        valores.put(TabelaCarros.CAMPO_FK_IDMARCA,id_TipoDeMarcas)


    }
}