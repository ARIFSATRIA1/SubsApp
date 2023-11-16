package com.example.subsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.subsapp.Repository.AnimeRepository
import com.example.subsapp.ui.screen.cart.CartViewModel
import com.example.subsapp.ui.screen.detail.DetailViewModel
import com.example.subsapp.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: AnimeRepository): ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModelClass " + modelClass.name)
    }
}