package com.example.todolist.roomdb

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.todolist.R
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

 class TaskItem(
    var name: String,
    var description: String,
    var dueTime: LocalTime?,
    var  completedDate: LocalDate?,
    var id: UUID = UUID.randomUUID()
){
    fun isCompleted() = completedDate != null
    fun imageResource() : Int = if(isCompleted()) R.drawable.checked_box_icon else R.drawable.unchecked_box_icon
    fun imageColor(context: Context): Int = if(isCompleted()) purple(context) else black(context)

    private fun purple(context: Context) = ContextCompat.getColor(context, androidx.appcompat.R.color.material_grey_600 )
    private fun black(context: Context) = ContextCompat.getColor(context,R.color.black)
}
