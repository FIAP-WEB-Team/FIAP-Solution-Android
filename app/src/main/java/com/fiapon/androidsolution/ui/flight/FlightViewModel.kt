package com.fiapon.androidsolution.ui.flight

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FlightViewModel : ViewModel() {
    var arrival = MutableLiveData("")
    var departure = MutableLiveData("")
    var departureDate = MutableLiveData("")

    private fun validArrival(): Boolean {
        return true
    }

    private fun validDeparture(): Boolean {
        return true
    }

    private fun validDepartureDate(): Boolean {
        if (departureDate.value?.isNotEmpty() == true){
            val localDate = LocalDate.parse(departureDate.value!!, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            return LocalDate.now() <= localDate
        }
        return false
    }

    fun validateFields(): Boolean {
        return (validArrival() && validDeparture() && validDepartureDate())
    }

}