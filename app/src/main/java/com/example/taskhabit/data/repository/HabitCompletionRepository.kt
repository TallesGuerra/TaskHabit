package com.example.taskhabit.data.repository

import com.example.taskhabit.data.local.dao.HabitCompletionDao
import com.example.taskhabit.data.local.entity.HabitCompletion
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class HabitCompletionRepository @Inject constructor(
    private val dao: HabitCompletionDao
){
    fun getCompletionsForHabit(habitId: Int) : Flow<List<HabitCompletion>> = 
        dao.getCompletionsByHabit(habitId)

    suspend fun recordCompletion(habitId: Int) {
      dao.insertCompletion(HabitCompletion(habitId = habitId, completedAt = Date()))      

    }   

}