package com.easyappsolution.testloginfirebase.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.easyappsolution.testloginfirebase.R
import com.easyappsolution.testloginfirebase.databinding.ActivityMainBinding
import com.easyappsolution.testloginfirebase.ui.signin.SignInFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}