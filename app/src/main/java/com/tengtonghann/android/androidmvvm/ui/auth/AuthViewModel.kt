package com.tengtonghann.android.androidmvvm.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.tengtonghann.android.androidmvvm.data.repositories.UserRepository
import com.tengtonghann.android.androidmvvm.util.ApiException
import com.tengtonghann.android.androidmvvm.util.Coroutine
import com.tengtonghann.android.androidmvvm.util.NoInternetException

class AuthViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    var name: String? = null
    var email: String? = null
    var password: String? = null
    var passwordconfirm: String? = null
    var authListener: AuthListener? = null


    fun getLoggedInUser() = userRepository.getUser()

    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }
//        authListener?.onSuccess()
        // success
//        val loginResponse = UserRepository().userLogin(email!!, password!!)
//        authListener?.onSuccess(loginResponse)
        Coroutine.main {
            try {
                val authResponse = userRepository.userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    userRepository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e : NoInternetException) {
                authListener?.onFailure(e.message!!)
            }


            /*
                val response = UserRepository().userLogin(email!!, password!!)
                if (response.isSuccessful) {
                    authListener?.onSuccess(response.body()?.user!!)
                } else {
                    authListener?.onFailure("Error code: ${response.code()}")
                }
             */
        }
    }

    fun onLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignup(view: View) {
        Intent(view.context, SignupActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignupButtonClick(view: View) {
        authListener?.onStarted()

        if (name.isNullOrEmpty()) {
            authListener?.onFailure("Name is required")
            return
        }
        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Email is required")
            return
        }
        if (password.isNullOrEmpty()) {
            authListener?.onFailure("Please enter a password")
            return
        }
        if (password != passwordconfirm) {
            authListener?.onFailure("Password did not match")
            return
        }

        Coroutine.main {
            try {
                val authResponse = userRepository.userSignup(name!!, email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    userRepository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e : NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }
    }
}