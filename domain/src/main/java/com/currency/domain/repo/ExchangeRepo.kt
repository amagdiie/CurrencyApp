package com.currency.domain.repo

import com.currency.domain.entity.ExchangeCurrencyResponse

interface ExchangeRepo {
    suspend fun getExchangeRatesFromRemote() : ExchangeCurrencyResponse
}