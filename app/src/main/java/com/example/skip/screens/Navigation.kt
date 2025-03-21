package com.montanainc.simpleloginscreen.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skip.screens.CreateRequestScreen
import com.example.skip.screens.MainScreenStudent
import com.example.skip.screens.MainScreenTeacher
import com.example.skip.screens.WatchAllRequestsScreen
import com.example.skip.screens.WatchRequestsScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Login"
    ) {
        composable(route = "Login") {
            LoginScreen(navController)
        }
        composable(route = "Signup") {
            SignupScreen(navController)
        }
        composable(route = "MainStudent") {
            MainScreenStudent(navController)
        }
        composable(route = "MainTeacher") {
            MainScreenTeacher(navController)
        }
        composable(route = "CreateRequest") {
            CreateRequestScreen(navController)
        }
        composable(route = "WatchRequests") {
            WatchRequestsScreen()
        }
        composable(route = "WatchAllRequests") {
            WatchAllRequestsScreen()
        }
    }
}