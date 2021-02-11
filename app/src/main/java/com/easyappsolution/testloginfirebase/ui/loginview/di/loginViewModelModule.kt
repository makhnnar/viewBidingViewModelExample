package com.easyappsolution.testloginfirebase.ui.loginview.di

import com.easyappsolution.testloginfirebase.ui.loginview.viewmodel.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginViewModelListModule = module {
    viewModel {
        LoginViewModel(
            get()
        )
    }
}