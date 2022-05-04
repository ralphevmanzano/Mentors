package com.ralphevmanzano.apps.domain.model

import kotlin.random.Random

data class TimeSlot(
    val label: String,
    val hour: Int,
    val mins: Int? = null,
) {
    // TODO this is just to simulate timeslots that are unavailable
    var isAvailable = Random.nextInt(100) > 20

    var isSelected = false
}