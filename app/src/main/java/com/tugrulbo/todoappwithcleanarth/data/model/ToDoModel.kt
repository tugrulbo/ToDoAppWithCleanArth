package com.tugrulbo.todoappwithcleanarth.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tugrulbo.todoappwithcleanarth.Utils.Constants.TABLE_NAME
import java.io.Serializable


@Entity(
    tableName = TABLE_NAME
)
data class ToDoModel (
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val todoName:String,
    val priority: Priority,
    val desc:String ):Serializable
