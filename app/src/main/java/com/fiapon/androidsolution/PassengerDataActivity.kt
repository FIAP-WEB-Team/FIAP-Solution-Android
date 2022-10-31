package com.fiapon.androidsolution

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_passenger_data.*
import kotlinx.android.synthetic.main.main_edit_text.view.*

class PassengerDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger_data)

        setImportedLayoutsTexts()
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

        footer.textView.text = "Ir para Pagamento"
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            firstNameEditText.editText.setText(savedInstanceState.getString("first_name"))
            lastNameEditText.editText.setText(savedInstanceState.getString("last_name"))
            birthDateEditText.editText.setText(savedInstanceState.getString("birth_date"))
            genderEditText.editText.setText(savedInstanceState.getString("gender"))
            nationalityEditText.editText.setText(savedInstanceState.getString("nationality"))

        }
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