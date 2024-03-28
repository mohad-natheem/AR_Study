package com.example.ar_study.navigator

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ar_study.MainViewModel
import com.example.ar_study.R
import com.example.ar_study.home.HomeScreen
import com.example.ar_study.search.SearchScreen

@Composable
fun BottomNavigator(
    navHostController: NavHostController
) {
    val bottomNavController = rememberNavController()

    val bottomBackStackState by bottomNavController.currentBackStackEntryAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
        },
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(id = R.color.input_background),
                modifier = Modifier
                    .padding(16.dp)
                    .height(70.dp)
                    .clip(RoundedCornerShape(30.dp))

//                    .border(
//                        width = 2.dp,
//                        color = Color.LightGray.copy(alpha = 0.6f),
//                        shape = RoundedCornerShape(30.dp)
//                    ),
                ,
                tonalElevation = 0.dp
            ) {

                items.forEach { item ->
                    val isSelected = item.route == bottomBackStackState?.destination?.route

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            bottomNavController.navigate(item.route) {
                                popUpTo(bottomNavController.graph.startDestinationId) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title,
                                tint = if(isSelected) colorResource(id = R.color.app_primary) else Color.Black.copy(alpha = 0.8f)
                            )
                        },
                        label = {
                            Text(text = item.title)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                                LocalAbsoluteTonalElevation.current
                            )
                        )
                    )
                }

            }
        }
    ) { padding ->
        NavHost(modifier = Modifier.padding(),navController = bottomNavController, startDestination = "home") {
            composable(route = "home") {
                val viewModel = hiltViewModel<MainViewModel>()
                viewModel.reloadList()

                HomeScreen(navHostController=navHostController,bottomNavController =bottomNavController)

            }
            composable(route = "search") {
                val viewModel = hiltViewModel<MainViewModel>()
                viewModel.reloadList()
                SearchScreen(navHostController)

            }
            composable(route = "profile") {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    val viewModel = hiltViewModel<MainViewModel>()
                    viewModel.reloadList()
                    Text("Profile Screen", fontWeight = FontWeight.Bold)
                }

            }
        }

    }

}

data class BottomNavigationItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

val items = listOf(
    BottomNavigationItem(
        route = "home",
        title = "Home",
        selectedIcon = Icons.Rounded.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    BottomNavigationItem(
        route = "search",
        title = "Search",
        selectedIcon = Icons.TwoTone.Search,
        unselectedIcon = Icons.Outlined.Search
    ),
    BottomNavigationItem(
        route = "profile",
        title = "Profile",
        selectedIcon = Icons.Rounded.Person,
        unselectedIcon = Icons.Outlined.Person
    )
)