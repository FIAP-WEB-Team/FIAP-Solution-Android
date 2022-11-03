package com.fiapon.androidsolution

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PassengerViewModel : ViewModel() {
    var enabledFooterButton = MutableLiveData<Boolean>(false)

    var firstName = MutableLiveData<String>("")
    var lastName = MutableLiveData<String>("")
    var birthDate = MutableLiveData<String>("")
    var gender = MutableLiveData<String>("")
    var nationality = MutableLiveData<String>("")


    fun validateFields() {
        enabledFooterButton.value =
            validFirstName() && validLastName() && validBirthDate() && validGender() && validNationality()
    }

    private fun validFirstName(): Boolean {
        return firstName.value?.isNotEmpty() ?: false
    }

    private fun validLastName(): Boolean {
        return lastName.value?.isNotEmpty() ?: false
    }

    private fun validBirthDate(): Boolean {
        return birthDate.value.let { it?.length == 10 }
    }

    private fun validGender(): Boolean {
        return gender.value?.isNotEmpty() ?: false
    }

    private fun validNationality(): Boolean {
        return nationality.value?.isNotEmpty() ?: false
    }
}