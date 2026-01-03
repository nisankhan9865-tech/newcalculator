package com.example.newcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.newcalculator.ui.theme.NewCalculatorTheme
import com.example.newcalculator.ui.navigation.NavGraph
import com.example.newcalculator.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    // Obtain ViewModel scoped to the activity
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewCalculatorTheme {
                // Pass the same ViewModel instance to the NavGraph so all screens share state
                NavGraph(mainViewModel = mainViewModel)
            }
        }
    }
}
