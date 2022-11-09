/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui.flight_selection

import android.os.Bundle
import android.view.View
import com.fiapon.androidsolution.R
import com.fiapon.androidsolution.ui.auth.BaseAuthFragment
import kotlinx.android.synthetic.main.footer_bar.view.*
import kotlinx.android.synthetic.main.fragment_flight_selection.*

class FlightSelectionFragment : BaseAuthFragment() {

    override val layout: Int
        get() = R.layout.fragment_flight_selection

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        footer.footerButton.text = getString(R.string.select_flight_text)
        footer.footerButton.isEnabled = false

        createListeners()
    }

    private fun createListeners(){
        footer.footerButton.setOnClickListener{

        }
    }
}