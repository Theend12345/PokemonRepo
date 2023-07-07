package com.vyapp.pokemonapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vyapp.pokemonapp.data.local.model.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1)
abstract class PokemonDatabase: RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

}