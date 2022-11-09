/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fiapon.androidsolution.R
import com.fiapon.androidsolution.ui.flight_selection.FlightSelectionActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFlightSelection.setOnClickListener{gotoFlightSelectionActivity()}
    }

    fun gotoFlightSelectionActivity(){
        val intent = Intent(this, FlightSelectionActivity::class.java)
        startActivity(intent)
    }
}