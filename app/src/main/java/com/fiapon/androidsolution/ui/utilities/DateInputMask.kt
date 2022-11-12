/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui.utilities

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.*
import kotlin.math.max
import kotlin.math.min

class DateInputMask(val input: EditText, val minValidYear: Int, val maxValidYear: Int) {

    private val cal = Calendar.getInstance()

    fun listen() {
        input.addTextChangedListener(dateEntryWatcher)
    }

    private val dateEntryWatcher = object : TextWatcher {

        var edited = false

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (edited) {
                edited = false
                return
            }
            var working = getEditText()

            working = manageDateDivider(working)
            if (working.length == 10) working = validateDate(working)

            edited = true
            input.setText(working)
            input.setSelection(input.text.length)
        }

        private fun manageDateDivider(
            working: String
        ): String {
            val digits = working.filter { it.isDigit() }

            return if (digits.length <= 2)
                digits
            else if (digits.length <= 4)
                digits.substring(0, 2) + "/" + digits.substring(2)
            else
                digits.substring(0, 2) + "/" + digits.substring(2, 4) + "/" + digits.substring(4)
        }

        private fun getEditText(): String {
            return if (input.text.length >= 10) input.text.toString().substring(0, 10)
            else input.text.toString()
        }

        private fun validateDate(date: String): String {
            var day: Int = date.substring(0, 2).toInt()
            var mon: Int = date.substring(3, 5).toInt()
            var year: Int = date.substring(6, 10).toInt()

            mon = if (mon < 1) 1 else if (mon > 12) 12 else mon
            cal.set(Calendar.MONTH, mon - 1)
            year = min(max(year, minValidYear), maxValidYear)
            cal.set(Calendar.YEAR, year)
            day = min(cal.getActualMaximum(Calendar.DATE), day)
            return String.format("%02d/%02d/%04d", day, mon, year)
        }

        override fun afterTextChanged(s: Editable) {}
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    }
}