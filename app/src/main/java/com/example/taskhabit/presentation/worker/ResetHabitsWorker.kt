package com.example.taskhabit.worker


import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.taskhabit.data.local.database.HabitDatabase


class ResetHabitsWorker(
    context: Context,
    params: WorkerParameters

) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val db = HabitDatabase.getInstance(applicationContext)

        db.habitDao().resetAllHabits()

        return Result.success()
    }
}

