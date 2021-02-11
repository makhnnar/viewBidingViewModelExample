package com.easyappsolution.testloginfirebase.di

import com.easyappsolution.testloginfirebase.firebaserepository.FirebaseRepository
import org.koin.dsl.module

val firebaseModule = module {
    single {
        FirebaseRepository()
    }
}