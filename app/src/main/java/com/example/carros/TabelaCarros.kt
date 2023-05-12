package com.example.carros

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class TabelaCarros(db: SQLiteDatabase):TabelaBd(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_NOME TEXT NOT NULL, $CAMPO_DESCRICAO TEXT, $CAMPO_ANO TEXT, $CAMPO_FK_IDMARCA INTEGER NOT NULL, FOREIGN KEY(id_TipoDeMarcas) REFERENCES ${TabelaTipoDeMarcas.NOME_TABELA}(${BaseColumns._ID}))")
    }
    companion object{
        private const val NOME_TABELA = "Carros"
        const val CAMPO_NOME="nome"
        const val CAMPO_DESCRICAO="descricao"
        const val CAMPO_ANO="ano"
        const val CAMPO_FK_IDMARCA="id_TipoDeMarca"
    }
}
