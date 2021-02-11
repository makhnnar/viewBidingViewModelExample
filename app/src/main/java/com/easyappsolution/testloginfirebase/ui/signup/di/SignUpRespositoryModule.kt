package com.easyappsolution.testloginfirebase.ui.signup.di

import com.easyappsolution.testloginfirebase.ui.signup.repository.SignUpRepository
import org.koin.dsl.module

val signUpRespositoryModule = module {
    single {
        SignUpRepository(
            get()
        )
    }
}