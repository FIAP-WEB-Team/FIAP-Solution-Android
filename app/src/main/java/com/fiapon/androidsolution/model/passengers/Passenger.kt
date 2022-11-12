/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.model.passengers

import java.time.LocalDate

data class Passenger(
    val id: Int,
    val birthDate: LocalDate,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val nationality: String,
)
