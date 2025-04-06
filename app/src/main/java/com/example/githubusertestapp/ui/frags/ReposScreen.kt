package com.example.githubusertestapp.ui.frags

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.githubusertestapp.utils.Resource
import com.example.githubusertestapp.viewModel.ReposViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.text.font.FontFamily
import com.example.githubusertestapp.ui.components.TopBarWithSearch


@Composable
fun ReposScreen(
    username: String,
    navController: NavController,
    viewModel: ReposViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val searchQuery = viewModel.searchQuery

    LaunchedEffect(username) {
        viewModel.loadRepos(username)
    }

    Scaffold(
        topBar = {
            TopBarWithSearch(
                searchText = searchQuery,
                onSearchChange = viewModel::onSearchQueryChange,
                onBack = { navController.popBackStack() })
        },
        containerColor = Color.Black
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (state) {
                is Resource.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
                is Resource.Error -> {
                    Text(
                        text = state.message,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                is Resource.Success -> {
                    val repos = state.data.filter {
                        it.name.contains(searchQuery, ignoreCase = true)
                    }

                    LazyColumn {
                        items(repos) { repo ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate("details/$username/${repo.name}")
                                    }
                                    .padding(16.dp)
                            ) {
                                Text(repo.name, color = Color.White, style = MaterialTheme.typography.titleMedium)
                                Text("${repo.open_issues_count} open issues", color = Color.Gray)
                                Divider (
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
}