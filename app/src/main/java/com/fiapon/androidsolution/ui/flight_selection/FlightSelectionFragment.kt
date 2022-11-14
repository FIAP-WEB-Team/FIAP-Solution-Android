/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui.flight_selection

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fiapon.androidsolution.R
import com.fiapon.androidsolution.model.flights.Flight
import com.fiapon.androidsolution.ui.auth.BaseAuthFragment
import com.fiapon.androidsolution.ui.auth.RequestState
import com.fiapon.androidsolution.ui.passenger.PassengerDataActivity
import kotlinx.android.synthetic.main.footer_bar.view.*
import kotlinx.android.synthetic.main.fragment_flight_selection.*

class FlightSelectionFragment : BaseAuthFragment() {

    override val layout: Int
        get() = R.layout.fragment_flight_selection
    private lateinit var viewModel: FlightSelectionViewModel
    private lateinit var adapter: FlightSelectionAdapter
    private var token: String? = null
    private var date_flight: String? = null
    private var day_of_week: String? = null
    private var flight_arrival: String? = null
    private var flight_departure: String? = null
    private var flightData: MutableList<Flight> = mutableListOf<Flight>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider.NewInstanceFactory().create(FlightSelectionViewModel::class.java)
        token = requireActivity().intent?.extras?.getString("api_token")
        date_flight = requireActivity().intent?.extras?.getString("date_flight")
        day_of_week = requireActivity().intent?.extras?.getString("day_of_week")
        flight_arrival = requireActivity().intent?.extras?.getString("flight_arrival")
        flight_departure = requireActivity().intent?.extras?.getString("flight_departure")
        adapter = FlightSelectionAdapter(viewModel, flightData)

        viewModel.loadFlights(token!!)

        dateInfo.text = day_of_week + ", " + date_flight + " \n - A partir de 643,00"

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        footer.footerButton.text = getString(R.string.select_flight_text)
        footer.footerButton.isEnabled = false

        createListeners()
        createObservers()
    }

    private fun createListeners() {
        footer.footerButton.setOnClickListener {
            val position = viewModel.selectedPosition
            if (position >= 0) {
                val intent = Intent(context, PassengerDataActivity::class.java)
                intent.putExtra("selected_flight", adapter.getDataAt(position) as Parcelable)
                intent.putExtra("api_token", token)
                startActivity(intent)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun createObservers() {

        viewModel.showStatus.observe(viewLifecycleOwner) {
            noFlightAvailable.visibility =
                if (it == ShowStatus.NO_FLIGHT_AVAILABLE) View.VISIBLE else View.INVISIBLE
            loadingFlights.visibility =
                if (it == ShowStatus.LOADING) View.VISIBLE else View.INVISIBLE
            errorLoadingFlights.visibility =
                if (it == ShowStatus.ERROR) View.VISIBLE else View.INVISIBLE
        }
        viewModel.enabledSelectButton.observe(viewLifecycleOwner) {
            footer.footerButton.isEnabled = it
        }
        viewModel.flightLoadingStatus.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Error -> {
                    Toast.makeText(context, it.throwable.message.toString(), Toast.LENGTH_LONG).show()
                }
                is RequestState.Loading -> {}
                is RequestState.Success -> {
                    flightData.clear()
                    flightData.addAll(it.data)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}