package com.vyapp.pokemonapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.vyapp.pokemonapp.data.local.database.PokemonDatabase
import com.vyapp.pokemonapp.data.remote.PokemonService
import com.vyapp.pokemonapp.domain.model.PokemonDomain
import com.vyapp.pokemonapp.domain.repository.PokemonRepository
import com.vyapp.pokemonapp.util.LIMIT
import com.vyapp.pokemonapp.util.toPokemonDomain
import com.vyapp.pokemonapp.util.toPokemonEntity
import com.vyapp.pokemonapp.util.toPokemonInfoDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PokemonRepositoryImp(val api: PokemonService, val database: PokemonDatabase) :
    PokemonRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemonResult() = Pager(
        config = PagingConfig(pageSize = LIMIT),
        remoteMediator = PokemonRemoteMediator(database, api),
        pagingSourceFactory = { database.pokemonDao().pagingSource() }
    ).flow.map { data ->
        data.map { it.toPokemonDomain() }
    }

    override suspend fun getPokemon(name: String) = withContext(Dispatchers.IO) {
        flowOf(api.getPokemon(name).toPokemonInfoDomain())
    }

    override suspend fun getPokemonDb(name: String) = withContext(Dispatchers.IO) {
        flowOf(database.pokemonDao().getPokemon(name).toPokemonDomain())
    }


}