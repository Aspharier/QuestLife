package com.aspharier.questlife.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aspharier.questlife.navigation.QuestLifeNavHost
import com.aspharier.questlife.core.ui.theme.QuestLifeTheme
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