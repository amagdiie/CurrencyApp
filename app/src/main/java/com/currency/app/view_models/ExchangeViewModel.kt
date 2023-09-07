package com.currency.app.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.app.di.Resource
import com.currency.domain.entity.ExchangeCurrencyResponse
import com.currency.domain.usecase.GetExchangeRates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val getExchangeRatesUseCase: GetExchangeRates
) : ViewModel() {

    private val _currencyExchange = MutableLiveData<Resource<ExchangeCurrencyResponse?>>()
    val currencyExchange: LiveData<Resource<ExchangeCurrencyResponse?>> = _currencyExchange
    private val _rates: MutableLiveData<Map<String, Double>> = MutableLiveData()

    fun getExchangeRates() {
        _currencyExchange.postValue(Resource.loading())
        viewModelScope.launch {
            try {
                val response = getExchangeRatesUseCase()
                _currencyExchange.postValue(Resource.success(response))
                _rates.value = response.rates
            } catch (e: Exception) {
                _currencyExchange.postValue(Resource.error(e))
            }
        }
    }

    fun getAmountFromToExchange(amountToChange: String, fromCurrency: String, toCurrency: String): String{
        // TO Get 1 USD --> EGP => you must do EGP with Base (EUR) / USD with Base (EUR) * AMOUNT
        var amount = amountToChange.toDoubleOrNull()
        if (amount == null)
            amount = 1.0

        return calculateConvert(amount, _rates.value?.get(fromCurrency), _rates.value?.get(toCurrency))
    }

    private fun calculateConvert(amount: Double, fromCurrency: Double? = 1.0, toCurrency: Double? = 1.0): String {
        return ((toCurrency?.div(fromCurrency!!))?.times(amount)).toString()
    }
}