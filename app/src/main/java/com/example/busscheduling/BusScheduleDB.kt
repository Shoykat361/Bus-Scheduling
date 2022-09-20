package com.example.busscheduling

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [BusSchedule::class] , version = 3)
abstract class BusScheduleDB : RoomDatabase(){
    abstract fun getScheduleDao():ScheduleDao

    companion object{
        private var db :BusScheduleDB? = null
        private val migration_1_2:Migration= object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table 'table_schedule'add column 'favourite'integer not null default 0")
            }

        }
        private val migration_2_3:Migration= object :Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table 'table_schedule'add column 'image'text")
            }

        }
        fun getdb(context:Context):BusScheduleDB{
            if (db==null){
                db=Room.databaseBuilder(
                    context,
                    BusScheduleDB::class.java,"schedule_db")
                    .addMigrations(migration_1_2, migration_2_3)
                    //.allowMainThreadQueries()
                    .build()
                return db!!
            }

            return db!!

        }

    }
}