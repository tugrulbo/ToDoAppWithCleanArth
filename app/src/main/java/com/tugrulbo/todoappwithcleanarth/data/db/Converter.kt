package com.tugrulbo.todoappwithcleanarth.data.db

import  androidx.room.TypeConverter
import com.tugrulbo.todoappwithcleanarth.data.model.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority):String{
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority:String):Priority{
        return Priority.valueOf(priority)
    }


}