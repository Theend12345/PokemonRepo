package com.vyapp.pokemonapp.di.module

import com.vyapp.pokemonapp.data.remote.PokemonService
import com.vyapp.pokemonapp.data.remote.repository.RemoteRepositoryImp
import com.vyapp.pokemonapp.domain.repository.RemoteRepository
import com.vyapp.pokemonapp.util.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

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

}