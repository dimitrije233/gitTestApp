package com.example.githubusertestapp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusertestapp.model.User
import com.example.githubusertestapp.repos.GitHubRepository
import com.example.githubusertestapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: GitHubRepository
): ViewModel() {
    var userState by mutableStateOf<Resource<User>>(Resource.Loading)
        private set

    fun loadUser(username: String) {
        viewModelScope.launch {
            userState = Resource.Loading
            userState = repository.getUser(username).fold(
                onSuccess = { Resource.Success(it) },
                onFailure = { Resource.Error(it.message ?: "Failed to load user") }
            )
        }
    }
}