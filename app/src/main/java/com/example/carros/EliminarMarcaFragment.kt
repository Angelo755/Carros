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
import com.example.carros.EliminarCarrosFragmentArgs.Companion.fromBundle
import com.example.carros.databinding.FragmentEliminarMarcaBinding
import com.example.carros.editarCarroFragmentArgs.Companion.fromBundle
import com.example.carros.editarMarcaFragmentArgs.Companion.fromBundle
import com.google.android.material.snackbar.Snackbar

class EliminarMarcaFragment : Fragment() {


    private lateinit var marca: TipoDeMarcas
    private var _binding: FragmentEliminarMarcaBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarMarcaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar

        marca = EliminarMarcaFragmentArgs.fromBundle(requireArguments()).marcas

        binding.textViewNomeEliminarMarca.text = marca.nome_marca

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_eliminar -> {
                eliminar()
                true
            }

            R.id.action_cancelar -> {
                voltaListaMarcas()
                true
            }

            else -> false
        }
    }

    private fun voltaListaMarcas() {
        findNavController().navigate(R.id.action_fragmentEliminarMarca_to_listaMarcasFragment)
    }

    private fun eliminar() {
        val enderecoMarcas = Uri.withAppendedPath(
            CarrosContentProvider.ENDERECO_TIPOSDEMARCAS,
            marca.id.toString()
        )
        val numMarcasSelecionadas =
            requireActivity().contentResolver.delete(enderecoMarcas, null, null)

        if (numMarcasSelecionadas == 1) {
            Toast.makeText(
                requireContext(),
                "Marca eliminada com sucesso",
                Toast.LENGTH_LONG
            ).show()
            voltaListaMarcas()
        } else {
            Snackbar.make(
                binding.textViewNomeEliminarMarca,
                "Erro ao eliminar categoria",
                Snackbar.LENGTH_INDEFINITE
            )

        }
    }
}