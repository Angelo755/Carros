package com.example.carros

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

private const val NOME_TABELA = "Marcas"

class TabelaTipoDeMarcas(db:SQLiteDatabase):TabelaBd(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA($CHAVE_TABELA, nome TEXT NOT NULL)")
    }
}