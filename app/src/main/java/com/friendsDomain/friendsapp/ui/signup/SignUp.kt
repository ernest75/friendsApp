package com.friendsDomain.friendsapp.ui.signup

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.friendsDomain.friendsapp.R

@Composable
@Preview(device = Devices.PIXEL)
fun SignUp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ScreenTitle(R.string.createAccount)
        Spacer(modifier= Modifier.height(16.dp))
        var email by remember {
            mutableStateOf("")
        }
        EmailField(
            value = email,
            onValueChange = {email = it})
        var password by remember {
            mutableStateOf("")
        }
        PasswordField(
            value = password,
            onValueChange = {password = it})
        Spacer(modifier= Modifier.height(8.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
        ) {
            Text(text = stringResource(id = R.string.signUp))
        }
    }
}

@Composable
private fun EmailField(
    value: String,
    onValueChange:(String)-> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        label = {
            Text(text = stringResource(id = R.string.email))
        },
        onValueChange = onValueChange)
}

@Composable
private fun PasswordField(
    value: String,
    onValueChange:(String)-> Unit
) {
    var isVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_visibility),
                    contentDescription = stringResource(id = R.string.toogleVisibility)
                )
            }
        },
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        label = {
            Text(text = stringResource(id = R.string.password))
        },
        onValueChange = onValueChange)
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