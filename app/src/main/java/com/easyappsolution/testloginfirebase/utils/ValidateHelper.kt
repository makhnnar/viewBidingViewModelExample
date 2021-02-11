package com.easyappsolution.testloginfirebase.utils

import android.util.Patterns

class ValidateHelper {

    // A placeholder common string validation check
    fun isValueValid(value: String): Boolean {
        return value.isNotBlank()
    }

    // A placeholder password validation check
    fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

}