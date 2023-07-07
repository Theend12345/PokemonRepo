package com.vyapp.pokemonapp.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vyapp.pokemonapp.domain.usecase.GetPokemonListOfflineUseCase
import com.vyapp.pokemonapp.domain.usecase.GetPokemonRemoteListUseCase
import com.vyapp.pokemonapp.domain.usecase.GetPokemonRemoteUseCase
import com.vyapp.pokemonapp.domain.usecase.SavePokemonUseCase
import javax.inject.Inject

class PViewModelFactory @Inject constructor(
    val getPokemonRemoteListUseCase: GetPokemonRemoteListUseCase,
    val getPokemonRemoteUseCase: GetPokemonRemoteUseCase,
    val savePokemonUseCase: SavePokemonUseCase,
    val getPokemonListOfflineUseCase: GetPokemonListOfflineUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PokemonViewModel(
            getPokemonRemoteListUseCase,
            getPokemonRemoteUseCase,
            savePokemonUseCase,
            getPokemonListOfflineUseCase
        ) as T
    }

}