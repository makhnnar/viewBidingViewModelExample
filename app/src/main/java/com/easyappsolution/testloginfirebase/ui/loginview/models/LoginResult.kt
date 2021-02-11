package com.easyappsolution.testloginfirebase.ui.loginview.models

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: String? = null
)