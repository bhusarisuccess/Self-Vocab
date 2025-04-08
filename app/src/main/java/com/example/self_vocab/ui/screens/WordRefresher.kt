package com.example.self_vocab.ui.screens

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.self_vocab.di.DatabaseModule.provideDatabase
import java.util.concurrent.TimeUnit


class WordRefresh(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val dao = provideDatabase(applicationContext as Application).wordDao()
//        getDatabase(applicationContext).wordDao()
        val word = dao.getRandomWord() ?: return Result.retry()

        val intent = Intent("WORD_REFRESHED").apply {
            putExtra("text", word.word)
            putExtra("meaning", word.meaning)
            putExtra("sentence", word.sentence)
        }
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)

        return Result.success()
    }
}
fun scheduleWordRefresh(context: Context, intervalMinutes: Long) {
    val workManager = WorkManager.getInstance(context)

    val workRequest = PeriodicWorkRequestBuilder<WordRefresh>(
        intervalMinutes, TimeUnit.MINUTES
    )
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build()
        )
        .build()

    workManager.enqueueUniquePeriodicWork(
        "word_refresh_work",
        ExistingPeriodicWorkPolicy.UPDATE, // Updates existing if already scheduled
        workRequest
    )
}