package com.example.taskhabit.domain.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.taskhabit.data.local.entity.Category
import com.example.taskhabit.data.local.entity.Habit

data class HabitWithCategory(
    @Embedded val habit: Habit,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: Category
)
