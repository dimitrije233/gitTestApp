package com.example.githubusertestapp.network

import retrofit2.http.GET
import com.example.githubusertestapp.model.Repo
import com.example.githubusertestapp.model.Tag
import com.example.githubusertestapp.model.User
import retrofit2.http.Path

interface GitHubApi {
    @GET("users/{user}")
    suspend fun getUser(@Path("user") user: String): User

    @GET("users/{user}/repos")
    suspend fun getUserRepos(@Path("user") user: String): List<Repo>

    @GET("repos/{user}/{repo}")
    suspend fun getRepoDetails(
        @Path("user") user: String,
        @Path("repo") repo: String
    ): Repo

    @GET("repos/{user}/{repo}/tags")
    suspend fun getRepoTags(
        @Path("user") user: String,
        @Path("repo") repo: String
    ): List<Tag>
}