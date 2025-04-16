package com.example.self_vocab

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.glance.GlanceId
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.self_vocab.Widget.DictionaryWidget
import com.example.self_vocab.Widget.DictionaryWidgetWorker
import com.example.self_vocab.navigation.NavigationGraph
import com.example.self_vocab.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val request = PeriodicWorkRequestBuilder<DictionaryWidgetWorker>(15, TimeUnit.MINUTES)
            .setConstraints(Constraints.Builder().setRequiresBatteryNotLow(true).build())
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "widget_update",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )

        createNotificationChannel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this, arrayOf(POST_NOTIFICATIONS), 1001
            )
        }
        enableEdgeToEdge()
        setContent {
            AppTheme {
                NavigationGraph()
            }
        }
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val name = "Word Channel"
        val descriptionText = "Channel for word notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("word_channel_id", name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
