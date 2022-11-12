/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.model.flights

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Flight(
    val number: Int,
    val departure: String,
    val departureDate: LocalDateTime,
    val arrival: String,
    val arrivalDate: LocalDateTime,
    val price: Double
) : Parcelable
