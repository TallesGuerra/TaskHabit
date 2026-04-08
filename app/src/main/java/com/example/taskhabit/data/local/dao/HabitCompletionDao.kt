package com.example.taskhabit.data.local.dao

import androidx.room.*
import com.example.taskhabit.data.local.entity.HabitCompletion
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitCompletionDao {
    @Insert
    suspend fun insertCompletion(completion: HabitCompletion)

    @Query("SELECT * FROM habit_completions WHERE habitId = :habitId")
    fun getCompletionsByHabit(habitId: Int): Flow<List<HabitCompletion>>

    @Query("""
        SELECT * FROM habit_completions
        WHERE habitId = :habitId
        AND completedAt >= :startDate
    """)
    fun getCompletionsByHabitAndDate(
        habitId: Int,
        startDate: Long
    ): Flow<List<HabitCompletion>>
}
