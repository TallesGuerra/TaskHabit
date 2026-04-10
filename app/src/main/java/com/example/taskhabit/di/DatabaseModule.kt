package com.example.taskhabit.di

import android.content.Context
import com.example.taskhabit.data.local.dao.CategoryDao
import com.example.taskhabit.data.local.dao.HabitCompletionDao
import com.example.taskhabit.data.local.dao.HabitDao
import com.example.taskhabit.data.local.dao.StreakDao
import com.example.taskhabit.data.local.database.HabitDatabase
import com.example.taskhabit.data.repository.CategoryRepository
import com.example.taskhabit.data.repository.HabitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    
    @Provides 
    @Singleton
    fun provideHabitDatabase(@ApplicationContext context: Context): HabitDatabase {
        return HabitDatabase.getInstance(context)
    }
  
    @Provides
    @Singleton
    fun provideHabitDao(db: HabitDatabase): HabitDao = db.habitDao()

    @Provides
    @Singleton
    fun provideCategoryDao(db: HabitDatabase): CategoryDao = db.categoryDao()

    @Provides
    @Singleton
    fun provideStreakDao(db: HabitDatabase): StreakDao = db.streakDao()

    @Provides
    @Singleton
    fun provideHabitCompletionDao(db: HabitDatabase): HabitCompletionDao = db.habitCompletionDao()

    
    @Provides
    @Singleton
    fun provideHabitRepository(habitDao: HabitDao): HabitRepository = HabitRepository(habitDao)

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository = CategoryRepository(categoryDao)

    @Provides 
    @Singleton
    fun provideHabitCompletionRepository(dao: HabitCompletionDao): HabitCompletionRepository =
        HabitCompletionRepository(dao)

    @Provides 
    @Singleton
    fun provideStreakRepository(dao: StreakDao): StreakRepository =
        StreakRepository(dao)

}
