

package com.moun.scripts.aim

import com.moun.settings.AIM_DURATION
import com.moun.settings.ENABLE_FLAT_AIM
import com.moun.utils.writeAim

fun flatAim() = aimScript(AIM_DURATION, { ENABLE_FLAT_AIM }) { dest, current, aimSpeed ->
	writeAim(current, dest, aimSpeed.toDouble())
}