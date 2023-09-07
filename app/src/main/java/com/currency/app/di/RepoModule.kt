package com.currency.app.di

import com.currency.data.remote.ApiService
import com.currency.data.repo.ExchangeCurrencyRepoImpl
import com.currency.domain.repo.ExchangeRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideRepo(apiService: ApiService): ExchangeRepo{
        return ExchangeCurrencyRepoImpl(apiService)
    }
}