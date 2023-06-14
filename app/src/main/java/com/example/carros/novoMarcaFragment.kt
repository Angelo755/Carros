package com.example.carros

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.carros.databinding.FragmentNovoMarcaBinding


class novoMarcaFragment : Fragment() {
    private var _binding: FragmentNovoMarcaBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNovoMarcaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        TODO("Not yet implemented")
    }
    private fun voltarlistaMarca(){
        findNavController().navigate(R.id.action_novoMarcaFragment_to_listaMarcasFragment)
    }
}