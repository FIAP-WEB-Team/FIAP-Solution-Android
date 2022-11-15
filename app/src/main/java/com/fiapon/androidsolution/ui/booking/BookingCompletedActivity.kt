/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui.booking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fiapon.androidsolution.R
import com.fiapon.androidsolution.ui.MainActivity
import kotlinx.android.synthetic.main.activity_booking_completed.*

class BookingCompletedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_completed)

        returnToBeginningButton.setOnClickListener { returnToBeginning() }
    }

    override fun onBackPressed() {
        returnToBeginning()
    }

    private fun returnToBeginning() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}