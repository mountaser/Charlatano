

package com.moun.overlay

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.moun.game.CSGO.gameHeight
import com.moun.game.CSGO.gameWidth
import com.moun.game.CSGO.gameX
import com.moun.game.CSGO.gameY
import com.moun.overlay.transparency.TransparencyApplier
import com.moun.overlay.transparency.win10.Win10TransparencyApplier
import com.moun.overlay.transparency.win7.Win7TransparencyApplier
import com.moun.settings.OPENGL_FPS
import com.moun.settings.OPENGL_MSAA_SAMPLES
import com.moun.settings.OPENGL_VSYNC
import com.moun.utils.randLong
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef

object Overlay {
	
	@Volatile var opened = false
	
	lateinit var hwnd: WinDef.HWND
	
	fun open() = LwjglApplicationConfiguration().apply {
		width = gameWidth
		height = gameHeight
		title = randLong(Long.MAX_VALUE).toString()
		x = gameX
		y = gameY
		resizable = false
		fullscreen = false
		vSyncEnabled = OPENGL_VSYNC
		if (OPENGL_MSAA_SAMPLES > 0)
			samples = OPENGL_MSAA_SAMPLES
		
		foregroundFPS = OPENGL_FPS
		backgroundFPS = OPENGL_FPS
		
		LwjglApplication(MounOverlay, this)
		
		do {
			val hwnd = User32.INSTANCE.FindWindow(null, title)
			if (hwnd != null) {
				Overlay.hwnd = hwnd
				break
			}
			Thread.sleep(64) // decreased so it won't go black as long
		} while (!Thread.interrupted())
		
		// sets up window to be fullscreen, click-through, etc.
		WindowCorrector.setupWindow(hwnd)
		
		
		// sets up the full transparency of the Window (only Windows 7 and 10 can do this)
		val transparencyApplier: TransparencyApplier =
				if (System.getProperty("os.name").contains("windows 10", ignoreCase = true))
					Win10TransparencyApplier
				else
					Win7TransparencyApplier // will only work on Windows 7 or early Windows 10 builds
		transparencyApplier.applyTransparency(hwnd)
		
		opened = true
	}
	
	init {
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true")
	}
	
}