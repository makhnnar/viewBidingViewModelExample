package com.easyappsolution.testloginfirebase.ui.repositories

import com.easyappsolution.testloginfirebase.firebaserepository.FirebaseRepository
import com.easyappsolution.testloginfirebase.ui.models.LoggedInUser
import java.util.*

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(private var firebase:FirebaseRepository) {

    fun login(
        username: String,
        password: String,
        onLoginUser: OnLoginUser
    ) {
        try {
            firebase.getLoginData(
                username,
                object : FirebaseRepository.OnLoginData{
                    override fun onSuccess(realPass: String?) {
                        if(realPass == password){
                            onLoginUser.loginAccepted(
                                LoggedInUser(
                                    UUID.randomUUID().toString(),
                                    username
                                )
                            )
                        }else{
                            onLoginUser.loginDenied("User or Pass wrong")
                        }
                    }

                    override fun onFailed() {
                        onLoginUser.loginDenied("Check your network connection")
                    }

                }
            )
        } catch (e: Throwable) {

        }
    }

    fun logout() {
        // TODO: revoke authentication
    }

    interface OnLoginUser{
        fun loginAccepted(loggedInUser: LoggedInUser)
        fun loginDenied(reason:String)
    }

}