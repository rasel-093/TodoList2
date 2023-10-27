package com.example.todolist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.roomdb.TaskItem
import com.example.todolist.roomdb.TaskItemRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class TaskViewModel(private val repository: TaskItemRepository): ViewModel() {
    var taskItems: LiveData<List<TaskItem>> = repository.allTaskItems.asLiveData()
//    init {
//        taskItems.value = mutableListOf()
//    }

    fun addTaskItem(newTask: TaskItem) = viewModelScope.launch {
        repository.insertTaskItem(newTask)
    }
    fun updateTaskItem(taskItem: TaskItem) = viewModelScope.launch {
        repository.updateTaskItem(taskItem)
    }

    fun setCompleted(taskItem: TaskItem) = viewModelScope.launch {
        if(!taskItem.isCompleted()){
            taskItem.completedDateString = TaskItem.dateFormatter.format(LocalDate.now())
        }

        repository.updateTaskItem(taskItem)
    }
}

class TaskItemModelFactory(
    private val repository: TaskItemRepository
):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)){
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class for viewmodel")
        return super.create(modelClass)
    }
}