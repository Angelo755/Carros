package com.example.carros

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class TabelaCarros(db: SQLiteDatabase):TabelaBd(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, nome TEXT NOT NULL, descrição TEXT, id_TipoDeMarcas INTEGER NOT NULL, FOREIGN KEY(id_TipoDeMarcas) REFERENCES ${TabelaTipoDeMarcas.NOME_TABELA}(${BaseColumns._ID}))")
    }
    companion object{
        private const val NOME_TABELA = "Carros"
    }
}
