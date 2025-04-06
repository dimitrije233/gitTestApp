package com.example.githubusertestapp.repos

import com.example.githubusertestapp.model.Repo
import com.example.githubusertestapp.model.Tag
import com.example.githubusertestapp.model.User
import com.example.githubusertestapp.network.GitHubApi
import javax.inject.Inject

class GitHubRepository @Inject constructor(private val api: GitHubApi) {
    suspend fun getUser(username: String): Result<User> = runCatching {
        api.getUser(username)
    }

    suspend fun getUserRepos(username: String): Result<List<Repo>> = runCatching {
        api.getUserRepos(username)
    }

    suspend fun getRepoDetails(user: String, repo: String): Result<Repo> = runCatching {
        api.getRepoDetails(user, repo)
    }

    suspend fun getRepoTags(user: String, repo: String): Result<List<Tag>> = runCatching {
        api.getRepoTags(user, repo)
    }
}