package com.tylerlowrey.models

import kotlinx.serialization.Serializable

@Serializable
data class LedControlCommand(
    val speed: Double
)
