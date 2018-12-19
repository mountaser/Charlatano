

package com.moun.scripts

import com.moun.game.entity.EntityType
import com.moun.game.forEntities
import com.moun.game.me
import com.moun.settings.TRIGGER_DURATION
import com.moun.utils.every

fun trigger() = every(TRIGGER_DURATION) {
	forEntities {
		val entity = it.entity
		if (entity <= 0 || me == entity || it.type != EntityType.CCSPlayer) return@forEntities
		
		
	}
}