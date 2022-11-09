/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fiapon.androidsolution.ui.auth.RequestState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


private const val MIN_PASSWORD_LENGTH = 6

class LoginViewModel : ViewModel() {
    private var auth = FirebaseAuth.getInstance()

    val loginState = MutableLiveData<RequestState<FirebaseUser>>()
    val passwordState = MutableLiveData<String>("")
    val emailState = MutableLiveData<String>("")

    fun signIn(email: String, password: String) {

        loginState.value = RequestState.Loading

        if (validateFields(email, password)) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        loginState.value = RequestState.Success(auth.currentUser!!)
                    } else {
                        loginState.value = RequestState.Error(
                            Throwable(
                                it.exception?.message ?: "Não foi possível realizar a requisição"
                            )
                        )
                    }
                }
        }
    }

    private fun validateFields(email: String, password: String): Boolean {
        checkPassword(password)
        checkEmail(email)
        var isValid = true
        if (emailState.value?.isEmpty() == false) {
            loginState.value = RequestState.Error(Throwable(emailState.value.toString()))
            isValid = false
        } else if (passwordState.value?.isEmpty() == false) {
            loginState.value = RequestState.Error(Throwable(passwordState.value.toString()))
            isValid = false
        }

        return isValid
    }

    fun checkPassword(password: String) {
        var errorMsg = ""
        if (password.isEmpty())
            errorMsg = "Senha não pode ser vazia"
        else if (password.length < MIN_PASSWORD_LENGTH)
            errorMsg = "Senha tem que ter pelo menos $MIN_PASSWORD_LENGTH caracteres"
        passwordState.value = errorMsg
    }

    fun checkEmail(email: String) {
        var errorMsg = ""
        if (email.isEmpty())
            errorMsg = "E-mail não pode ser vazio"
        emailState.value = errorMsg
    }
}