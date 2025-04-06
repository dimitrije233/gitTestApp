package com.example.githubusertestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.githubusertestapp.ui.frags.ProfileScreen
import com.example.githubusertestapp.ui.frags.RepoDetailsScreen
import com.example.githubusertestapp.ui.frags.ReposScreen
import com.example.githubusertestapp.ui.frags.UsernameInputScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    background = Color.Black,
                    surface = Color.Black,
                    primary = Color.White,
                    onPrimary = Color.Black,
                    onSurface = Color.White
                ),
                typography = Typography(
                    bodyLarge = TextStyle(color = Color.White),
                    bodyMedium = TextStyle(color = Color.White),
                    bodySmall = TextStyle(color = Color.Gray),
                    titleMedium = TextStyle(color = Color.White)
                )
            ) {
                GitHubApp()
            }
        }
    }
}

@Composable
fun GitHubApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "input") {
        composable("input") { UsernameInputScreen(navController) }
        composable("repos/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            ReposScreen(username = username, navController = navController)
        }
        composable("details/{username}/{repo}") {
            val username = it.arguments?.getString("username") ?: return@composable
            val repo = it.arguments?.getString("repo") ?: return@composable
            RepoDetailsScreen(username, repo, navController = navController)
        }
        composable("profile/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: return@composable
            ProfileScreen(username, navController = navController)
        }
    }
}
