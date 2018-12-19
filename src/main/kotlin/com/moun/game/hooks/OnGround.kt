

package com.moun.game.hooks

import com.moun.game.CSGO.clientDLL
import com.moun.game.entity.dead
import com.moun.game.entity.onGround
import com.moun.game.offsets.ClientOffsets.dwLocalPlayer
import com.moun.utils.extensions.uint
import com.moun.utils.hook

val onGround = hook(4) {
	val me = clientDLL.uint(dwLocalPlayer)
	me > 0 && !me.dead() && me.onGround()
}