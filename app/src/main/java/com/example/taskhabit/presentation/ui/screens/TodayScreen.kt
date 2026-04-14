package com.example.taskhabit.presentation.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskhabit.data.local.entity.Habit
import com.example.taskhabit.presentation.ui.components.HabitBottomNavBar
import com.example.taskhabit.presentation.ui.components.KineticTopAppBar
import com.example.taskhabit.presentation.ui.util.habitIconFor
import com.example.taskhabit.presentation.viewmodel.HabitViewModel
import com.example.taskhabit.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayScreen(
    currentRoute: String = "today",
    onNavigate: (String) -> Unit = {},
    onAddHabit: () -> Unit = {},
    onAvatarClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    viewModel: HabitViewModel = hiltViewModel()
) {
    val habits by viewModel.allHabits.collectAsStateWithLifecycle()
    val completed = habits.count { it.isCompleted }
    val total = habits.size

    Scaffold(
        containerColor = Background,
        topBar = { KineticTopAppBar(onAvatarClick = onAvatarClick, onSettingsClick = onSettingsClick) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddHabit,
                containerColor = Primary,
                contentColor = OnPrimary,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Habit",
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        bottomBar = {
            HabitBottomNavBar(currentRoute = currentRoute, onNavigate = onNavigate)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                ProgressHeroSection(completed = completed, total = total)
                Spacer(modifier = Modifier.height(24.dp))
                TodaySectionHeader()
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (habits.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No habits yet.\nTap + to create your first ritual!",
                            color = OnSurfaceVariant,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 22.sp
                        )
                    }
                }
            } else {
                items(habits, key = { it.id }) { habit ->
                    HabitListItem(
                        habit = habit,
                        onToggle = { viewModel.toggleHabitCompletion(habit) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
                DecorativeBanner()
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun HabitListItem(habit: Habit, onToggle: () -> Unit) {
    val icon = habitIconFor(habit.name)

    if (habit.isCompleted) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Primary.copy(alpha = 0.1f))
                .border(1.dp, Primary.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                .clickable { onToggle() }
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(
                    modifier = Modifier.size(56.dp).clip(CircleShape).background(Primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = OnPrimary, modifier = Modifier.size(28.dp))
                }
                Column {
                    Text(
                        text = habit.name,
                        color = OnSurface.copy(alpha = 0.5f),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Text(text = "Completed", color = Primary, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                }
            }
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape).background(Primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Done", tint = OnPrimary)
            }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceContainerLow)
                .clickable { onToggle() }
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(
                    modifier = Modifier.size(56.dp).clip(CircleShape).background(SurfaceContainerHigh),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = Secondary, modifier = Modifier.size(28.dp))
                }
                Column {
                    Text(text = habit.name, color = OnSurface, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    if (habit.description.isNotBlank()) {
                        Text(text = habit.description, color = OnSurfaceVariant, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
            Box(modifier = Modifier.size(40.dp).clip(CircleShape).border(2.dp, OutlineVariant, CircleShape))
        }
    }
}

@Composable
private fun ProgressHeroSection(completed: Int, total: Int) {
    val momentumText = if (total == 0) "0/0" else "$completed/$total"

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1.4f)
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Primary)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val cx = size.width + 64f
                val cy = size.height + 64f
                drawCircle(color = Color.White.copy(alpha = 0.2f), radius = 160f, center = Offset(cx, cy), style = Stroke(width = 32f))
                drawCircle(color = Color.White.copy(alpha = 0.15f), radius = 110f, center = Offset(cx, cy), style = Stroke(width = 16f))
            }
            Column(modifier = Modifier.align(Alignment.TopStart).padding(24.dp)) {
                Text(text = "Current Momentum", color = OnPrimary.copy(alpha = 0.8f), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = momentumText, color = OnPrimary, fontSize = 52.sp, fontWeight = FontWeight.Black, lineHeight = 52.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (total == 0) "Add your first habit to get started." else "Keep going, you're doing great!",
                    color = OnPrimary.copy(alpha = 0.9f),
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(TertiaryContainer)
                .padding(20.dp)
        ) {
            Column {
                Icon(imageVector = Icons.Default.FormatQuote, contentDescription = null, tint = OnTertiaryContainer, modifier = Modifier.size(36.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "\"Flow is the result of focused energy.\"", color = OnTertiaryContainer, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, lineHeight = 18.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "— KINETIC MINDSET", color = OnTertiaryContainer.copy(alpha = 0.7f), fontSize = 9.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 1.sp)
            }
        }
    }
}

@Composable
private fun TodaySectionHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column {
            Text(text = "Today's Rituals", color = OnSurface, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = (-0.5).sp)
            Text(text = "Keep the streak alive", color = OnSurfaceVariant, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun DecorativeBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.horizontalGradient(colors = listOf(Primary.copy(alpha = 0.3f), Secondary.copy(alpha = 0.2f)))),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "YOUR POTENTIAL IS INFINITE", color = Primary.copy(alpha = 0.6f), fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
    }
}
