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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskhabit.data.local.entity.Habit
import com.example.taskhabit.presentation.viewmodel.CategoryViewModel
import com.example.taskhabit.presentation.viewmodel.HabitViewModel
import com.example.taskhabit.ui.theme.*
import com.example.taskhabit.ui.theme.LocalStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitScreen(
    onBack: () -> Unit = {},
    habitViewModel: HabitViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    val strings = LocalStrings.current
    var habitName by remember { mutableStateOf("") }
    var isDaily by remember { mutableStateOf(true) }
    var showDayPicker by remember { mutableStateOf(false) }

    // Day codes and their display labels
    val dayEntries = listOf(
        "MON" to strings.monday,
        "TUE" to strings.tuesday,
        "WED" to strings.wednesday,
        "THU" to strings.thursday,
        "FRI" to strings.friday,
        "SAT" to strings.saturday,
        "SUN" to strings.sunday
    )
    var selectedDays by remember { mutableStateOf(setOf<String>()) }

    val categories by categoryViewModel.allCategories.collectAsStateWithLifecycle()
    var selectedCategoryId by remember { mutableIntStateOf(0) }

    // Set default category once list loads
    LaunchedEffect(categories) {
        if (selectedCategoryId == 0 && categories.isNotEmpty()) {
            selectedCategoryId = categories.first().id
        }
    }

    // Day picker dialog
    if (showDayPicker) {
        AlertDialog(
            onDismissRequest = { showDayPicker = false },
            containerColor = SurfaceContainer,
            title = {
                Text(
                    text = strings.selectDays,
                    color = OnSurface,
                    fontWeight = FontWeight.ExtraBold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    dayEntries.forEach { (code, label) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedDays = if (selectedDays.contains(code)) {
                                        selectedDays - code
                                    } else {
                                        selectedDays + code
                                    }
                                }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Checkbox(
                                checked = selectedDays.contains(code),
                                onCheckedChange = { checked ->
                                    selectedDays = if (checked) {
                                        selectedDays + code
                                    } else {
                                        selectedDays - code
                                    }
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Primary,
                                    uncheckedColor = OnSurfaceVariant
                                )
                            )
                            Text(
                                text = label,
                                color = OnSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDayPicker = false }) {
                    Text(text = strings.confirm, color = Primary, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDayPicker = false }) {
                    Text(text = strings.cancel, color = OnSurfaceVariant)
                }
            }
        )
    }

    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = strings.addNewHabit,
                        color = OnSurface,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = strings.back,
                            tint = Primary
                        )
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(SurfaceContainerHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
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
                    .background(Brush.linearGradient(listOf(Primary.copy(alpha = 0.4f), Secondary.copy(alpha = 0.2f))))
            ) {
                Column(modifier = Modifier.align(Alignment.BottomStart).padding(24.dp)) {
                    Text(
                        text = strings.newRitual,
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                    Text(
                        text = strings.startYourFlow,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            // Habit Name
            FormSection(label = strings.whatIsHabit) {
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
                            Text(
                                text = strings.habitPlaceholder,
                                color = Outline.copy(alpha = 0.5f),
                                fontSize = 18.sp
                            )
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
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            // Frequency
            FormSection(label = strings.frequency) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        FrequencyButton(
                            label = strings.daily,
                            icon = Icons.Default.CalendarToday,
                            isSelected = isDaily,
                            onClick = { isDaily = true },
                            modifier = Modifier.weight(1f)
                        )
                        FrequencyButton(
                            label = strings.custom,
                            icon = Icons.Default.DateRange,
                            isSelected = !isDaily,
                            onClick = {
                                isDaily = false
                                showDayPicker = true
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Show selected days preview when custom is active
                    if (!isDaily && selectedDays.isNotEmpty()) {
                        val orderedCodes = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
                        val displayDays = orderedCodes
                            .filter { selectedDays.contains(it) }
                            .joinToString(", ") { code ->
                                dayEntries.first { it.first == code }.second
                            }
                        Text(
                            text = displayDays,
                            color = Primary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }

            // Reminder Time + Category row
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FormSection(label = strings.reminderTime, modifier = Modifier.weight(1f)) {
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
                FormSection(label = strings.category, modifier = Modifier.weight(1f)) {
                    CategoryPicker(
                        categories = categories,
                        selectedId = selectedCategoryId,
                        onSelect = { selectedCategoryId = it }
                    )
                }
            }

            // Color Palette
            FormSection(label = strings.accentColor) {
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
                    Text(text = strings.custom, color = Primary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
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
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Primary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = Primary)
                }
                Column {
                    Text(text = strings.smartSuggestions, color = OnSurface, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = strings.smartSuggestionText,
                        color = OnSurfaceVariant,
                        fontSize = 13.sp
                    )
                }
            }

            // Create Button
            val frequencyString = when {
                isDaily -> "DAILY"
                selectedDays.isEmpty() -> "DAILY"
                else -> {
                    val orderedCodes = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
                    orderedCodes.filter { selectedDays.contains(it) }.joinToString(",")
                }
            }

            Button(
                onClick = {
                    if (habitName.isNotBlank() && selectedCategoryId != 0) {
                        habitViewModel.insertHabit(
                            Habit(
                                name = habitName.trim(),
                                categoryId = selectedCategoryId,
                                frequency = frequencyString
                            )
                        )
                        onBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                enabled = habitName.isNotBlank() && selectedCategoryId != 0
            ) {
                Text(text = strings.createHabit, color = OnPrimary, fontSize = 18.sp, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = OnPrimary)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CategoryPicker(
    categories: List<com.example.taskhabit.data.local.entity.Category>,
    selectedId: Int,
    onSelect: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selected = categories.find { it.id == selectedId }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainerLow)
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
            .clickable { expanded = true }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selected?.name ?: "Select",
                color = if (selected != null) OnSurface else Outline,
                fontWeight = FontWeight.SemiBold
            )
            Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null, tint = Outline)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(SurfaceContainer)
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(text = category.name, color = OnSurface, fontWeight = FontWeight.Medium) },
                    onClick = { onSelect(category.id); expanded = false }
                )
            }
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
private fun FrequencyButton(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) OnPrimary else OnSurface,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, color = if (isSelected) OnPrimary else OnSurface, fontWeight = FontWeight.Bold)
    }
}
