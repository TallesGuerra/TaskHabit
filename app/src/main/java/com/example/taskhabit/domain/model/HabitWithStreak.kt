package com.example.taskhabit.domain.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.taskhabit.data.local.entity.Habit
import com.example.taskhabit.data.local.entity.Streak

data class HabitWithStreak(
    @Embedded val habit: Habit,
    @Relation(
        parentColumn = "id",
        entityColumn = "habitId"
    )
    val streak: Streak?
)
