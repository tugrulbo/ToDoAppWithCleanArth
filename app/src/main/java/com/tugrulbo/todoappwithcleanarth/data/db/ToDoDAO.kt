package com.tugrulbo.todoappwithcleanarth.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tugrulbo.todoappwithcleanarth.data.model.ToDoModel

@Dao
interface ToDoDAO {

    @Query("SELECT * FROM todoList ORDER BY id ASC")
    fun getAllData():LiveData<List<ToDoModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(todo:ToDoModel)

    @Update
    suspend fun updateData(data:ToDoModel)

    @Delete
    suspend fun deleteData(data:ToDoModel)

    @Query("DELETE FROM todoList")
    suspend fun deleteAll()

    @Query("SELECT * FROM todoList WHERE todoName LIKE :searchQuery")
    fun searchQuery(searchQuery:String): LiveData<List<ToDoModel>>

}