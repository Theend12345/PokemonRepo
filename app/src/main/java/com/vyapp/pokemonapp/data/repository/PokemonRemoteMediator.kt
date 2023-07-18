package com.vyapp.pokemonapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vyapp.pokemonapp.data.local.database.PokemonDatabase
import com.vyapp.pokemonapp.data.local.model.PokemonEntity
import com.vyapp.pokemonapp.data.remote.PokemonService
import com.vyapp.pokemonapp.util.*

@ExperimentalPagingApi
class PokemonRemoteMediator(
    private val pokemonDatabase: PokemonDatabase,
    val pokemonService: PokemonService
) :
    RemoteMediator<Int, PokemonEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {

        return try {
            val key = when (loadType) {
                LoadType.REFRESH -> FIRST_PAGE
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    LIMIT + lastItem.id
                }
            }

            val pokemonResult = pokemonService.getPokemonList(
                key, LIMIT
            )

            val pokemons = pokemonResult.results.map {
                pokemonService.getPokemon(it.name)
            }

            pokemonDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pokemonDatabase.pokemonDao().clearAll()
                }
                val pokemonEntities = pokemons.map {
                    it.toPokemonInfoDomain().toPokemonDomain().toPokemonEntity()
                }

                pokemonDatabase.pokemonDao().insertAll(pokemonEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = pokemonResult.results.isEmpty()
            )

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }

    }
}