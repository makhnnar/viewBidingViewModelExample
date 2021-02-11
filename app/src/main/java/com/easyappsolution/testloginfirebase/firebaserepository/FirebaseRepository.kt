package com.easyappsolution.testloginfirebase.firebaserepository

import com.easyappsolution.testloginfirebase.ui.models.User
import com.google.firebase.database.*

class FirebaseRepository{

    private val firebaseDatabase : FirebaseDatabase

    private val fireDbInstance : DatabaseReference

    private val dbRef = "users"

    init {
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseDatabase.setLogLevel(Logger.Level.DEBUG)
        fireDbInstance = firebaseDatabase.reference
    }

    /**
     * We gets users data using username as key on firebase data base
     * */
    fun getLoginData(userName:String, onLoginUser:OnLoginUser){
        fireDbInstance.child(dbRef).child(userName).addListenerForSingleValueEvent(
            object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    onLoginUser.onFailed()
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val pass = p0.getValue(User::class.java)
                    onLoginUser.onSuccess(pass)
                }
            }
        )
    }

    fun writeNewUser(
        user:User,
        onSaveUser: OnSaveUser
    ) {
        fireDbInstance.child(dbRef)
            .child(user.userName?:"")
            .setValue(user)
            .addOnSuccessListener {
                onSaveUser.onSuccess()
            }
            .addOnFailureListener {
                onSaveUser.onFailed()
            }
    }

    interface OnLoginUser{
        fun onSuccess(realPass:User?)
        fun onFailed()
    }

    interface OnSaveUser{
        fun onSuccess()
        fun onFailed()
    }

}