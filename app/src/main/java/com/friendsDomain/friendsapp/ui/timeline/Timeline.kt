package com.friendsDomain.friendsapp.ui.timeline

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.friendsDomain.friendsapp.R

@Composable
fun Timeline() {
    Image(
        painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = "Timeline",
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.Crop
    )
}