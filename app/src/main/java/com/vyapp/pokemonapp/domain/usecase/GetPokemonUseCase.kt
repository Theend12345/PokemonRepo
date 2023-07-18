package com.vyapp.pokemonapp.domain.usecase

import com.vyapp.pokemonapp.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(val repository: PokemonRepository) {

    suspend fun execute(name: String) = repository.getPokemonDb(name)

}