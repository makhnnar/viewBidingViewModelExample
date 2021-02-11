package com.easyappsolution.testloginfirebase.ui.repositories

import com.easyappsolution.testloginfirebase.firebaserepository.FirebaseRepository
import com.easyappsolution.testloginfirebase.ui.models.LoggedInUser
import com.easyappsolution.testloginfirebase.ui.models.User
import java.util.*

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(
    private var firebaseRepository:FirebaseRepository
) {

    fun login(
        username: String,
        password: String,
        onLoginUser: OnLoginUser
    ) {
        try {
            firebaseRepository.getLoginData(
                username,
                object :
                    FirebaseRepository.OnLoginUser {
                    override fun onSuccess(realUser: User?) {
                        if(realUser!=null){
                            if(realUser.password == password){
                                onLoginUser.loginAccepted(
                                    LoggedInUser(
                                        realUser
                                    )
                                )
                            }else{
                                onLoginUser.loginDenied("User or Pass wrong")
                            }
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
            onLoginUser.loginDenied("Check your network connection")
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