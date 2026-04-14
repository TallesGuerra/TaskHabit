package com.example.taskhabit

import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskhabit.presentation.ui.screens.AddHabitScreen
import com.example.taskhabit.presentation.ui.screens.BadgesScreen
import com.example.taskhabit.presentation.ui.screens.HabitsScreen
import com.example.taskhabit.presentation.ui.screens.StatsScreen
import com.example.taskhabit.presentation.ui.screens.TodayScreen
import com.example.taskhabit.ui.theme.TaskHabitTheme
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.taskhabit.presentation.ui.screens.EditHabitScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskHabitTheme {
                val navController = rememberNavController()
                NavHost(navController = navController,
                        startDestination = "today"
                    ) {
                    composable("today") {
                        TodayScreen(
                            currentRoute = "today",
                            onNavigate = { route -> navController.navigate(route) },
                            onAddHabit = { navController.navigate("add_habit") }
                        )
                    }
                    composable("habits") {
                        HabitsScreen(
                            currentRoute = "habits",
                            onNavigate = { route -> navController.navigate(route) },
                            onAddHabit = { navController.navigate("add_habit") },
                            onEdit = { habitId -> navController.navigate("edit_habit/$habitId") }
                        )
                    }

                    composable(
                        route = "edit_habit/{habitId}",
                        arguments = listOf(navArgument("habitId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val habitId = backStackEntry.arguments?.getInt("habitId") ?: return@composable
                        EditHabitScreen(
                            habitId = habitId,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable("stats") {
                        StatsScreen(
                            currentRoute = "stats",
                            onNavigate = { route -> navController.navigate(route) }
                        )
                    }
                    composable("badges") {
                        BadgesScreen(
                            currentRoute = "badges",
                            onNavigate = { route -> navController.navigate(route) }
                        )
                    }
                    composable("add_habit") {
                        AddHabitScreen(
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
