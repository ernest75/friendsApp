package com.friendsDomain.friendsapp.ui.signup

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.friendsDomain.friendsapp.R
import com.friendsDomain.friendsapp.ui.signup.state.SignUpState

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel,
    onSignedUp: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var about by remember { mutableStateOf("") }
    val signUpState by signUpViewModel.signUpState.observeAsState()

    if (signUpState is SignUpState.SignedUp) {
        onSignedUp()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ScreenTitle(R.string.createAccount)
            Spacer(modifier = Modifier.height(16.dp))

            EmailField(
                value = email,
                onValueChange = { email = it })
            PasswordField(
                value = password,
                onValueChange = { password = it })
            AboutField(
                value = about,
                onValueChange = { about = it }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    signUpViewModel.createAccount(email, password, about)
                },
            ) {
                Text(text = stringResource(id = R.string.signUp))
            }
        }
        if (signUpState is SignUpState.DuplicateAccount) {
            InfoMessage(R.string.duplicateAccountError)
        } else if (signUpState is SignUpState.BackEndError){
            InfoMessage(stringResource = R.string.createAccountError)
        } else if (signUpState is SignUpState.Offline){
            InfoMessage(stringResource = R.string.offLineError)
        } else if (signUpState is SignUpState.BadEmail){
            InfoMessage(stringResource = R.string.badEmailError)
        } else if (signUpState is SignUpState.BadPassword){
            InfoMessage(stringResource = R.string.badPasswordError)
        }
    }
}

@Composable
fun InfoMessage(@StringRes stringResource: Int) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.secondaryVariant)
    ) {

        Text(text = stringResource(id = stringResource))
    }
}

@Composable
private fun ScreenTitle(@StringRes resource: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(resource),
            style = typography.h4
        )
    }
}

@Composable
private fun EmailField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        label = {
            Text(text = stringResource(id = R.string.email))
        },
        onValueChange = onValueChange
    )
}

@Composable
private fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }
    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        trailingIcon = {
            VisibilityToggle(isVisible) {
                isVisible = !isVisible
            }
        },
        visualTransformation = visualTransformation,
        label = {
            Text(text = stringResource(id = R.string.password))
        },
        onValueChange = onValueChange
    )
}

@Composable
private fun VisibilityToggle(
    isVisible: Boolean,
    onToggle: () -> Unit
) {
    IconButton(onClick = {
        onToggle()
    }) {
        val resource = if (isVisible) R.drawable.ic_invisible else R.drawable.ic_visible
        Icon(
            painter = painterResource(id = resource),
            contentDescription = stringResource(id = R.string.toogleVisibility)
        )
    }
}

@Composable
fun AboutField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        label = {
            Text(text = stringResource(id = R.string.about))
        },
        onValueChange = onValueChange
    )
}
