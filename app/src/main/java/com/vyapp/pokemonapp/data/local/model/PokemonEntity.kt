package com.vyapp.pokemonapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vyapp.pokemonapp.data.remote.model.Sprites
import com.vyapp.pokemonapp.data.remote.model.Type

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey val name : String
)
