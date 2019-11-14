package com.tengtonghann.android.androidmvvm.ui.home.profile

import androidx.lifecycle.ViewModel
import com.tengtonghann.android.androidmvvm.data.repositories.UserRepository

class ProfileViewModel(
    repository: UserRepository
) : ViewModel() {

    val user = repository.getUser()
}
