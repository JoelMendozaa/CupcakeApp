package com.example.codelabnavigation.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.codelabnavigation.data.OrderUiState

@Composable
fun EjemploPantalla(
    orderUiState: OrderUiState,
    onVavigateBack: () -> Unit
){

    Column (){
        Text(text = "Hola tienes ${orderUiState.quantity} cupcakes en el carrito")
        Button(onClick = { onVavigateBack() }) {
            Text(text = "Volver")
        }

    }
}
