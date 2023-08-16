package com.example.calendar.presentataion.addreminder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.calendar.presentataion.addreminder.dialogs.MyDatePicker
import com.example.calendar.presentataion.addreminder.dialogs.MyTimePicker
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReminderScreen(
    state: ReminderState,
    onEvent: (ReminderEvents) -> Unit,
    onPopBackStack: () -> Unit
) {
    val showDatePicker = remember { mutableStateOf(false) }
    val showTimePicker = remember { mutableStateOf(false) }
    val formattedDate = remember(state.date) {
        derivedStateOf {
            state.date.let {
                LocalDate.parse(it).format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            TopAppBar(
                title = { Text(text = "Add a Reminder") },
                navigationIcon = {
                    IconButton(onClick = onPopBackStack) {
                        Icon(
                            imageVector =
                            Icons.Filled.ArrowBack,
                            contentDescription =
                            "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onEvent(ReminderEvents.OnSave)
                        onPopBackStack()
                    }) {
                        Icon(
                            imageVector =
                            Icons.Filled.Check,
                            contentDescription =
                            "Save"
                        )
                    }
                }
            )
            TextField(
                value = state.reminderText,
                onValueChange = {
                    onEvent(ReminderEvents.OnReminderTextChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textStyle = MaterialTheme.typography.headlineLarge,
                colors = TextFieldDefaults.colors(

                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,

                    ),
                placeholder = {
                    Text(
                        text = "Title",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            )
            Divider(
                modifier =
                Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                TextButton(
                    onClick = { showDatePicker.value = true },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                {
                    Text(
                        text = "Date: ${formattedDate.value ?: "Select Date"}",
                        style =
                        MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(
                    modifier =
                    Modifier.height(8.dp)
                )
                if (showDatePicker.value) {
                    DatePickerDialog(onDismissRequest =
                    {
                        showDatePicker.value =
                            false
                    }, confirmButton = {
                        //Not in use for now as we are using custom date picker
                    }) {
                        Surface {
                            MyDatePicker(onDismiss =
                            {
                                showDatePicker.value =
                                    false
                            }, onConfirm =
                            {
                                it?.let {
                                    val instant =
                                        Instant.ofEpochMilli(it)
                                    val zoneId =
                                        ZoneId.systemDefault()
                                    val localDate =
                                        instant.atZone(zoneId).toLocalDate()
                                    val dateString =
                                        localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                                    onEvent(ReminderEvents.OnDateSelected(dateString))

                                }
                                showDatePicker.value =
                                    false
                            })
                        }
                    }
                }
                Spacer(
                    modifier =
                    Modifier.height(16.dp)
                )
                TextButton(
                    onClick = { showTimePicker.value = true },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                {
                    Text(
                        text = "Time: ${state.time}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(
                    modifier =
                    Modifier.height(8.dp)
                )
                if (showTimePicker.value) {
                    Dialog(onDismissRequest =
                    {
                        showTimePicker.value =
                            false
                    }) {
                        MyTimePicker(onDismiss =
                        {showTimePicker.value = false }, onConfirm =

                        {

                            onEvent(ReminderEvents.OnTimeSelected(it))
                            showTimePicker.value =
                                false
                        })
                    }
                }
            }
        }

    }
}
