/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.model.flights

import java.time.LocalDateTime

class FlightSource {
    val flightDummyData = mutableListOf(
        Flight(
            12344,
            "Budapeste",
            LocalDateTime.of(2022, 11, 1, 12, 0),
            1500.00,
            "Barcelona",
            LocalDateTime.of(2022, 11, 2, 10, 0)
        ),
        Flight(
            34419,
            "São Paulo (GRU)",
            LocalDateTime.of(2022, 10, 1, 13, 30),
            1500.00,
            "Rio de Janeiro (SDU)",
            LocalDateTime.of(2022, 10, 1, 15, 0)
        ),
    )
}