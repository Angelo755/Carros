package com.example.carros

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carros.databinding.FragmentListacarrosBinding
import com.example.carros.databinding.FragmentMenuPrincipalBinding

// TODO: Rename parameter arguments, choose names that match


/**
 * A simple [Fragment] subclass.
 * Use the [ListaCarrosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaCarrosFragment : Fragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterCarros=AdapterCarros()
        binding.recyclerViewCarros.adapter=adapterCarros
        binding.recyclerViewCarros.layoutManager = LinearLayoutManager(requireContext())
    }



    companion object {

    }
}