package com.vyapp.pokemonapp.domain.model

data class PokemonDomain(
    val id: Int = 0,
    val name: String,
    val weight: Int? = null,
    val height: Int? = null,
    val type: String? = null
)