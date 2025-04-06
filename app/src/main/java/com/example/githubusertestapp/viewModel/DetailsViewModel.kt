package com.example.githubusertestapp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusertestapp.model.Repo
import com.example.githubusertestapp.model.Tag
import com.example.githubusertestapp.model.User
import com.example.githubusertestapp.repos.GitHubRepository
import com.example.githubusertestapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: GitHubRepository
): ViewModel() {
    var headerState by mutableStateOf<Resource<Pair<User, Repo>>>(Resource.Loading)
        private set
    var tagsState by mutableStateOf<Resource<List<Tag>>>(Resource.Loading)
        private set

    fun loadDetails(user: String, repo: String) {
        viewModelScope.launch {
            headerState = Resource.Loading
            tagsState = Resource.Loading
            val userRes = repository.getUser(user)
            val repoRes = repository.getRepoDetails(user, repo)
            val tagsRes = repository.getRepoTags(user, repo)

            if (userRes.isSuccess && repoRes.isSuccess) {
                headerState = Resource.Success(userRes.getOrThrow() to repoRes.getOrThrow())
            } else {
                headerState = Resource.Error("Failed loading repo or user")
            }

            tagsState = tagsRes.fold(
                onSuccess = { Resource.Success(it) },
                onFailure = { Resource.Error(it.message ?: "Failed loading tags") }
            )
        }
    }
}