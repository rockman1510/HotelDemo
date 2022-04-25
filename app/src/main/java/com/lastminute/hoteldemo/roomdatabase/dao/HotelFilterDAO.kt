package com.lastminute.hoteldemo.roomdatabase.dao

import androidx.room.*
import com.lastminute.hoteldemo.roomdatabase.entity.HotelFilterEntity

@Dao
interface HotelFilterDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHotelFilterEntity(hotelFilterEntity: HotelFilterEntity?): Long

    @Query("SELECT * FROM HotelFilterEntity")
    suspend fun getHotelFilterEntity(): HotelFilterEntity

    @Query("DELETE FROM HotelFilterEntity")
    suspend fun deleteAll(): Int

    @Update
    suspend fun updateHotelFilterEntity(hotelFilterEntity: HotelFilterEntity?)

    @Transaction
    suspend fun insertOrUpdateHotelFilterEntity(hotelFilterEntity: HotelFilterEntity?){
        if(insertHotelFilterEntity(hotelFilterEntity) == -1L){
            updateHotelFilterEntity(hotelFilterEntity)
        }
    }

}