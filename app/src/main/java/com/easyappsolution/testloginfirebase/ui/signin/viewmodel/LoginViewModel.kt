package com.easyappsolution.testloginfirebase.ui.signin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.easyappsolution.testloginfirebase.R
import com.easyappsolution.testloginfirebase.ui.repositories.LoginDataSource
import com.easyappsolution.testloginfirebase.ui.repositories.LoginRepository
import com.easyappsolution.testloginfirebase.ui.models.LoggedInUser
import com.easyappsolution.testloginfirebase.ui.signin.models.LoggedInUserView
import com.easyappsolution.testloginfirebase.ui.signin.models.LoginFormState
import com.easyappsolution.testloginfirebase.ui.signin.models.LoginResult


class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        loginRepository.login(
            username,
            password,
            object : LoginDataSource.OnLoginUser{
                override fun loginAccepted(loggedInUser: LoggedInUser) {
                    _loginResult.value =
                        LoginResult(success = LoggedInUserView(displayName = loggedInUser.displayName))
                }

                override fun loginDenied(reason: String) {
                    _loginResult.value = LoginResult(error = reason)
                }

            }
        )
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}