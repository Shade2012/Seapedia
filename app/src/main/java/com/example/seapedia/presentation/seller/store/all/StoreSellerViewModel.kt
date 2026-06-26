package com.example.seapedia.presentation.seller.store.all

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seapedia.domain.usecases.store.GetStoreUseCase
import com.example.seapedia.global.utils.CommonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreSellerViewModel @Inject constructor(
    private val getStoreUseCase: GetStoreUseCase
) : ViewModel(){
    private val _state = MutableStateFlow(StoreSellerState())
    val state = _state.asStateFlow()
    init {
        getStore()
    }
    fun getStore(){
        viewModelScope.launch {
            updateState {
                copy(store = CommonState.Loading())
            }
            getStoreUseCase.run().collect {
                result ->
                Log.d("Hasil StoreViewModle",result.toString())
                when(result){
                    is CommonState.Error<*> -> {
                        updateState {
                            copy(result)
                        }
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(result)
                        }
                    }
                    is CommonState.Success<*> -> {
                        updateState {
                            copy(result)
                        }
                    }
                }
            }
        }
    }

    fun refreshStore(){
        viewModelScope.launch {
            updateState {
                copy(store = CommonState.Loading(), isRefreshing = true)
            }
            getStoreUseCase.run().collect {
                    result ->
                Log.d("Hasil StoreViewModle",result.toString())
                when(result){
                    is CommonState.Error<*> -> {
                        updateState {
                            copy(result, isRefreshing = false)
                        }
                    }
                    is CommonState.Loading<*> -> {
                        updateState {
                            copy(result, isRefreshing = true)
                        }
                    }
                    is CommonState.Success<*> -> {
                        updateState {
                            copy(result, isRefreshing = false)
                        }
                    }
                }
            }
        }
    }
    private fun updateState(
        update: StoreSellerState.() -> StoreSellerState
    ) {
        _state.update {
            it.update()
        }
    }
}