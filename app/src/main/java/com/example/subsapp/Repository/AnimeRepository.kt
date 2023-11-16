package com.example.subsapp.Repository

import com.example.subsapp.model.Anime
import com.example.subsapp.model.FakeAnimeData
import com.example.subsapp.model.OrderAnime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class AnimeRepository {

    private val orderAnimes = mutableListOf<OrderAnime>()


    init {
        if (orderAnimes.isEmpty()) {
            FakeAnimeData.dummyAnimeList.forEach {
                orderAnimes.add(OrderAnime(it,0))
            }
        }
    }

    fun getAllAnimeList(): Flow<List<OrderAnime>> {
        return flowOf(orderAnimes)
    }

    fun getOrderAnimeById(animeId: Long): OrderAnime {
        return orderAnimes.first {
            it.anime.id == animeId
        }
    }

    fun updateOrderAnime(animeId: Long, newPriceValue: Int): Flow<Boolean> {
        val index = orderAnimes.indexOfFirst { it.anime.id == animeId }
        val result = if (index >= 0) {
            val orderAnime = orderAnimes[index]
            orderAnimes[index] = orderAnime.copy(orderAnime.anime, newPriceValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderAnime():Flow<List<OrderAnime>> {
        return getAllAnimeList()
            .map { orderAnimes ->
                orderAnimes.filter { orderAnime ->
                    orderAnime.totalPrice != 0
                }
            }
    }

    fun getAnime(): List<Anime> {
        return FakeAnimeData.dummyAnimeList
    }

    fun searchAnime(query:String): List<Anime> {
        return FakeAnimeData.dummyAnimeList.filter {
            it.title.contains(query,ignoreCase = true)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AnimeRepository? = null

        fun getInstance(): AnimeRepository =
            INSTANCE ?: synchronized(this) {
                AnimeRepository().apply {
                    INSTANCE = this
                }
            }
    }
}
