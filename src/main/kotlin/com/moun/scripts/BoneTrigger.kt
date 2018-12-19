

package com.moun.scripts

import com.moun.game.angle
import com.moun.game.clientState
import com.moun.game.entity.position
import com.moun.game.me
import com.moun.scripts.aim.findTarget
import com.moun.settings.BONE_TRIGGER_BONE
import com.moun.settings.BONE_TRIGGER_FOV
import com.moun.settings.ENABLE_BONE_TRIGGER
import com.moun.settings.FIRE_KEY
import com.moun.utils.*
import org.jire.arrowhead.keyReleased

private val onBoneTriggerTarget = hook(1) {
	if (ENABLE_BONE_TRIGGER) findTarget(me.position(), clientState.angle(), false,
			BONE_TRIGGER_FOV, BONE_TRIGGER_BONE) >= 0
	else false
}

fun boneTrigger() = onBoneTriggerTarget {
	if (keyReleased(FIRE_KEY)) {
		mouse(MOUSEEVENTF_LEFTDOWN)
		Thread.sleep(8 + randLong(16))
		mouse(MOUSEEVENTF_LEFTUP)
	}
}