package com.vyapp.pokemonapp.app

import android.app.Application
import com.vyapp.pokemonapp.di.AppComponent
import com.vyapp.pokemonapp.di.DaggerAppComponent

class App: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }

}