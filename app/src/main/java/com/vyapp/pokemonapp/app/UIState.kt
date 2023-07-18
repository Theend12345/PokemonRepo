package com.vyapp.pokemonapp.app

sealed class UIState<out T: Any>{
    object Loading: UIState<Nothing>()
    data class Success<out T: Any> (val data: T): UIState<T>()
    data class Error(val e: Throwable): UIState<Nothing>()
}
