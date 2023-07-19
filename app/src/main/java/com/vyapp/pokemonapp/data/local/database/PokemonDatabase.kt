package com.vyapp.pokemonapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vyapp.pokemonapp.data.local.model.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 3)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

}