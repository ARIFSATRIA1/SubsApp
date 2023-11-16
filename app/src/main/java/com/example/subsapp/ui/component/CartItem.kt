package com.example.subsapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.subsapp.R
import com.example.subsapp.ui.theme.Shapes

@Composable
fun CartItem(
    animeId: Long,
    image: Int,
    title: String,
    totalPrice: Int,
    count: Int,
    onProductChanged: (id: Long, count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier.fillMaxWidth()
    ){
        Image(
            painter = painterResource(image),
            contentDescription = stringResource(id = R.string.Gambar_anime),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(90.dp)
                .clip(Shapes.small)
        )
        Column (
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ){
            Text(
                text = title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = stringResource(id = R.string.sincewas, totalPrice),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
        }
        ProductCounter(
            orderId = animeId,
            orderCount = count,
            onProductIncreased = {onProductChanged(animeId, count + 1)},
            onProductDecreased = {onProductChanged(animeId, count - 1)},
            modifier = modifier.padding(8.dp)
        )
    }
}