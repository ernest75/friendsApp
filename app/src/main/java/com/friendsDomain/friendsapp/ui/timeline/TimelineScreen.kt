package com.friendsDomain.friendsapp.ui.timeline

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.friendsDomain.friendsapp.R

@Composable
fun TimelineScreen() {

    Column{
        Text(text = stringResource(id = R.string.timeline))
        Text(text = stringResource(id = R.string.emptyTimelineMessage))
    }
}