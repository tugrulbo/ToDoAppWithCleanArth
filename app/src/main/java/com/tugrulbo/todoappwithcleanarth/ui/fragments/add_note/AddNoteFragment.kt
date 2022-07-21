package com.tugrulbo.todoappwithcleanarth.ui.fragments.add_note

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tugrulbo.todoappwithcleanarth.R
import com.tugrulbo.todoappwithcleanarth.data.model.Priority
import com.tugrulbo.todoappwithcleanarth.data.model.ToDoModel
import com.tugrulbo.todoappwithcleanarth.databinding.FragmentAddNoteBinding
import com.tugrulbo.todoappwithcleanarth.ui.SharedToDoViewModel

class AddNoteFragment : Fragment() {

    lateinit var binding: FragmentAddNoteBinding
    private val viewModelShared: SharedToDoViewModel by viewModels()
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

        binding.spinnerPriorty.onItemSelectedListener = viewModelShared.listener

        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_title_add){
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb(){
        val title = binding.eTNoteName.text.toString()
        val priority = binding.spinnerPriorty.selectedItem.toString()
        val desc = binding.etDesc.text.toString()

        val validation = viewModelShared.verifyDataFromUser(title,desc)
        if(validation){
            val data = ToDoModel(
                todoName = title,
                desc = desc,
                priority = viewModelShared.parsePriority(priority)
            )

            viewModelShared.insertData(data)
            Toast.makeText(requireContext(), "Successfully Saved", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_addNoteFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }



}