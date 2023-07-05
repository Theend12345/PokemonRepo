package com.vyapp.pokemonapp.domain.usecase

import com.vyapp.pokemonapp.domain.repository.RemoteRepository
import javax.inject.Inject

class GetPokemonRemoteUseCase @Inject constructor(val repository: RemoteRepository) {

    suspend fun execute(name: String) = repository.getPokemon(name)

}