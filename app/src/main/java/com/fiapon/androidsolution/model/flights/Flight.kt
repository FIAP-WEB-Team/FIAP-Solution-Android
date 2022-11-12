/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.model.flights

import java.time.LocalDateTime

data class Flight(
    val number: Int,
    val departure: String,
    val departureDate: LocalDateTime,
    val price:Double,
    val arrival: String,
    val arrivalDate: LocalDateTime,
)
