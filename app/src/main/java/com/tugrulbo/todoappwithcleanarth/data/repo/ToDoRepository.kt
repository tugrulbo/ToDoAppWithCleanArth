package com.tugrulbo.todoappwithcleanarth.data.repo

import androidx.lifecycle.LiveData
import com.tugrulbo.todoappwithcleanarth.data.db.ToDoDAO
import com.tugrulbo.todoappwithcleanarth.data.model.ToDoModel

class ToDoRepository(private val toDoDAO: ToDoDAO) {

    val getAllData : LiveData<List<ToDoModel>> = toDoDAO.getAllData()
    val getHighPriority: LiveData<List<ToDoModel>> = toDoDAO.sortByHighPriority()
    val getLowPriority: LiveData<List<ToDoModel>> = toDoDAO.sortByLowPriority()

    suspend fun insertData(toDoModel: ToDoModel) = toDoDAO.insertData(toDoModel)

    suspend fun updateData(toDoModel: ToDoModel) = toDoDAO.updateData(toDoModel)

    suspend fun deleteData(toDoModel: ToDoModel) = toDoDAO.deleteData(toDoModel)

    suspend fun deleteAll() = toDoDAO.deleteAll()

    fun searchQuery(searchQuery:String):LiveData<List<ToDoModel>> = toDoDAO.searchQuery(searchQuery)
}