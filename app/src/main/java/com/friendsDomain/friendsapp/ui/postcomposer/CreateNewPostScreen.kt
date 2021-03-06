package com.friendsDomain.friendsapp.ui.postcomposer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.friendsDomain.friendsapp.R
import com.friendsDomain.friendsapp.postcomposer.CreatePostViewModel
import com.friendsDomain.friendsapp.postcomposer.state.CreatePostState
import com.friendsDomain.friendsapp.ui.composables.ScreenTitle

@Composable
fun CreateNewPostScreen(
    createPostViewModel: CreatePostViewModel,
    onPostCreated: () -> Unit
) {

    var postText by remember { mutableStateOf("") }

    val createPostState by createPostViewModel.postState.observeAsState()
    when(createPostState){
        is CreatePostState.Created -> onPostCreated()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenTitle(resource = R.string.createNewPost)
        Box(modifier = Modifier.fillMaxSize()) {
            PostComposer(postText) { postText = it }
            FloatingActionButton(
                onClick = {
                    createPostViewModel.createPost(postText)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .testTag(stringResource(id = R.string.submitPost))
            ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = stringResource(id = R.string.submitPost)
                    )
            }
        }
    }
}

@Composable
private fun PostComposer(
    postText: String,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = postText,
        onValueChange = onValueChanged,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = stringResource(id = R.string.newPostHint))}
    )
}
