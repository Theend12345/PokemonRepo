package com.vyapp.pokemonapp.data.remote

import com.vyapp.pokemonapp.data.remote.model.PokemonInfo
import com.vyapp.pokemonapp.data.remote.model.PokemonResult
import com.vyapp.pokemonapp.util.GET_POKEMON
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET(GET_POKEMON)
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonResult

    @GET("$GET_POKEMON{name}/")
    suspend fun getPokemon(@Path("name") name: String): PokemonInfo

}