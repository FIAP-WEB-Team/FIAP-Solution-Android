/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui.flight_selection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class FlightSelectionViewModel : ViewModel() {
    var enabledSelectButton = MutableLiveData(false)
    var noFlightAvailable = MutableLiveData(false)

    private lateinit var statusChanged: List<MutableLiveData<Boolean>>
    var selectedPosition = -1
        private set

    fun setNumFlights(numFlights: Int) {
        if (numFlights > 0)
            statusChanged = List(numFlights) { MutableLiveData(false) }
        noFlightAvailable.value = numFlights == 0
    }

    fun selectFlight(position: Int) {
        if (selectedPosition >= 0 && selectedPosition != position) {
            statusChanged[selectedPosition].value = statusChanged[selectedPosition].value?.not()
        }
        if (selectedPosition == position)
            selectedPosition = -1
        else
            selectedPosition = position

        statusChanged[position].value = statusChanged[position].value?.not()
        enabledSelectButton.value = selectedPosition >= 0
    }

    fun getFlightItemObserver(position: Int): MutableLiveData<Boolean> {
        return statusChanged[position]
    }
}