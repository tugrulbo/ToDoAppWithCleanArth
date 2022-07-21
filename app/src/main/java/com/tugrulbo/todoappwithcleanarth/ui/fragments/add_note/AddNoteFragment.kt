package com.tugrulbo.todoappwithcleanarth.ui.fragments.add_note

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.tugrulbo.todoappwithcleanarth.R
import com.tugrulbo.todoappwithcleanarth.databinding.FragmentAddNoteBinding

class AddNoteFragment : Fragment() {

    lateinit var binding: FragmentAddNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

}