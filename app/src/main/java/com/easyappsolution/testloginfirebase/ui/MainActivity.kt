package com.easyappsolution.testloginfirebase.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.easyappsolution.testloginfirebase.R
import com.easyappsolution.testloginfirebase.ui.loginview.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment(LoginFragment())
    }

    fun addFragment(fragment: Fragment?) {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_fragment, fragment)
                .addToBackStack(fragment.javaClass.simpleName)
                .setPrimaryNavigationFragment(fragment)
                .commit()
        }
    }

}