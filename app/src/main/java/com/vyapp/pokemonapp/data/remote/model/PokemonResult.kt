package com.vyapp.pokemonapp.data.remote.model

data class PokemonResult(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>
)