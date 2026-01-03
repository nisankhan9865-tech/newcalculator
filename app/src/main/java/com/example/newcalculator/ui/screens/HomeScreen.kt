package com.example.newcalculator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.newcalculator.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel) {
    // Observe LiveData from ViewModel
    val expression by viewModel.expression.observeAsState("")
    val result by viewModel.result.observeAsState("")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calculator") },
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Display area
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = expression,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = result,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    textAlign = TextAlign.End
                )
            }

            // Buttons grid
            val buttonSpacing = 8.dp
            val buttonModifier = Modifier
                .weight(1f)
                .fillMaxWidth()

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                // First row: C, DEL, %, /
                Row(
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(text = "C", onClick = { viewModel.onClear() }, modifier = buttonModifier, backgroundColor = Color.LightGray)
                    CalculatorButton(text = "DEL", onClick = { viewModel.onDelete() }, modifier = buttonModifier, backgroundColor = Color.LightGray)
                    CalculatorButton(text = "%", onClick = { viewModel.onAppend("%") }, modifier = buttonModifier, backgroundColor = Color.LightGray)
                    CalculatorButton(text = "/", onClick = { viewModel.onAppend("/") }, modifier = buttonModifier, backgroundColor = MaterialTheme.colorScheme.secondary)
                }
                // Second row: 7,8,9, *
                Row(
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(text = "7", onClick = { viewModel.onAppend("7") }, modifier = buttonModifier)
                    CalculatorButton(text = "8", onClick = { viewModel.onAppend("8") }, modifier = buttonModifier)
                    CalculatorButton(text = "9", onClick = { viewModel.onAppend("9") }, modifier = buttonModifier)
                    CalculatorButton(text = "*", onClick = { viewModel.onAppend("*") }, modifier = buttonModifier, backgroundColor = MaterialTheme.colorScheme.secondary)
                }
                // Third row: 4,5,6, -
                Row(
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(text = "4", onClick = { viewModel.onAppend("4") }, modifier = buttonModifier)
                    CalculatorButton(text = "5", onClick = { viewModel.onAppend("5") }, modifier = buttonModifier)
                    CalculatorButton(text = "6", onClick = { viewModel.onAppend("6") }, modifier = buttonModifier)
                    CalculatorButton(text = "-", onClick = { viewModel.onAppend("-") }, modifier = buttonModifier, backgroundColor = MaterialTheme.colorScheme.secondary)
                }
                // Fourth row: 1,2,3, +
                Row(
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(text = "1", onClick = { viewModel.onAppend("1") }, modifier = buttonModifier)
                    CalculatorButton(text = "2", onClick = { viewModel.onAppend("2") }, modifier = buttonModifier)
                    CalculatorButton(text = "3", onClick = { viewModel.onAppend("3") }, modifier = buttonModifier)
                    CalculatorButton(text = "+", onClick = { viewModel.onAppend("+") }, modifier = buttonModifier, backgroundColor = MaterialTheme.colorScheme.secondary)
                }
                // Fifth row: 0, ., =
                Row(
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(text = "0", onClick = { viewModel.onAppend("0") }, modifier = buttonModifier.weight(2f))
                    CalculatorButton(text = ".", onClick = { viewModel.onAppend(".") }, modifier = buttonModifier)
                    CalculatorButton(text = "=", onClick = { viewModel.onCalculate() }, modifier = buttonModifier, backgroundColor = MaterialTheme.colorScheme.tertiary)
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.height(64.dp)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
