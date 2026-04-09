package com.example.taskhabit.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskhabit.presentation.ui.components.HabitBottomNavBar
import com.example.taskhabit.presentation.ui.components.KineticTopAppBar
import com.example.taskhabit.presentation.viewmodel.HabitViewModel
import com.example.taskhabit.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    currentRoute: String = "stats",
    onNavigate: (String) -> Unit = {},
    viewModel: HabitViewModel = hiltViewModel()
) {
    val habits by viewModel.allHabits.collectAsStateWithLifecycle()
    val totalCompleted = habits.count { it.isCompleted }
    val totalHabits = habits.size

    Scaffold(
        containerColor = Background,
        topBar = { KineticTopAppBar() },
        bottomBar = { HabitBottomNavBar(currentRoute = currentRoute, onNavigate = onNavigate) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                HeroStatsBento(totalCompleted = totalCompleted, totalHabits = totalHabits)
            }
            item { WeeklyRitualsSection() }
            item { HeatmapSection() }
            item {
                AreasOfGrowthSection()
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun HeroStatsBento(totalCompleted: Int, totalHabits: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Main hero card
        Box(
            modifier = Modifier
                .weight(1.6f)
                .height(220.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Primary)
                .padding(28.dp)
        ) {
            // Glow orb decoration
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 60.dp, y = (-60).dp)
                    .background(
                        Brush.radialGradient(listOf(Color.White.copy(alpha = 0.2f), Color.Transparent))
                    )
            )
            Column {
                Text(
                    text = "TOTAL HABITS COMPLETED",
                    color = OnPrimary.copy(alpha = 0.8f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$totalCompleted",
                    color = OnPrimary,
                    fontSize = 56.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 56.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                            .padding(4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                    Text(text = "+12% from last month", color = OnPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Streak card
        Box(
            modifier = Modifier
                .weight(1f)
                .height(220.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(SecondaryContainer)
                .border(1.dp, Secondary.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Icon(imageVector = Icons.Default.WorkspacePremium, contentDescription = null, tint = Secondary, modifier = Modifier.size(40.dp))
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "BEST STREAK", color = OnSecondaryContainer.copy(alpha = 0.7f), fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Text(text = "42", color = OnSecondaryContainer, fontSize = 40.sp, fontWeight = FontWeight.ExtraBold, lineHeight = 44.sp)
                Text(text = "Days", color = OnSecondaryContainer, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Active since Oct 12", color = OnSecondaryContainer.copy(alpha = 0.6f), fontSize = 11.sp)
            }
        }
    }
}

@Composable
private fun WeeklyRitualsSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(horizontal = 4.dp)) {
            Text(text = "Weekly Rituals", color = OnSurface, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Text(text = "Performance over the last 7 days", color = OnSurfaceVariant, fontSize = 13.sp)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceContainerLow)
                .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val bars = listOf(
                    Triple("Mon", 0.65f, false),
                    Triple("Tue", 0.85f, false),
                    Triple("Wed", 0.45f, false),
                    Triple("Thu", 1.0f, true),
                    Triple("Fri", 0.75f, false),
                    Triple("Sat", 0.90f, false),
                    Triple("Sun", 0.60f, false)
                )
                bars.forEach { (day, fraction, isToday) ->
                    WeeklyBar(day = day, heightFraction = fraction, isToday = isToday)
                }
            }
        }
    }
}

@Composable
private fun RowScope.WeeklyBar(day: String, heightFraction: Float, isToday: Boolean) {
    Column(
        modifier = Modifier.weight(1f).fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
            // Track
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                    .background(SurfaceContainerHighest)
            )
            // Fill
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .fillMaxHeight(heightFraction)
                    .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                    .background(if (isToday) Primary else Primary.copy(alpha = 0.4f))
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = day.uppercase(),
            color = if (isToday) Primary else OnSurfaceVariant,
            fontSize = 10.sp,
            fontWeight = if (isToday) FontWeight.ExtraBold else FontWeight.Bold
        )
    }
}

@Composable
private fun HeatmapSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Kinetic Flow", color = OnSurface, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(text = "Less", color = OnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    listOf(0f, 0.2f, 0.6f, 1.0f).forEach { alpha ->
                        Box(
                            modifier = Modifier.size(12.dp).clip(RoundedCornerShape(2.dp))
                                .background(if (alpha == 0f) SurfaceContainerHighest else Primary.copy(alpha = alpha))
                        )
                    }
                }
                Text(text = "More", color = OnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceContainerLow)
                .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
                .padding(20.dp)
        ) {
            val data = listOf(
                1f, 0.2f, 1f, 0f, 0.6f, 1f, 0.2f,
                0.6f, 1f, 0.2f, 1f, 1f, 0f, 1f,
                1f, 1f, 0.6f, 1f, 0.2f, 1f, 0.6f,
                0f, 0.2f, 1f, 1f, 0.6f, 1f, 1f,
                1f, 0.6f, 1f, 0.2f, 1f, 1f, 0.6f,
                1f, 1f, 1f, 0.6f, 0f, 1f, 0.2f,
                1f, 0.2f, 1f, 0f, 0.6f, 1f, 0.2f,
                0.6f, 1f, 0.2f, 0.6f, 1f
            )
            val rows = 7
            val cols = (data.size + rows - 1) / rows
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                for (row in 0 until rows) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        for (col in 0 until cols) {
                            val idx = col * rows + row
                            val alpha = if (idx < data.size) data[idx] else 0f
                            Box(
                                modifier = Modifier.size(16.dp).clip(RoundedCornerShape(2.dp))
                                    .background(if (alpha == 0f) SurfaceContainerHighest else Primary.copy(alpha = alpha))
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AreasOfGrowthSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Areas of Growth",
            color = OnSurface,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CategoryCard(name = "Wellness", percentage = "Completed 84%", icon = Icons.Default.Eco, accentColor = Tertiary, modifier = Modifier.weight(1f))
            CategoryCard(name = "Knowledge", percentage = "Completed 62%", icon = Icons.Default.MenuBook, accentColor = Primary, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun CategoryCard(name: String, percentage: String, icon: ImageVector, accentColor: Color, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainer)
            .drawBehind {
                drawRect(color = accentColor, topLeft = Offset.Zero, size = Size(8.dp.toPx(), size.height))
            }
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = name, color = OnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = percentage.uppercase(), color = OnSurfaceVariant, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.8.sp)
        }
        Icon(imageVector = icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(36.dp))
    }
}
