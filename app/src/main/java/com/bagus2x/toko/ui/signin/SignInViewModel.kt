package com.bagus2x.toko.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagus2x.toko.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SignStateState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.authenticated().collectLatest { authenticated ->
                _state.update { it.copy(authenticated = authenticated) }
            }
        }
    }

    fun setUsername(username: String) {
        _state.update { it.copy(username = username) }
    }

    fun setPassword(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun signIn() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(loading = true) }
                authRepository.signIn(
                    username = state.value.username,
                    password = state.value.password
                )
            } catch (e: Exception) {
                _state.update { it.copy(message = e.message ?: "") }
            }
            _state.update { it.copy(loading = false) }
        }
    }

    fun consumeMessage() {
        _state.update { it.copy(message = "") }
    }
}
