

package com.moun.game.hooks

import com.moun.game.CSGO.clientDLL
import com.moun.game.CSGO.csgoEXE
import com.moun.game.entity.Player
import com.moun.game.entity.dead
import com.moun.game.netvars.NetVarOffsets.flFlashMaxAlpha
import com.moun.game.offsets.ClientOffsets.dwLocalPlayer
import com.moun.utils.extensions.uint
import com.moun.utils.hook

val onFlash = hook(256) {
	val me = clientDLL.uint(dwLocalPlayer)
	if (me > 0 && !me.dead()) csgoEXE.float(me + flFlashMaxAlpha) > 0F
	else false
}