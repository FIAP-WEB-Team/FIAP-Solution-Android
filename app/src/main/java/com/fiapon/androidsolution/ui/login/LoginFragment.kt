/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.fiapon.androidsolution.R
import com.fiapon.androidsolution.ui.auth.NAVIGATION_KEY
import com.fiapon.androidsolution.ui.auth.RequestState
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private val viewModel = LoginViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        btnLogin.setOnClickListener { onLoginClick() }
        editTextPasswordLogin.setOnFocusChangeListener { _, hasFocus ->
            onPasswordFocusChange(
                hasFocus
            )
        }
        editTextEmailLogin.setOnFocusChangeListener { _, hasFocus ->
            onEmailFocusChange(
                hasFocus
            )
        }
    }

    private fun onLoginClick() {
        if (btnLogin.isEnabled)
            viewModel.signIn(
                editTextEmailLogin.text.toString(),
                editTextPasswordLogin.text.toString()
            )
    }

    private fun onPasswordFocusChange(hasFocus: Boolean) {
        if (!hasFocus)
            viewModel.checkPassword(editTextPasswordLogin.text.toString())
    }

    private fun onEmailFocusChange(hasFocus: Boolean) {
        if (!hasFocus)
            viewModel.checkEmail(editTextEmailLogin.text.toString())
    }

    private fun setObservers() {
        viewModel.passwordState.observe(viewLifecycleOwner) {
            txtPasswordError.text = it
        }
        viewModel.emailState.observe(viewLifecycleOwner) {
            txtEmailError.text = it
        }
        viewModel.loginState.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Error -> {
                    Toast.makeText(context, it.throwable.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is RequestState.Success -> {
                    val navId = arguments?.getInt(NAVIGATION_KEY)
                    if (navId == null)
                        findNavController().navigate(R.id.main_nav_graph)
                    else
                        findNavController().popBackStack(navId, false)
                }
                is RequestState.Loading -> {}
            }
        }
    }
}