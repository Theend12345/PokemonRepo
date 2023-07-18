package com.vyapp.pokemonapp.domain.usecase

import com.vyapp.pokemonapp.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonRemoteListUseCase @Inject constructor(val repository: PokemonRepository) {

    fun execute() = repository.getPokemonResult()

}