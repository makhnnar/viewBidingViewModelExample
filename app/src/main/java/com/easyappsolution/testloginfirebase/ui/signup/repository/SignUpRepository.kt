package com.easyappsolution.testloginfirebase.ui.signup.repository

import com.easyappsolution.testloginfirebase.firebaserepository.FirebaseRepository
import com.easyappsolution.testloginfirebase.ui.models.User

class SignUpRepository(
    private var firebaseRepository: FirebaseRepository
) {

    fun saveNewUser(
        firstName : String,
        lastName : String,
        userName : String,
        password : String,
        onSaveUser: FirebaseRepository.OnSaveUser
    ){
        val newUser = User(
            firstName,
            lastName,
            userName,
            password
        )
        firebaseRepository.writeNewUser(
            newUser,
            onSaveUser
        )
    }

}