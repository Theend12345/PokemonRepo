package com.vyapp.pokemonapp.util

import com.vyapp.pokemonapp.data.remote.model.*
import com.vyapp.pokemonapp.domain.model.*

fun Pokemon.toPokemonDomain() = PokemonDomain(this.name)
fun PokemonInfo.toPokemonInfoDomain() =
    PokemonInfoDomain(
        this.name,
        this.weight,
        this.height,
        this.types?.toListTypeDomain(),
        this.sprites?.toSpritesDomain()
    )

fun Sprites.toSpritesDomain() = SpritesDomain(this.frontDefault)

fun Type.toTypeDomain() = TypeDomain(this.name)

fun List<Pokemon>.toListPokemonDomain() = this.map {
    it.toPokemonDomain()
}

fun List<Type>.toListTypeDomain() = this.map {
    it.toTypeDomain()
}

fun PokemonResult.toPokemonResultDomain() =
    PokemonResultDomain(this.count, this.next, this.previous, this.results.toListPokemonDomain())