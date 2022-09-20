package com.example.busscheduling

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ScheduleViewModel(application:Application):AndroidViewModel(application) {
    /*fun addSchedule(schedule: BusSchedule){
        scheduleList.add(schedule)
    }
    fun getSchedule()= scheduleList*/
    private lateinit var repository: ScheduleRepository
    init {
        val Dao = BusScheduleDB.getdb(application).getScheduleDao()
        repository=ScheduleRepository(Dao)
    }
    fun addSchedule(busSchedule: BusSchedule){


        viewModelScope.launch {
            repository.addSchedule(busSchedule)

        }

    }
    fun deleteSchedule(busSchedule: BusSchedule){
        viewModelScope.launch {
            repository.deleteSchedule(busSchedule)

        }


    }
    fun updateSchedule(busSchedule: BusSchedule){
        viewModelScope.launch {
            repository.updateSchedule(busSchedule)
        }
    }
    fun  getAllschedule():LiveData<List<BusSchedule>> = repository.getAllschedule()
    fun getSchedulebyId(id:Long)=repository.getSchedulebyId(id)

}