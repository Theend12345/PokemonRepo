package com.vyapp.pokemonapp.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vyapp.pokemonapp.domain.model.PokemonInfoDomain
import com.vyapp.pokemonapp.domain.model.PokemonResultDomain
import com.vyapp.pokemonapp.domain.usecase.GetPokemonRemoteListUseCase
import com.vyapp.pokemonapp.domain.usecase.GetPokemonRemoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonViewModel(
    val getPokemonRemoteListUseCase: GetPokemonRemoteListUseCase,
    val getPokemonRemoteUseCase: GetPokemonRemoteUseCase
) : ViewModel() {
    private val _pokemonRemoteList = MutableStateFlow<UIState<PokemonResultDomain>>(UIState.Loading)

    val pokemonRemoteList: StateFlow<UIState<PokemonResultDomain>>
        get() = _pokemonRemoteList.asStateFlow()

    private val _pokemonRemote = MutableStateFlow<UIState<PokemonInfoDomain>>(UIState.Loading)

    val pokemonRemote: StateFlow<UIState<PokemonInfoDomain>>
        get() = _pokemonRemote.asStateFlow()


    fun fetchPokemonRemote(name: String) {
        viewModelScope.launch {
            try {
                val data = getPokemonRemoteUseCase.execute(name)
                data.collect {
                    _pokemonRemote.value = UIState.Success(it)
                }
            } catch (e: Throwable) {
                _pokemonRemote.value = UIState.Error(e)
            }
        }
    }

    fun fetchPokemonRemoteList() {
        viewModelScope.launch {
            try {
                val data = getPokemonRemoteListUseCase.execute()
                data.collect {
                    _pokemonRemoteList.value = UIState.Success(it)
                }
            } catch (e: Throwable) {
                _pokemonRemoteList.value = UIState.Error(e)
            }
        }
    }

}