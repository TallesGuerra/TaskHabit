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

// @Module → diz ao Hilt que essa classe contém receitas de como criar objetos
// @InstallIn(SingletonComponent) → essas dependências vivem enquanto o APP viver
//   (uma instância só, compartilhada em todo lugar — igual um Singleton)
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // @Provides → ensina o Hilt a criar o HabitDatabase
    // @Singleton → cria UMA única instância e reutiliza sempre
    // @ApplicationContext → o Hilt injeta automaticamente o Context do app aqui
    @Provides
    @Singleton
    fun provideHabitDatabase(@ApplicationContext context: Context): HabitDatabase {
        return HabitDatabase.getInstance(context)
    }

    // O Hilt já sabe criar HabitDatabase (receita acima),
    // então consegue criar o HabitDao chamando db.habitDao()
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

    // Com o HabitDao disponível, o Hilt agora sabe criar o HabitRepository
    @Provides
    @Singleton
    fun provideHabitRepository(habitDao: HabitDao): HabitRepository = HabitRepository(habitDao)

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository = CategoryRepository(categoryDao)
}
