package com.example.imgur.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity
class ImageEntity : Serializable {


    var id: String = ""
    @PrimaryKey(autoGenerate = true)
    var requestQueueId: Int = 0
    var comments: String = ""
    override fun toString(): String {
        return "OrderData(id=$id, comments=$comments)"
    }


}