package com.example.taskhabit.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskhabit.data.local.dao.CategoryDao
import com.example.taskhabit.data.local.dao.HabitCompletionDao
import com.example.taskhabit.data.local.dao.HabitDao
import com.example.taskhabit.data.local.dao.StreakDao
import com.example.taskhabit.data.local.database.converter.DateConverter
import com.example.taskhabit.data.local.entity.Category
import com.example.taskhabit.data.local.entity.Habit
import com.example.taskhabit.data.local.entity.HabitCompletion
import com.example.taskhabit.data.local.entity.Streak

@Database(
    entities = [
        Category::class,
        Habit::class,
        Streak::class,
        HabitCompletion::class
    ],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun habitDao(): HabitDao
    abstract fun streakDao(): StreakDao
    abstract fun habitCompletionDao(): HabitCompletionDao

    companion object {
        @Volatile
        private var INSTANCE: HabitDatabase? = null

        fun getInstance(context: Context): HabitDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    "habit_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
