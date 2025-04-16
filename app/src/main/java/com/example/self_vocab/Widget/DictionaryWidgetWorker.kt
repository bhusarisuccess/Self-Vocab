package com.example.self_vocab.Widget


import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.self_vocab.Widget.DictionaryWidget
import com.example.self_vocab.data.database.WordDao
import com.example.self_vocab.repository.DatabaseRepository
import com.example.self_vocab.viewmodel.DictionaryViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DictionaryWidgetWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: DatabaseRepository
): CoroutineWorker(context, params) {
    private val wordKey = stringPreferencesKey("word")
    private val meaningKey = stringPreferencesKey("meaning")
    private val sentenceKey = stringPreferencesKey("sentence")
    override suspend fun doWork(): Result {
        val Widget_word =  repository.getRandomWord()?: return Result.success()


        Widget_word?.let {
            val glanceIds = GlanceAppWidgetManager(applicationContext).getGlanceIds(DictionaryWidget::class.java)
            glanceIds.forEach { glanceId ->
                updateAppWidgetState(
                    context = applicationContext,
                    definition = PreferencesGlanceStateDefinition,
                    glanceId = glanceId
                ) { prefs ->
                    prefs.toMutablePreferences().apply {
                        this[wordKey] = it.word
                        this[meaningKey] = it.meaning
                        this[sentenceKey] = it.sentence
                    }
                }

                DictionaryWidget.update(applicationContext, glanceId)
            }
        }

        return Result.success()
    }
}

//    override suspend fun doWork(): Result {
//        Log.d("WidgetWorker", "Worker started")
//        val word = repository.getRandomWord()?: return Result.success()
//        Log.d("DatabaseCheck", "Words in DB: $word")
//        val manager = GlanceAppWidgetManager(applicationContext)
//        val glanceIds = manager.getGlanceIds(DictionaryWidget::class.java)
//
//        glanceIds.forEach { glanceId ->
//            updateAppWidgetState(
//                applicationContext, PreferencesGlanceStateDefinition, glanceId
//            ) { prefs ->
//                prefs.toMutablePreferences().apply {
//                    this[stringPreferencesKey("Widget_word")] = word.word
//                    this[stringPreferencesKey("Widget_meaning")] = word.meaning
//                    this[stringPreferencesKey("Widget_sentence")] = word.sentence
//                }
//            }
//            DictionaryWidget().update(applicationContext, glanceId)
//        }
//
//        return Result.success()
//    }
//}