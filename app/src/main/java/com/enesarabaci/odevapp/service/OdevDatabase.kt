package com.enesarabaci.odevapp.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.enesarabaci.odevapp.model.Odev

@Database(entities = arrayOf(Odev::class), version = 1)
abstract class OdevDatabase : RoomDatabase() {

    abstract fun dao() : OdevDAO

    companion object {
        @Volatile private var instance : OdevDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(Any()) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        fun createDatabase(context: Context) : OdevDatabase {
            return Room.databaseBuilder(context, OdevDatabase::class.java, "database").build()
        }

    }

}