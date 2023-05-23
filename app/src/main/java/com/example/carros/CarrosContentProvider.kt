package com.example.carros

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class CarrosContentProvider : ContentProvider(){
    private var bdOpenHelper : BdCarrosOpenHelper?=null
    override fun onCreate(): Boolean {
        bdOpenHelper = BdCarrosOpenHelper(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val bd = bdOpenHelper!!.readableDatabase

        val endereco= uriMatcher().match(uri)

        val tabela = when (endereco){
            Uri_TIPOSDEMARCAS, Uri_TIPODEMARCAS_ID ->TabelaTipoDeMarcas(bd)
            Uri_CARROS, Uri_CARROS_ID ->TabelaCarros(bd)
            else -> null
        }

        val id = uri.lastPathSegment

        val (selecao, argsSel) = when (endereco){
            Uri_TIPODEMARCAS_ID, Uri_CARROS_ID -> Pair("${BaseColumns._ID}", arrayOf(id))
            else -> Pair(selection,selectArgs)
        }



        return tabela?.cosulta(
            projection as Array<String>,
            selection,
            selectArgs as Array<String>?,
            null,
            null,
            sortOrder)
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, projection: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, projection: String?, selection: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(uri: Uri, projection: ContentValues?, selection: String?, selectArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    companion object{
        private const val AUTORIDADE = "com.example.carros"

        const val TIPOSDEMARCAS = "TiposDeMarcas"
        const val CARROS = "Carros"

        private const val Uri_TIPOSDEMARCAS = 100
        private const val Uri_TIPODEMARCAS_ID = 101
        private const val Uri_CARROS = 200
        private const val Uri_CARROS_ID = 201


        fun uriMatcher()= UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTORIDADE, TIPOSDEMARCAS, Uri_TIPOSDEMARCAS)
            addURI(AUTORIDADE, "$TIPOSDEMARCAS/#", Uri_TIPODEMARCAS_ID)
            addURI(AUTORIDADE, CARROS, Uri_CARROS)
            addURI(AUTORIDADE, "$CARROS/#", Uri_CARROS_ID)


            /*
            content: //com.example.carros/TIPOSDEMARCAS
             */
        }
    }

}