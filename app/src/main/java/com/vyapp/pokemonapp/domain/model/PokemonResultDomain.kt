package com.vyapp.pokemonapp.domain.model

data class PokemonResultDomain(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonDomain>
)