/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiapon.androidsolution.model.flights.Flight
import com.fiapon.androidsolution.model.passengers.Passenger
import com.fiapon.androidsolution.model.tickets.Ticket
import com.fiapon.androidsolution.ui.auth.RequestState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class PassengerViewModel : ViewModel() {
    var enabledFooterButton = MutableLiveData<Boolean>(false)

    var firstName = MutableLiveData("")
    var lastName = MutableLiveData("")
    var birthDate = MutableLiveData("")
    var gender = MutableLiveData("")
    var nationality = MutableLiveData("")

    var checkDataState = MutableLiveData<RequestState<Ticket>>()
    private var isCheckingData = false


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

    private fun stringToDate(dateStr: String): LocalDate {
        return LocalDate.now()
    }

    private fun <R> CoroutineScope.executeAsyncTask(
        onPreExecute: () -> Unit,
        doInBackground: () -> R,
        onPostExecute: (R) -> Unit
    ) = launch {
        onPreExecute()
        val result =
            withContext(Dispatchers.IO) { // runs in background thread without blocking the Main Thread
                doInBackground()
            }
        onPostExecute(result)
    }

    fun createTicket(flight: Flight) {
        if (!isCheckingData) {
            isCheckingData = true
            checkDataState.value = RequestState.Loading

            val passenger = getPassengerData()

            viewModelScope.executeAsyncTask(
                onPreExecute = {},
                doInBackground = {
                    val ticket = Ticket(passenger.id, flight.number)

                    Thread.sleep(5000)

                    ticket
                },
                onPostExecute = {
                    isCheckingData = false
                    checkDataState.value = RequestState.Success(it)
                },
            )
        }
    }

    private fun getPassengerData(): Passenger {
        return Passenger(
            1,
            stringToDate(birthDate.value!!),
            firstName.value!!,
            lastName.value!!,
            gender.value!!,
            nationality.value!!
        )
    }
}