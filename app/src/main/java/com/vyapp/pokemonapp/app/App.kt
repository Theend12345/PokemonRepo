package com.vyapp.pokemonapp.app

import android.app.Application
import com.vyapp.pokemonapp.di.AppComponent
import com.vyapp.pokemonapp.di.DaggerAppComponent
import com.vyapp.pokemonapp.di.module.DataModule

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().dataModule(DataModule(this)).build()
    }

}