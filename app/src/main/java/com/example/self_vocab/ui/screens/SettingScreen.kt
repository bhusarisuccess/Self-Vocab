package com.example.self_vocab.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.self_vocab.Widget.DictionaryWidgetWorker
import com.example.self_vocab.ui.theme.PrimaryColor
import com.example.self_vocab.viewmodel.DictionaryViewModel
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavHostController) {
    Scaffold(
        content = { paddingValues ->
            SettingContent(paddingValues)
        }
    )
}


@Composable
fun SettingContent(paddingValues: PaddingValues) {
    val viewModel: DictionaryViewModel = hiltViewModel()
    var selectedinterval by remember { mutableStateOf<Long?>(null) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimeIntervalSetting(onTimeSelected = { selectedinterval = it })
    }

}


@Composable
fun TimeIntervalSetting(onTimeSelected: (Long) -> Unit) {
    val timeOptions = listOf(1L, 15L, 30L, 60L, 120L) // in minutes
    var expanded by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf<Long?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "Select refresh interval:",
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box {
            TextButton(
                onClick = { expanded = true },
                modifier = Modifier
                    .background(color = PrimaryColor)
                    .border(color = PrimaryColor, shape = RoundedCornerShape(15.dp), width = 2.dp),
                shape = RoundedCornerShape(15.dp),

                ) {
                Text(selectedTime?.toString() ?: "Choose time", color = Color.Black)
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                timeOptions.forEach { time ->
                    DropdownMenuItem(
                        text = { Text("$time minutes") },
                        onClick = {
                            selectedTime = time
                            expanded = false
                            onTimeSelected(time)
                        }
                    )
                }
            }
        }
    }
}



//fun saveIntervalToPrefs(context: Context, minutes: Long) {
//    val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
//    prefs.edit().putLong("interval_minutes", minutes).apply()
//}


