package com.example.carros

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.example.carros.databinding.FragmentNovoCarroBinding
import com.example.carros.databinding.FragmentSobreBinding

private const val ID_LOADER_TIPODEMARCA = 0
class NovoCarroFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentNovoCarroBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNovoCarroBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spinnerMarca.adapter = SimpleCursorAdapter()

        val loader= LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_TIPODEMARCA, null, this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_lista_carros
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOptionMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                cancelar()
                true
            }
            else -> false
        }
    }

    private fun cancelar() {
        findNavController().navigate(R.id.action_novoCarroFragment_to_ListaCarrosFragment)
    }

    private fun guardar() {
        TODO("Not yet implemented")
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            CarrosContentProvider.ENDERECO_TIPOSDEMARCAS,
            TabelaTipoDeMarcas.CAMPOS,
            null,null,
            TabelaTipoDeMarcas.CAMPO_NOME

        )
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
       binding.spinnerMarca.adapter = null
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data == null){
            binding.spinnerMarca.adapter = null
            return
        }
        binding.spinnerMarca.adapter = SimpleCursorAdapter{
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaTipoDeMarcas.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0,
        }
    }
}