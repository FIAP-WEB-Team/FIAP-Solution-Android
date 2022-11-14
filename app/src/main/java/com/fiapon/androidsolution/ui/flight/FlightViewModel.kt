package com.fiapon.androidsolution.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FlightViewModel : ViewModel() {
    var arrival = MutableLiveData("")
    var arrivalDate = MutableLiveData("")
    var departure = MutableLiveData("")
    var departureDate = MutableLiveData("")
    var price = MutableLiveData("")

    private fun validArrival(): Boolean {
        return (!arrival.value.equals("Selecione"))
    }

    private fun validArrivalDate(): Boolean {
        return arrivalDate.value?.isNotEmpty() ?: false
    }

    private fun validDeparture(): Boolean {
        return (!departure.value.equals("Selecione"))
    }

    private fun validDepartureDate(): Boolean {
        return departureDate.value?.isNotEmpty() ?: false
    }

    private fun validPrice(): Boolean {
        return price.value?.isNotEmpty() ?: false
    }

    fun validateFields(): Boolean {
        return (validArrival() && validDeparture() && validDepartureDate())
    }

}