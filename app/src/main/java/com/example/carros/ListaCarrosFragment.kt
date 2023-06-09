package com.example.carros

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carros.databinding.FragmentListacarrosBinding

private const val ID_LOADER_CARROS = 0

class ListaCarrosFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentListacarrosBinding? = null

    private val binding get() = _binding!!

    var carroSelecionado : carros? = null

        set(value){
            field = value
            val mostarEliminarAlterar = (value != null)
            val activity = activity as MainActivity
            activity.mostraOpcaoMenu(R.id.action_editar, mostarEliminarAlterar)
            activity.mostraOpcaoMenu(R.id.action_eliminar, mostarEliminarAlterar)
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListacarrosBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    private var adapterCarros: AdapterCarros?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterCarros= AdapterCarros(this)
        binding.recyclerViewCarros.adapter=adapterCarros
        binding.recyclerViewCarros.layoutManager = LinearLayoutManager(requireContext())

        val loader=LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_CARROS, null, this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_lista_carros
    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            CarrosContentProvider.ENDERECO_CARROS,
            TabelaCarros.CAMPOS,
            null,null,
            TabelaCarros.CAMPO_NOME

        )
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        if (adapterCarros != null){
            adapterCarros!!.cursor = null
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterCarros!!.cursor=data
    }

    fun processaOptionMenu(item: MenuItem):Boolean {
        return when (item.itemId) {
            R.id.action_adicionar -> {
                adicionarCarro()
                true
            }
            R.id.action_editar -> {
                editarCarro()
                true
            }
            R.id.action_eliminar -> {
                eliminarCarro()
                true
            }
            else -> false
        }
    }

    private fun eliminarCarro() {
        val acao = ListaCarrosFragmentDirections.actionListaCarrosFragmentToEliminarCarrosFragment(carroSelecionado!!)
        findNavController().navigate(acao)
    }

    private fun editarCarro() {
        val acao = ListaCarrosFragmentDirections.actionListaCarrosFragmentToEditarCarroFragment(carroSelecionado!!)
        findNavController().navigate(acao)
    }

    private fun adicionarCarro() {
        val acao = ListaCarrosFragmentDirections.actionListaCarrosFragmentToEditarCarroFragment(null)
        findNavController().navigate(acao)
    }


}