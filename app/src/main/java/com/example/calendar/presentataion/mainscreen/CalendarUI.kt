package com.example.calendar.presentataion.mainscreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.calendar.presentataion.navigation.Routes
import com.himanshoe.kalendar.Kalendar
import com.himanshoe.kalendar.KalendarEvent
import com.himanshoe.kalendar.KalendarEvents
import com.himanshoe.kalendar.KalendarType
import com.himanshoe.kalendar.color.KalendarColor
import com.himanshoe.kalendar.color.KalendarColors
import com.himanshoe.kalendar.ui.component.day.KalendarDayKonfig
import com.himanshoe.kalendar.ui.component.header.KalendarTextKonfig
import com.himanshoe.kalendar.ui.firey.DaySelectionMode
import kotlinx.datetime.LocalDate


@Composable
fun CalendarUI(
    state: CalendarState,
    onEvent: (CalendarEvents) -> Unit,
    navHostController: NavHostController
){




    Scaffold (
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navHostController.navigate(Routes.ADD_REMINDER) },
                shape = CircleShape,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }

        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {


        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Kalendar(
                currentDay = null,
                kalendarType = KalendarType.Firey,
                modifier = Modifier.weight(1f),
                showLabel = true,
                events = KalendarEvents(
                    state.reminders.map {
                        KalendarEvent(
                            date = LocalDate.parse(it.date),
                            eventName = it.reminderText,
                        )
                    },
                ),
                kalendarHeaderTextKonfig = KalendarTextKonfig(
                    kalendarTextSize = 20.sp,
                    kalendarTextColor = MaterialTheme.colorScheme.onBackground,

                    ),
                kalendarColors = KalendarColors(
                    List(12) {
                        KalendarColor(
                            Color.Transparent,
                            Color.Transparent,
                            MaterialTheme.colorScheme.onBackground
                        )
                    }

                ),
                kalendarDayKonfig = KalendarDayKonfig(
                    textSize = 14.sp,
                    selectedTextColor = Color.Red,
                    size = 40.dp,
                    textColor = MaterialTheme.colorScheme.onBackground,
                ),
                daySelectionMode = DaySelectionMode.Single,
                dayContent = null,
                headerContent = null,
                onDayClick = { selectedDate, events ->
                    // Handle day click event
                    onEvent(CalendarEvents.OnDateSelected(selectedDate.toString()))
                },

                onRangeSelected = { selectedRange, events ->
                    // Handle range selection event


                },
                onErrorRangeSelected = { error ->
                    // Handle error
                },
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Reminders",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    if (state.reminders.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {

                            items(state.reminders) { reminder ->
                                ReminderItem(
                                    reminder = reminder,
                                    onDeleted = onEvent,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



