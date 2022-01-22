package com.example.templateapp01.di

import android.content.Context
import com.example.templateapp01.data.room.TodoDatabase
import com.example.templateapp01.data.room.dao.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    internal fun provideTodoDatabase(@ApplicationContext context: Context): TodoDatabase {
        return TodoDatabase.getInstance(context)
    }

    @Provides
    internal fun provideTodoDao(database: TodoDatabase): TodoDao {
        return database.onsenDao()
    }
}