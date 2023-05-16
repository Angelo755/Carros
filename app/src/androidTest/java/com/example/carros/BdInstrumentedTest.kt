package com.example.carros

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BdInstrumentedTest {

    private fun getAppContext(): Context =
        InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun apagabd() {
        //
        getAppContext().deleteDatabase(BdCarrosOpenHelper.NOME_BASE_DADOS)
    }

    @Test

    fun consegueInserirCarros() {
        val bd = getWritableDataBase()

        val tipoDeMarcas = TipoDeMarcas("Ferrari")
        insereTipoDeMarcas(bd, tipoDeMarcas)

        val carro = carros("Camiao", tipoDeMarcas.id, "Vermelho", "2000")
        insereCarro(bd, carro)

    }

    fun insereCarro(
        bd: SQLiteDatabase,
        carro: carros
    ) {
        carro.id = TabelaCarros(bd).insere(carro.toContentValues())
        assertNotEquals(-1, carro.id)
    }

    fun consegueInserirTipoDeMarcas() {
        val bd = getWritableDataBase()

        val tipoDeMarcas = TipoDeMarcas("BMW")
        insereTipoDeMarcas(bd, tipoDeMarcas)
    }

    private fun insereTipoDeMarcas(
        bd: SQLiteDatabase,
        tipoDeMarcas: TipoDeMarcas
    ) {
        tipoDeMarcas.id = TabelaTipoDeMarcas(bd).insere(tipoDeMarcas.toContentValues())
        assertNotEquals(-1, tipoDeMarcas.id)
    }

    private fun getWritableDataBase(): SQLiteDatabase {
        val openHelper = BdCarrosOpenHelper(getAppContext())
        return openHelper.writableDatabase
    }

    fun consegueAbrirBaseDados() {
        val openHelper = BdCarrosOpenHelper(getAppContext())
        val bd = openHelper.readableDatabase
        assert(bd.isOpen)
    }

    fun useAppContext() {
        // Context of the app under test.
        val appContext = getAppContext()
        assertEquals("com.example.carros", appContext.packageName)
    }

    @Test
    fun consegueLerTipoDeMarcas() {
        val bd = getWritableDataBase()

        val tipoDeMarcasMercedes = TipoDeMarcas("Mercedes")
        insereTipoDeMarcas(bd, tipoDeMarcasMercedes)

        val tipoDeMarcasFiat = TipoDeMarcas("Fiat")
        insereTipoDeMarcas(bd, tipoDeMarcasFiat)

        val cursor = TabelaCarros(bd).cosulta(
            TabelaTipoDeMarcas.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(tipoDeMarcasFiat.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val tipoMarcaBD = TipoDeMarcas.fromCursor(cursor)

        assertEquals(tipoDeMarcasMercedes, tipoMarcaBD)


        val cursorTodosTipoDeMarcas = TabelaTipoDeMarcas(bd).cosulta(
            TabelaTipoDeMarcas.CAMPOS,
            null, null, null, null,
            TabelaTipoDeMarcas.CAMPO_NOME
        )

        assert(cursorTodosTipoDeMarcas.count > 1)
    }


    @Test
    fun consegueLerCarros() {
        val bd = getWritableDataBase()

        val tipoDeMarcas = TipoDeMarcas("Bugatti")
        insereTipoDeMarcas(bd, tipoDeMarcas)

        val carro1 = carros("Carro", tipoDeMarcas.id, "Preto", "2015")
        insereCarro(bd, carro1)

        val tabelaCarros = TabelaCarros(bd)
        val cursor = tabelaCarros.cosulta(
            TabelaTipoDeMarcas.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(carro1.id.toString()),
            null,
            null,
            null
        )

        val carroBd = carros.fromCursor(cursor)

        assertEquals(carro1, carroBd)

        val cursorTodosCarros = tabelaCarros.cosulta(
            TabelaCarros.CAMPOS,
            null, null, null, null,
            TabelaCarros.CAMPO_NOME
        )

        assert(cursorTodosCarros.count > 1)

    }
}
