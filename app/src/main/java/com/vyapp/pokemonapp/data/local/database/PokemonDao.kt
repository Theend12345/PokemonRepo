package com.vyapp.pokemonapp.data.local.database

import androidx.paging.PagingSource
import androidx.room.*
import com.vyapp.pokemonapp.data.local.model.PokemonEntity
import com.vyapp.pokemonapp.data.remote.model.PokemonInfo
import com.vyapp.pokemonapp.data.remote.model.PokemonResult


@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemons")
    fun pagingSource(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemons WHERE name=:p_name")
    fun getPokemon(p_name: String): PokemonEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemons: List<PokemonEntity>)

    @Query("DELETE FROM pokemons")
    fun clearAll()

}