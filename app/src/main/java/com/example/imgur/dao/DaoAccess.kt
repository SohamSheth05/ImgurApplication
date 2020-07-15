package com.example.imgur.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.imgur.model.Image


@Dao
interface DaoAccess {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTask(image: ImageEntity)


    @Query("SELECT * FROM ImageEntity")
    fun fetchAllTasks(): LiveData<List<ImageEntity>>


    @Query("SELECT * FROM ImageEntity WHERE id =:taskId")
    fun getTask(taskId: String): ImageEntity

    @Query("SELECT * FROM ImageEntity WHERE id =:taskId and comments=:isVisited")
    fun getTask(taskId: String, isVisited: String): ImageEntity

    @Update
    fun updateTask(note: ImageEntity)


    @Delete
    fun deleteTask(note: ImageEntity)
}
