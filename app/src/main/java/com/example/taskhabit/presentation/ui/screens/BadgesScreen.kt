package com.example.taskhabit.presentation.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskhabit.presentation.ui.components.HabitBottomNavBar
import com.example.taskhabit.presentation.ui.components.KineticTopAppBar
import com.example.taskhabit.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgesScreen(
    currentRoute: String = "badges",
    onNavigate: (String) -> Unit = {},
    onAvatarClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = Background,
        topBar = { KineticTopAppBar(onAvatarClick = onAvatarClick, onSettingsClick = onSettingsClick) },
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
                HeroProgressCard()
            }
            item { UnlockedBadgesSection() }
            item { UpcomingMilestonesSection() }
            item {
                StatsGlimpseSection()
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun HeroProgressCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Primary)
            .padding(28.dp)
    ) {
        // Glow orb
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopEnd)
                .offset(x = 48.dp, y = (-48).dp)
                .background(Brush.radialGradient(listOf(Secondary.copy(alpha = 0.3f), Color.Transparent)))
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "ACHIEVEMENT RANK", color = Color.White.copy(alpha = 0.7f), fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Flow Master II", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "You've unlocked 14 out of 25 kinetic milestones. Keep the momentum!",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Next reward row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Black.copy(alpha = 0.2f))
                    .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "NEXT REWARD", color = Color.White.copy(alpha = 0.6f), fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    Text(text = "Gold 7-Day Hero", color = Color.White, fontWeight = FontWeight.Bold)
                }
                // Progress ring (75%)
                Box(modifier = Modifier.size(64.dp), contentAlignment = Alignment.Center) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val strokeWidth = 4.dp.toPx()
                        val radius = size.minDimension / 2 - strokeWidth
                        drawCircle(color = Color.White.copy(alpha = 0.1f), radius = radius, style = Stroke(strokeWidth))
                        drawArc(
                            color = Color.White,
                            startAngle = -90f,
                            sweepAngle = 270f,
                            useCenter = false,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                    }
                    Text(text = "75%", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

private data class BadgeItem(val title: String, val subtitle: String, val icon: ImageVector, val gradient: Pair<Color, Color>)

@Composable
private fun UnlockedBadgesSection() {
    val badges = listOf(
        BadgeItem("Month Master", "Consistency Elite", Icons.Default.RocketLaunch, Primary to PrimaryContainer),
        BadgeItem("Morning Lark", "Before 7AM", Icons.Default.WbSunny, Secondary to SecondaryContainer),
        BadgeItem("7-Day Hero", "Perfect Week", Icons.Default.LocalFireDepartment, Tertiary to TertiaryContainer),
        BadgeItem("Peak Performer", "Max Efficiency", Icons.Default.MilitaryTech, Primary to Secondary)
    )

    Column {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "Unlocked Rituals", color = OnSurface, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Primary.copy(alpha = 0.1f))
                    .border(1.dp, Primary.copy(alpha = 0.2f), CircleShape)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(text = "14 Badges", color = Primary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                BadgeCard(badge = badges[0])
                BadgeCard(badge = badges[2])
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                BadgeCard(badge = badges[1])
                BadgeCard(badge = badges[3])
            }
        }
    }
}

@Composable
private fun BadgeCard(badge: BadgeItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainerLow)
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(badge.gradient.first, badge.gradient.second))),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = badge.icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = badge.title, color = OnSurface, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(text = badge.subtitle.uppercase(), color = badge.gradient.first, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
    }
}

private data class MilestoneItem(
    val title: String,
    val progress: String,
    val fraction: Float,
    val progressColor: Color,
    val icon: ImageVector,
    val tag: String? = null
)

@Composable
private fun UpcomingMilestonesSection() {
    val milestones = listOf(
        MilestoneItem("Yearly Legend", "184/365 Days", 0.5f, Outline, Icons.Default.Star),
        MilestoneItem("Zen Seeker", "48/50 Rituals", 0.96f, Tertiary, Icons.Default.SelfImprovement, "Almost there"),
        MilestoneItem("Sprinter", "2/10 Quick Finish", 0.2f, Secondary, Icons.Default.DirectionsRun)
    )

    Column {
        Text(text = "Upcoming Milestones", color = OnSurface, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            milestones.forEach { MilestoneCard(it) }
        }
    }
}

@Composable
private fun MilestoneCard(milestone: MilestoneItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainerLow)
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(SurfaceContainerHighest)
                .alpha(0.4f),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = milestone.icon, contentDescription = null, tint = OnSurfaceVariant, modifier = Modifier.size(28.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = milestone.title, color = OnSurface.copy(alpha = 0.5f), fontWeight = FontWeight.Bold)
                Text(text = milestone.progress, color = OnSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape).background(SurfaceContainerHighest)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(milestone.fraction)
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .background(milestone.progressColor)
                )
            }
        }
        if (milestone.tag != null) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Tertiary.copy(alpha = 0.2f))
                    .border(1.dp, Tertiary.copy(alpha = 0.3f), CircleShape)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(text = milestone.tag.uppercase(), color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun StatsGlimpseSection() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(Tertiary).padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "GLOBAL RANKING", color = Color.White.copy(alpha = 0.8f), fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Text(text = "Top 3%", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Black)
            }
            Icon(imageVector = Icons.Default.Leaderboard, contentDescription = null, tint = Color.White.copy(alpha = 0.3f), modifier = Modifier.size(48.dp))
        }
        Row(
            modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(Secondary).padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "TOTAL POINTS", color = Color.White.copy(alpha = 0.8f), fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Text(text = "12,450", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Black)
            }
            Icon(imageVector = Icons.Default.Diamond, contentDescription = null, tint = Color.White.copy(alpha = 0.3f), modifier = Modifier.size(48.dp))
        }
    }
}
