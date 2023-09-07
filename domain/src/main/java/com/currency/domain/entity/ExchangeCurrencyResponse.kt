package com.currency.domain.entity

data class ExchangeCurrencyResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double>,
    val success: Boolean,
    val timestamp: Int
)