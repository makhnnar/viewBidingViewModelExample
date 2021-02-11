package com.easyappsolution.testloginfirebase.di

import com.easyappsolution.testloginfirebase.utils.ValidateHelper
import org.koin.dsl.module

val validateHelperModule = module {
    single {
        ValidateHelper()
    }
}