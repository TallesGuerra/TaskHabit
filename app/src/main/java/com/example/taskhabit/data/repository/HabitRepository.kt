package com.example.taskhabit.data.repository

import com.example.taskhabit.data.local.dao.HabitDao
import com.example.taskhabit.data.local.entity.Habit
import kotlinx.coroutines.flow.Flow

class HabitRepository(private val habitDao: HabitDao) {

    val allHabits: Flow<List<Habit>> = habitDao.getAllHabits()
    val pendingHabits: Flow<List<Habit>> = habitDao.getPendingHabits()
    val completedHabits: Flow<List<Habit>> = habitDao.getCompletedHabits()

    suspend fun insertHabit(habit: Habit) = habitDao.insertHabit(habit)
    suspend fun updateHabit(habit: Habit) = habitDao.updateHabit(habit)
    suspend fun deleteHabit(habit: Habit) = habitDao.deleteHabit(habit)

    suspend fun toggleHabitCompletion(habit: Habit) {
        habitDao.updateHabit(
            habit.copy(isCompleted = !habit.isCompleted)
        )
    }

    fun getHabitsByCategory(categoryId: Int): Flow<List<Habit>> =
        habitDao.getHabitsByCategory(categoryId)
}
