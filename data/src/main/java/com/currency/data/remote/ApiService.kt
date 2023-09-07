package com.currency.data.remote

import com.currency.domain.entity.ExchangeCurrencyResponse
import retrofit2.http.GET

interface ApiService {

    @GET("latest?access_key=2269531fc8bac651db1eb9d238997ce6")
    suspend fun getExchangeCurrency(): ExchangeCurrencyResponse
}