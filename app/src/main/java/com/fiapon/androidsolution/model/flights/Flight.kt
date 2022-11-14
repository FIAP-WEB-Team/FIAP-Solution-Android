/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.model.flights

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Flight(
    val flightNumber: Int,
    val departure: String,
    val departureDate: String,
    val arrival: String,
    val arrivalDate: String,
    val price: Double
) : Parcelable
