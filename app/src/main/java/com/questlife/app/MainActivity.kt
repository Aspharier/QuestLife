package com.questlife.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.questlife.core.ui.theme.QuestLifeTheme
import com.questlife.app.navigation.QuestLifeNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuestLifeTheme {
                QuestLifeNavHost()
            }
        }
    }
}
