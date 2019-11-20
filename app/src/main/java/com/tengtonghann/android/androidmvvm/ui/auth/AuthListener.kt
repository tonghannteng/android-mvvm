package com.tengtonghann.android.androidmvvm.ui.auth

import com.tengtonghann.android.androidmvvm.data.db.entities.User

interface AuthListener {

    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
}