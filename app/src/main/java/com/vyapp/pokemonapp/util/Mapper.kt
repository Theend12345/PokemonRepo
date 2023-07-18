package com.vyapp.pokemonapp.util

import com.vyapp.pokemonapp.data.local.model.PokemonEntity
import com.vyapp.pokemonapp.data.remote.model.*
import com.vyapp.pokemonapp.domain.model.*

fun Pokemon.toPokemonDomain() = PokemonDomain(0, this.name)
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

fun PokemonEntity.toPokemonDomain() =
    PokemonDomain(this.id, this.name, this.weight, this.height, this.type)

fun Pokemon.toPokemonEntity() = PokemonEntity(name = this.name)

fun PokemonDomain.toPokemonEntity() =
    PokemonEntity(this.id, this.name, this.weight, this.height, this.type)

fun PokemonInfoDomain.toPokemonDomain() =
    PokemonDomain(name = this.name!!, weight = this.weight, height = this.height, type = this.type!!.name)

fun PokemonDomain.toPokemonInfoDomain() =
    PokemonInfoDomain(name = this.name, weight = this.weight, height = this.height, type = PokemonTypeDomain(this.type),null)

