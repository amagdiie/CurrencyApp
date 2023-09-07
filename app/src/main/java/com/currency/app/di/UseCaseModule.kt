package com.currency.app.di

import com.currency.domain.repo.ExchangeRepo
import com.currency.domain.usecase.GetExchangeRates
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUseCase(exchangeRepo: ExchangeRepo): GetExchangeRates{
        return GetExchangeRates(exchangeRepo)
    }
}