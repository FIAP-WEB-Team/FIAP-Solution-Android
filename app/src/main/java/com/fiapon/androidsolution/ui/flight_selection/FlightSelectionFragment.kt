/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui.flight_selection

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fiapon.androidsolution.R
import com.fiapon.androidsolution.data_context.retrofitFlightService
import com.fiapon.androidsolution.model.flights.Flight
import com.fiapon.androidsolution.model.tickets.Ticket
import com.fiapon.androidsolution.ui.PassengerDataActivity
import com.fiapon.androidsolution.ui.auth.BaseAuthFragment
import com.fiapon.androidsolution.ui.auth.RequestState
import kotlinx.android.synthetic.main.footer_bar.view.*
import kotlinx.android.synthetic.main.fragment_flight_selection.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FlightSelectionFragment : BaseAuthFragment() {

    override val layout: Int
        get() = R.layout.fragment_flight_selection
    private lateinit var viewModel: FlightSelectionViewModel
    private lateinit var adapter: FlightSelectionAdapter
    private var token: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider.NewInstanceFactory().create(FlightSelectionViewModel::class.java)

        token = requireActivity().intent?.extras?.getString("api_token")
        adapter = FlightSelectionAdapter(viewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        footer.footerButton.text = getString(R.string.select_flight_text)
        footer.footerButton.isEnabled = false

        createListeners()
        createObservers()
        test()
    }

    private fun test(){
        val longhand = retrofitFlightService().getFlights("Bearer $token")

        longhand.enqueue(object : Callback<List<Flight>> {
            override fun onResponse(call: Call<List<Flight>>, response: Response<List<Flight>>) {
                if (response.body() != null) {
                    Log.i("", "")
                } else {
                    Log.i("", "")
                }
            }

            override fun onFailure(call: Call<List<Flight>>, t: Throwable) {
                Log.i("", "")
            }

        })
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

    private fun createObservers(){
        viewModel.noFlightAvailable.observe(viewLifecycleOwner) {
            noFlightAvailable.visibility = if (it) View.VISIBLE else View.INVISIBLE
        }
        viewModel.enabledSelectButton.observe(viewLifecycleOwner) {
            footer.footerButton.isEnabled = it
        }
    }
}