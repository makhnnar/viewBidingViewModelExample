package com.easyappsolution.testloginfirebase.ui.signin

import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.easyappsolution.testloginfirebase.R
import com.easyappsolution.testloginfirebase.databinding.FragmentSigninBinding
import com.easyappsolution.testloginfirebase.ui.signin.models.LoggedInUserView
import com.easyappsolution.testloginfirebase.ui.signin.viewmodel.LoginViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSigninBinding

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentSigninBinding.inflate(
            inflater,
            container,
            false
        )
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel.loginFormState.observe(this,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                this.binding.login.isEnabled = loginFormState.isDataValid
                loginFormState.usernameError?.let {
                    this.binding.username.error = getString(it)
                }
                loginFormState.passwordError?.let {
                    this.binding.password.error = getString(it)
                }
            })

        loginViewModel.loginResult.observe(this,
            Observer { loginResult ->
                loginResult ?: return@Observer
                this.binding.loading.visibility = View.GONE
                loginResult.error?.let {
                    showLoginFailed(it)
                }
                loginResult.success?.let {
                    updateUiWithUser(it)
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    this@SignInFragment.binding.username.text.toString(),
                    this@SignInFragment.binding.password.text.toString()
                )
            }
        }
        this.binding.username.addTextChangedListener(afterTextChangedListener)
        this.binding.password.addTextChangedListener(afterTextChangedListener)
        this.binding.password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                    this.binding.username.text.toString(),
                    this.binding.password.text.toString()
                )
            }
            false
        }

        this.binding.login.setOnClickListener {
            this.binding.loading.visibility = View.VISIBLE
            loginViewModel.login(
                this.binding.username.text.toString(),
                this.binding.password.text.toString()
            )
        }
        this.binding.signupBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome =  getString(R.string.welcome) + " " + model.user.firstName + " " + model.user.lastName
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
    }

    private fun showLoginFailed(errorString: String) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}