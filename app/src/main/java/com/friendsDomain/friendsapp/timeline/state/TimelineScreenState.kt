package com.friendsDomain.friendsapp.timeline.state

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.friendsDomain.friendsapp.domain.post.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimelineScreenState(
    private val coroutineScope: CoroutineScope
) {
    var isLoading by mutableStateOf(false)
    var posts by mutableStateOf(emptyList<Post>())
    var loadedUserId by mutableStateOf("")
    var isInfoMessageShowing by mutableStateOf(false)
    var currentInfoMessage by mutableStateOf(0)

    fun updatePosts(newPosts: List<Post>) {
        isLoading = false
        this.posts = newPosts
    }

    fun shouldLoadPostFor(userId: String): Boolean {
        if (loadedUserId != userId){
            loadedUserId = userId
            return true
        }
        return false
    }

    fun showLoading(){
        isLoading = true
    }

   fun showInfoMessageError(@StringRes infoMessage: Int) =
       coroutineScope.launch() {
       isLoading = false
       if(currentInfoMessage!= infoMessage){
           currentInfoMessage = infoMessage
           if (!isInfoMessageShowing){
               isInfoMessageShowing = true
               delay(1500)
               isInfoMessageShowing = false
           }
       }
    }
}