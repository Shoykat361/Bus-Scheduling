package com.example.busscheduling

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ScheduleDao {
    @Insert
    suspend fun addSchedule(busSchedule: BusSchedule)
    @Update
    suspend fun updateSchedule(busSchedule: BusSchedule)

    @Delete
    suspend fun deleteSchedule(busSchedule: BusSchedule)

    @Query("select * from table_schedule")
    fun getAllschedule(): LiveData<List<BusSchedule>>
    @Query("select * from table_schedule where id = :id")
    fun getSchedulebyId(id:Long):LiveData<BusSchedule>

}