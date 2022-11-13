/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui.flight_selection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fiapon.androidsolution.model.flights.Flight
import com.fiapon.androidsolution.model.flights.FlightSource
import com.fiapon.androidsolution.ui.auth.RequestState
import kotlin.reflect.typeOf

enum class ShowStatus {
    NONE, LOADING, NO_FLIGHT_AVAILABLE, HAS_DATA, ERROR
}

class FlightSelectionViewModel : ViewModel() {
    var enabledSelectButton = MutableLiveData(false)
    var showStatus = MutableLiveData(ShowStatus.LOADING)
    var flightLoadingStatus = MutableLiveData<RequestState<List<Flight>>>()

    private lateinit var statusChanged: List<MutableLiveData<Boolean>>
    var selectedPosition = -1
        private set

    fun setNumFlights(numFlights: Int) {
        if (numFlights > 0)
            statusChanged = List(numFlights) { MutableLiveData(false) }
        if (showStatus.value != ShowStatus.LOADING)
            showStatus.value =
                if (numFlights == 0) ShowStatus.NO_FLIGHT_AVAILABLE else ShowStatus.HAS_DATA
    }

    fun loadFlights(token: String) {
        flightLoadingStatus.value = RequestState.Loading
        showStatus.value = ShowStatus.LOADING

        FlightSource().getAllFlights(token) {
            when (it) {
                is RequestState.Error -> {
                    showStatus.value = ShowStatus.ERROR
                }
                else -> {
                    showStatus.value = ShowStatus.NONE
                }
            }
            flightLoadingStatus.value = it
        }
    }

    fun selectFlight(position: Int) {
        if (selectedPosition >= 0 && selectedPosition != position) {
            statusChanged[selectedPosition].value = statusChanged[selectedPosition].value?.not()
        }
        selectedPosition = if (selectedPosition == position) -1 else position

        statusChanged[position].value = statusChanged[position].value?.not()
        enabledSelectButton.value = selectedPosition >= 0
    }

    fun getFlightItemObserver(position: Int): MutableLiveData<Boolean> {
        return statusChanged[position]
    }
}