package com.example.self_vocab.utility

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.self_vocab.R


object NotificationUtils {

    fun showWordAddedNotification(context: Context?, word: String) {
        if (context == null) {
            Log.e("NotificationUtils", "Context is null, skipping notification")
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notification = NotificationCompat.Builder(context, "word_channel_id")
            .setSmallIcon(R.drawable.dictionary) // Use a real icon in your app
            .setContentTitle("New Word Added").setContentText("You added: $word")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), notification.build())
        }
    }
}