package com.vyapp.pokemonapp.domain.repository

import androidx.paging.PagingData
import com.vyapp.pokemonapp.domain.model.PokemonDomain
import com.vyapp.pokemonapp.domain.model.PokemonInfoDomain
import kotlinx.coroutines.flow.Flow
import org.junit.Test

class PokemonRepositoryTest: PokemonRepository {
    override fun getPokemonResult(): Flow<PagingData<PokemonDomain>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPokemon(name: String): Flow<PokemonInfoDomain> {
        TODO("Not yet implemented")
    }

    override suspend fun getPokemonDb(name: String): Flow<PokemonDomain> {
        TODO("Not yet implemented")
    }

    @Test
    fun test(){
        TODO("Repository test")
    }

}