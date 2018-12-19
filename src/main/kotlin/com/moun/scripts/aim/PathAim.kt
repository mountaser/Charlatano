

package com.moun.scripts.aim

import com.moun.game.entity.isScoped
import com.moun.game.me
import com.moun.settings.*
import com.moun.utils.pathAim

fun pathAim() = aimScript(AIM_DURATION, { ENABLE_PATH_AIM }) { dest, current, aimSpeed ->
	pathAim(current, dest, aimSpeed,
			sensMultiplier = if (me.isScoped()) 1.0 else AIM_STRICTNESS,
			perfect = perfect.getAndSet(false))
}