package com.tugrulbo.todoappwithcleanarth.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tugrulbo.todoappwithcleanarth.R
import com.tugrulbo.todoappwithcleanarth.databinding.FragmentListBinding

class ListFragment : Fragment() {

    lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClicks()
    }

    private fun onClicks(){
        binding.floatBtnAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addNoteFragment)
        }
    }


}