package com.easyappsolution.testloginfirebase.ui.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User (
    val firstName : String? = null,
    val lastName : String? = null,
    val userName : String? = null,
    val password : String? = null,
)