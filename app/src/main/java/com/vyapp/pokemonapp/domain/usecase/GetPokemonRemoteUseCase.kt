package com.vyapp.pokemonapp.domain.usecase

import com.vyapp.pokemonapp.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonRemoteUseCase @Inject constructor(val repository: PokemonRepository) {

    suspend fun execute(name: String) = repository.getPokemon(name)

}