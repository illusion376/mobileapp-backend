package io.illusion.features.training.dto

import kotlinx.serialization.Serializable

@Serializable
data class TrainingSessionDTO(
    val steps: Int,
    val distanceMeters: Int,
    val durationMinutes: Int
)