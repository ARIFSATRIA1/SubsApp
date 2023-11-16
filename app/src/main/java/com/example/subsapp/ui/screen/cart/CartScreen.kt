package com.example.subsapp.ui.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.subsapp.R
import com.example.subsapp.di.Injection
import com.example.subsapp.ui.ViewModelFactory
import com.example.subsapp.ui.common.UiState
import com.example.subsapp.ui.component.CartItem
import com.example.subsapp.ui.component.OrderButton
import com.example.subsapp.ui.theme.SubsAppTheme

@Composable
fun CartScreen (
    viewModel: CartViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory =
    ViewModelFactory(Injection.provideRepository())),
    onOrderButtonCLicked: (String) -> Unit
) {
    viewModel.uiState.collectAsState(UiState.Loading).value.let {uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedOrderRewards()
            }
            is UiState.Success -> {
                CartContent(
                    state = uiState.data,
                    onProductCountChanged = {animeId, count ->
                        viewModel.updatOrderReward(animeId, count)
                    },
                    onOrderButtonCLicked = onOrderButtonCLicked
                )
            }
            is UiState.Error -> {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    state: CartState,
    onProductCountChanged: (id:Long, count: Int) -> Unit,
    onOrderButtonCLicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shareMessage = stringResource(
        R.string.share_message,
        state.orderReward.count(),
        state.totalRequiredPrice
    )
    Column (modifier = modifier.fillMaxSize()){
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.Keranjang),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.weight(weight = 1f)
        ) {
            items(state.orderReward, key = {it.anime.id}) {item ->
                CartItem(
                    animeId = item.anime.id,
                    image = item.anime.image,
                    title = item.anime.title,
                    totalPrice = item.anime.price * item.totalPrice,
                    count = item.totalPrice,
                    onProductChanged = onProductCountChanged
                )
                Divider()
            }
        }
        OrderButton(
            text = stringResource(id = R.string.total_price, state.totalRequiredPrice),
            enabled = true,
            onClick = {
                onOrderButtonCLicked(shareMessage)
            },
            modifier = modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun CartScreenPreview(){
    SubsAppTheme {
        CartScreen(onOrderButtonCLicked = {})
    }
}
