package com.tugrulbo.todoappwithcleanarth.ui

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tugrulbo.todoappwithcleanarth.R
import com.tugrulbo.todoappwithcleanarth.data.db.ToDoDatabase
import com.tugrulbo.todoappwithcleanarth.data.model.Priority
import com.tugrulbo.todoappwithcleanarth.data.model.ToDoModel
import com.tugrulbo.todoappwithcleanarth.data.repo.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedToDoViewModel(application: Application):AndroidViewModel(application) {

    private val toDoDao = ToDoDatabase.getDatabase(application.applicationContext)?.toDoDao()
    private var repository : ToDoRepository? = toDoDao?.let { ToDoRepository(it) }

    val emptyDatabase:MutableLiveData<Boolean> = MutableLiveData(true)

    var getAllData:LiveData<List<ToDoModel>> = repository?.getAllData!!

    var getHighPriority : LiveData<List<ToDoModel>> = repository?.getHighPriority!!

    var getLowPriority : LiveData<List<ToDoModel>> = repository?.getLowPriority!!



    val listener :AdapterView.OnItemSelectedListener = object :AdapterView.OnItemSelectedListener{
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            when(position){
                0 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,
                        R.color.red))
                }
                1 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,
                        R.color.yellow))
                }
                2 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,
                        R.color.green))
                }
            }

        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}

    }

    fun insertData(toDoModel: ToDoModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository?.insertData(toDoModel)
        }
    }

    fun updateData(toDoModel: ToDoModel){
        viewModelScope.launch (Dispatchers.IO){
            repository?.updateData(toDoModel)
        }
    }

    fun deleteData(toDoModel: ToDoModel){
        viewModelScope.launch(Dispatchers.IO){
            repository?.deleteData(toDoModel)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository?.deleteAll()
        }
    }

    fun searchQuery(searchQuery:String):LiveData<List<ToDoModel>>?{
        return repository?.searchQuery(searchQuery)

    }
    fun verifyDataFromUser(title:String,desc:String):Boolean{
        return !(title.isEmpty() || desc.isEmpty())
    }

    fun checkIfDatabaseEmpty(toDoData:List<ToDoModel>){
        emptyDatabase.value = toDoData.isEmpty()
    }

    fun parsePriority(priority:String): Priority {
        return when(priority){
            "High Priority" -> {
                Priority.HIGH}
            "Medium Priority" -> {
                Priority.MEDIUM}
            "Low Priority" -> {
                Priority.LOW}
            else-> Priority.LOW
        }
    }

    fun parsePriorityToInt(priority: Priority) : Int{
        return when(priority){
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }

}