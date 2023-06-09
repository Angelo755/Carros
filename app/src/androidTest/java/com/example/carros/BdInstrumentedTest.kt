package com.example.carros

import android.content.Context
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
        // getAppContext().deleteDatabase(BdCarrosOpenHelper.NOME_BASE_DADOS)
    }

    @Test

    fun consegueInserirCarros() {
        val bd = getWritableDataBase()

        val tipoDeMarcas = TipoDeMarcas("Ferrari")
        insereTipoDeMarcas(bd, tipoDeMarcas)

        val carro = carros("Camiao", tipoDeMarcas, "Vermelho", "2020")
        insereCarro(bd, carro)

        val carro2 = carros("Mota", tipoDeMarcas, "Vermelho", "2020")
        insereCarro(bd, carro2)

    }

    fun insereCarro(
        bd: SQLiteDatabase,
        carro: carros
    ) {
        carro.id = TabelaCarros(bd).insere(carro.toContentValues())
        assertNotEquals(-1, carro.id)
    }
    @Test
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
    @Test
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

        val tipoDeMarca = TipoDeMarcas("Mercedes")
        insereTipoDeMarcas(bd, tipoDeMarca)

        val tipoDeMarca2 = TipoDeMarcas("Fiat")
        insereTipoDeMarcas(bd, tipoDeMarca2)

        val cursor = TabelaTipoDeMarcas(bd).consulta(
            TabelaTipoDeMarcas.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(tipoDeMarca2.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val tipoMarcaBD = TipoDeMarcas.fromCursor(cursor)

        assertEquals(tipoDeMarca2, tipoMarcaBD)


        val cursorTodosTipoDeMarcas = TabelaTipoDeMarcas(bd).consulta(
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

        val carro = carros("Giron", tipoDeMarcas,"descricao","2019")
        insereCarro(bd,carro)

        val carro2 = carros("Veron", tipoDeMarcas,"descricao","2002")
        insereCarro(bd,carro2)


        val cursor = TabelaCarros(bd).consulta(
            TabelaCarros.CAMPOS,
            "${TabelaCarros.CAMPO_ID}=?",
            arrayOf(carro2.id.toString()),
            null,
            null,
            null
        )
        assert(cursor.moveToNext())

        val carroBd = carros.fromCursor(cursor)

        assertEquals(carro2, carroBd)

        val cursorTodosCarros = TabelaCarros(bd).consulta(
            TabelaCarros.CAMPOS,
            null, null, null, null,
            TabelaCarros.CAMPO_NOME
        )

        assert(cursorTodosCarros.count > 1)

    }

    @Test
    fun consegueAlterarTiposDeMarcas(){
        val bd = getWritableDataBase()

        val tipoDeMarcas = TipoDeMarcas("...")
        insereTipoDeMarcas(bd, tipoDeMarcas)

        tipoDeMarcas.nome_marca="320"
        val registosAlterados = TabelaTipoDeMarcas(bd).altera(
            tipoDeMarcas.toContentValues(),"${BaseColumns._ID}=?",
            arrayOf(tipoDeMarcas.id.toString()),
        )
        assertEquals(1, registosAlterados)

    }
    @Test
    fun consegueAlterarCarros() {
        val bd = getWritableDataBase()

        val tipoDeMarcas = TipoDeMarcas("...")
        insereTipoDeMarcas(bd, tipoDeMarcas)

        val tipoDeMarcasLandRover = TipoDeMarcas("LandRover")
        insereTipoDeMarcas(bd, tipoDeMarcasLandRover)

        val carro = carros("...", tipoDeMarcasLandRover,"descricao","2000")
        insereCarro(bd,carro)

        carro.TipoDeMarcas=tipoDeMarcas
        carro.nome_carro="E999"

        val registosAlterados = TabelaCarros(bd).altera(
            carro.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(carro.id.toString()))

        assertEquals(1, registosAlterados)

    }

    @Test
    fun consegueEliminarTipoDeMarca(){
        val bd = getWritableDataBase()

        val tipoDeMarcas=TipoDeMarcas("...")
        insereTipoDeMarcas(bd, tipoDeMarcas)

        val registosEliminados=TabelaTipoDeMarcas(bd).eliminar(
            "${BaseColumns._ID}=?",
            arrayOf(tipoDeMarcas.id.toString())
        )
        assertEquals(1, registosEliminados)
    }

    @Test
    fun consegueEliminarCarros(){
        val bd = getWritableDataBase()

        val tipoDeMarcas=TipoDeMarcas("LandRover")
        insereTipoDeMarcas(bd, tipoDeMarcas)

        val carro = carros("...", tipoDeMarcas,"descricao","2000")
        insereCarro(bd,carro)

        val registosEliminados=TabelaCarros(bd).eliminar(
            "${BaseColumns._ID}=?", arrayOf(carro.id.toString())
        )
        assertEquals(1,registosEliminados)
    }
}
