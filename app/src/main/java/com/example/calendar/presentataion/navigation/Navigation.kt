package com.example.calendar.presentataion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.calendar.presentataion.addreminder.AddReminderScreen
import com.example.calendar.presentataion.addreminder.ReminderViewmodel
import com.example.calendar.presentataion.mainscreen.CalendarUI
import com.example.calendar.presentataion.mainscreen.CalendarViewmodel
import org.koin.androidx.compose.getViewModel

@Composable
fun Navigation(
    navHostController: NavHostController,

) {
    NavHost(navController = navHostController, startDestination = Routes.MAIN_SCREEN) {
        composable(Routes.MAIN_SCREEN) {
            val viewmodel = getViewModel<CalendarViewmodel>()
            CalendarUI(
                state = viewmodel.state,
                onEvent = viewmodel::onEvent,
                navHostController = navHostController
            )
        }
        composable(Routes.ADD_REMINDER) {
            val viewmodel = getViewModel<ReminderViewmodel>()

            AddReminderScreen(
                onEvent = viewmodel::onEvent,
                state = viewmodel.state,
            ) {
                navHostController.popBackStack()
            }
        }
    }
}
