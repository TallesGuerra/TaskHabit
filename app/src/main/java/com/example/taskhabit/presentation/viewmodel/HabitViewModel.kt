package com.example.taskhabit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskhabit.data.local.entity.Habit
import com.example.taskhabit.data.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HabitViewModel @Inject constructor(
    private val repository: HabitRepository
) : ViewModel() {

    val allHabits: StateFlow<List<Habit>> = repository
        .allHabits
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val pendingHabits: StateFlow<List<Habit>> = repository
        .pendingHabits
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insertHabit(habit: Habit) {
        viewModelScope.launch { repository.insertHabit(habit) }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch { repository.updateHabit(habit) }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch { repository.deleteHabit(habit) }
    }

    fun toggleHabitCompletion(habit: Habit) {
        viewModelScope.launch {
            repository.toggleHabitCompletion(habit)

            if (!habit.isCompleted){
                completionRepository.recordCompletion(habit.id)
                streakRepository.onHabitCompleted(habit.id)
            } else {
                streakRepository.onHabitCompleted(habit.id)
            
            }
        }
    }
}
