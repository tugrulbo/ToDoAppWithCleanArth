package com.tugrulbo.todoappwithcleanarth.ui.fragments.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tugrulbo.todoappwithcleanarth.R
import com.tugrulbo.todoappwithcleanarth.databinding.FragmentListBinding
import com.tugrulbo.todoappwithcleanarth.ui.SharedToDoViewModel
import com.tugrulbo.todoappwithcleanarth.ui.fragments.home.adapter.ListAdapter

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    lateinit var binding: FragmentListBinding
    lateinit var todoAdapter:ListAdapter
    private val viewmodel:SharedToDoViewModel by viewModels()

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
        setHasOptionsMenu(true)
        checkDatabaseSize()
        setupRecyclerView()
    }

    private fun onClicks(){
        binding.floatBtnAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addNoteFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    private fun checkDatabaseSize(){
        viewmodel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabase(it)
        })
    }

    private fun showEmptyDatabase(emptyDatabaseBoolean: Boolean){
        if(emptyDatabaseBoolean){
            binding.rvHomeFragment.visibility = View.GONE
            binding.ivNoData.visibility = View.VISIBLE
            binding.tvHomeNoData.visibility = View.VISIBLE
        }else{
            binding.rvHomeFragment.visibility = View.VISIBLE
            binding.ivNoData.visibility = View.GONE
            binding.tvHomeNoData.visibility = View.GONE
        }
    }

    private fun setupRecyclerView(){

        todoAdapter = ListAdapter()
        binding.rvHomeFragment.apply {
            adapter = todoAdapter
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        }

        viewmodel.getAllData.observe(viewLifecycleOwner, Observer {data->
            viewmodel.checkIfDatabaseEmpty(data)
            todoAdapter.setData(data)
            binding.rvHomeFragment.scheduleLayoutAnimation()

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete_all -> confirmedItemRemoval()
            R.id.menu_priority_Low -> viewmodel.getLowPriority.observe(viewLifecycleOwner, Observer {
                todoAdapter.setData(it) })
            R.id.menu_priority_high -> viewmodel.getHighPriority.observe(viewLifecycleOwner, Observer {
                todoAdapter.setData(it) })
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmedItemRemoval(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){ _,_ ->
           viewmodel.deleteAll()
        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete all?")
        builder.setMessage("Are you sure you want to remove all?")
        builder.create().show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        if (query != null) {
                searchThrougTheDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {

            searchThrougTheDatabase(query)
        }
        return true
    }

    private fun searchThrougTheDatabase(query:String){
        var searchQuery:String = "%$query%"
        todoAdapter = ListAdapter()
        viewmodel.searchQuery(searchQuery)?.observe(viewLifecycleOwner, Observer {list->
            list?.let {
                todoAdapter.setData(list)
            }
        })
    }


}