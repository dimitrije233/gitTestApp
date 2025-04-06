package com.example.githubusertestapp.model

data class Tag(
    val name: String,
    val commit: Commit
)

data class Commit(
    val sha: String
)