package com.example.subsapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.subsapp.R
import com.example.subsapp.ui.theme.Shapes
import com.example.subsapp.ui.theme.SubsAppTheme

@Composable
fun AnimeItem(
    image: Int,
    title: String,
    price: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = "Gambar Anime",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(Shapes.medium)
                .size(170.dp)

        )
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        Text(
            text = stringResource(R.string.sincewas, price),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AnimeItemPreview() {
    SubsAppTheme {
        AnimeItem(image = R.drawable.jujutsu, title = "Jujutsu Kaisen", price = 90000)
    }
}