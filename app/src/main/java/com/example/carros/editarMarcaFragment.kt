package com.example.carros

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.carros.databinding.FragmentEditarCarroBinding
import com.example.carros.databinding.FragmentEditarMarcaBinding


class editarMarcaFragment : Fragment() {

    private var marcas: TipoDeMarcas?= null
    private var _binding: FragmentEditarMarcaBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditarMarcaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar

        val marca = editarMarcaFragmentArgs.fromBundle(requireArguments()).marcas

        if (marca != null){
            activity.atualizaNome ("Editar Categoria")

            binding.editTextNomeMarca.setText(marca.nome_marca)
        }else{
            activity.atualizaNome("Nova Marca")
        }

        this.marcas = marca
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun voltarlistaMarca(){
        findNavController().navigate(R.id.action_editarMarcaFragment_to_listaMarcasFragment)
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_guardar -> {
                guardarMarca()
                true
            }
            R.id.action_cancelar -> {
                voltarlistaMarca()
                true
            }
            else -> false
        }
    }

    private fun guardarMarca() {
        val nome = binding.editTextNomeMarca.text.toString()
        if (nome.isBlank()) {
            binding.editTextNomeMarca.error = "Campo Obrigatório"
            binding.editTextNomeMarca.requestFocus()
            return
        }


        if (marcas == null){
            val marca = TipoDeMarcas(nome)

            insereMarca(marca)
        }else{
            val marca = marcas!!
            marca.nome_marca = nome

            alteraMarca(marca)
        }

    }

    private fun alteraMarca(marca: TipoDeMarcas) {
        val enderecoMarca = Uri.withAppendedPath(CarrosContentProvider.ENDERECO_TIPOSDEMARCAS, marca.id.toString())
        val MarcasAlteradas = requireActivity().contentResolver.update(enderecoMarca, marca.toContentValues(), null,null)

        if(MarcasAlteradas == 1){
            Toast.makeText(requireContext(), "Marca alterada com sucesso", Toast.LENGTH_LONG).show()
            voltarlistaMarca()
        }else{
            binding.editTextNomeMarca.error = "Impossivel Guardar marca"
        }
    }

    private fun insereMarca(marca: TipoDeMarcas) {
        val id = requireActivity().contentResolver.insert(
            CarrosContentProvider.ENDERECO_TIPOSDEMARCAS,
            marca.toContentValues()
        )

        if (id == null) {
            binding.editTextNomeMarca.error =
                "Impossivel guardar marca"
            return
        }


        Toast.makeText(
            requireContext(),
            "Marca Salva",
            Toast.LENGTH_LONG
        ).show()
        voltarlistaMarca()
    }

}