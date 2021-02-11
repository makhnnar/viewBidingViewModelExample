package com.easyappsolution.testloginfirebase.ui.signup.models

data class SaveFormState (
    val firstNameError : Int? = null,
    val lastNameError : Int? = null,
    val userNameError : Int? = null,
    val passwordError : Int? = null,
    val isDataValid: Boolean = false
)