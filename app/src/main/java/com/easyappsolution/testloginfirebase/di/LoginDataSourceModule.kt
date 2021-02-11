package com.easyappsolution.testloginfirebase.di

import com.easyappsolution.testloginfirebase.ui.repositories.LoginDataSource
import com.easyappsolution.testloginfirebase.ui.repositories.LoginRepository
import org.koin.dsl.module

val loginDataSourceModule = module {
    single {
        LoginDataSource(
            get()
        )
    }
}

val loginDataRepository = module {
    single {
        LoginRepository(
            get()
        )
    }
}