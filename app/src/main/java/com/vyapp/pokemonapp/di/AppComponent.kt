package com.vyapp.pokemonapp.di

import com.vyapp.pokemonapp.app.PokemonFragment
import com.vyapp.pokemonapp.app.PokemonListFragment
import com.vyapp.pokemonapp.di.module.DataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface AppComponent {

    fun pokemonListInject(pokemonListFragment: PokemonListFragment)
    fun pokemonInject(pokemonFragment: PokemonFragment)

}