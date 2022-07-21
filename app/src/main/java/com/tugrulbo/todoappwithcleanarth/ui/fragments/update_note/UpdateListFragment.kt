package com.tugrulbo.todoappwithcleanarth.ui.fragments.update_note

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tugrulbo.todoappwithcleanarth.R
import com.tugrulbo.todoappwithcleanarth.data.model.Priority
import com.tugrulbo.todoappwithcleanarth.data.model.ToDoModel
import com.tugrulbo.todoappwithcleanarth.databinding.FragmentUpdateNoteBinding
import com.tugrulbo.todoappwithcleanarth.ui.SharedToDoViewModel


class UpdateListFragment : Fragment() {

    lateinit var binding: FragmentUpdateNoteBinding
    private val viewModel:SharedToDoViewModel by viewModels()
    private val args: UpdateListFragmentArgs by navArgs()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        setupData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    private fun setupData(){
        val title = args.data.todoName
        val desc = args.data.desc
        val priority = args.data.priority

        binding.eTNoteName.setText(title)
        binding.etDesc.setText(desc)
        binding.spinnerPriorty.setSelection(viewModel.parsePriorityToInt(priority))
        binding.spinnerPriorty.onItemSelectedListener = viewModel.listener
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_update_save -> updateItem()
            R.id.menu_update_delete -> confirmedItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem(){
        val title = binding.eTNoteName.text.toString()
        val desc = binding.etDesc.text.toString()
        val priority =binding.spinnerPriorty.selectedItem.toString()

        val validation = viewModel.verifyDataFromUser(title,desc)
        if(validation){
            val updateData = ToDoModel(
                id= args.data.id,
                todoName = title,
                desc = desc,
                priority = viewModel.parsePriority(priority)
            )

            viewModel.updateData(updateData)

            Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateListFragment_to_listFragment)

        }else{
            Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteData(){
        val title = binding.eTNoteName.text.toString()
        val desc = binding.etDesc.text.toString()
        val priority =binding.spinnerPriorty.selectedItem.toString()

        val validation = viewModel.verifyDataFromUser(title,desc)
        if(validation){
            val deleteData = ToDoModel(
                id= args.data.id,
                todoName = title,
                desc = desc,
                priority = viewModel.parsePriority(priority)
            )

            viewModel.deleteData(deleteData)

            Toast.makeText(requireContext(), "Successfully deleted", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateListFragment_to_listFragment)

        }else{
            Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmedItemRemoval(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){ _,_ ->
           deleteData()
        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete ${args.data.todoName}?")
        builder.setMessage("Are you sure you want to remove ${args.data.todoName}?")
        builder.create().show()
    }

}