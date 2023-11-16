package com.example.subsapp.ui.screen.cart

import com.example.subsapp.model.OrderAnime

data class CartState (
    val orderReward: List<OrderAnime>,
    val totalRequiredPrice: Int,
)