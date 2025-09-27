package com.dog.expensetracker.features.home

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import com.dog.expensetracker.navigation.LocalRootNavigator

@Composable
fun HomeController(
    viewModel: HomeViewModel = hiltViewModel(),
    // optional nav param: use your app nav controller pattern
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalRootNavigator.current // or pass navController in
    // Collect one-off actions
    LaunchedEffect(Unit) {
        viewModel.action.collectLatest { action ->
            when (action) {
                is HomeContract.Action.ShowMessage -> {
                }
                is HomeContract.Action.NavigateToOverview -> {
                    // navController.navigate("overview") OR typed navigation
                }
            }
        }
    }
    HomeScreen(
        state = state,
        onEvent = { viewModel.sendEvent(it) }
    )
}
