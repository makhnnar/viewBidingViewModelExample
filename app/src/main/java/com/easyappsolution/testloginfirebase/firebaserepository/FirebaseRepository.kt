package com.easyappsolution.testloginfirebase.firebaserepository

import com.google.firebase.database.*


class FirebaseRepository : ChildEventListener {

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

    override fun onCancelled(p0: DatabaseError) {

    }

    override fun onChildMoved(p0: DataSnapshot, p1: String?) {

    }

    override fun onChildChanged(p0: DataSnapshot, p1: String?) {

    }

    override fun onChildAdded(p0: DataSnapshot, p1: String?) {

    }

    override fun onChildRemoved(p0: DataSnapshot) {

    }

}