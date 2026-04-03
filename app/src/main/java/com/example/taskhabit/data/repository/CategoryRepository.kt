package com.example.taskhabit.data.repository

import com.example.taskhabit.data.local.dao.CategoryDao
import com.example.taskhabit.data.local.entity.Category
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: CategoryDao) {

    val allCategories: Flow<List<Category>> =
        categoryDao.getAllCategories()

    suspend fun insertCategory(category: Category) =
        categoryDao.insertCategory(category)

    suspend fun updateCategory(category: Category) =
        categoryDao.updateCategory(category)

    suspend fun deleteCategory(category: Category) =
        categoryDao.deleteCategory(category)
}
