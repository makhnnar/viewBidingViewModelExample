package com.easyappsolution.testloginfirebase.ui.signin.models

import com.easyappsolution.testloginfirebase.ui.models.User

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val user: User
    //... other data fields that may be accessible to the UI
)