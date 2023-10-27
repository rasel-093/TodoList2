package com.example.todolist

import com.example.todolist.roomdb.TaskItem

interface TaskItemClickListener {
    fun editTaskItem(taskItem: TaskItem)
    fun completeTaskItem(taskItem: TaskItem)
}