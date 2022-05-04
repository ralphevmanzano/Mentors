package com.ralphevmanzano.apps.mindfichallenge.navigation

import androidx.navigation.NavController
import com.ralphevmanzano.apps.home.navigation.HomeNavigation
import com.ralphevmanzano.apps.mindfichallenge.NavMainDirections
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class HomeNavigator @Inject constructor(
    private val navController: NavController
) : HomeNavigation {

    override fun navigateToDetails(login: String) {
        navController.navigate(NavMainDirections.actionHomeToDetails(login))
    }

}