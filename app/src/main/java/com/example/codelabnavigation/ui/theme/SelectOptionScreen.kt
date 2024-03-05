package com.example.codelabnavigation.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SelectOptionScreen(
    modifier: Modifier,
    radioButtonList: List<String>,
    currentPrice: String,
    onSelectionChange: (String) -> Unit,
    onNavigateNext: () -> Unit
    ){
    val context = LocalContext.current
    var selectOption by remember {
        mutableStateOf(radioButtonList.get(0))
    }
    Column(
        modifier = modifier,
    ){
        radioButtonList.forEach { eachOption: String ->
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                RadioButton(
                    selected = (selectOption == eachOption),
                    onClick = {
                        selectOption = eachOption
                        onSelectionChange(selectOption)
                    }
                )
                Text(text = eachOption, modifier = Modifier.padding(8.dp))
            }
        }
        Text(
            modifier = Modifier.padding(vertical = 40.dp, horizontal = 10.dp),
            text = "Has seleccionado: $selectOption"
        )

        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "Precio: 0€"
        )
        Button(onClick = { onNavigateNext() }) {
            Text(text = "Siguiente")
        }
    }
}