package com.example.imgur.repository

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.imgur.dao.ImageEntity
import com.example.imgur.db.OrderDataBase
import com.example.imgur.model.Image
import java.util.*


class CommentsRepository(private val context: Context) {


    val tasks: LiveData<List<ImageEntity>>?
        get() = OrderDataBase.getDatabase(context).daoAccess().fetchAllTasks()


    fun insertTask(requestQueueId: String, isVisited: String) {

        val note = ImageEntity()
        note.id = requestQueueId
        note.comments = isVisited


        insertTask(note)
    }

    private fun insertTask(note: ImageEntity) {
        OrderDataBase.getDatabase(context).daoAccess().insertTask(note)
    }

    fun updateTask(note: ImageEntity) {
        OrderDataBase.getDatabase(context).daoAccess().updateTask(note)
    }

    @SuppressLint("StaticFieldLeak")
    fun deleteTask(id: String, isVisited: String) {
        val task = getTask(id, isVisited)
        OrderDataBase.getDatabase(context).daoAccess().deleteTask(Objects.requireNonNull(task))
    }

    @SuppressLint("StaticFieldLeak")
    fun deleteTask(note: ImageEntity) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                OrderDataBase.getDatabase(context).daoAccess().deleteTask(note)
                return null
            }
        }.execute()
    }

    fun getTask(id: String, isVisited: String): ImageEntity {
        return OrderDataBase.getDatabase(context).daoAccess().getTask(id, isVisited)
    }

    fun getTask(id: String): ImageEntity {
        return OrderDataBase.getDatabase(context).daoAccess().getTask(id)
    }
}
