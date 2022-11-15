/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fiapon.androidsolution.ui.flight.FlightDataActivity
import com.fiapon.androidsolution.R
import com.fiapon.androidsolution.ui.auth.RequestState
import com.fiapon.androidsolution.ui.login.ServerTokenRetriever
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ServerTokenRetriever().requestAccessToken { tokenRetrieved(it) }
    }

    private fun tokenRetrieved(state: RequestState<String>){
        when(state){
            is RequestState.Error -> {
                Toast.makeText(this, state.throwable.message.toString(), Toast.LENGTH_SHORT).show()
                textViewSlogan.text = getString(R.string.server_error)
            }
            is RequestState.Loading -> {}
            is RequestState.Success -> {
                token = state.data

                val intent = Intent(this, FlightDataActivity::class.java)
                intent.putExtra("api_token", token)
                startActivity(intent)
            }
        }
    }
}