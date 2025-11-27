package com.example.todoapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ToDoListTable")
data class Entity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String
)
