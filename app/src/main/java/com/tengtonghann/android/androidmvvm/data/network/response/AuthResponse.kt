package com.tengtonghann.android.androidmvvm.data.network.response

import com.tengtonghann.android.androidmvvm.data.db.entities.User

data class AuthResponse(
    val isSuccessful: Boolean?,
    val message: String?,
    val user: User
)