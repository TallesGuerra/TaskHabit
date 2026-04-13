package com.example.taskhabit.data.repository

import com.example.taskhabit.data.local.dao.StreakDao
import com.example.taskhabit.data.local.entity.Streak
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class StreakRepository @Inject constructor(
    private val dao: StreakDao
) {
    fun getStreakForHabit(habitId: Int): Flow<Streak?> =
        dao.getStreakByHabit(habitId)

    suspend fun onHabitCompleted(habitId: Int) {
        val existing = dao.getStreakByHabitSync(habitId)
        val now = Date()

        if (existing == null) {
            dao.insertStreak(
                Streak(habitId = habitId, currentStreak = 1, longestStreak = 1, lastCompletedAt = now)
            )
            return
        }

        val lastCompleted = existing.lastCompletedAt
        val todayStart = startOfDay(now)
        val yesterdayStart = startOfDay(now, offsetDays = -1)

        val newCurrent = when {
            lastCompleted == null           -> 1
            lastCompleted >= todayStart     -> existing.currentStreak
            lastCompleted >= yesterdayStart -> existing.currentStreak + 1
            else                            -> 1
        }

        dao.updateStreak(
            existing.copy(
                currentStreak = newCurrent,
                longestStreak = maxOf(existing.longestStreak, newCurrent),
                lastCompletedAt = now
            )
        )
    }

    suspend fun onHabitUncompleted(habitId: Int) {
        val existing = dao.getStreakByHabitSync(habitId) ?: return
        dao.updateStreak(existing.copy(currentStreak = (existing.currentStreak - 1).coerceAtLeast(0)))
    }

    private fun startOfDay(date: Date, offsetDays: Int = 0): Date {
        return Calendar.getInstance().apply {
            time = date
            add(Calendar.DAY_OF_YEAR, offsetDays)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
    }
}
