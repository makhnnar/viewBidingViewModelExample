package com.easyappsolution.testloginfirebase.ui.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.easyappsolution.testloginfirebase.R
import com.easyappsolution.testloginfirebase.firebaserepository.FirebaseRepository
import com.easyappsolution.testloginfirebase.ui.signup.models.SaveFormState
import com.easyappsolution.testloginfirebase.ui.signup.models.SignUpResult
import com.easyappsolution.testloginfirebase.ui.signup.repository.SignUpRepository
import com.easyappsolution.testloginfirebase.utils.ValidateHelper

class SignUpViewModel(
    private val signUpRepository:SignUpRepository,
    private val validateHelper: ValidateHelper
) : ViewModel(),FirebaseRepository.OnSaveUser {


    private val _signUpForm = MutableLiveData<SaveFormState>()
    val signUpFormState: LiveData<SaveFormState> = _signUpForm

    private val _sigUpResult = MutableLiveData<SignUpResult>()
    val sigUpResult: LiveData<SignUpResult> = _sigUpResult

    fun saveUser(
        firstName : String,
        lastName : String,
        userName : String,
        password : String
    ){
        signUpRepository.saveNewUser(
                firstName,
                lastName,
                userName,
                password,
                this
        )
    }

    fun signUpDataChanged(
         firstName : String,
         lastName : String,
         userName : String,
         password : String
    ) {
        if (!validateHelper.isValueValid(firstName)) {
            _signUpForm.value = SaveFormState(firstNameError = R.string.invalid_first_name)
        } else if (!validateHelper.isValueValid(lastName)) {
            _signUpForm.value = SaveFormState(lastNameError = R.string.invalid_last_name)
        }else if (!validateHelper.isValueValid(userName)) {
            _signUpForm.value = SaveFormState(userNameError = R.string.invalid_username)
        } else if (!validateHelper.isPasswordValid(password)) {
            _signUpForm.value = SaveFormState(passwordError = R.string.invalid_password)
        } else {
            _signUpForm.value = SaveFormState(isDataValid = true)
        }
    }

    override fun onSuccess() {
        _sigUpResult.value = SignUpResult(
            R.string.signup_success,
            true
        )
    }

    override fun onFailed() {
        _sigUpResult.value = SignUpResult(
            R.string.signup_failed,
            false
        )
    }
}