package com.dog.expensetracker.ui.bottom_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dog.expensetracker.ui.common.AddTransactionDialog
import com.dog.expensetracker.ui.global.GlobalContract


@Composable
fun CustomBottomNavigationBar(
    modifier: Modifier,
    selectedTab: BottomNavTab,
    onTabSelected: (BottomNavTab) -> Unit = {},
    onEvent: (GlobalContract.Event) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BottomNavigationBarItem(
            modifier = Modifier.weight(1f),
            item = BottomNavTab.HOME,
            isSelected = selectedTab == BottomNavTab.HOME,
            onSelect = { onTabSelected(BottomNavTab.HOME) }
        )

        BottomNavigationBarItem(
            modifier = Modifier.weight(1f),
            item = BottomNavTab.OVERVIEW,
            isSelected = selectedTab == BottomNavTab.OVERVIEW,
            onSelect = { onTabSelected(BottomNavTab.OVERVIEW) }
        )

        var showDialog by remember { mutableStateOf(false) }


        Box(
            Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            FloatingActionButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .offset(y = (-12).dp),
                containerColor = Color(0xFF6C5CE7),
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Transaction")
            }
        }


        BottomNavigationBarItem(
            modifier = Modifier.weight(1f),
            item = BottomNavTab.HOME,
            isSelected = selectedTab == BottomNavTab.HOME,
            onSelect = { onTabSelected(BottomNavTab.HOME) }
        )

        BottomNavigationBarItem(
            modifier = Modifier.weight(1f),
            item = BottomNavTab.HOME,
            isSelected = selectedTab == BottomNavTab.HOME,
            onSelect = { onTabSelected(BottomNavTab.HOME) }
        )


        if (showDialog) {
            AddTransactionDialog(
                onDismiss = { showDialog = false },
                onSave = { expense ->
                    onEvent(GlobalContract.Event.AddExpense(expense))
                    showDialog = false
                }
            )
        }
    }
}


@Composable
private fun BottomNavigationBarItem(
    modifier: Modifier,
    item: BottomNavTab,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val iconResId = when (item) {
        BottomNavTab.HOME -> Icons.Default.Home
        BottomNavTab.OVERVIEW -> Icons.Filled.BarChart
    }

    val color = if (isSelected) Color(0xFF6C5CE7) else Color.Gray



    Row(
        modifier = modifier
            .clickable { onSelect() }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,

        ) {
        Icon(
            imageVector = iconResId,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = color
        )
    }


}