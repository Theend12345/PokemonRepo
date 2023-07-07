package com.vyapp.pokemonapp.domain.usecase

import androidx.paging.cachedIn
import com.vyapp.pokemonapp.domain.repository.RemoteRepository
import javax.inject.Inject

class GetPokemonRemoteListUseCase @Inject constructor(val repository: RemoteRepository) {

    fun execute() = repository.getPokemonResult()

}