package com.horizon.service.serviceapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.horizon.service.serviceapplication.model.FeaturedItemData
import com.horizon.service.serviceapplication.network.RetrofitApiService
import com.horizon.service.serviceapplication.screens.FeaturedProductScreen
import com.horizon.service.serviceapplication.screens.HelpSupportScreen
import com.horizon.service.serviceapplication.screens.HomeScreen
import com.horizon.service.serviceapplication.screens.ServiceRequestScreen
import com.horizon.service.serviceapplication.utils.showCustomToast
import com.horizon.service.serviceapplication.viewModels.ServiceRequestViewModel
import com.horizon.service.serviceapplication.viewModels.ServiceRequestViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController) }

        composable("serviceRequest") { backStackEntry ->
            // Get the product items from previous screen
            val item = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<List<FeaturedItemData>>("data")

            if (item != null) {
                // Create your Retrofit service
                val apiService = RetrofitApiService()

                // Create the ViewModel with custom factory
                val viewModel: ServiceRequestViewModel = viewModel(
                    factory = ServiceRequestViewModelFactory(apiService)
                )

                // Pass the ViewModel to the screen
                ServiceRequestScreen(
                    navController = navController,
                    productItem = item,
                    viewModel = viewModel
                )
            }
        }


        composable("featuredProduct") {
            val promoItem = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<FeaturedItemData>("promoItem")

            if (promoItem != null) {
                FeaturedProductScreen(
                    navController = navController,
                    productName = promoItem.name.toString(),
                    productDesc = promoItem.description.toString(),
                    manufacturer = promoItem.manufact.toString(),
                    warranty = promoItem.warranty.toString(),
                    supportAvailable = true,
                    contactNumber = promoItem.phone.toString(),
                    imageResId = promoItem.image.toString()
                )
            }
        }

        composable("support") { HelpSupportScreen(navController, context) }

    }
}
