package com.example.subsapp.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.subsapp.di.Injection
import com.example.subsapp.ui.ViewModelFactory
import com.example.subsapp.ui.component.AnimeItem
import com.example.subsapp.ui.component.searchBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ), navigateToDetail: (Long) -> Unit
) {
    val groupedHeroes by viewModel.getAllAnime.collectAsState()
    val query by viewModel.query


    Column(modifier = modifier) {
        searchBar(
            query = query,
            onQueryChange = viewModel::search,
            modifier = Modifier.background(MaterialTheme.colorScheme.primary)
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
        ) {
            groupedHeroes.forEach { (_, anime) ->
                items(anime) { data ->
                    AnimeItem(image = data.image,
                        title = data.title,
                        price = data.price,
                        modifier = modifier.clickable {
                            navigateToDetail(data.id)
                        }
                    )
                }
            }
        }
    }
}


