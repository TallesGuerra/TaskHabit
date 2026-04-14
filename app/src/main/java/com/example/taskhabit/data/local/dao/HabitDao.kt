package com.example.taskhabit.data.local.dao

import androidx.room.*
import com.example.taskhabit.data.local.entity.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<Habit>>
    
    @Query("SELECT * FROM habits WHERE id = :id")
    fun getHabitById(id: Int): Flow<Habit?>

    @Query("SELECT * FROM habits WHERE categoryId = :categoryId")
    fun getHabitsByCategory(categoryId: Int): Flow<List<Habit>>

    @Query("SELECT * FROM habits WHERE isCompleted = 0")
    fun getPendingHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habits WHERE isCompleted = 1")
    fun getCompletedHabits(): Flow<List<Habit>>

    @Query("UPDATE habits SET isCompleted = 0")
    suspend fun resetAllHabits(): Int


}
