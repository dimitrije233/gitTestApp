package com.example.githubusertestapp.model

data class User(
    val login: String,
    val avatar_url: String,
    val bio: String? = null
)