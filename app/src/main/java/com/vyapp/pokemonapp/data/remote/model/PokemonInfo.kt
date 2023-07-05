package com.vyapp.pokemonapp.data.remote.model

data class PokemonInfo(
    val name : String?,
    val weight : Int?,
    val height : Int?,
    val types : List<Type>?,
    val sprites : Sprites?
    )
