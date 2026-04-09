package com.example.taskhabit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskhabit.presentation.ui.screens.AddHabitScreen
import com.example.taskhabit.presentation.ui.screens.BadgesScreen
import com.example.taskhabit.presentation.ui.screens.StatsScreen
import com.example.taskhabit.presentation.ui.screens.TodayScreen
import com.example.taskhabit.ui.theme.TaskHabitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskHabitTheme {
                val navController = rememberNavController()
                NavHost(navController = navController,
                        startDestination = "Today"
                    ) {
                    composable("today") {
                        TodayScreen(
                            currentRoute = "today",
                            onNavigate = { route -> navController.navigate(route) },
                            onAddHabit = { navController.navigate("add_habit") }
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
                            onBack = { navController.popBackStack() } //volta para a tela anterior
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskHabitTheme {
        Greeting("Android")
    }
}