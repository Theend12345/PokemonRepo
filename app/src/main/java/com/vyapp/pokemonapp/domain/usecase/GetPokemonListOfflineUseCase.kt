package com.vyapp.pokemonapp.domain.usecase

import com.vyapp.pokemonapp.domain.repository.LocalRepository
import javax.inject.Inject

class GetPokemonListOfflineUseCase @Inject constructor(val repository: LocalRepository) {

    fun execute() = repository.getPokemonListOffline()

}