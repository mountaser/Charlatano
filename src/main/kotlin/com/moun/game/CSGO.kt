

package com.moun.game

import com.moun.game.hooks.constructEntities
import com.moun.game.netvars.NetVars
import com.moun.game.offsets.ClientOffsets.dwLocalPlayer
import com.moun.game.offsets.EngineOffsets.dwClientState
import com.moun.game.offsets.EngineOffsets.dwInGame
import com.moun.overlay.MounOverlay
import com.moun.overlay.MounOverlay.camera
import com.moun.overlay.Overlay
import com.moun.settings.*
import com.moun.utils.every
import com.moun.utils.extensions.uint
import com.moun.utils.natives.CUser32
import com.moun.utils.inBackground
import com.moun.utils.retry
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import org.jire.arrowhead.Module
import org.jire.arrowhead.Process
import org.jire.arrowhead.processByName

object CSGO {
	
	const val ENTITY_SIZE = 16
	const val GLOW_OBJECT_SIZE = 56
	
	lateinit var csgoEXE: Process
		private set
	
	lateinit var clientDLL: Module
		private set
	lateinit var engineDLL: Module
		private set
	
	var gameHeight: Int = 0
		private set
	
	var gameX: Int = 0
		private set
	
	var gameWidth: Int = 0
		private set
	
	var gameY: Int = 0
		private set
	
	fun initialize() {
		retry(128) {
			csgoEXE = processByName(PROCESS_NAME, PROCESS_ACCESS_FLAGS)!!
		}
		
		retry(128) {
			csgoEXE.loadModules()
			clientDLL = csgoEXE.modules[CLIENT_MODULE_NAME]!!
			engineDLL = csgoEXE.modules[ENGINE_MODULE_NAME]!!
		}
		
		val rect = WinDef.RECT()
		val hwd = CUser32.FindWindowA(null, "Counter-Strike: "
				+ (if (CLASSIC_OFFENSIVE) "Classic" else "Global") + " Offensive")
		
		var lastWidth = 0
		var lastHeight = 0
		var lastX = 0
		var lastY = 0
		
		every(1000) {
			if (!CUser32.GetClientRect(hwd, rect)) System.exit(2)
			gameWidth = rect.right - rect.left
			gameHeight = rect.bottom - rect.top
			
			if (!CUser32.GetWindowRect(hwd, rect)) System.exit(3)
			gameX = rect.left + (((rect.right - rect.left) - gameWidth) / 2)
			gameY = rect.top + ((rect.bottom - rect.top) - gameHeight)
			
			if (Overlay.opened && (lastX != gameX || lastY != gameY))
				User32.INSTANCE.MoveWindow(Overlay.hwnd, gameX, gameY, gameWidth, gameHeight, false)
			
			if (Overlay.opened && MounOverlay.created && (lastWidth != gameWidth || lastHeight != gameHeight))
				camera.setToOrtho(true, gameWidth.toFloat(), gameHeight.toFloat())
			
			lastWidth = gameWidth
			lastHeight = gameHeight
			lastX = gameX
			lastY = gameY
		}
		every(1024, continuous = true) {
			inBackground = Pointer.nativeValue(hwd.pointer) != CUser32.GetForegroundWindow()
			if (inBackground) return@every
		}
		
		NetVars.load()
		
		retry(16) {
			var myAddress = clientDLL.uint(dwLocalPlayer)
			if (myAddress <= 0) {
				dwLocalPlayer = dwLocalPlayer + 0x1C // can't do dwLocalPlayer += 0x1C because of compiler bug...
				myAddress = clientDLL.uint(dwLocalPlayer)
			}
			
			val enginePointer = engineDLL.uint(dwClientState)
			val inGame = csgoEXE.int(enginePointer + dwInGame) == 6
			inBackground = !inGame || myAddress <= 0
		}
		
		constructEntities()
	}
	
}
