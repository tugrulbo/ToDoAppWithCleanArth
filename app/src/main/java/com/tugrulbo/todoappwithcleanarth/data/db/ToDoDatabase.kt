package com.tugrulbo.todoappwithcleanarth.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tugrulbo.todoappwithcleanarth.Utils.Constants.DATABASE_NAME
import com.tugrulbo.todoappwithcleanarth.data.model.ToDoModel


@Database(entities = [ToDoModel::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ToDoDatabase:RoomDatabase() {

    abstract fun toDoDao():ToDoDAO

    companion object{

        @Volatile
        private var INSTANCE:ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase? {
            val tempInstance = INSTANCE
            if(INSTANCE != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}