package com.dog.expensetracker.features.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.dog.expensetracker.ui.global.GlobalContract
import com.dog.expensetracker.ui.global.GlobalViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeController(
    viewModel: GlobalViewModel = hiltViewModel(),
    // optional nav param: use your app nav controller pattern
) {
    val state by viewModel.state.collectAsState()
    // Collect one-off actions
    LaunchedEffect(Unit) {
        viewModel.action.collectLatest { action ->
            when (action) {
                is GlobalContract.Action.ShowMessage -> {
                }
            }
        }
    }
    HomeScreen(
        state = state,
        onEvent = { viewModel.sendEvent(it) }
    )
}
