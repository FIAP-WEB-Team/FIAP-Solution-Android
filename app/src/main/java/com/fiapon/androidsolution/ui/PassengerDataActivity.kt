/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui

import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.fiapon.androidsolution.R
import com.fiapon.androidsolution.ui.utilities.DateInputMask
import kotlinx.android.synthetic.main.activity_passenger_data.*
import kotlinx.android.synthetic.main.footer_bar.*
import kotlinx.android.synthetic.main.footer_bar.view.*
import kotlinx.android.synthetic.main.main_edit_text.view.*

class PassengerDataActivity : AppCompatActivity() {

    private lateinit var viewModel: PassengerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger_data)

        viewModel = ViewModelProvider.NewInstanceFactory().create(PassengerViewModel::class.java)
        footer.footerButton.isEnabled = false

        setImportedLayoutsTexts()
        setEditTextFilters()
        createListeners()
    }

    private fun setImportedLayoutsTexts() {
        firstNameEditText.textView.text = "PRIMEIRO NOME:"
        firstNameEditText.editText.hint = "Digite seu primeiro nome"

        lastNameEditText.textView.text = "ÚLTIMO NOME:"
        lastNameEditText.editText.hint = "Digite seu último nome"

        birthDateEditText.textView.text = "DATA DE NASCIMENTO:"
        birthDateEditText.editText.hint = "dd/mm/aaaa"

        genderEditText.textView.text = "GÊNERO:"
        genderEditText.editText.hint = "-------"

        nationalityEditText.textView.text = "NACIONALIDADE:"
        nationalityEditText.editText.hint = "País"

        footer.footerButton.text = "Ir para Pagamento"
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
                Toast.makeText(
                    this,
                    "Payment section is only available for premium users",
                    Toast.LENGTH_SHORT
                ).show()
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
        viewModel.enabledFooterButton.observe(this) {
            footer.footerButton.isEnabled = it
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