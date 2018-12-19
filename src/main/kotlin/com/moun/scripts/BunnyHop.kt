

package com.moun.scripts

import com.moun.game.CSGO.clientDLL
import com.moun.game.hooks.onGround
import com.moun.game.offsets.ClientOffsets.dwForceJump
import com.moun.settings.BUNNY_HOP_KEY
import com.moun.settings.ENABLE_BUNNY_HOP
import com.moun.settings.LEAGUE_MODE
import com.moun.utils.*
import org.jire.arrowhead.keyPressed

fun bunnyHop() = onGround {
	if (ENABLE_BUNNY_HOP && keyPressed(BUNNY_HOP_KEY)) {
		randScroll()
		Thread.sleep(8 + randLong(10))
		randScroll()
	}
}

private fun randScroll() {
	Thread.sleep(randLong(1, 4))
	if (LEAGUE_MODE) {
		val amount = randInt(60) + 10
		mouseWheel(if (randBoolean()) amount else -amount)
	} else {
		clientDLL[dwForceJump] = 5.toByte()
		Thread.sleep(randLong(20, 30))
		clientDLL[dwForceJump] = 4.toByte()
	}
}