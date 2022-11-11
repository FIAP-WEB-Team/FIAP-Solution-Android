/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui.flight_selection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fiapon.androidsolution.R
import com.fiapon.androidsolution.model.flights.Flight
import com.fiapon.androidsolution.model.flights.FlightSource

class FlightSelectionAdapter(private val viewModel: FlightSelectionViewModel) :
    RecyclerView.Adapter<FlightViewHolder>() {

    private val dataSource = FlightSource()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FlightViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.flight_item, viewGroup, false)
        viewModel.setNumFlights(itemCount)

        return FlightViewHolder(view, viewModel)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        holder.bind(position, dataSource.flightDummyData[position])
    }

    override fun getItemCount(): Int {
        return dataSource.flightDummyData.size
    }

    fun getDataAt(position: Int): Flight {
        return dataSource.flightDummyData[position]
    }
}