package com.vyapp.pokemonapp.util

import com.vyapp.pokemonapp.data.local.model.PokemonEntity
import com.vyapp.pokemonapp.data.remote.model.*
import com.vyapp.pokemonapp.domain.model.*

fun Pokemon.toPokemonDomain() = PokemonDomain(this.name)
fun PokemonInfo.toPokemonInfoDomain() =
    PokemonInfoDomain(
        this.name,
        this.weight,
        this.height,
        this.types?.toPokemonTypeDomain(),
        this.sprites?.toSpritesDomain()
    )

fun Sprites.toSpritesDomain() = SpritesDomain(this.frontDefault)

fun List<Pokemon>.toListPokemonDomain() = this.map {
    it.toPokemonDomain()
}

fun List<Type>.toPokemonTypeDomain() = PokemonTypeDomain(this.get(0).type?.name)

fun PokemonResult.toPokemonResultDomain() =
    PokemonResultDomain(this.count, this.next, this.previous, this.results.toListPokemonDomain())

fun PokemonEntity.toPokemonEntityDomain() =
    PokemonEntityDomain(this.name)

fun PokemonEntity.toPokemonDomain() =
    this.name?.let { PokemonDomain(it) }

fun PokemonEntityDomain.toPokemonEntity() =
    this.name?.let { PokemonEntity(it) }

fun PokemonDomain.toPokemonDomainEntity() = PokemonEntityDomain(this.name)