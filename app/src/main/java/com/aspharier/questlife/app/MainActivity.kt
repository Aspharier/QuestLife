package com.aspharier.questlife.app

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
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkMode by
                    settingsRepository.darkModeEnabled.collectAsState(
                            initial = isSystemInDarkTheme()
                    )
            QuestLifeTheme(darkTheme = isDarkMode) { QuestLifeNavHost() }
        }
    }
}
