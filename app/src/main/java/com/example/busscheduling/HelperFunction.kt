package com.example.busscheduling

import java.text.SimpleDateFormat
import java.util.*

fun getformatedDatetime(value:Long,format:String) :String{
    return SimpleDateFormat(format).format(Date(value))

}