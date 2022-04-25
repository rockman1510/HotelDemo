package com.lastminute.hoteldemo.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lastminute.hoteldemo.BuildConfig
import com.lastminute.hoteldemo.roomdatabase.dao.HotelFilterDAO
import com.lastminute.hoteldemo.roomdatabase.entity.HotelFilterEntity

@Database(entities = [HotelFilterEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getHotelFilterDAO(): HotelFilterDAO

    companion object {
        private var INSTANCE: AppDataBase? = null
        fun getRoomDatabase(context: Context): AppDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDataBase::class.java,
                    BuildConfig.APPLICATION_ID + AppDataBase::class.java.simpleName
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}