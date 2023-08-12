package com.bagus2x.toko.ui.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bagus2x.toko.BuildConfig
import com.bagus2x.toko.R
import com.bagus2x.toko.ui.main.LocalSnackbar
import com.bagus2x.toko.ui.theme.TokoTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignInScreen(
    viewModel: SignInViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    SignInScreen(
        stateProvider = { state },
        setUsername = viewModel::setUsername,
        setPassword = viewModel::setPassword,
        signIn = viewModel::signIn,
        navigateToHomeScreen = {
            navController.navigate("home") {
                popUpTo("signin") {
                    inclusive = true
                }
            }
        },
        consumeMessage = viewModel::consumeMessage
    )
}

@Composable
fun SignInScreen(
    stateProvider: () -> SignStateState,
    setUsername: (String) -> Unit,
    setPassword: (String) -> Unit,
    signIn: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    consumeMessage: () -> Unit
) {
    val snackbar = LocalSnackbar.current
    LaunchedEffect(Unit) {
        snapshotFlow { stateProvider() }.collectLatest { state ->
            if (state.message != "") {
                snackbar.show(state.message)
                consumeMessage()
            }
            if (state.authenticated) {
                navigateToHomeScreen()
            }
        }
    }

    Box {
        val state = stateProvider()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.bg_wave),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .rotate(180f),
            )
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = state.username,
                    onValueChange = setUsername,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(text = stringResource(R.string.text_username))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    )
                )
                Spacer(modifier = Modifier.height(32.dp))
                OutlinedTextField(
                    value = state.password,
                    onValueChange = setPassword,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(text = stringResource(R.string.text_password))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Lock,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = false,
                        onCheckedChange = {},
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = MaterialTheme.colors.primary,
                            checkedColor = MaterialTheme.colors.primary
                        )
                    )
                    Text(
                        text = stringResource(R.string.text_keep_username),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.primary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Rounded.Download,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.text_check_update),
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(48.dp))
                Button(
                    onClick = signIn,
                    enabled = !state.loading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.text_login)
                    )
                }
                Spacer(modifier = Modifier.height(48.dp))
                Text(text = "App Ver ${BuildConfig.VERSION_NAME}")
            }
            Image(
                painter = painterResource(R.drawable.bg_wave),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (state.loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignInScreen() {
    TokoTheme {
        SignInScreen(
            stateProvider = { SignStateState() },
            setUsername = {},
            setPassword = {},
            signIn = {},
            navigateToHomeScreen = {},
            consumeMessage = {}
        )
    }
}
