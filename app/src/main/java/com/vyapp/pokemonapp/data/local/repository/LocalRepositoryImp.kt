package com.vyapp.pokemonapp.data.local.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vyapp.pokemonapp.data.local.database.PokemonDatabase
import com.vyapp.pokemonapp.data.remote.model.PokemonPagingSource
import com.vyapp.pokemonapp.data.remote.model.PokemonPagingSourceOffline
import com.vyapp.pokemonapp.domain.model.PokemonDomain
import com.vyapp.pokemonapp.domain.model.PokemonEntityDomain
import com.vyapp.pokemonapp.domain.model.PokemonInfoDomain
import com.vyapp.pokemonapp.domain.repository.LocalRepository
import com.vyapp.pokemonapp.util.LIMIT
import com.vyapp.pokemonapp.util.toPokemonEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalRepositoryImp(val database: PokemonDatabase): LocalRepository {

    override fun getPokemonListOffline(): Flow<PagingData<PokemonDomain>> = Pager(
        PagingConfig(LIMIT),
        pagingSourceFactory = { PokemonPagingSourceOffline(database) }
    ).flow

    override fun getPokemonOffline(id: Int): Flow<PokemonInfoDomain> {
        return flow {  }
    }

    override fun addPokemon(pokemonDomain: PokemonEntityDomain) {
        pokemonDomain.toPokemonEntity()?.let { database.pokemonDao().addPokemon(it) }
    }
}