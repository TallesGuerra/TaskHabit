package com.example.taskhabit.presentation.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.graphics.Color
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
import com.example.taskhabit.presentation.viewmodel.CategoryViewModel
import com.example.taskhabit.presentation.viewmodel.HabitViewModel
import com.example.taskhabit.ui.theme.*

enum class HabitFilter(val label: String) {
    ALL("All"),
    PENDING("Pending"),
    COMPLETED("Completed")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsScreen(
    currentRoute: String = "habits",
    onNavigate: (String) -> Unit = {},
    onAddHabit: () -> Unit = {},
    onEdit: (Int) -> Unit = {},                       
    habitViewModel: HabitViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    val habits by habitViewModel.allHabits.collectAsStateWithLifecycle()
    val categories by categoryViewModel.allCategories.collectAsStateWithLifecycle()
    val categoryMap = remember(categories) { categories.associateBy { it.id } }

    var selectedFilter by remember { mutableStateOf(HabitFilter.ALL) }
    val filteredHabits by remember(selectedFilter, habits) {
        derivedStateOf {
            when (selectedFilter) {
                HabitFilter.ALL       -> habits
                HabitFilter.PENDING   -> habits.filter { !it.isCompleted }
                HabitFilter.COMPLETED -> habits.filter { it.isCompleted }
            }
        }
    }

    val accentColors = listOf(Primary, Secondary, Tertiary)

    Scaffold(
        containerColor = Background,
        topBar = { KineticTopAppBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddHabit,
                containerColor = Primary,
                contentColor = OnPrimary,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(64.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Habit", modifier = Modifier.size(32.dp))
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                HabitsHeader(
                    total = habits.size,
                    completed = habits.count { it.isCompleted }
                )
                Spacer(modifier = Modifier.height(20.dp))
                FilterTabBar(selected = selectedFilter, onFilterChange = { selectedFilter = it })
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (filteredHabits.isEmpty()) {
                item { EmptyState(filter = selectedFilter) }
            } else {
                items(filteredHabits, key = { it.id }) { habit ->
                    val categoryName = categoryMap[habit.categoryId]?.name ?: "Other"
                    val accentColor = accentColors[(habit.categoryId - 1).coerceAtLeast(0) % accentColors.size]
                    SwipeableHabitCard(
                        habit = habit,
                        categoryName = categoryName,
                        accentColor = accentColor,
                        onToggle = { habitViewModel.toggleHabitCompletion(habit) },
                        onDelete = { habitViewModel.deleteHabit(habit) },
                        onEdit = { onEdit(habit.id) }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun HabitsHeader(total: Int, completed: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column {
            Text(text = "My Habits", color = OnSurface, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = (-0.5).sp)
            Text(text = "$completed of $total completed today", color = OnSurfaceVariant, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Primary.copy(alpha = 0.15f))
                .border(1.dp, Primary.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = "$completed/$total", color = Primary, fontSize = 15.sp, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
private fun FilterTabBar(selected: HabitFilter, onFilterChange: (HabitFilter) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainerLow)
            .padding(6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        HabitFilter.entries.forEach { filter ->
            val isSelected = filter == selected
            val bgColor by animateColorAsState(
                targetValue = if (isSelected) Primary else Color.Transparent,
                animationSpec = tween(durationMillis = 300),
                label = "chip_bg"
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) OnPrimary else OnSurfaceVariant,
                animationSpec = tween(durationMillis = 300),
                label = "chip_text"
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(bgColor)
                    .clickable { onFilterChange(filter) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = filter.label, color = textColor, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun HabitCard(
    habit: Habit,
    categoryName: String,
    accentColor: Color,
    onToggle: () -> Unit,
    onEdit: () -> Unit                                 
) {
    val cardBg = if (habit.isCompleted) accentColor.copy(alpha = 0.08f) else SurfaceContainerLow
    val icon = habitIconFor(habit.name)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(cardBg)
            .then(
                if (!habit.isCompleted)
                    Modifier.drawBehind {
                        drawRect(color = accentColor.copy(alpha = 0.5f), topLeft = Offset.Zero, size = Size(4.dp.toPx(), size.height))
                    }
                else Modifier
            )
            .clickable { onToggle() }
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(if (habit.isCompleted) accentColor else SurfaceContainerHigh),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = if (habit.isCompleted) OnPrimary else accentColor, modifier = Modifier.size(26.dp))
            }
            Column {
                Text(
                    text = habit.name,
                    color = if (habit.isCompleted) OnSurface.copy(alpha = 0.5f) else OnSurface,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = if (habit.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )
                Text(
                    text = categoryName,
                    color = if (habit.isCompleted) accentColor else OnSurfaceVariant,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Botão editar                             
            IconButton(onClick = onEdit, modifier = Modifier.size(36.dp)) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = OnSurfaceVariant,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            // Check circle
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .then(if (habit.isCompleted) Modifier.background(accentColor) else Modifier.border(2.dp, OutlineVariant, CircleShape)),
                contentAlignment = Alignment.Center
            ) {
                if (habit.isCompleted) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Completed", tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}


@Composable
private fun EmptyState(filter: HabitFilter) {
    val message = when (filter) {
        HabitFilter.PENDING   -> "No pending habits.\nGreat job today!"
        HabitFilter.COMPLETED -> "No completed habits yet.\nKeep going!"
        HabitFilter.ALL       -> "No habits yet.\nCreate your first ritual!"
    }
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = Primary.copy(alpha = 0.3f), modifier = Modifier.size(64.dp))
        Text(text = message, color = OnSurfaceVariant, fontSize = 15.sp, fontWeight = FontWeight.Medium, lineHeight = 22.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeableHabitCard(
    habit: Habit,
    categoryName: String,
    accentColor: Color,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDelete(); true
            } else false
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFEF4444))
                    .padding(end = 24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.White)
            }
        }
    ) {
        HabitCard(
            habit = habit,
            categoryName = categoryName,
            accentColor = accentColor,
            onToggle = onToggle,
            onEdit = onEdit
        )
    }
}
