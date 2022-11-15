/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui.passenger

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.fiapon.androidsolution.R
import com.fiapon.androidsolution.model.passengers.Passenger
import com.fiapon.androidsolution.ui.auth.RequestState
import com.fiapon.androidsolution.ui.booking.BookingCompletedActivity
import com.fiapon.androidsolution.ui.utilities.DateInputMask
import kotlinx.android.synthetic.main.activity_passenger_data.*
import kotlinx.android.synthetic.main.footer_bar.*
import kotlinx.android.synthetic.main.footer_bar.view.*
import kotlinx.android.synthetic.main.main_edit_text.view.*

class PassengerDataActivity : AppCompatActivity() {

    private lateinit var viewModel: PassengerViewModel
    private var selectedFlight: Int? = null
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger_data)

        selectedFlight = intent.extras?.getInt("selected_flight")
        token = intent.extras?.getString("api_token")

        viewModel = ViewModelProvider(this, PassengerViewModelFactory(token!!))[PassengerViewModel::class.java]
        footer.footerButton.isEnabled = false

        setImportedLayoutsTexts()
        setEditTextFilters()
        createListeners()
        createObservers()
    }

    private fun setImportedLayoutsTexts() {
        firstNameEditText.textView.text = getString(R.string.first_name)
        firstNameEditText.editText.hint = getString(R.string.first_name_hint)

        lastNameEditText.textView.text = getString(R.string.last_name)
        lastNameEditText.editText.hint = getString(R.string.last_name_hint)

        birthDateEditText.textView.text = getString(R.string.birth_date)
        birthDateEditText.editText.hint = "dd/mm/aaaa"

        genderEditText.textView.text = getString(R.string.gender)
        genderEditText.editText.hint = "-------"

        nationalityEditText.textView.text = getString(R.string.nationality)
        nationalityEditText.editText.hint = getString(R.string.nationality_hint)

        footer.footerButton.text = getString(R.string.book_flight)
    }

    private fun setEditTextFilters() {
        firstNameEditText.editText.filters += InputFilter.LengthFilter(100)
        lastNameEditText.editText.filters += InputFilter.LengthFilter(100)
        birthDateEditText.editText.inputType = InputType.TYPE_CLASS_NUMBER
        genderEditText.editText.filters += InputFilter.LengthFilter(40)
        nationalityEditText.editText.filters += InputFilter.LengthFilter(50)
    }

    private fun createListeners() {
        footer.footerButton.setOnClickListener {
            if (footerButton.isEnabled)
                viewModel.createTicket(selectedFlight!!)
        }
        firstNameEditText.editText.addTextChangedListener(
            { _, _, _, _ -> },
            { _, _, _, _ ->
                viewModel.firstName.value = firstNameEditText.editText.text.toString()
                viewModel.validateFields()
            },
            {})
        lastNameEditText.editText.addTextChangedListener({ _, _, _, _ -> },
            { _, _, _, _ ->
                viewModel.lastName.value = lastNameEditText.editText.text.toString()
                viewModel.validateFields()
            },
            {})
        DateInputMask(birthDateEditText.editText, 1900, 2021).listen()
        birthDateEditText.editText.addTextChangedListener({ _, _, _, _ -> },
            { _, _, _, _ ->
                viewModel.birthDate.value = birthDateEditText.editText.text.toString()
                viewModel.validateFields()
            },
            {})
        genderEditText.editText.addTextChangedListener(
            { _, _, _, _ -> },
            { _, _, _, _ ->
                viewModel.gender.value = genderEditText.editText.text.toString()
                viewModel.validateFields()
            },
            {})
        nationalityEditText.editText.addTextChangedListener({ _, _, _, _ -> },
            { _, _, _, _ ->
                viewModel.nationality.value = nationalityEditText.editText.text.toString()
                viewModel.validateFields()
            },
            {})
        radioGroup.setOnCheckedChangeListener { _, checkedId -> radioGroupChanged(checkedId) }
        radioGroupChanged(radioGroup.checkedRadioButtonId)
    }

    private fun createObservers() {
        viewModel.enabledFooterButton.observe(this) {
            footer.footerButton.isEnabled = it
        }
        viewModel.checkDataState.observe(this) {
            when (it) {
                is RequestState.Error -> {
                    loadingHandler(false)
                    Toast.makeText(
                        this,
                        "Não foi possível reservar a passagem: " + it.throwable.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
                is RequestState.Loading -> {
                    loadingHandler(true)
                }
                is RequestState.Success -> {
                    loadingHandler(false)
                    val intent = Intent(this, BookingCompletedActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun radioGroupChanged(checkedId: Int) {
        val radio = findViewById<RadioButton>(checkedId)

        if (radio.text == getString(R.string.yes_text)) {
            passengerInfoToView(
                Passenger(
                    "11/03/1995",
                    "Giba",
                    "dos Santos",
                    "Masculino",
                    "Brasil"
                )
            )
        } else {
            passengerInfoToView(
                Passenger(
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            )
        }
    }

    private fun passengerInfoToView(passenger: Passenger) {
        firstNameEditText.editText.setText(passenger.firstName)
        lastNameEditText.editText.setText(passenger.lastName)
        birthDateEditText.editText.setText(passenger.birthDate)
        genderEditText.editText.setText(passenger.gender)
        nationalityEditText.editText.setText(passenger.nationality)
    }

    private fun loadingHandler(isLoading: Boolean) {
        if (isLoading) {
            footer.footerButton.text = getString(R.string.loading_text)
        } else {
            footer.footerButton.text = getString(R.string.book_flight)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        firstNameEditText.editText.setText(savedInstanceState.getString("first_name"))
        lastNameEditText.editText.setText(savedInstanceState.getString("last_name"))
        birthDateEditText.editText.setText(savedInstanceState.getString("birth_date"))
        genderEditText.editText.setText(savedInstanceState.getString("gender"))
        nationalityEditText.editText.setText(savedInstanceState.getString("nationality"))
    }

    override fun onSaveInstanceState(extra: Bundle) {
        super.onSaveInstanceState(extra)
        extra.putString("first_name", firstNameEditText.editText.text.toString())
        extra.putString("last_name", lastNameEditText.editText.text.toString())
        extra.putString("birth_date", birthDateEditText.editText.text.toString())
        extra.putString("gender", genderEditText.editText.text.toString())
        extra.putString("nationality", nationalityEditText.editText.text.toString())
    }
}