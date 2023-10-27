package com.example.todolist.Fragment

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.example.todolist.databinding.FragmentNewTaskSheetBinding
import com.example.todolist.roomdb.TaskItem
import com.example.todolist.viewmodels.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        //Opening newTaskSheet in edit mode
        if(taskItem != null){
            binding.taskTitleIdEt.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.taskNameEtId.text = editable.newEditable(taskItem!!.name)
            binding.taskDescriptionEtId.text = editable.newEditable(taskItem!!.description)
        }
        //Opening newTaskSheet in New Task mode
        else{
            binding.taskTitleIdEt.text = "New Task"
        }

        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        binding.saveBtn.setOnClickListener{
            saveAction()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskSheetBinding.inflate(inflater,container,false)

        return binding.root
    }

    private fun saveAction() {
        val name = binding.taskNameEtId.text.toString()
        val desc = binding.taskDescriptionEtId.text.toString()

        if(taskItem == null){
            val newTask = TaskItem(name,desc,null,null)
            taskViewModel.addTaskItem(newTask)
        }else{
            taskViewModel.updateTaskItem(taskItem!!.id,name,desc,null)
        }

        binding.taskNameEtId.setText("")
        binding.taskDescriptionEtId.setText("")
        dismiss()
    }
}