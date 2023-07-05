package com.vyapp.pokemonapp.domain.repository

import com.vyapp.pokemonapp.domain.model.PokemonInfoDomain
import com.vyapp.pokemonapp.domain.model.PokemonResultDomain
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    suspend fun getPokemonResult(): Flow<PokemonResultDomain>
    suspend fun getPokemon(name: String): Flow<PokemonInfoDomain>

}