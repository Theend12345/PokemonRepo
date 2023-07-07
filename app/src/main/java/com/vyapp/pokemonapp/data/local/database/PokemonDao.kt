package com.vyapp.pokemonapp.data.local.database

import androidx.room.*
import com.vyapp.pokemonapp.data.local.model.PokemonEntity
import com.vyapp.pokemonapp.data.remote.model.PokemonInfo
import com.vyapp.pokemonapp.data.remote.model.PokemonResult


@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemons")
    fun getPokemonList(): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPokemon(pokemonEntity: PokemonEntity)

   // fun getPokemon(): PokemonInfo

}