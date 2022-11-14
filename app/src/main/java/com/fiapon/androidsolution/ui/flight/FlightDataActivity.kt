package com.fiapon.androidsolution

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fiapon.androidsolution.databinding.ModalSeatsPassengersBinding
import com.fiapon.androidsolution.ui.FlightViewModel
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

    private fun setInfo() {
        // Spinner trecho
        //var options = arrayOf("Ida e volta", "Só ida ou volta")
        val options = arrayOf("Ida")
        flightSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)
        flightSpinner.setSelection(0)

        // Spinner destination origin
        val optionsDestination = arrayOf(
            "Selecione",
            "Barcelona",
            "Budapeste",
            "Cairo",
            "Campinas",
            "Curitiba",
            "Pequim",
            "Ushaia",
            "Seul",
            "Vancouver"
        )
        flightSpinnerOrigin.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, optionsDestination)
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
                if (selectedItem.equals("Ida e volta")) {
                    dateDestiny.setVisibility(View.VISIBLE)
                } else {
                    dateDestiny.setVisibility(View.GONE)
                    editTextDateDestiny.setText("")
                    editTextDateDestiny.setHint("Quando?")
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

        // Calendar
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Origin date
        editTextDateOrigin.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    val xDay = if(mDay < 10) "0"+mDay else mDay
                    val xMonth = if((mMonth+1) < 10) "0"+(mMonth+1) else (mMonth+1)
                    val dateSelect = "" + xDay + "/" + xMonth + "/" + mYear

                    editTextDateOrigin.setText(dateSelect)
                    editTextDateOrigin.setHint(dateSelect)

                    viewModel.departureDate.value = editTextDateOrigin.getText().toString()
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
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    val dateSelect = "" + mDay + "/" + (mMonth+1) + "/" + mYear
                    editTextDateDestiny.setText(dateSelect)
                    editTextDateDestiny.setHint(dateSelect)
                    validateFields()
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        editTextFlightPassengers.setText("1 adulto(s)")

        btnFooterFlight.footerButton.text = "Escolher voo"
        btnFooterFlight.footerButton.isEnabled = false
        btnFooterFlight.footerButton.setOnClickListener {
            val intent = Intent(this, FlightSelectionActivity::class.java)
            val format1 = SimpleDateFormat("dd/MM/yyyy")
            val dt1: Date = format1.parse(editTextDateOrigin.getText().toString())
            val format2: DateFormat = SimpleDateFormat("EEEE")
            val finalDay: String = format2.format(dt1)

            intent.putExtra("api_token", token)
            intent.putExtra("date_flight", editTextDateOrigin.getText().toString())
            intent.putExtra("day_of_week", dayOfWeek(finalDay))
            intent.putExtra("flight_departure", flightSpinnerOrigin.getSelectedItem().toString())
            intent.putExtra("flight_arrival", flightSpinnerDestination.getSelectedItem().toString())

            startActivity(intent)
        }

        editTextFlightPassengers.setOnClickListener { showModalSeats() }
    }

    private fun showModalSeats() {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog)

        val sheetBinding: ModalSeatsPassengersBinding =
            ModalSeatsPassengersBinding.inflate(layoutInflater, null, false)

        // INCREASE/DESCREASE NUMBER OF PASSAGERS
        /*
        sheetBinding.addAdult.setOnClickListener {
            val quantity = sheetBinding.quantityAdult.text.toString()
            sheetBinding.quantityAdult.setText("" + (Integer.parseInt(quantity) + 1))
        }
        sheetBinding.removeAdult.setOnClickListener {
            val quantity = sheetBinding.quantityAdult.text.toString()
            if(Integer.parseInt(quantity) > 1){
                sheetBinding.quantityAdult.setText("" + (Integer.parseInt(quantity) - 1))
            }
        }

        sheetBinding.addChild.setOnClickListener {
            val quantity = sheetBinding.quantityChild.text.toString()
            sheetBinding.quantityChild.setText("" + (Integer.parseInt(quantity) + 1))
            sheetBinding.removeChild.setVisibility(View.VISIBLE)
            //dateDestiny.setVisibility(View.VISIBLE)
        }
        sheetBinding.removeChild.setOnClickListener {
            val quantity = sheetBinding.quantityChild.text.toString()
            if(Integer.parseInt(quantity) == 1){
                sheetBinding.removeChild.setVisibility(View.INVISIBLE)
            }
            sheetBinding.quantityChild.setText("" + (Integer.parseInt(quantity) - 1))
        }

        sheetBinding.addBaby.setOnClickListener {
            val quantity = sheetBinding.quantityBaby.text.toString()
            sheetBinding.quantityBaby.setText("" + (Integer.parseInt(quantity) + 1))
            sheetBinding.removeBaby.setVisibility(View.VISIBLE)
            //dateDestiny.setVisibility(View.VISIBLE)
        }
        sheetBinding.removeBaby.setOnClickListener {
            val quantity = sheetBinding.quantityBaby.text.toString()
            if(Integer.parseInt(quantity) == 1){
                sheetBinding.removeBaby.setVisibility(View.INVISIBLE)
            }
            sheetBinding.quantityBaby.setText("" + (Integer.parseInt(quantity) - 1))
        } */

        sheetBinding.btnConfirmSeats.setOnClickListener {
            val quantity_adult = sheetBinding.quantityAdult.text.toString()
            var seats = quantity_adult + " adulto(s)"

            val quantity_child = sheetBinding.quantityChild.text.toString()
            if(Integer.parseInt(quantity_child) > 0){
                seats += ", " + quantity_child + " criança(s)"
            }

            val quantity_baby = sheetBinding.quantityBaby.text.toString()
            if(Integer.parseInt(quantity_baby) > 0){
                seats += ", " + quantity_baby + " bebê(s)"
            }

            dialog.dismiss()
            editTextFlightPassengers.setText(seats)
        }

        dialog.setContentView(sheetBinding.root)
        dialog.show()
    }

    private fun validateFields(){
        btnFooterFlight.footerButton.isEnabled = viewModel.validateFields()
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