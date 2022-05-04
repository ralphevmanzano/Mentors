package com.ralphevmanzano.apps.mindfichallenge.di

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ralphevmanzano.apps.details.navigation.DetailsNavigation
import com.ralphevmanzano.apps.home.navigation.HomeNavigation
import com.ralphevmanzano.apps.mindfichallenge.R
import com.ralphevmanzano.apps.mindfichallenge.navigation.DetailsNavigator
import com.ralphevmanzano.apps.mindfichallenge.navigation.HomeNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object NavControllerModule {

    @Provides
    fun navController(activity: FragmentActivity): NavController {
        return NavHostFragment.findNavController(activity.supportFragmentManager.findFragmentById(R.id.nav_host)!!)
    }
}

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigatorModule {

    @Binds
    abstract fun homeNavigator(navigator: HomeNavigator): HomeNavigation

    @Binds
    abstract fun detailsNavigator(navigator: DetailsNavigator): DetailsNavigation

    // TODO add other navigations here
}