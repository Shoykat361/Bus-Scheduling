package com.example.busscheduling

import androidx.lifecycle.LiveData

class ScheduleRepository( val scheduleDao: ScheduleDao) {

    suspend fun addSchedule(busSchedule: BusSchedule){
        scheduleDao.addSchedule(busSchedule)
    }
    suspend fun deleteSchedule(busSchedule: BusSchedule){
        scheduleDao.deleteSchedule(busSchedule)
    }
    suspend fun updateSchedule(busSchedule: BusSchedule){
        scheduleDao.updateSchedule(busSchedule)
    }
    fun  getAllschedule():LiveData<List<BusSchedule>> = scheduleDao.getAllschedule()
    //fun getSchedulebyId(id:Long)=scheduleDao.getSchedulebyId(id)
    fun getSchedulebyId(id:Long):LiveData<BusSchedule>
    =scheduleDao.getSchedulebyId(id)
}