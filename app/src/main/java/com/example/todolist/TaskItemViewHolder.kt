package com.example.todolist

import android.content.Context
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.FragmentNewTaskSheetBinding
import com.example.todolist.databinding.TaskItemCardBinding
import com.example.todolist.roomdb.TaskItem
import java.time.format.DateTimeFormatter

class TaskItemViewHolder(
    private val context: Context,
    private val binding: TaskItemCardBinding,
    private val clickListener: TaskItemClickListener
): RecyclerView.ViewHolder(binding.root) {
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

    fun bindTaskItem(taskItem: TaskItem){
        binding.nameView.text = taskItem.name

        if(taskItem.isCompleted()){
            //Line through text
            binding.nameView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.dueTimeView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
        binding.completeBtn.setImageResource(taskItem.imageResource())
        binding.completeBtn.setColorFilter(taskItem.imageColor(context))

        binding.completeBtn.setOnClickListener{
            clickListener.completeTaskItem(taskItem)
        }
        binding.taskContainerCard.setOnClickListener {
            clickListener.editTaskItem(taskItem)
        }

        if(taskItem.dueTime != null){
            binding.dueTimeView.text = timeFormat.format(taskItem.dueTime)
        }
        else
            binding.dueTimeView.text = ""
    }
}