package com.vyapp.pokemonapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vyapp.pokemonapp.data.remote.model.Sprites
import com.vyapp.pokemonapp.data.remote.model.Type

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val weight: Int? = null,
    val height: Int? = null,
    val type: String? = null,
)
