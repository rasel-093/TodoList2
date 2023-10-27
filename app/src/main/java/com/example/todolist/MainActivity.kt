package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.Fragment.NewTaskSheet
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.roomdb.TaskItem
import com.example.todolist.viewmodels.TaskItemModelFactory
import com.example.todolist.viewmodels.TaskViewModel

class MainActivity : AppCompatActivity(),TaskItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels{
        TaskItemModelFactory((application as ToDoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Fetching TaskViewModel and assigning to taskViewModel
        //taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        //New Task button onClickListener
        binding.newTaskBtnId.setOnClickListener{
            NewTaskSheet(null).show(supportFragmentManager,"newTaskTag")
        }

        setRecyclerView()
    }

    private fun setRecyclerView() {
        val mainActivity = this
        taskViewModel.taskItems.observe(this){
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(it,mainActivity)
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(supportFragmentManager,"newTaskTag")
    }

    override fun completeTaskItem(taskItem: TaskItem) {
        if(taskItem.isCompleted()){
            Log.d("Completed","Already completed")
            //Uncheck to do
        }
        else{
            taskViewModel.setCompleted(taskItem)
        }
    }
}