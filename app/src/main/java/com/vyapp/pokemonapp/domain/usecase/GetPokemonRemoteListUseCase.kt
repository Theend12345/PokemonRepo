package com.vyapp.pokemonapp.domain.usecase

import com.vyapp.pokemonapp.domain.repository.RemoteRepository
import javax.inject.Inject

class GetPokemonRemoteListUseCase @Inject constructor(val repository: RemoteRepository) {

    suspend fun execute() = repository.getPokemonResult()

}