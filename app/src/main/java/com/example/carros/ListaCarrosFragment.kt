package com.example.carros

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.app.LoaderManager.LoaderCallbacks
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carros.databinding.FragmentListacarrosBinding
import com.example.carros.databinding.FragmentMenuPrincipalBinding

// TODO: Rename parameter arguments, choose names that match

private const val ID_LOADER_LIVROS = 0

/**
 * A simple [Fragment] subclass.
 * Use the [ListaCarrosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaCarrosFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentListacarrosBinding? = null

    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listacarros, container, false)
    }

    private val adapterCarros = AdapterCarros()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterCarros=AdapterCarros()
        binding.recyclerViewCarros.adapter=adapterCarros
        binding.recyclerViewCarros.layoutManager = LinearLayoutManager(requireContext())

        val loader=LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_LIVROS, null, this)
    }



    companion object {

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
        TODO("Not yet implemented")
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}