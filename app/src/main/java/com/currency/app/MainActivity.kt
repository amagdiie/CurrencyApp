package com.currency.app

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.appcompat.widget.AppCompatCheckedTextView
import com.currency.app.databinding.ActivityMainBinding
import com.currency.app.di.RequestStatus
import com.currency.app.view_models.ExchangeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val viewModel: ExchangeViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel.getExchangeRates()
        observeData()
        initUi()
        setContentView(binding.root)
    }

    private fun initUi() {
        binding.etFrom.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val tex = s.toString()
                if (tex.isNotEmpty())
                    binding.etTo.setText(getTextFromConvert())
                else
                    binding.etTo.text.clear()
            }

        })

        binding.ivConvert.setOnClickListener {
            val fromSelectedPosition = binding.spFrom.selectedItemPosition
            val toSelectedPosition = binding.spTo.selectedItemPosition
            binding.spFrom.setSelection(toSelectedPosition, true)
            binding.spTo.setSelection(fromSelectedPosition, true)
        }

    }

    private fun observeData() {
        viewModel.currencyExchange.observe(this){
            when(it.requestStatus){
                RequestStatus.LOADING ->{
                    binding.pbLoading.visibility = View.VISIBLE
                }
                RequestStatus.SUCCESS ->{
                    binding.pbLoading.visibility = View.GONE
                    if (it.data != null && it.data.rates.isNotEmpty()) {
                        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, it.data.rates.keys.toList())
                        binding.spFrom.adapter = adapter
                        binding.spTo.adapter = adapter
                        binding.spFrom.onItemSelectedListener = this
                        binding.spTo.onItemSelectedListener = this
                    }
                }
                RequestStatus.ERROR ->{
                    binding.pbLoading.visibility = View.GONE
                }
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        binding.etTo.setText(getTextFromConvert())
    }

    private fun getTextFromConvert(): String {
        return viewModel.getAmountFromToExchange(binding.etFrom.text.toString(), binding.spFrom.selectedItem.toString(), binding.spTo.selectedItem.toString())
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}