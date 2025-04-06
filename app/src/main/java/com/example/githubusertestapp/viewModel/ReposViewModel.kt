package com.example.githubusertestapp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusertestapp.model.Repo
import com.example.githubusertestapp.repos.GitHubRepository
import com.example.githubusertestapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val repository: GitHubRepository
): ViewModel() {
    var state by mutableStateOf<Resource<List<Repo>>>(Resource.Loading)
        private set

    var searchQuery by mutableStateOf("")
        private set

    fun onSearchQueryChange(query: String) {
        searchQuery = query
    }

    fun loadRepos(username: String) {
        viewModelScope.launch {
            state = Resource.Loading
            repository.getUserRepos(username)
                .onSuccess { state = Resource.Success(it) }
                .onFailure { state = Resource.Error(it.message ?: "Unknown error") }
        }
    }
}