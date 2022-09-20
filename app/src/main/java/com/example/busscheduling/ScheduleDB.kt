package com.example.busscheduling

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

val citylist= listOf("Dhaka","Chittagong","comilla","Lakshmipur",
    "Cox Bazer","Teaknaf","Rangamati","Khulna","Rangpur","Pabna","faridpur"
)
val bustypeEconomy="Economy"
val bustypeBusiness="Business"
val bustypePremimum="premimum"
@Entity(tableName = "table_schedule")
data class BusSchedule(
    @PrimaryKey(autoGenerate = true)
    var id :Long,
    @ColumnInfo(name = "bus_name")
    val busname:String,
    val from :String,
    val to :String,
    @ColumnInfo(name = "depature_date")
    val Depaturedate:String,
    @ColumnInfo(name = "depature_time")
    val DepatureTime: String,
    val bustype:String,
    var favourite:Boolean=false,
    var image :String? ,
)
//val scheduleList= mutableListOf<BusSchedule>()
