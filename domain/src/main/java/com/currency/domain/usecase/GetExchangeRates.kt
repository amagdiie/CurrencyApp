package com.currency.domain.usecase

import com.currency.domain.repo.ExchangeRepo

class GetExchangeRates(private val exchangeRepo: ExchangeRepo) {
    suspend operator fun invoke() = exchangeRepo.getExchangeRatesFromRemote()
}