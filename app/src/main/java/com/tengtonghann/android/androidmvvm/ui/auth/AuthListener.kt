package com.tengtonghann.android.androidmvvm.ui.auth

import androidx.lifecycle.LiveData
import com.tengtonghann.android.androidmvvm.data.db.entities.User

interface AuthListener {

    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
}