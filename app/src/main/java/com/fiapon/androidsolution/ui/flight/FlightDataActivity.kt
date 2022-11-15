package com.fiapon.androidsolution.ui.flight

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fiapon.androidsolution.R
import com.fiapon.androidsolution.databinding.ModalSeatsPassengersBinding
import com.fiapon.androidsolution.ui.flight_selection.FlightSelectionActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_flight_data.*
import kotlinx.android.synthetic.main.flight_calendar_destiny.*
import kotlinx.android.synthetic.main.flight_calendar_origin.*
import kotlinx.android.synthetic.main.flight_passengers.*
import kotlinx.android.synthetic.main.flight_spinner.*
import kotlinx.android.synthetic.main.flight_spinner_destination.*
import kotlinx.android.synthetic.main.footer_bar.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class FlightDataActivity : AppCompatActivity() {
    lateinit var viewModel: FlightViewModel
    lateinit var calendar: Calendar
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_data)

        token = intent.extras?.getString("api_token")
        viewModel = ViewModelProvider.NewInstanceFactory().create(FlightViewModel::class.java)
        calendar = Calendar.getInstance()

        setInfo()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    @SuppressLint("SimpleDateFormat")
    private fun setInfo() {
        val options = arrayOf("Ida")
        flightSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)
        flightSpinner.setSelection(0)

        // Spinner destination origin
        val optionsOrigin = arrayOf(
            "Qualquer origem",
            "Maceió",
            "Barcelona",
            "Campinas",
            "Cairo",
            "Pequim",
        )

        val optionsDestination = arrayOf(
            "Qualquer destino",
            "Curitiba",
            "Budapeste",
            "Ushaia",
            "Vancouver",
            "Seul",
        )
        flightSpinnerOrigin.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, optionsOrigin)
        flightSpinnerDestination.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, optionsDestination)

        flightSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = flightSpinner.getItemAtPosition(position).toString()
                if (selectedItem == "Ida e volta") {
                    dateDestiny.visibility = View.VISIBLE
                } else {
                    dateDestiny.visibility = View.GONE
                    editTextDateDestiny.setText("")
                    editTextDateDestiny.hint = "Quando?"
                }

                validateFields()
            }

        }

        flightSpinnerOrigin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.departure.value = flightSpinnerOrigin.getItemAtPosition(position).toString()
                validateFields()
            }

        }

        flightSpinnerDestination.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.arrival.value = flightSpinnerDestination.getItemAtPosition(position).toString()
                validateFields()
            }

        }

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Origin date
        editTextDateOrigin.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                { _, mYear, mMonth, mDay ->
                    val xDay = if(mDay < 10) "0$mDay" else mDay
                    val xMonth = if((mMonth+1) < 10) "0"+(mMonth+1) else (mMonth+1)
                    val dateSelect = "$xDay/$xMonth/$mYear"

                    editTextDateOrigin.setText(dateSelect)
                    editTextDateOrigin.hint = dateSelect

                    viewModel.departureDate.value = editTextDateOrigin.text.toString()
                    validateFields()
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        // Destination date
        editTextDateDestiny.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                { _, mYear, mMonth, mDay ->
                    val dateSelect = "" + mDay + "/" + (mMonth+1) + "/" + mYear
                    editTextDateDestiny.setText(dateSelect)
                    editTextDateDestiny.hint = dateSelect
                    validateFields()
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        editTextFlightPassengers.setText(getString(R.string.txt_edit_text_passenger))

        btnFooter.footerButton.text = getString(R.string.search_flight)
        btnFooter.footerButton.isEnabled = false
        btnFooter.footerButton.setOnClickListener {
            val intent = Intent(this, FlightSelectionActivity::class.java)
            val format1 = SimpleDateFormat("dd/MM/yyyy")
            val dt1: Date = format1.parse(editTextDateOrigin.text.toString()) as Date
            val format2: DateFormat = SimpleDateFormat("EEEE")
            val finalDay: String = format2.format(dt1)

            val origin = flightSpinnerOrigin.selectedItem.toString()
            val destiny = flightSpinnerDestination.selectedItem.toString()

            intent.putExtra("api_token", token)
            intent.putExtra("date_flight", editTextDateOrigin.text.toString())
            intent.putExtra("day_of_week", dayOfWeek(finalDay))
            intent.putExtra("flight_departure", if (origin == "Qualquer origem") "" else origin)
            intent.putExtra("flight_arrival", if (destiny == "Qualquer destino") "" else destiny)

            startActivity(intent)
        }

        editTextFlightPassengers.setOnClickListener { showModalSeats() }
    }

    private fun showModalSeats() {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog)

        val sheetBinding: ModalSeatsPassengersBinding =
            ModalSeatsPassengersBinding.inflate(layoutInflater, null, false)

        sheetBinding.btnConfirmSeats.setOnClickListener {
            val quantityAdult = sheetBinding.quantityAdult.text.toString()
            var seats = "$quantityAdult adulto(s)"

            val quantityChild = sheetBinding.quantityChild.text.toString()
            if(Integer.parseInt(quantityChild) > 0){
                seats += ", $quantityChild criança(s)"
            }

            val quantityBaby = sheetBinding.quantityBaby.text.toString()
            if(Integer.parseInt(quantityBaby) > 0){
                seats += ", $quantityBaby bebê(s)"
            }

            dialog.dismiss()
            editTextFlightPassengers.setText(seats)
        }

        dialog.setContentView(sheetBinding.root)
        dialog.show()
    }

    private fun validateFields(){
        btnFooter.footerButton.isEnabled = viewModel.validateFields()
    }

    private fun dayOfWeek(nameDay: String) : String {
        return when(nameDay){
            "Sunday" -> "Domingo"
            "Monday" -> "Segunda"
            "Tuesday" -> "Terça"
            "Wednesday" -> "Quarta"
            "Thursday" -> "Quinta"
            "Friday" -> "Sexta"
            else -> "Sábado"
        }
    }

}