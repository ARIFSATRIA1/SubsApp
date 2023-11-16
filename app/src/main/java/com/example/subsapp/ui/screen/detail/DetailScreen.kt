package com.example.subsapp.ui.screen.detail

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.subsapp.R
import com.example.subsapp.di.Injection
import com.example.subsapp.ui.ViewModelFactory
import com.example.subsapp.ui.common.UiState
import com.example.subsapp.ui.component.OrderButton
import com.example.subsapp.ui.component.ProductCounter

@Composable
fun DetailScreen(
    animeId: Long,
    viewModel: DetailViewModel = viewModel(factory =
    ViewModelFactory(Injection.provideRepository())),
    navigateBack: () -> Unit,
    navigateToCart: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAnimeById(animeId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    image = data.anime.image,
                    title = data.anime.title,
                    sinopsis = data.anime.sinopsis,
                    basePrice = data.anime.price,
                    price = data.totalPrice,
                    onBackCLick = navigateBack,
                    onAddToCart = {count ->
                        viewModel.addToCart(data.anime, count)
                        navigateToCart()
                    }
                )
            }
            is UiState.Error -> {
                Toast.makeText(LocalContext.current, stringResource(id = R.string.error), Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    title:String,
    sinopsis: String,
    basePrice: Int,
    price: Int,
    onBackCLick: () -> Unit,
    onAddToCart: (count: Int) -> Unit,
    modifier: Modifier = Modifier
) {

    var totalPrice by rememberSaveable { mutableStateOf(0) }
    var orderCount by rememberSaveable { mutableStateOf(price) }

    Column (modifier = modifier){
        Column (
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ){
            Box {
                Icon(
                    imageVector = Icons.Default.ArrowBack, 
                    contentDescription = stringResource(id = R.string.back),
                    modifier = modifier
                        .padding(16.dp)
                        .clickable { onBackCLick() }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .padding(16.dp)
            ){
                Image(
                    painter = painterResource(id = image),
                    contentDescription = stringResource(id = R.string.Gambar_anime),
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                )
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Text(
                    text = stringResource(id = R.string.sincewas, basePrice),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = modifier
                        .padding(bottom = 16.dp)
                )
                Text(
                    text = sinopsis,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    fontSize = 12.sp,
                )
            }
        }
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.White)
        )
        Column (
            modifier = modifier
                .padding(16.dp)
        ) {
            ProductCounter(
                orderId = 1,
                orderCount = orderCount,
                onProductIncreased = {orderCount++},
                onProductDecreased = {if (orderCount > 0 ) orderCount--},
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)

            )

            OrderButton(
                text = stringResource(id = R.string.TambahKeKeranjang, orderCount),
                enabled = orderCount > 0,
                onClick = {
                    onAddToCart(orderCount)
                }
            )
        }
    }
}