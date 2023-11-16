package com.example.subsapp.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subsapp.Repository.AnimeRepository
import com.example.subsapp.model.Anime
import com.example.subsapp.model.FakeAnimeData
import com.example.subsapp.model.OrderAnime
import com.example.subsapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel (private val repository: AnimeRepository): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<OrderAnime>>> = MutableStateFlow(UiState.Loading)
    val uiState : StateFlow<UiState<List<OrderAnime>>> get() = _uiState

//    fun getAllAnimeList() {
//        viewModelScope.launch {
//            repository.getAllAnimeList()
//                .catch {
//                    _uiState.value = UiState.Error(it.message.toString())
//                }
//                .collect {orderAnimes ->
//                    _uiState.value = UiState.Success(orderAnimes)
//                }
//        }
//    }

    private val _groupedAnime = MutableStateFlow(
        repository.getAnime()
            .sortedBy { it.title }
            .groupBy { it.title[0] }
    )

    val getAllAnime: StateFlow<Map<Char, List<Anime>>> get() = _groupedAnime

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedAnime.value = repository.searchAnime(_query.value)
            .sortedBy { it.title }
            .groupBy { it.title[0] }

    }

}