package com.example.taskhabit.data.local.dao

import androidx.room.*
import com.example.taskhabit.data.local.entity.Streak
import kotlinx.coroutines.flow.Flow

@Dao
interface StreakDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStreak(streak: Streak)

    @Update
    suspend fun updateStreak(streak: Streak)

    @Query("SELECT * FROM streaks WHERE habitId = :habitId")
    fun getStreakByHabit(habitId: Int): Flow<Streak?>
}
