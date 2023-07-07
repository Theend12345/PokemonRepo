package com.vyapp.pokemonapp.domain.repository

import androidx.paging.PagingData
import com.vyapp.pokemonapp.domain.model.PokemonDomain
import com.vyapp.pokemonapp.domain.model.PokemonEntityDomain
import com.vyapp.pokemonapp.domain.model.PokemonInfoDomain
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    fun getPokemonListOffline(): Flow<PagingData<PokemonDomain>>
    fun getPokemonOffline(id: Int): Flow<PokemonInfoDomain>
    fun addPokemon(pokemonDomain: PokemonEntityDomain)

}