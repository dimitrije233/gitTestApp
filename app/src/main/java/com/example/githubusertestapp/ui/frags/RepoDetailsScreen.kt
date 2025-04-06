package com.example.githubusertestapp.ui.frags

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.githubusertestapp.utils.Resource
import com.example.githubusertestapp.viewModel.DetailsViewModel
import coil.compose.AsyncImage
import com.example.githubusertestapp.ui.components.TopBar

@Composable
fun RepoDetailsScreen(user: String, repo: String, navController: NavController,
                      viewModel: DetailsViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) { viewModel.loadDetails(user, repo) }
    val headerState = viewModel.headerState
    val tagsState = viewModel.tagsState
    Scaffold(
        topBar = {
            TopBar(title = "Repo Details", onBack = { navController.popBackStack() })
        },
        containerColor = Color.Black
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            item {
                when (headerState) {
                    is Resource.Loading -> CircularProgressIndicator(color = Color.White)
                    is Resource.Error -> Text(
                        "Error: ${headerState.message}",
                        color = Color.Red,
                        fontFamily = FontFamily.Monospace
                    )

                    is Resource.Success -> {
                        val (userInfo, repoInfo) = headerState.data
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = userInfo.avatar_url,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        navController.navigate("profile/${userInfo.login}")
                                    }
                            )
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text(
                                    userInfo.login,
                                    color = Color.White,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    repoInfo.name,
                                    color = Color.White,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    "Forks: ${repoInfo.forks_count} Watchers: ${repoInfo.watchers_count}",
                                    color = Color.Gray,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                    }
                }
            }

            when (tagsState) {
                is Resource.Loading -> item { CircularProgressIndicator(color = Color.White) }
                is Resource.Error -> item { Text("${tagsState.message}", color = Color.Red) }
                is Resource.Success -> {
                    items(tagsState.data) { tag ->
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Text(
                                tag.name,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                tag.commit.sha,
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = FontFamily.Monospace
                            )
                            Divider(
                                color = Color.White,
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
