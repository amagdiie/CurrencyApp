package com.currency.data.repo

import com.currency.data.remote.ApiService
import com.currency.domain.entity.ExchangeCurrencyResponse
import com.currency.domain.repo.ExchangeRepo

class ExchangeCurrencyRepoImpl(private val apiService: ApiService): ExchangeRepo {

    override suspend fun getExchangeRatesFromRemote(): ExchangeCurrencyResponse = apiService.getExchangeCurrency()
}