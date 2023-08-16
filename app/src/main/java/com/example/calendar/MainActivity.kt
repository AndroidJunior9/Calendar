package com.example.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.calendar.presentataion.navigation.Navigation
import com.example.calendar.ui.theme.CalendarTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendarTheme {

                val navController = rememberNavController()
                Navigation(navHostController = navController)

            }
        }
    }
}

