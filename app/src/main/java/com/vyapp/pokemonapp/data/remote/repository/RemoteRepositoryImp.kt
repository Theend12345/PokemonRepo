package com.vyapp.pokemonapp.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vyapp.pokemonapp.data.remote.PokemonService
import com.vyapp.pokemonapp.data.remote.model.PokemonPagingSource
import com.vyapp.pokemonapp.domain.repository.RemoteRepository
import com.vyapp.pokemonapp.util.LIMIT
import com.vyapp.pokemonapp.util.toPokemonInfoDomain
import com.vyapp.pokemonapp.util.toPokemonResultDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class RemoteRepositoryImp(val api: PokemonService) : RemoteRepository {

    override fun getPokemonResult() = Pager(
        PagingConfig(LIMIT),
        pagingSourceFactory = { PokemonPagingSource(api) }
    ).flow

    override suspend fun getPokemon(name: String) = withContext(Dispatchers.IO) {
        flowOf(api.getPokemon(name).toPokemonInfoDomain())
    }


}