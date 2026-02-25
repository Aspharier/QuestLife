package com.aspharier.questlife.app.di

import android.content.Context
import androidx.room.Room
import com.aspharier.questlife.data.local.database.QuestLifeDatabase
import com.aspharier.questlife.data.local.entities.EquipmentEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): QuestLifeDatabase {
        val db =
                Room.databaseBuilder(context, QuestLifeDatabase::class.java, "questlife.db")
                        .fallbackToDestructiveMigration()
                        .build()

        // Seed default equipment on first launch
        CoroutineScope(Dispatchers.IO).launch {
            val dao = db.equipmentDao()
            if (dao.getCount() == 0) {
                dao.insertAll(defaultEquipment())
            }
        }

        return db
    }

    @Provides fun provideHabitDao(db: QuestLifeDatabase) = db.habitDao()

    @Provides fun provideCompletionDao(db: QuestLifeDatabase) = db.completionDao()

    @Provides fun provideEquipmentDao(db: QuestLifeDatabase) = db.equipmentDao()

    private fun defaultEquipment(): List<EquipmentEntity> =
            listOf(
                    // ── HELMETS ──────────────────────────────────────────────────
                    EquipmentEntity(
                            "helm_iron",
                            "HELMET",
                            "Iron Helm",
                            "COMMON",
                            "DEF",
                            5,
                            1,
                            true,
                            false,
                            0
                    ),
                    EquipmentEntity(
                            "helm_silver",
                            "HELMET",
                            "Silver Helm",
                            "RARE",
                            "DEF",
                            12,
                            5,
                            false,
                            false,
                            0
                    ),
                    EquipmentEntity(
                            "helm_arcane",
                            "HELMET",
                            "Arcane Crown",
                            "EPIC",
                            "MANA",
                            20,
                            10,
                            false,
                            false,
                            0
                    ),
                    // ── ARMOR ────────────────────────────────────────────────────
                    EquipmentEntity(
                            "armor_leather",
                            "ARMOR",
                            "Leather Armor",
                            "COMMON",
                            "HP",
                            20,
                            1,
                            true,
                            false,
                            0
                    ),
                    EquipmentEntity(
                            "armor_chain",
                            "ARMOR",
                            "Chain Mail",
                            "RARE",
                            "DEF",
                            18,
                            5,
                            false,
                            false,
                            0
                    ),
                    EquipmentEntity(
                            "armor_dragon",
                            "ARMOR",
                            "Dragon Scale",
                            "LEGENDARY",
                            "HP",
                            50,
                            15,
                            false,
                            false,
                            0
                    ),
                    // ── WEAPONS ──────────────────────────────────────────────────
                    EquipmentEntity(
                            "wpn_sword",
                            "WEAPON",
                            "Iron Sword",
                            "COMMON",
                            "ATK",
                            8,
                            1,
                            true,
                            false,
                            0
                    ),
                    EquipmentEntity(
                            "wpn_axe",
                            "WEAPON",
                            "Battle Axe",
                            "RARE",
                            "ATK",
                            20,
                            5,
                            false,
                            false,
                            0
                    ),
                    EquipmentEntity(
                            "wpn_staff",
                            "WEAPON",
                            "Mage Staff",
                            "EPIC",
                            "MANA",
                            25,
                            10,
                            false,
                            false,
                            0
                    ),
                    // ── ACCESSORIES ──────────────────────────────────────────────
                    EquipmentEntity(
                            "acc_ring",
                            "ACCESSORY",
                            "Lucky Ring",
                            "COMMON",
                            "LUCK",
                            5,
                            1,
                            true,
                            false,
                            0
                    ),
                    EquipmentEntity(
                            "acc_amulet",
                            "ACCESSORY",
                            "Mana Amulet",
                            "RARE",
                            "MANA",
                            15,
                            5,
                            false,
                            false,
                            0
                    ),
                    EquipmentEntity(
                            "acc_legendary",
                            "ACCESSORY",
                            "Fate Pendant",
                            "LEGENDARY",
                            "LUCK",
                            30,
                            20,
                            false,
                            false,
                            0
                    )
            )
}
