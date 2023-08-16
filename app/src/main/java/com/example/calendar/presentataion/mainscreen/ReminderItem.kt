package com.example.calendar.presentataion.mainscreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.calendar.domain.model.Reminder
import com.example.calendar.ui.theme.autumnGradientDark
import com.example.calendar.ui.theme.autumnGradientLight
import com.example.calendar.ui.theme.monsoonGradientDark
import com.example.calendar.ui.theme.monsoonGradientLight
import com.example.calendar.ui.theme.springGradientDark
import com.example.calendar.ui.theme.springGradientLight
import com.example.calendar.ui.theme.summerGradientDark
import com.example.calendar.ui.theme.summerGradientLight
import com.example.calendar.ui.theme.winterGradientDark
import com.example.calendar.ui.theme.winterGradientLight
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ReminderItem(
    reminder: Reminder,
    modifier: Modifier = Modifier,
    onDeleted: (CalendarEvents) -> Unit
    ) {
    val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val formatter = SimpleDateFormat("d MMM, yyyy", Locale.getDefault())
    val parsedDate = parser.parse(reminder.date)
    val formattedDate = formatter.format(parsedDate!!)
    val isDarkMode = isSystemInDarkTheme()


    val gradient = when {
        reminder.season == "Spring" && !isDarkMode -> springGradientLight
        reminder.season == "Spring" && isDarkMode -> springGradientDark
        reminder.season == "Summer" && !isDarkMode -> summerGradientLight
        reminder.season == "Summer" && isDarkMode -> summerGradientDark
        reminder.season == "Monsoon" && !isDarkMode -> monsoonGradientLight
        reminder.season == "Monsoon" && isDarkMode -> monsoonGradientDark
        reminder.season == "Autumn" && !isDarkMode -> autumnGradientLight
        reminder.season == "Autumn" && isDarkMode -> autumnGradientDark
        reminder.season == "Winter" && !isDarkMode -> winterGradientLight
        reminder.season == "Winter" && isDarkMode -> winterGradientDark
        else -> Brush.linearGradient(
            colors = listOf(
                Color(0xFFE0C3FC),
                Color(0xFF8EC5FC)
            )
        )
    }


    Box(
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(
                brush = gradient,
                cornerRadius = CornerRadius(16.dp.toPx())
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = reminder.reminderText,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
                Row { // Add this block
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = reminder.time,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }
            }
            IconButton(onClick = {
                onDeleted(CalendarEvents.OnReminderDeleted(reminder))
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
