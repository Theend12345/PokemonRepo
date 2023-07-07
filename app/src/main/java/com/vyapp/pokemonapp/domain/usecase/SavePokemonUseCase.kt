package com.vyapp.pokemonapp.domain.usecase

import com.vyapp.pokemonapp.domain.model.PokemonEntityDomain
import com.vyapp.pokemonapp.domain.repository.LocalRepository
import javax.inject.Inject

class SavePokemonUseCase@Inject constructor(val repository: LocalRepository) {

    fun execute(pokemonEntity: PokemonEntityDomain){
        repository.addPokemon(pokemonEntity)
    }

}