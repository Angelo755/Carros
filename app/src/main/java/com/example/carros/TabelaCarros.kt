package com.example.carros

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns


class TabelaCarros(db: SQLiteDatabase):TabelaBd(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_NOME TEXT NOT NULL, $CAMPO_DESCRICAO TEXT, $CAMPO_ANO TEXT, $CAMPO_FK_IDMARCA INTEGER NOT NULL, FOREIGN KEY($CAMPO_FK_IDMARCA) REFERENCES ${TabelaTipoDeMarcas.NOME_TABELA}(${BaseColumns._ID})ON DELETE RESTRICT)")
    }

    override fun consulta(
        colunas: Array<String>,
        selecao: String?,
        argsSelecao: Array<String>?,
        groupby: String?,
        having: String?,
        orderby: String?
    ): Cursor {
        val sql = SQLiteQueryBuilder()
        sql.tables = "$NOME_TABELA INNER JOIN ${TabelaTipoDeMarcas.NOME_TABELA} ON ${TabelaTipoDeMarcas.CAMPO_ID} = $CAMPO_FK_IDMARCA"

        return sql.query(db,colunas,selecao,argsSelecao,groupby,having,orderby)
    }
    companion object{
        private const val NOME_TABELA = "Carros"
        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_NOME="nome"
        const val CAMPO_DESCRICAO="descricao"
        const val CAMPO_ANO="ano"
        const val CAMPO_FK_IDMARCA="id_TipoDeMarca"
        const val CAMPO_NOME_MARCA = TabelaTipoDeMarcas.CAMPO_NOME

        val CAMPOS = arrayOf(BaseColumns._ID, CAMPO_NOME, CAMPO_DESCRICAO, CAMPO_ANO, CAMPO_FK_IDMARCA, CAMPO_NOME_MARCA)
    }
}
