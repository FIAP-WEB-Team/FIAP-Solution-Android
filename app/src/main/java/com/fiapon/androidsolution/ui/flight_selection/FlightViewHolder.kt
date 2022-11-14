/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui.flight_selection

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.fiapon.androidsolution.R
import com.fiapon.androidsolution.model.flights.Flight
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FlightViewHolder(private val view: View, viewModel: FlightSelectionViewModel) : RecyclerView.ViewHolder(view) {
    private val flightDepartureTextView: TextView = view.findViewById(R.id.txtViewFlightDeparture)
    private val flightArrivalTextView: TextView = view.findViewById(R.id.txtViewFlightArrival)
    private val flightPrice: TextView = view.findViewById(R.id.txtViewFlightPrice)
    private val flightItemLayout: ConstraintLayout = view.findViewById(R.id.flightItemConstraintLayout)

    private var position: Int? = null

    private val dateFormatter = DateTimeFormatter.ofPattern("HH:mm")

    init {
        flightItemLayout.setOnClickListener {
            viewModel.selectFlight(position!!)
            viewModel.getFlightItemObserver(position!!).observe(view.findViewTreeLifecycleOwner()!!) {
                onComponentToggled(it)
            }
        }
    }

    private fun onComponentToggled(isSelected: Boolean) {
        if (isSelected)
            flightItemLayout.setBackgroundColor(
                ContextCompat.getColor(
                    view.context,
                    R.color.beige_medium
                )
            )
        else
            flightItemLayout.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
    }

    private fun getTimeFromDate(date: String): String{
        return LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME).format(dateFormatter)
    }

    @SuppressLint("SetTextI18n")
    fun bind(position: Int, data: Flight) {
        this.position = position

        flightDepartureTextView.text = "${data.departure} - ${getTimeFromDate(data.departureDate)}"
        flightArrivalTextView.text = "${data.arrival} - ${getTimeFromDate(data.arrivalDate)}"
        flightPrice.text = "R$${String.format("%.2f", data.price).replace(".", ",")}"
    }
}
