package com.vyapp.pokemonapp.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vyapp.pokemonapp.domain.model.PokemonDomain
import com.vyapp.pokemonapp.domain.model.PokemonInfoDomain
import com.vyapp.pokemonapp.domain.usecase.GetPokemonRemoteListUseCase
import com.vyapp.pokemonapp.domain.usecase.GetPokemonRemoteUseCase
import com.vyapp.pokemonapp.domain.usecase.GetPokemonUseCase
import com.vyapp.pokemonapp.util.toPokemonInfoDomain
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PokemonViewModel(
    val getPokemonRemoteListUseCase: GetPokemonRemoteListUseCase,
    val getPokemonRemoteUseCase: GetPokemonRemoteUseCase,
    val getPokemonUseCase: GetPokemonUseCase
) : ViewModel() {
    private val _pokemonList = MutableStateFlow<UIState<PagingData<PokemonDomain>>>(UIState.Loading)

    val pokemonList: StateFlow<UIState<PagingData<PokemonDomain>>>
        get() = _pokemonList.asStateFlow()

    private val _pokemon = MutableStateFlow<UIState<PokemonInfoDomain>>(UIState.Loading)

    val pokemon: StateFlow<UIState<PokemonInfoDomain>>
        get() = _pokemon.asStateFlow()


    fun fetchPokemon(name: String) {

        viewModelScope.launch {
            try {
                val data = getPokemonRemoteUseCase.execute(name)
                data.collectLatest {
                    _pokemon.value = UIState.Success(it)
                }
            } catch (e: Throwable) {
                val data = getPokemonUseCase.execute(name).map { it.toPokemonInfoDomain() }
                data.collectLatest {
                    _pokemon.value = UIState.Success(it)
                }
            }
        }
    }

    fun fetchPokemonList() {
        viewModelScope.launch {
            try {
                val data = getPokemonRemoteListUseCase.execute().cachedIn(viewModelScope)
                data.collect {

                    _pokemonList.value = UIState.Success(it)
                }
            } catch (e: Throwable) {
                _pokemonList.value = UIState.Error(e)
            }
        }
    }

}