package com.easyappsolution.testloginfirebase

import android.app.Application
import com.easyappsolution.testloginfirebase.di.firebaseModule
import com.easyappsolution.testloginfirebase.di.loginDataRepository
import com.easyappsolution.testloginfirebase.di.loginDataSourceModule
import com.easyappsolution.testloginfirebase.di.validateHelperModule
import com.easyappsolution.testloginfirebase.ui.signin.di.loginViewModelModule
import com.easyappsolution.testloginfirebase.ui.signup.di.signUpRespositoryModule
import com.easyappsolution.testloginfirebase.ui.signup.di.signUpViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TestLoginFirebaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(
                this@TestLoginFirebaseApplication
            )
            androidLogger()
            modules(
                listOf(
                    firebaseModule,
                    loginDataSourceModule,
                    loginDataRepository,
                    loginViewModelModule,
                    validateHelperModule,
                    signUpRespositoryModule,
                    signUpViewModelModule
                )
            )
        }
    }

}