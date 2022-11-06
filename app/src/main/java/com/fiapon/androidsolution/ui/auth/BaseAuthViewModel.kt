package com.fiapon.androidsolution.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class BaseAuthViewModel : ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    val loggedState = MutableLiveData<RequestState<FirebaseUser>>()

    fun isLoggedIn() {
        auth.currentUser?.reload()

        val user = auth.currentUser
        loggedState.value = RequestState.Loading
        if (user == null) {
            loggedState.value = RequestState.Error(Throwable("User logged off"))
        } else {
            loggedState.value = RequestState.Success(user)
        }
    }
}