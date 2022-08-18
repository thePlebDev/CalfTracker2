package com.elliottsoftware.calftracker2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elliottsoftware.calftracker2.daos.CalfDao
import com.elliottsoftware.calftracker2.models.Calf
import com.elliottsoftware.calftracker2.util.DateTypeConverter

@Database(entities = [Calf::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
public abstract class CalfRoomDatabase : RoomDatabase(){

    abstract fun calfDao(): CalfDao

    companion object{
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CalfRoomDatabase? = null //companion property

        fun getDatabase(context: Context): CalfRoomDatabase{
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this){

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CalfRoomDatabase::class.java,
                    "calf_database"
                ).build()
                INSTANCE = instance
                 instance
            }
        }

    }
}