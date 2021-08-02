package com.friendsDomain.friendsapp.ui.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.friendsDomain.friendsapp.R

@Composable
@Preview(device = Devices.PIXEL)
fun SignUp() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Create an Account")
        OutlinedTextField(
            value = "",
            label = {
                Text(text = stringResource(id = R.string.email))
            },
            onValueChange = {})
        OutlinedTextField(
            value = "",
            label = {
                Text(text = stringResource(id = R.string.password))
            },
            onValueChange = {})
        Button(
            onClick = {},
        ) {
            Text(text = stringResource(id = R.string.signUp))
        }
    }
}