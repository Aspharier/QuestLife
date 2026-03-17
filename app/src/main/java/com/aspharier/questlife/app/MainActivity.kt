package com.aspharier.questlife.app

import androidx.activity.enableEdgeToEdge
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.aspharier.questlife.core.navigation.QuestLifeNavHost
import com.aspharier.questlife.core.ui.theme.QuestLifeTheme
import com.aspharier.questlife.domain.repository.SettingsRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var settingsRepository: SettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val themeType by
                    settingsRepository.themeType.collectAsState(
                            initial = com.aspharier.questlife.core.ui.theme.ThemeType.DEEP_DARK
                    )
            QuestLifeTheme(themeType = themeType) { QuestLifeNavHost() }
        }
    }
}
