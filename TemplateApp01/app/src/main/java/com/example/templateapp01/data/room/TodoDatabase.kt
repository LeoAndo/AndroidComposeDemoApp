package com.example.templateapp01.data.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.templateapp01.data.room.dao.TodoDao
import com.example.templateapp01.data.room.entity.TodoEntity

@Database(
    entities = [TodoEntity::class],
    version = 1
)
@TypeConverters(DateConverter::class)
internal abstract class TodoDatabase : RoomDatabase() {
    abstract fun onsenDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null
        fun getInstance(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context, TodoDatabase::class.java,
                    "onsen.db"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d("TodoDatabase", "!!!!onCreate!!!!!!!!!")
                        //pre-populate data
                    }
                }).build().also { INSTANCE = it }
            }
        }
    }
}