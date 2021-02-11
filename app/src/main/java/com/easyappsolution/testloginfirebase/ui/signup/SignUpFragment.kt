package com.easyappsolution.testloginfirebase.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.easyappsolution.testloginfirebase.R
import com.easyappsolution.testloginfirebase.databinding.FragmentSigninBinding
import com.easyappsolution.testloginfirebase.databinding.FragmentSignupBinding
import com.easyappsolution.testloginfirebase.ui.signin.viewmodel.LoginViewModel
import com.easyappsolution.testloginfirebase.ui.signup.viewmodel.SignUpViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment() {

    private lateinit var binding : FragmentSignupBinding

    private val signUpViewModel: SignUpViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentSignupBinding.inflate(
            inflater,
            container,
            false
        )
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        this.binding.signUpBtn.setOnClickListener {
            signUpViewModel.saveUser(
                binding.firstName.text.toString(),
                binding.lastName.text.toString(),
                binding.username.text.toString(),
                binding.password.text.toString(),
            )
        }
    }

    fun initObservers(){
        signUpViewModel.signUpFormState.observe(
            this,
            Observer { saveFormState ->
                if(saveFormState == null){
                    return@Observer
                }
                this.binding.signUpBtn.isEnabled = saveFormState.isDataValid
                saveFormState.firstNameError?.let {
                    binding.firstName.error = getString(it)
                }
                saveFormState.lastNameError?.let {
                    binding.lastName.error = getString(it)
                }
                saveFormState.userNameError?.let {
                    binding.username.error = getString(it)
                }
                saveFormState.passwordError?.let {
                    binding.password.error = getString(it)
                }
            }
        )
        signUpViewModel.sigUpResult.observe(
            this,
            Observer { signUpResult ->
                signUpResult ?: return@Observer
                this.binding.loading.visibility = View.GONE
                signUpResult.result?.let {
                    showResult(
                        getString(it)
                    )
                }
                if(signUpResult.goToSingIn){
                    findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
                }
            }
        )
        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                signUpViewModel.signUpDataChanged(
                    this@SignUpFragment.binding.firstName.text.toString(),
                    this@SignUpFragment.binding.lastName.text.toString(),
                    this@SignUpFragment.binding.username.text.toString(),
                    this@SignUpFragment.binding.password.text.toString()
                )
            }
        }
        this.binding.firstName.addTextChangedListener(afterTextChangedListener)
        this.binding.lastName.addTextChangedListener(afterTextChangedListener)
        this.binding.username.addTextChangedListener(afterTextChangedListener)
        this.binding.password.addTextChangedListener(afterTextChangedListener)
    }

    private fun showResult(errorString: String) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

}