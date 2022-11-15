/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui.passenger

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fiapon.androidsolution.model.passengers.Passenger
import com.fiapon.androidsolution.model.passengers.PassengerSource
import com.fiapon.androidsolution.model.tickets.Ticket
import com.fiapon.androidsolution.model.tickets.TicketSource
import com.fiapon.androidsolution.ui.auth.RequestState


class PassengerViewModelFactory(private val someString: String) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        PassengerViewModel(someString) as T
}

class PassengerViewModel(private val token: String) : ViewModel() {
    var enabledFooterButton = MutableLiveData(false)

    var firstName = MutableLiveData("")
    var lastName = MutableLiveData("")
    var birthDate = MutableLiveData("")
    var gender = MutableLiveData("")
    var nationality = MutableLiveData("")

    var checkDataState = MutableLiveData<RequestState<String>>()

    private var isCheckingData = false
    private var isPassengerDataValid = false


    fun validateFields() {
        isPassengerDataValid =
            validFirstName() && validLastName() && validBirthDate() && validGender() && validNationality()
        updateFooterEnabledStatus()
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

    fun createTicket(flightID: Int) {
        if (!isCheckingData) {
            isCheckingData = true
            updateFooterEnabledStatus()
            checkDataState.value = RequestState.Loading

            val passenger = getPassengerData()

            PassengerSource().createPassenger(passenger, token) {
                when (it) {
                    is RequestState.Success -> {
                        persistTicket(Ticket(it.data, flightID))
                    }
                    else -> {
                        checkDataState.value = it
                        isCheckingData = false
                        updateFooterEnabledStatus()
                    }
                }
            }
        }
    }

    private fun persistTicket(ticket: Ticket) {
        TicketSource().createTickets(ticket, token) {
            checkDataState.value = it
            isCheckingData = false
            updateFooterEnabledStatus()
        }
    }

    private fun updateFooterEnabledStatus() {
        enabledFooterButton.value = isPassengerDataValid && !isCheckingData
    }

    private fun getPassengerData(): Passenger {
        return Passenger(
            birthDate.value!!,
            firstName.value!!,
            lastName.value!!,
            gender.value!!,
            nationality.value!!
        )
    }
}