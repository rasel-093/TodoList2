package com.example.todolist.Fragment

import android.app.TimePickerDialog
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
import java.time.LocalTime


class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        //Opening newTaskSheet in edit mode , getting previous text
        if(taskItem != null){
            binding.taskTitleIdEt.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.taskNameEtId.text = editable.newEditable(taskItem!!.name)
            binding.taskDescriptionEtId.text = editable.newEditable(taskItem!!.description)
            if(taskItem!!.dueTime != null){
                dueTime = taskItem!!.dueTime!!
                updateTimeButtonText()
            }
        }
        //Opening newTaskSheet in New Task mode
        else{
            binding.taskTitleIdEt.text = "New Task"
        }

        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        binding.saveBtn.setOnClickListener{
            saveAction()
        }
        binding.timePickerBtn.setOnClickListener {
            openTimePicker()
        }
    }

    private fun openTimePicker() {
        if(dueTime == null){
            dueTime = LocalTime.now()
            val listener = TimePickerDialog.OnTimeSetListener{_,selectedHour,selectedMinute ->
                dueTime = LocalTime.of(selectedHour,selectedMinute)
                updateTimeButtonText()
            }
            val dialog = TimePickerDialog(activity,listener,dueTime!!.hour,dueTime!!.minute,true)
            dialog.setTitle("Task Due")
            dialog.show()
        }
    }

    private fun updateTimeButtonText() {
        binding.timePickerBtn.text = String.format("%02d.%02d",dueTime!!.hour,dueTime!!.minute)
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
            val newTask = TaskItem(name,desc,dueTime,null)
            taskViewModel.addTaskItem(newTask)
        }else{
            taskViewModel.updateTaskItem(taskItem!!.id,name,desc,dueTime)
        }

        binding.taskNameEtId.setText("")
        binding.taskDescriptionEtId.setText("")
        dismiss()
    }
}