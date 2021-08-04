package com.friendsDomain.friendsapp.domain.validation

sealed class CredentialsValidationResult {
    object InvalidEmail: CredentialsValidationResult()
    object InvalidPassword: CredentialsValidationResult()
}