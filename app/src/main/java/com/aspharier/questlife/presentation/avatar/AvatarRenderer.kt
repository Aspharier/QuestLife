package com.aspharier.questlife.presentation.avatar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aspharier.questlife.domain.model.Equipment
import com.questlife.app.R

@Composable
fun AvatarRenderer(
        state: AvatarState,
        modifier: Modifier = Modifier,
        equippedItems: List<Equipment> = emptyList()
) {
    val idleScale = rememberIdleAnimation()
    val victoryScale = rememberVictoryAnimation(state.isLevelUp)

    val finalScale = if (state.isLevelUp) victoryScale else idleScale

    Box(modifier = modifier.size(140.dp).scale(finalScale)) {

        // BASE LAYER
        Image(
                painter = painterResource(id = getBaseRes(state.avatarClass)),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
        )

        // EQUIPMENT LAYERS — drawn in order (helmet → armor → weapon → accessory)
        equippedItems.forEach { item ->
            if (item.spriteRes != 0) {
                Image(
                        painter = painterResource(id = item.spriteRes),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

private fun getBaseRes(avatarClass: AvatarClass): Int {
    return when (avatarClass) {
        AvatarClass.WARRIOR -> R.drawable.avatar_warrior
        AvatarClass.MAGE -> R.drawable.avatar_mage
        AvatarClass.ROGUE -> R.drawable.avatar_rogue
        AvatarClass.HEALER -> R.drawable.avatar_healer
        AvatarClass.RANGER -> R.drawable.avatar_ranger
    }
}
