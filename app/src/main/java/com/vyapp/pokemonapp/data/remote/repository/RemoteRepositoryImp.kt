package com.vyapp.pokemonapp.data.remote.repository

import com.vyapp.pokemonapp.data.remote.PokemonService
import com.vyapp.pokemonapp.domain.repository.RemoteRepository
import com.vyapp.pokemonapp.util.toPokemonInfoDomain
import com.vyapp.pokemonapp.util.toPokemonResultDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class RemoteRepositoryImp(val api: PokemonService): RemoteRepository {

    override suspend fun getPokemonResult() = withContext(Dispatchers.IO){
        flowOf(api.getPokemonList(0,20).toPokemonResultDomain())
    }

    override suspend fun getPokemon(name: String) = withContext(Dispatchers.IO){
        flowOf(api.getPokemon(name).toPokemonInfoDomain())
    }


}