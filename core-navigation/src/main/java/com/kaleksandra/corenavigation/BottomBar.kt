package com.kaleksandra.corenavigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.kaleksandra.coretheme.Dimen


@Composable
fun BottomBar(
    screens: List<Screen>,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.onBackground,
    ) {
        screens.forEach { item ->
            val selected =
                currentDestination?.hierarchy?.any { it.route == item.route.path } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = {
                    Text(
                        text = stringResource(id = item.resourceId),
                        style = MaterialTheme.typography.bodySmall,
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = stringResource(id = item.resourceId),
                        modifier = Modifier.size(Dimen.size_24)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.onBackground,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}