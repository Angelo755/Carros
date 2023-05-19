package com.example.carros

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class CarrosContentProvider : ContentProvider(){
    private var bdOpenHelper : BdCarrosOpenHelper?=null
    override fun onCreate(): Boolean {
        bdOpenHelper = BdCarrosOpenHelper(context)
        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    companion object{
        private const val AUTORIDADE = "com.example.carros"

        const val TIPOSDEMARCAS = "TiposDeMarcas"
        const val CARROS = "Carros"

        private const val Uri_TIPOSDEMARCAS = 100
        private const val Uri_CARROS = 200


        fun uriMatcher()= UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTORIDADE, TIPOSDEMARCAS, Uri_TIPOSDEMARCAS)
            addURI(AUTORIDADE, CARROS, Uri_CARROS)

            /*
            content: //com.example.carros/TIPOSDEMARCAS
             */
        }
    }

}