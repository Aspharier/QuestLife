package com.aspharier.questlife.app.di

import android.content.Context
import androidx.room.Room
import com.aspharier.questlife.data.local.database.QuestLifeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): QuestLifeDatabase {
        return Room.databaseBuilder(
            context,
            QuestLifeDatabase::class.java,
            "questlife.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideHabitDao(db: QuestLifeDatabase) = db.habitDao()

    @Provides
    fun provideCompletionDao(db: QuestLifeDatabase) = db.completionDao()
}