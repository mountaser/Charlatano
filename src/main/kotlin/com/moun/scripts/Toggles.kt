

package com.moun.scripts

import com.moun.game.*
import com.moun.scripts.*
import com.moun.settings.*
import com.moun.utils.*
import org.jire.arrowhead.keyPressed

fun Toggles_AIM() = every(10) {
	if (keyPressed(0x12) && keyPressed(TOGGLE_KEY_AIM)) {
		ENABLE_AIM = !ENABLE_AIM
		do {
			Thread.sleep(25)
		} while (keyPressed(TOGGLE_KEY_AIM))
	}
}

fun Toggles_BUNNYHOP() = every(10) {
	if (keyPressed(0x12) && keyPressed(TOGGLE_KEY_BUNNYHOP)) {
		ENABLE_BUNNY_HOP = !ENABLE_BUNNY_HOP
		do {
			Thread.sleep(25)
		} while (keyPressed(TOGGLE_KEY_BUNNYHOP))
	}
}

fun Toggles_ESP() = every(10) {
	if (keyPressed(0x12) && keyPressed(TOGGLE_KEY_ESP)) {
		ENABLE_ESP = !ENABLE_ESP
		do {
			Thread.sleep(25)
		} while (keyPressed(TOGGLE_KEY_ESP))
	}
}

fun Toggles_RCS() = every(10) {
	if (keyPressed(0x12) && keyPressed(TOGGLE_KEY_RCS)) {
		ENABLE_RCS = !ENABLE_RCS
		do {
			Thread.sleep(25)
		} while (keyPressed(TOGGLE_KEY_RCS))
	}
}

fun Toggles_TRIGGER() = every(10) {
	if (keyPressed(0x12) && keyPressed(TOGGLE_KEY_BONETRIGGER)) {
		ENABLE_BONE_TRIGGER = !ENABLE_BONE_TRIGGER
		do {
			Thread.sleep(25)
		} while (keyPressed(TOGGLE_KEY_BONETRIGGER))
	}
}