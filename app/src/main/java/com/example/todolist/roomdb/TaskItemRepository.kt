package com.example.todolist.roomdb

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class TaskItemRepository(
    private val taskItemDao: TaskItemDao
)
{
    val allTaskItems: Flow<List<TaskItem>> = taskItemDao.allTaskItems()

    @WorkerThread
    suspend fun insertTaskItem(taskItem: TaskItem){
        taskItemDao.inserTaskItem(taskItem)
    }

    @WorkerThread
    suspend fun updateTaskItem(taskItem: TaskItem){
        taskItemDao.updateTaskItem(taskItem)
    }
}