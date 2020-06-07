package com.easyappsolution.testloginfirebase.firebaserepository

import android.util.Log
import com.google.firebase.database.*


class FirebaseRepository{

    private val firebaseDatabase : FirebaseDatabase

    private val fireDbInstance : DatabaseReference

    init {
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseDatabase.setLogLevel(Logger.Level.DEBUG)
        fireDbInstance = firebaseDatabase.reference
    }

    fun testImpl(){
        fireDbInstance.child("msj").setValue("Hello, World!")
    }

    fun getLoginData(userName:String,onLoginData:OnLoginData){
        fireDbInstance.child("users").child(userName).addListenerForSingleValueEvent(
            object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    onLoginData.onFailed()
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val pass = p0.getValue(String::class.java)
                    onLoginData.onSuccess(pass)
                }

            }
        )
    }

    interface OnLoginData{
        fun onSuccess(realPass:String?)
        fun onFailed()
    }

}