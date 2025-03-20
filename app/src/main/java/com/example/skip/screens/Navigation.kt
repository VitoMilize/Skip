package com.montanainc.simpleloginscreen.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skip.screens.MainScreenStudent
import com.example.skip.screens.MainScreenTeacher

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "MainTeacher"
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
    }
}