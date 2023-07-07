package com.vyapp.pokemonapp.di.module

import android.content.Context
import androidx.room.Room
import com.vyapp.pokemonapp.data.local.database.PokemonDatabase
import com.vyapp.pokemonapp.data.local.repository.LocalRepositoryImp
import com.vyapp.pokemonapp.data.remote.PokemonService
import com.vyapp.pokemonapp.data.remote.repository.RemoteRepositoryImp
import com.vyapp.pokemonapp.domain.repository.LocalRepository
import com.vyapp.pokemonapp.domain.repository.RemoteRepository
import com.vyapp.pokemonapp.util.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule(val context: Context) {

    @Provides
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun providePokemonService(): PokemonService =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonService::class.java)

    @Provides
    @Singleton
    fun provideRemoteRepository(api: PokemonService): RemoteRepository = RemoteRepositoryImp(api)

    @Provides
    @Singleton
    fun providePokemonDatabase(_context: Context): PokemonDatabase =
        Room.databaseBuilder(_context, PokemonDatabase::class.java, "pokemon").build()

    @Provides
    @Singleton
    fun provideLocalRepository(database: PokemonDatabase): LocalRepository =
        LocalRepositoryImp(database)

}