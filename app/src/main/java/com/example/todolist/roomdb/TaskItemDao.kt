package com.example.todolist.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import java.util.concurrent.Flow

@Dao
interface TaskItemDao {
    @Query("SELECT * FROM task_item_table ORDER BY id ASC")
    fun allTaskItems(): kotlinx.coroutines.flow.Flow<List<TaskItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserTaskItem(taskItem: TaskItem)
    @Update
    suspend fun updateTaskItem(taskItem: TaskItem)
    @Delete
    suspend fun deleteTaskItem(taskItem: TaskItem)
}