package com.example.taskhabit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskhabit.presentation.ui.screens.AddHabitScreen
import com.example.taskhabit.presentation.ui.screens.BadgesScreen
import com.example.taskhabit.presentation.ui.screens.EditHabitScreen
import com.example.taskhabit.presentation.ui.screens.HabitsScreen
import com.example.taskhabit.presentation.ui.screens.ProfileScreen
import com.example.taskhabit.presentation.ui.screens.SettingsScreen
import com.example.taskhabit.presentation.ui.screens.StatsScreen
import com.example.taskhabit.presentation.ui.screens.TodayScreen
import com.example.taskhabit.presentation.viewmodel.ProfileViewModel
import com.example.taskhabit.ui.theme.EnglishStrings
import com.example.taskhabit.ui.theme.LocalStrings
import com.example.taskhabit.ui.theme.PortugueseStrings
import com.example.taskhabit.ui.theme.TaskHabitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskHabitTheme {
                val profileViewModel: ProfileViewModel = hiltViewModel()
                val userProfile by profileViewModel.userProfile.collectAsStateWithLifecycle()
                val strings = if (userProfile.language == "pt") PortugueseStrings else EnglishStrings

                CompositionLocalProvider(LocalStrings provides strings) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "today"
                    ) {
                        composable("today") {
                            TodayScreen(
                                currentRoute = "today",
                                onNavigate = { route -> navController.navigate(route) },
                                onAddHabit = { navController.navigate("add_habit") },
                                onAvatarClick = { navController.navigate("profile") },
                                onSettingsClick = { navController.navigate("settings") }
                            )
                        }
                        composable("habits") {
                            HabitsScreen(
                                currentRoute = "habits",
                                onNavigate = { route -> navController.navigate(route) },
                                onAddHabit = { navController.navigate("add_habit") },
                                onEdit = { habitId -> navController.navigate("edit_habit/$habitId") },
                                onAvatarClick = { navController.navigate("profile") },
                                onSettingsClick = { navController.navigate("settings") }
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
                                onNavigate = { route -> navController.navigate(route) },
                                onAvatarClick = { navController.navigate("profile") },
                                onSettingsClick = { navController.navigate("settings") }
                            )
                        }
                        composable("badges") {
                            BadgesScreen(
                                currentRoute = "badges",
                                onNavigate = { route -> navController.navigate(route) },
                                onAvatarClick = { navController.navigate("profile") },
                                onSettingsClick = { navController.navigate("settings") }
                            )
                        }
                        composable("add_habit") {
                            AddHabitScreen(
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("profile") {
                            ProfileScreen(
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("settings") {
                            SettingsScreen(
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
