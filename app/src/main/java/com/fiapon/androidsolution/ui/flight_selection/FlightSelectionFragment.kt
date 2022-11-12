/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui.flight_selection

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fiapon.androidsolution.R
import com.fiapon.androidsolution.ui.PassengerDataActivity
import com.fiapon.androidsolution.ui.auth.BaseAuthFragment
import kotlinx.android.synthetic.main.footer_bar.view.*
import kotlinx.android.synthetic.main.fragment_flight_selection.*

class FlightSelectionFragment : BaseAuthFragment() {

    override val layout: Int
        get() = R.layout.fragment_flight_selection
    private lateinit var viewModel: FlightSelectionViewModel
    private lateinit var adapter: FlightSelectionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider.NewInstanceFactory().create(FlightSelectionViewModel::class.java)

        adapter = FlightSelectionAdapter(viewModel)
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
                startActivity(intent)
            }
        }
    }

    private fun createObservers(){
        viewModel.noFlightAvailable.observe(viewLifecycleOwner) {
            noFlightAvailable.visibility = if (it) View.INVISIBLE else View.VISIBLE
        }
        viewModel.enabledSelectButton.observe(viewLifecycleOwner) {
            footer.footerButton.isEnabled = it
        }
    }
}