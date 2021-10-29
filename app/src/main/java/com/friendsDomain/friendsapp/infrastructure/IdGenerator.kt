package com.friendsDomain.friendsapp.infrastructure

interface IdGenerator {
    fun next(): String
}