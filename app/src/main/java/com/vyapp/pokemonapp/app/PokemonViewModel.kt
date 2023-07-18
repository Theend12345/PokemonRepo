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
import com.vyapp.pokemonapp.util.toPokemonEntity
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
                val data = getPokemonUseCase.execute(name).map { it.toPokemonInfoDomain() }
                data.collect {
                    _pokemonRemote.value = UIState.Success(it)
                }
            }
        }
    }

    fun fetchPokemonRemoteList() {
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