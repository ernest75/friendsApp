package com.friendsDomain.friendsapp.infrastructure

class ControllableClock(
    private val timeStamp: Long
) {

    fun now(): Long {
        return timeStamp
    }
}