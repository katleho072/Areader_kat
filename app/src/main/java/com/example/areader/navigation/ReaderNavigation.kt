package com.example.areader.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.areader.screens.ReaderSplashScreen
import com.example.areader.screens.details.BookDetailsScreen
import com.example.areader.screens.home.Home
import com.example.areader.screens.home.HomeScreenViewModel
import com.example.areader.screens.login.ReaderLoginScreen
import com.example.areader.screens.search.BooksSearchViewModel
import com.example.areader.screens.search.ReaderBookSearchScreen
import com.example.areader.screens.stats.ReaderStatsScreen
import com.example.areader.screens.update.BookUpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {
        composable(ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderStatsScreen.name) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            ReaderStatsScreen(navController = navController, viewModel = homeViewModel)
        }
        composable(ReaderScreens.ReaderHomeScreen.name) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            Home(navController = navController, viewModel = homeViewModel)
            
        }
        composable(ReaderScreens.SearchScreen.name) {
            val viewModel = hiltViewModel<BooksSearchViewModel>()
           ReaderBookSearchScreen(navController = navController, viewModel)

        }
        val detailName = ReaderScreens.DetailsScreen.name
        composable("$detailName/{bookId}", arguments = listOf(navArgument("bookId"){
            type = NavType.StringType
        })) {backStackEntry->
            backStackEntry.arguments?.getString("bookId").let {
                BookDetailsScreen(navController = navController, bookId = it!!)

            }

        }

        val updateName = ReaderScreens.UpdateScreen.name
        composable("$updateName/{booItemId}",
            arguments = listOf(navArgument("bookIteId"){
                type = NavType.StringType
            })
        ){navBackStackEntry ->
            navBackStackEntry.arguments?.getString("bookItemId").let {
                BookUpdateScreen(navController = navController, bookItem = it.toString())
            }

        }
    }
}