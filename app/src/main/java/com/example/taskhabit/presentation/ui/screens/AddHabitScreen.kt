package com.example.taskhabit.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskhabit.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitScreen(onBack: () -> Unit = {}) {
    var habitName by remember { mutableStateOf("") }
    var isDaily by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add New Habit", color = OnSurface, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Primary)
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier.padding(end = 8.dp).size(40.dp).clip(CircleShape).background(SurfaceContainerHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Profile", tint = OnSurfaceVariant, modifier = Modifier.size(20.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Surface.copy(alpha = 0.8f))
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Hero Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(192.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(listOf(Primary.copy(alpha = 0.4f), Secondary.copy(alpha = 0.2f)))
                    )
            ) {
                Column(
                    modifier = Modifier.align(Alignment.BottomStart).padding(24.dp)
                ) {
                    Text(text = "NEW RITUAL", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                    Text(text = "Start Your Flow", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Black)
                }
            }

            // Habit Name
            FormSection(label = "What is the habit?") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainerLow)
                        .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
                ) {
                    TextField(
                        value = habitName,
                        onValueChange = { habitName = it },
                        placeholder = {
                            Text(text = "e.g. Morning Meditation", color = Outline.copy(alpha = 0.5f), fontSize = 18.sp)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = OnSurface,
                            unfocusedTextColor = OnSurface
                        ),
                        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    )
                }
            }

            // Frequency
            FormSection(label = "Frequency") {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    FrequencyButton(
                        label = "Daily",
                        icon = Icons.Default.CalendarToday,
                        isSelected = isDaily,
                        onClick = { isDaily = true },
                        modifier = Modifier.weight(1f)
                    )
                    FrequencyButton(
                        label = "Weekly",
                        icon = Icons.Default.DateRange,
                        isSelected = !isDaily,
                        onClick = { isDaily = false },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Reminder Time + Icon row
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FormSection(label = "Reminder Time", modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceContainerLow)
                            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "08:00", color = OnSurface, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = Primary)
                    }
                }
                FormSection(label = "Icon", modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceContainerLow)
                            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
                            .clickable { }
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier.size(32.dp).clip(CircleShape).background(Primary.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.SelfImprovement, contentDescription = null, tint = Primary, modifier = Modifier.size(18.dp))
                            }
                            Text(text = "Wellness", color = OnSurface, fontWeight = FontWeight.SemiBold)
                        }
                        Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null, tint = Outline)
                    }
                }
            }

            // Color Palette
            FormSection(label = "Accent Color") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainerLow)
                        .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        listOf(Primary to true, Secondary to false, Color(0xFF047857) to false, Color(0xFF06B6D4) to false, Tertiary to false)
                            .forEach { (color, isSelected) ->
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .then(if (isSelected) Modifier.border(3.dp, color.copy(alpha = 0.5f), CircleShape) else Modifier)
                                )
                            }
                    }
                    Text(text = "Custom", color = Primary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Smart Suggestions Card
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainerLow)
                    .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier.size(48.dp).clip(RoundedCornerShape(16.dp)).background(Primary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = Primary)
                }
                Column {
                    Text(text = "Smart Suggestions", color = OnSurface, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "We've noticed you're usually active at 8:00 AM. Want to pair this with your 'Coffee' ritual?",
                        color = OnSurfaceVariant,
                        fontSize = 13.sp
                    )
                }
            }

            // Create Button
            Button(
                onClick = { /* TODO: wire to HabitViewModel.insertHabit() */ },
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary)
            ) {
                Text(text = "Create Habit", color = OnPrimary, fontSize = 18.sp, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = OnPrimary)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun FormSection(label: String, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label.uppercase(),
            color = OnSurfaceVariant,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(start = 4.dp)
        )
        content()
    }
}

@Composable
private fun FrequencyButton(label: String, icon: ImageVector, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Primary else SurfaceContainerHigh)
            .border(1.dp, if (isSelected) Color.Transparent else Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = if (isSelected) OnPrimary else OnSurface, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, color = if (isSelected) OnPrimary else OnSurface, fontWeight = FontWeight.Bold)
    }
}
