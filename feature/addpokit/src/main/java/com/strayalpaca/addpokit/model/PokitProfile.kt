package com.strayalpaca.addpokit.model

data class PokitProfile(
    val id: String,
)

internal val samplePokitProfileList =
    listOf(
        PokitProfile("1"), PokitProfile("2"), PokitProfile("3"), PokitProfile("4"),
        PokitProfile("5"), PokitProfile("6"), PokitProfile("7"), PokitProfile("8"),
        PokitProfile("9"), PokitProfile("10"), PokitProfile("11"), PokitProfile("12"),
        PokitProfile("13"), PokitProfile("14"), PokitProfile("15")
    )
