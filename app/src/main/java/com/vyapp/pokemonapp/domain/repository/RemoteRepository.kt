package com.vyapp.pokemonapp.domain.repository

import androidx.paging.PagingData
import com.vyapp.pokemonapp.data.remote.model.Pokemon
import com.vyapp.pokemonapp.domain.model.PokemonDomain
import com.vyapp.pokemonapp.domain.model.PokemonInfoDomain
import com.vyapp.pokemonapp.domain.model.PokemonResultDomain
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    fun getPokemonResult(): Flow<PagingData<PokemonDomain>>
    suspend fun getPokemon(name: String): Flow<PokemonInfoDomain>

}