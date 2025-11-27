package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DAO {
    @Insert
    suspend fun insertTask(entity: Entity): Long

    @Update
    suspend fun updateTask(entity: Entity)

    @Delete
    suspend fun deleteOne(entity: Entity)

    @Query("DELETE FROM ToDoListTable")
    suspend fun deleteAll()

    @Query("SELECT * FROM ToDoListTable")
    fun getTask(): LiveData<List<Entity>>

    @Query("SELECT title FROM ToDoListTable WHERE id = :id")
    fun getTitleById(id: Int): LiveData<String>

}