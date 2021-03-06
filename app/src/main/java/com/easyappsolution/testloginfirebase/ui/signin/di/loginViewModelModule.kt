package com.easyappsolution.testloginfirebase.ui.signin.di

import com.easyappsolution.testloginfirebase.ui.signin.viewmodel.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginViewModelModule = module {
    viewModel {
        LoginViewModel(
            get(),
            get()
        )
    }
}