package com.friendsDomain.friendsapp.infrastructure

class SystemClock: Clock {

    override fun now(): Long {
        return System.currentTimeMillis()
    }
}