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
            "Barcelona",
            LocalDateTime.of(2022, 11, 2, 10, 0),
            2100.32
        ),
        Flight(
            34419,
            "São Paulo (GRU)",
            LocalDateTime.of(2022, 10, 1, 13, 30),
            "Rio de Janeiro (SDU)",
            LocalDateTime.of(2022, 10, 1, 15, 0),
            320.54
        ),
        Flight(
            9622,
            "São Paulo (CGH)",
            LocalDateTime.of(2022, 11, 26, 9, 20),
            "Nova York",
            LocalDateTime.of(2022, 11, 26, 15, 10),
            2134.1
        ),
    )
}