package com.example.subsapp.di

import com.example.subsapp.Repository.AnimeRepository

object Injection {
    fun provideRepository(): AnimeRepository {
        return AnimeRepository.getInstance()
    }
}