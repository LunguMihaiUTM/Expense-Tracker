package com.dog.expensetracker.features.overview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OverviewController(
    viewModel: OverviewViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // collect one-off actions
    LaunchedEffect(Unit) {
        viewModel.action.collectLatest { action ->
            when (action) {
                is OverviewContract.Action.ShowMessage -> {

                }
            }
        }
    }

    OverviewScreen(
        state = state,
        onEvent = { viewModel.sendEvent(it) },
    )
}
