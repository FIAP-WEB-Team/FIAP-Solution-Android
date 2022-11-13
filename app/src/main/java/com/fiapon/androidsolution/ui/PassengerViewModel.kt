/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fiapon.androidsolution.data_context.retrofitPassengerService
import com.fiapon.androidsolution.model.flights.Flight
import com.fiapon.androidsolution.model.passengers.Passenger
import com.fiapon.androidsolution.model.tickets.Ticket
import com.fiapon.androidsolution.ui.auth.RequestState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

    var checkDataState = MutableLiveData<RequestState<Ticket>>()

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

    fun createTicket(flight: Flight) {
        if (!isCheckingData) {
            isCheckingData = true
            updateFooterEnabledStatus()
            checkDataState.value = RequestState.Loading

            val passenger = getPassengerData()

            val longhand = retrofitPassengerService().setPassenger(passenger, "Bearer $token")

            longhand.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.body() != null) {
                        persistTicket(Ticket(passenger.id, flight.number))
                    } else {
                        checkDataState.value =
                            RequestState.Error(Error("Null body message received from token request"))
                        isCheckingData = false
                        updateFooterEnabledStatus()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    checkDataState.value = RequestState.Error(t)
                    isCheckingData = false
                    updateFooterEnabledStatus()
                }

            })
        }
    }

    private fun updateFooterEnabledStatus(){
        enabledFooterButton.value = isPassengerDataValid && !isCheckingData
    }

    private fun persistTicket(ticket: Ticket) {
        isCheckingData = false
        updateFooterEnabledStatus()
    }

    private fun getPassengerData(): Passenger {
        return Passenger(
            1,
            birthDate.value!!,
            firstName.value!!,
            lastName.value!!,
            gender.value!!,
            nationality.value!!
        )
    }
}