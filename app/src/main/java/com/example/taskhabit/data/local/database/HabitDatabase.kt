package com.example.taskhabit.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
    version = 2
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

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE habits ADD COLUMN frequency TEXT NOT NULL DEFAULT 'DAILY'")
            }
        }

        private val seedCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL("INSERT INTO categories (name, color) VALUES ('Wellness',  '#10B981')")
                db.execSQL("INSERT INTO categories (name, color) VALUES ('Knowledge', '#0EA5E9')")
                db.execSQL("INSERT INTO categories (name, color) VALUES ('Fitness',   '#14B8A6')")
            }
        }

        fun getInstance(context: Context): HabitDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    "habit_database"
                )
                .addMigrations(MIGRATION_1_2)
                .addCallback(seedCallback)
                .build()
                .also { INSTANCE = it }
            }
        }
    }
}
