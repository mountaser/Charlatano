/*
 * Moun: Free and open-source (FOSS) cheat for CS:GO/CS:CO
 * Copyright (C) 2017 - Thomas G. P. Nappo, Jonathan Beaudoin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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