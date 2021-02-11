package com.easyappsolution.testloginfirebase.ui.signup.di

import com.easyappsolution.testloginfirebase.ui.signup.viewmodel.SignUpViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val signUpViewModelModule = module {
    viewModel {
        SignUpViewModel(
            get(),
            get()
        )
    }
}