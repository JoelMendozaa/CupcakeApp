package com.example.codelabnavigation

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.codelabnavigation.data.DataSource
import com.example.codelabnavigation.ui.theme.EjemploPantalla
import com.example.codelabnavigation.ui.theme.OrderViewModel
import com.example.codelabnavigation.ui.theme.SelectOptionScreen
import com.example.codelabnavigation.ui.theme.StartOrderScreen
import com.example.codelabnavigation.ui.theme.SummaryScreen


enum class CupcakeScreen() {
    Start,
    Flavor,
    Pickup,
    Summary
}

@Composable
fun CupCakeApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold() { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = CupcakeScreen.Start.name) {
                StartOrderScreen(
                    quantityOptions = DataSource.quantityOptions,
                    onNextButtonClicked = { cupcakeQuantity ->
                        viewModel.setQuantity(cupcakeQuantity)
                        navController.navigate(CupcakeScreen.Flavor.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(route = CupcakeScreen.Flavor.name) {
                val context = LocalContext.current
                SelectOptionScreen(
                    modifier = Modifier.fillMaxSize(),
                    radioButtonList = DataSource.flavors.map { each -> context.getString(each) },
                    currentPrice = uiState.price,
                    onSelectionChange = {
                        flavor -> viewModel.setFlavor(flavor)
                    }) {
                    navController.navigate(CupcakeScreen.Pickup.name)
                }
            }
            composable(route = CupcakeScreen.Pickup.name) {
                SelectOptionScreen(
                    modifier = Modifier.fillMaxSize(),
                    radioButtonList = uiState.pickupOptions,
                    currentPrice = uiState.price,
                    onSelectionChange = {
                        date -> viewModel.setDate(date)
                    }) {
                    navController.navigate(CupcakeScreen.Summary.name)
                }
            }
            composable(route = CupcakeScreen.Summary.name){
                val context = LocalContext.current
                SummaryScreen(
                    modifier = Modifier.fillMaxHeight(),
                    orderUiState = uiState
                ){
                    subject: String, summary: String ->
                        shareOrder(context, subject = subject, summary = summary)
                }
            }
        }
    }
}

private fun shareOrder(context: Context, subject: String, summary: String) {
    // Create an ACTION_SEND implicit intent with order details in the intent extras
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_cupcake_order)
        )
    )
}