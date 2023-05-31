package com.alezzgo.notes.ui

import com.alezzgo.notes.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun BrowserScreen(viewModel: BrowserViewModel) {
    Column {
        // Search bar
        SearchBar(hint = "Search", modifier = Modifier.padding(16.dp))

        // Lazy list of items
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(viewModel.itemsList) { item ->
                ListItem(item = item)
            }
        }
    }
}

@Composable
fun SearchBar(hint: String, modifier: Modifier = Modifier) {
    val textState = remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.Gray, RoundedCornerShape(12.dp))
    ) {
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 8.dp),
            textStyle = MaterialTheme.typography.body1,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            placeholder = { Text(hint) }
        )
        Image(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            alignment = Alignment.CenterStart,
            modifier = Modifier.size(48.dp)
        )
        if (textState.value.isNotEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.ic_clear),
                contentDescription = "Clear search",
                alignment = Alignment.CenterEnd,
                modifier = Modifier
                    .clickable {
                        textState.value = ""
                    }
                    .size(48.dp)
            )
        }
    }
}

@Composable
fun ListItem(item: Item) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(bounded = true),
                onClick = { /* Handle item click */ }
            )
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(CircleShape)
        )
//        data = item.imageUrl,
//        contentDescription = item.title,
//        modifier = Modifier
//            .size(64.dp)
//            .clip(RoundedCornerShape(8.dp))
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(text = item.title, style = MaterialTheme.typography.h6)
            Text(text = item.subtitle, style = MaterialTheme.typography.caption)
        }
    }
}
