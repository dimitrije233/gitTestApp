package com.example.githubusertestapp.model

data class Repo(
    val name: String,
    val open_issues_count: Int,
    val forks_count: Int,
    val watchers_count: Int
)