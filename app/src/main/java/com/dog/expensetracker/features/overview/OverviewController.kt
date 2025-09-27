package com.dog.expensetracker.features.overview

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import androidx.navigation.NavHostController
import com.dog.expensetracker.navigation.LocalRootNavigator

@Composable
fun OverviewController(
    viewModel: OverviewViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalRootNavigator.current

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
        navController = navController
    )
}
