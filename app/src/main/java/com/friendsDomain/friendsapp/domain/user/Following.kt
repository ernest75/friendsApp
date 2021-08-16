package com.friendsDomain.friendsapp.domain.user

data class Following(
    val userId: String,
    val followedId:String
)