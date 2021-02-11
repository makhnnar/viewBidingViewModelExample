package com.easyappsolution.testloginfirebase.ui.signin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.easyappsolution.testloginfirebase.R
import com.easyappsolution.testloginfirebase.ui.repositories.LoginDataSource
import com.easyappsolution.testloginfirebase.ui.repositories.LoginRepository
import com.easyappsolution.testloginfirebase.ui.models.LoggedInUser
import com.easyappsolution.testloginfirebase.ui.signin.models.LoggedInUserView
import com.easyappsolution.testloginfirebase.ui.signin.models.LoginFormState
import com.easyappsolution.testloginfirebase.ui.signin.models.LoginResult
import com.easyappsolution.testloginfirebase.utils.ValidateHelper


class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val validateHelper: ValidateHelper
) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        loginRepository.login(
            username,
            password,
            object : LoginDataSource.OnLoginUser{
                override fun loginAccepted(loggedInUser: LoggedInUser) {
                    _loginResult.value =
                        LoginResult(success = LoggedInUserView(user = loggedInUser.user))
                }

                override fun loginDenied(reason: String) {
                    _loginResult.value = LoginResult(error = reason)
                }

            }
        )
    }

    fun loginDataChanged(username: String, password: String) {
        if (!validateHelper.isValueValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!validateHelper.isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

}