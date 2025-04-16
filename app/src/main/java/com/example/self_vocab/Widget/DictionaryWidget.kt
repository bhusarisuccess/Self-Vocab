package com.example.self_vocab.Widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import androidx.glance.layout.*
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontStyle
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle


private val wordKey = stringPreferencesKey("word")
private val meaningKey = stringPreferencesKey("meaning")
private val sentenceKey = stringPreferencesKey("sentence")

object DictionaryWidget : GlanceAppWidget() {
    override val stateDefinition = PreferencesGlanceStateDefinition
    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        provideContent {
            Content()
        }

    }

    @Composable
    fun Content() {
        val prefs = currentState<Preferences>()
        val word = prefs[wordKey] ?: "Loading..."
        val meaning = prefs[meaningKey] ?: "Please wait"
        val sentence = prefs[sentenceKey] ?: ""

        Column(
            modifier = GlanceModifier.padding(16.dp).fillMaxSize(),
            verticalAlignment = Alignment.Vertical.CenterVertically,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            Text(text = word, style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
            Spacer(modifier = GlanceModifier.height(8.dp))
            Text(text = meaning, style = TextStyle(fontSize = 14.sp))
            Spacer(modifier = GlanceModifier.height(4.dp))
            Text(text = "\"$sentence\"", style = TextStyle(fontSize = 12.sp, fontStyle = FontStyle.Italic))
        }
    }
}
//class DictionaryWidget : GlanceAppWidget() {
//    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition
//
//    override suspend fun provideGlance(context: Context, id: GlanceId) {
//        provideContent {
//            val prefs = currentState<Preferences>()
//            val word = prefs[stringPreferencesKey("Widget_word")] ?: "Loading..."
//            val meaning = prefs[stringPreferencesKey("Widget_meaning")] ?: "Meaning..."
//            val sentence = prefs[stringPreferencesKey("Widget_sentence")] ?: "Sentence..."
//
//            Column(
//                modifier = GlanceModifier.fillMaxSize().padding(12.dp),
//                verticalAlignment = Alignment.Vertical.CenterVertically,
//                horizontalAlignment = Alignment.Horizontal.CenterHorizontally
//            ) {
//                Text(word, style = TextStyle(fontSize = 18.sp))
//                Spacer(GlanceModifier.height(8.dp))
//                Text(meaning, style = TextStyle(fontSize = 14.sp))
//                Spacer(GlanceModifier.height(8.dp))
//                Text(sentence, style = TextStyle(fontSize = 14.sp))
//            }
//        }
//    }
//}



class DictionaryWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget : GlanceAppWidget = DictionaryWidget

}