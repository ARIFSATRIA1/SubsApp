package com.example.subsapp.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subsapp.Repository.AnimeRepository
import com.example.subsapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CartViewModel(private val repository: AnimeRepository):  ViewModel() {

    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>> get() = _uiState

    fun getAddedOrderRewards() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderAnime()
                .collect {orderAnime ->
                    val totalRequiredPrice = orderAnime.sumOf { it.anime.price * it.totalPrice }
                    _uiState.value = UiState.Success(CartState(orderAnime, totalRequiredPrice))
                }
        }
    }

    fun updatOrderReward(animeId: Long, price: Int) {
        viewModelScope.launch {
            repository.updateOrderAnime(animeId, price)
                .collect {isUpdate ->
                    if (isUpdate) {
                        getAddedOrderRewards()
                    }
                }
        }
    }

}