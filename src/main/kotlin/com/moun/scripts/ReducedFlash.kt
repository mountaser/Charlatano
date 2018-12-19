

package com.moun.scripts

import com.moun.game.CSGO.clientDLL
import com.moun.game.CSGO.csgoEXE
import com.moun.game.entity.Player
import com.moun.game.entity.dead
import com.moun.game.hooks.onFlash
import com.moun.game.netvars.NetVarOffsets.flFlashMaxAlpha
import com.moun.game.offsets.ClientOffsets.dwLocalPlayer
import com.moun.settings.ENABLE_REDUCED_FLASH
import com.moun.settings.FLASH_MAX_ALPHA
import com.moun.utils.extensions.uint

fun reducedFlash() = onFlash {
	if (!ENABLE_REDUCED_FLASH) return@onFlash
	
	val me: Player = clientDLL.uint(dwLocalPlayer)
	if (me > 0 && !me.dead()) csgoEXE[me + flFlashMaxAlpha] = FLASH_MAX_ALPHA
}