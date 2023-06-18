package com.example.carros

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.example.carros.databinding.FragmentEditarCarroBinding

private const val ID_LOADER_TIPODEMARCA = 0
class editarCarroFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var carros: carros?= null

    private var _binding: FragmentEditarCarroBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditarCarroBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val loader= LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_TIPODEMARCA, null, this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar

        val carro = editarCarroFragmentArgs.fromBundle(requireArguments()).carro

        if (carro != null) {
            binding.insertTextNome.setText(carro.nome_carro)
            binding.insertTextAno.setText(carro.ano)

        }

        this.carros = carro
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
                voltaListaCarros()
                true
            }
            else -> false
        }
    }

    private fun voltaListaCarros() {
        findNavController().navigate(R.id.action_editarCarroFragment_to_ListaCarrosFragment)
    }

    private fun guardar() {
        val NomeCarro = binding.insertTextNome.text.toString()
        if(NomeCarro.isBlank()){
            binding.insertTextNome.error = getString(R.string.nome_obrigatorio)
            binding.insertTextNome.requestFocus()
            return
        }

        val AnoCarro = binding.insertTextAno.text.toString()
        if(AnoCarro.isBlank()){
            binding.insertTextAno.error = "Ano Obrigatório"
            binding.insertTextAno.requestFocus()
            return
        }

        val tipoDeMarcas = binding.spinnerMarca.selectedItemId
        if (tipoDeMarcas == Spinner.INVALID_ROW_ID){
            binding.textViewMarca1.error = "Categoria Obrigatória"
            binding.spinnerMarca.requestFocus()
            return
        }

        if (carros == null) {
            val carro = carros(
                NomeCarro,
                TipoDeMarcas("",tipoDeMarcas),
                "?",
                AnoCarro
            )
            insereCarro(carro)
        }else{
            val carro = carros!!
            carro.nome_carro = NomeCarro
            carro.descricao
            carro.ano = AnoCarro
            carro.TipoDeMarcas = TipoDeMarcas("?",tipoDeMarcas)

            alteraCarro(carro)
        }
    }

    private fun alteraCarro(carro: carros) {
        val enderecoCarro = Uri.withAppendedPath(CarrosContentProvider.ENDERECO_CARROS, carro.id.toString())
        val CarrosAlterados = requireActivity().contentResolver.update(enderecoCarro, carro.toContentValues(), null, null)

        if (CarrosAlterados == 1) {
            Toast.makeText(requireContext(), "Carro Guardado com sucesso", Toast.LENGTH_LONG).show()
            voltaListaCarros()
        } else {
            binding.insertTextNome.error = "Erro ao Guardar carro"
        }
    }
    private fun insereCarro(
        carro: carros
    ){


        val id = requireActivity().contentResolver.insert(
            CarrosContentProvider.ENDERECO_CARROS,
            carro.toContentValues()
        )

        Toast.makeText(requireContext(), "Carro guardado com sucesso", Toast.LENGTH_LONG).show()
        if (id == null) {
            binding.insertTextNome.error =
                "Não foi possivel guardar carro"
            return
        }


        Toast.makeText(
            requireContext(),
            "Carro guardado com sucesso",
            Toast.LENGTH_LONG
        ).show()
        voltaListaCarros()
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
        binding.spinnerMarca.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaTipoDeMarcas.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0,
        )
        mostraCategoriaSelecionadaSpinner()
    }

    private fun mostraCategoriaSelecionadaSpinner() {
        if (carros == null) return

        val idCategoria = carros!!.TipoDeMarcas.id

        val ultimaCategoria = binding.spinnerMarca.count - 1
        for (i in 0..ultimaCategoria) {
            if (idCategoria == binding.spinnerMarca.getItemIdAtPosition(i)) {
                binding.spinnerMarca.setSelection(i)
                return
            }
        }
    }
}