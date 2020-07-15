package com.example.imgur.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.imgur.dao.DaoAccess
import com.example.imgur.dao.ImageEntity
import com.example.imgur.model.Image


@Database(entities = [ImageEntity::class], version = 1, exportSchema = false)
abstract class OrderDataBase : RoomDatabase() {


    abstract fun daoAccess(): DaoAccess

    companion object {
        @Volatile
        private var INSTANCE: OrderDataBase? = null

        fun getDatabase(context: Context): OrderDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrderDataBase::class.java,
                    "order_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

