package com.example.githubusertestapp.ui.frags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.githubusertestapp.ui.components.TopBar
import com.example.githubusertestapp.utils.Resource
import com.example.githubusertestapp.viewModel.ProfileViewModel

@Composable
fun ProfileScreen(username: String, navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) { viewModel.loadUser(username) }
    val state = viewModel.userState
    Scaffold(
        topBar = {
            TopBar(title = "Repo Details", onBack = { navController.popBackStack() })
        },
        containerColor = Color.Black
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black).padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is Resource.Loading -> CircularProgressIndicator(color = Color.White)
                is Resource.Error -> Text("${state.message}", color = Color.Red)
                is Resource.Success -> {
                    val user = state.data
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = user.avatar_url,
                            contentDescription = null,
                            modifier = Modifier.size(160.dp).clip(CircleShape)
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            user.login,
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            user.bio ?: "No bio",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}