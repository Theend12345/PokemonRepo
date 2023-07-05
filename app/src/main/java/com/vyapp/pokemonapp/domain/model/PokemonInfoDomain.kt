package com.vyapp.pokemonapp.domain.model

data class PokemonInfoDomain(
    val name : String?,
    val weight : Int?,
    val height : Int?,
    val types : List<TypeDomain>?,
    val sprites : SpritesDomain?
    )
