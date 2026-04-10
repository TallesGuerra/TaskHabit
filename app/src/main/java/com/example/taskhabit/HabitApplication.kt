package com.example.taskhabit

import android.app.Application
import androidx.work.*
import com.example.taskhabit.worker.ResetHabitsWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.Calendar
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class HabitApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        scheduleDailyReset()
    }

    private fun scheduleDailyReset() {
        // Calcula o tempo até a próxima meia-noite
        val now = Calendar.getInstance()
        val midnight = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val delayMs = midnight.timeInMillis - now.timeInMillis

        val resetRequest = PeriodicWorkRequestBuilder<ResetHabitsWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(delayMs, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_habit_reset",
            ExistingPeriodicWorkPolicy.KEEP,  // se já existe, não reagenda
            resetRequest
        )
    }
}
