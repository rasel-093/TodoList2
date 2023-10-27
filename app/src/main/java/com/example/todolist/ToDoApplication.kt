package com.example.todolist

import android.app.Application
import com.example.todolist.roomdb.TaskItemDB
import com.example.todolist.roomdb.TaskItemRepository

class ToDoApplication: Application() {
    private val databse by lazy { TaskItemDB.getDB(this) }
    val repository by lazy { TaskItemRepository(databse.taskItemDao()) }

}