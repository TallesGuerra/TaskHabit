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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskhabit.presentation.ui.components.HabitBottomNavBar
import com.example.taskhabit.presentation.ui.components.KineticTopAppBar
import com.example.taskhabit.ui.theme.*

// ---------------------------------------------------------------------------
// Modelo de dados local para exibição na tela (ainda sem Room)
// TODO: substituir por `data class Habit` do Room quando ligar o ViewModel
// ---------------------------------------------------------------------------
data class HabitUiModel(
    val id: Int,
    val name: String,
    val category: String,
    val icon: ImageVector,
    val accentColor: Color,
    val isCompleted: Boolean
)

// ---------------------------------------------------------------------------
// Enum que representa os filtros da tab bar
// ---------------------------------------------------------------------------
enum class HabitFilter(val label: String) {
    ALL("All"),
    PENDING("Pending"),
    COMPLETED("Completed")
}

// ---------------------------------------------------------------------------
// Dados estáticos de exemplo
// TODO: remover quando HabitViewModel estiver conectado
// ---------------------------------------------------------------------------
private val sampleHabits = listOf(
    HabitUiModel(1, "Drink Water",     "Wellness",  Icons.Default.WaterDrop,       Primary,   isCompleted = true),
    HabitUiModel(2, "Read 10 mins",    "Knowledge", Icons.Default.MenuBook,         Secondary, isCompleted = false),
    HabitUiModel(3, "Evening Yoga",    "Wellness",  Icons.Default.FitnessCenter,    Secondary, isCompleted = false),
    HabitUiModel(4, "Mindful Breath",  "Wellness",  Icons.Default.SelfImprovement,  Primary,   isCompleted = false),
    HabitUiModel(5, "Morning Run",     "Fitness",   Icons.Default.DirectionsRun,    Tertiary,  isCompleted = true),
    HabitUiModel(6, "Journal Entry",   "Knowledge", Icons.Default.Edit,             Primary,   isCompleted = false),
    HabitUiModel(7, "Cold Shower",     "Wellness",  Icons.Default.Shower,           Secondary, isCompleted = true),
)

// ---------------------------------------------------------------------------
// Tela principal de Hábitos
// Parâmetros:
//   currentRoute  → qual aba está ativa no BottomNavBar
//   onNavigate    → callback que o NavController vai usar para trocar de tela
//   onAddHabit    → abre AddHabitScreen ao clicar no FAB
// ---------------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsScreen(
    currentRoute: String = "habits",
    onNavigate: (String) -> Unit = {},
    onAddHabit: () -> Unit = {}
) {
    // `remember` mantém o estado do filtro selecionado entre recomposições
    var selectedFilter by remember { mutableStateOf(HabitFilter.ALL) }

    // `derivedStateOf` só recalcula a lista quando `selectedFilter` ou
    // `sampleHabits` mudam — evita recomposições desnecessárias
    val filteredHabits by remember(selectedFilter) {
        derivedStateOf {
            when (selectedFilter) {
                HabitFilter.ALL       -> sampleHabits
                HabitFilter.PENDING   -> sampleHabits.filter { !it.isCompleted }
                HabitFilter.COMPLETED -> sampleHabits.filter { it.isCompleted }
            }
        }
    }

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
            // Cabeçalho da seção
            item {
                Spacer(modifier = Modifier.height(16.dp))
                HabitsHeader(total = sampleHabits.size, completed = sampleHabits.count { it.isCompleted })
                Spacer(modifier = Modifier.height(20.dp))
                // Barra de filtros (chips)
                FilterTabBar(
                    selected = selectedFilter,
                    onFilterChange = { selectedFilter = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Lista de hábitos filtrada
            // `items` do LazyColumn itera sobre a lista e cria um item por hábito
            if (filteredHabits.isEmpty()) {
                item { EmptyState(filter = selectedFilter) }
            } else {
                items(
                    items = filteredHabits,
                    key = { it.id } // chave única garante animações corretas
                ) { habit ->
                    HabitCard(habit = habit)
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

// ---------------------------------------------------------------------------
// Cabeçalho: título + contagem de progresso
// ---------------------------------------------------------------------------
@Composable
private fun HabitsHeader(total: Int, completed: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column {
            Text(
                text = "My Habits",
                color = OnSurface,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-0.5).sp
            )
            Text(
                text = "$completed of $total completed today",
                color = OnSurfaceVariant,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
        // Badge de progresso: ex. "3/7"
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Primary.copy(alpha = 0.15f))
                .border(1.dp, Primary.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "$completed/$total",
                color = Primary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Barra de filtros com 3 chips: All / Pending / Completed
// animateColorAsState anima a transição de cor ao trocar de filtro
// ---------------------------------------------------------------------------
@Composable
private fun FilterTabBar(
    selected: HabitFilter,
    onFilterChange: (HabitFilter) -> Unit
) {
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

            // Anima a cor de fundo do chip suavemente (300ms)
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
                Text(
                    text = filter.label,
                    color = textColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Card de um hábito individual
// Muda de aparência conforme isCompleted
// ---------------------------------------------------------------------------
@Composable
private fun HabitCard(habit: HabitUiModel) {
    // Cor de fundo depende do estado do hábito
    val cardBg = if (habit.isCompleted)
        habit.accentColor.copy(alpha = 0.08f)
    else
        SurfaceContainerLow

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(cardBg)
            // Borda colorida à esquerda para hábitos pendentes
            .then(
                if (!habit.isCompleted)
                    Modifier.drawBehind {
                        drawRect(
                            color = habit.accentColor.copy(alpha = 0.5f),
                            topLeft = Offset.Zero,
                            size = Size(4.dp.toPx(), size.height)
                        )
                    }
                else Modifier
            )
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícone + nome + categoria
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(
                        if (habit.isCompleted) habit.accentColor
                        else SurfaceContainerHigh
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = habit.icon,
                    contentDescription = null,
                    tint = if (habit.isCompleted) OnPrimary else habit.accentColor,
                    modifier = Modifier.size(26.dp)
                )
            }
            Column {
                Text(
                    text = habit.name,
                    color = if (habit.isCompleted) OnSurface.copy(alpha = 0.5f) else OnSurface,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    // Riscado quando concluído
                    textDecoration = if (habit.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )
                Text(
                    text = habit.category,
                    color = if (habit.isCompleted) habit.accentColor else OnSurfaceVariant,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Botão de check — preenchido se concluído, vazio se pendente
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .then(
                    if (habit.isCompleted)
                        Modifier.background(habit.accentColor)
                    else
                        Modifier.border(2.dp, OutlineVariant, CircleShape)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (habit.isCompleted) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Completed",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Estado vazio — exibido quando nenhum hábito bate com o filtro ativo
// ---------------------------------------------------------------------------
@Composable
private fun EmptyState(filter: HabitFilter) {
    val message = when (filter) {
        HabitFilter.PENDING   -> "No pending habits.\nGreat job today!"
        HabitFilter.COMPLETED -> "No completed habits yet.\nKeep going!"
        HabitFilter.ALL       -> "No habits yet.\nCreate your first ritual!"
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Primary.copy(alpha = 0.3f),
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = message,
            color = OnSurfaceVariant,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 22.sp
        )
    }
}
