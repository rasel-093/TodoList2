package com.example.todolist.roomdb

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(tableName = "task_item_table")
 class TaskItem(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "dueTimeString") var dueTimeString: String?, //: LocalTime?,
    @ColumnInfo(name = "completedDateString") var  completedDateString: String?, //LocalDate?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
){
    fun completedDate(): LocalDate? = if(completedDateString == null) null
            else LocalDate.parse(completedDateString, dateFormatter)
    fun dueTime(): LocalTime? = if(dueTimeString == null) null
    else LocalTime.parse(dueTimeString, timeFormatter)
    fun isCompleted() = completedDate() != null
    fun imageResource() : Int = if(isCompleted()) R.drawable.checked_box_icon else R.drawable.unchecked_box_icon
    fun imageColor(context: Context): Int = if(isCompleted()) purple(context) else black(context)

    private fun purple(context: Context) = ContextCompat.getColor(context, androidx.appcompat.R.color.material_grey_600 )
    private fun black(context: Context) = ContextCompat.getColor(context,R.color.black)

    companion object{
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_TIME
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE
    }
}
