package com.example.subsapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subsapp.Repository.AnimeRepository
import com.example.subsapp.model.Anime
import com.example.subsapp.model.OrderAnime
import com.example.subsapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel (private val repository: AnimeRepository): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<OrderAnime>> = MutableStateFlow(UiState.Loading)
    val uiState : StateFlow<UiState<OrderAnime>> get() = _uiState

    fun getAnimeById(animeId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderAnimeById(animeId))
        }
    }

    fun addToCart(anime: Anime, price: Int) {
        viewModelScope.launch {
            repository.updateOrderAnime(anime.id,price)
        }
    }
}