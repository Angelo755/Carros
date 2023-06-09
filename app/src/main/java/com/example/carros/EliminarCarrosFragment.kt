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
import com.example.carros.databinding.FragmentEliminarCarroBinding
import com.google.android.material.snackbar.Snackbar


class EliminarCarrosFragment : Fragment() {
    private lateinit var carro: carros
    private var _binding: FragmentEliminarCarroBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEliminarCarroBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar

        carro = EliminarCarrosFragmentArgs.fromBundle(requireArguments()).carro

        binding.textViewNomeEliminar.text = carro.nome_carro
        binding.textViewModeloEliminar.text = carro.descricao
        binding.textViewAnoEliminar.text = carro.ano

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
                voltaListaCarros()
                true
            }
            else -> false
        }
    }

    private fun voltaListaCarros() {
        findNavController().navigate(R.id.action_eliminarCarrosFragment_to_ListaCarrosFragment)
    }

    private fun eliminar() {
            val enderecoCarro = Uri.withAppendedPath(CarrosContentProvider.ENDERECO_CARROS, carro.id.toString())
        val numCarrosEliminados = requireActivity().contentResolver.delete(enderecoCarro, null, null)

        if (numCarrosEliminados == 1) {
            Toast.makeText(requireContext(), "Carro eliminado com sucesso", Toast.LENGTH_LONG).show()
            voltaListaCarros()
        } else {
            Snackbar.make(binding.textViewNomeEliminar,"Erro ao eliminar Carro", Snackbar.LENGTH_INDEFINITE)
        }
    }
}