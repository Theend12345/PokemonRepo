package com.vyapp.pokemonapp.data.remote.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vyapp.pokemonapp.data.remote.PokemonService
import com.vyapp.pokemonapp.domain.model.PokemonDomain
import com.vyapp.pokemonapp.util.LIMIT
import com.vyapp.pokemonapp.util.toListPokemonDomain
import com.vyapp.pokemonapp.util.toPokemonResultDomain
import javax.inject.Inject

class PokemonPagingSource @Inject constructor(val pokemonService: PokemonService) :
    PagingSource<Int, PokemonDomain>() {

    override fun getRefreshKey(state: PagingState<Int, PokemonDomain>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonDomain> {
        return try {
            val offset = params.key ?: 0
            val response = pokemonService.getPokemonList(offset, LIMIT).toPokemonResultDomain()

            LoadResult.Page(
                data = response.results,
                prevKey = if (offset == 0) null else offset - LIMIT,
                nextKey = if (response.results.isEmpty()) null else offset + LIMIT
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
