package com.riseup.noteapp.models

data class UserResponse(
    val token: String,
    val user: User
)