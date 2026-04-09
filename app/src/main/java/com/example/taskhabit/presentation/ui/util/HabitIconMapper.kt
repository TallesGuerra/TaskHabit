package com.example.taskhabit.presentation.ui.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

fun habitIconFor(name: String): ImageVector {
    val lower = name.lowercase()
    return when {
        lower.contains("water") || lower.contains("drink") -> Icons.Default.WaterDrop
        lower.contains("run") || lower.contains("jog")     -> Icons.Default.DirectionsRun
        lower.contains("walk")                             -> Icons.Default.DirectionsWalk
        lower.contains("yoga") || lower.contains("stretch")-> Icons.Default.FitnessCenter
        lower.contains("gym") || lower.contains("workout") || lower.contains("exercise") -> Icons.Default.FitnessCenter
        lower.contains("read") || lower.contains("book")   -> Icons.Default.MenuBook
        lower.contains("meditat") || lower.contains("breath") || lower.contains("mindful") -> Icons.Default.SelfImprovement
        lower.contains("journal") || lower.contains("write") || lower.contains("diary") -> Icons.Default.Edit
        lower.contains("sleep") || lower.contains("rest")  -> Icons.Default.Bedtime
        lower.contains("shower") || lower.contains("bath") -> Icons.Default.Shower
        lower.contains("eat") || lower.contains("meal") || lower.contains("diet") -> Icons.Default.Restaurant
        lower.contains("music") || lower.contains("practice") -> Icons.Default.MusicNote
        lower.contains("code") || lower.contains("study")  -> Icons.Default.Laptop
        else                                               -> Icons.Default.Star
    }
}
