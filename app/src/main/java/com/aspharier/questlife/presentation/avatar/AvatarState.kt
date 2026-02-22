package com.aspharier.questlife.presentation.avatar

data class AvatarState(
    val avatarClass: AvatarClass = AvatarClass.WARRIOR,
    val helmetRes: Int? = null,
    val armorRes: Int? = null,
    val weaponRes: Int? = null,
    val accessoryRes: Int? = null,
    val isLevelUp: Boolean = false
)
