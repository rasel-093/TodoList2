package com.example.todolist.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.roomdb.TaskItem
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class TaskViewModel: ViewModel() {
    var taskItems = MutableLiveData<MutableList<TaskItem>>()
    init {
        taskItems.value = mutableListOf()
    }

    fun addTaskItem(taskItem: TaskItem){
        val list = taskItems.value
        list!!.add(taskItem)
        taskItems.postValue(list)
    }
    fun updateTaskItem(id: UUID, name: String, desc: String, dueTime: LocalTime?){
        val list = taskItems.value
        val task = list!!.find { it.id == id } !!
        task.name = name
        task.description = desc
        task.dueTime = dueTime
        taskItems.postValue(list)
    }

    fun setCompleted(taskItem: TaskItem){
        val list = taskItems.value
        val task = list!!.find { it.id == taskItem.id }!!
        if(task.completedDate == null ){
            task.completedDate = LocalDate.now()
        }
        taskItems.postValue(list)
    }

}