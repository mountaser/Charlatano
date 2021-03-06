

@file:JvmName("Moun")

package com.moun

import com.moun.game.CSGO
import com.moun.overlay.Overlay
import com.moun.scripts.*
import com.moun.scripts.aim.flatAim
import com.moun.scripts.aim.pathAim
import com.moun.scripts.esp.esp
import com.moun.settings.*
import com.moun.settings.ENABLE_AIM
import com.moun.settings.ENABLE_BUNNY_HOP
import com.moun.settings.ENABLE_RCS
import com.moun.settings.ENABLE_BONE_TRIGGER
import com.moun.utils.Dojo
import com.sun.jna.platform.win32.WinNT
import org.jetbrains.kotlin.cli.common.environment.setIdeaIoUseFallback
import java.io.File
import java.io.FileReader
import java.util.*

const val SETTINGS_DIRECTORY = "settings"

fun main(args: Array<String>) {
	loadSettings()
	
	CSGO.initialize()
	
	bunnyHop()
	rcs()
	esp()
	flatAim()
	pathAim()
	boneTrigger()
	reducedFlash()
	bombTimer()
	
 	Toggles_AIM()
 	Toggles_BUNNYHOP()
 	Toggles_ESP()
 	Toggles_RCS()
 	Toggles_TRIGGER()
	
	if (LEAGUE_MODE) {
		GLOW_ESP = false
		BOX_ESP = false
		SKELETON_ESP = false
		ENABLE_ESP = false
		
		ENABLE_BOMB_TIMER = false
		ENABLE_REDUCED_FLASH = false
		ENABLE_FLAT_AIM = false
		
		SERVER_TICK_RATE = 128 // most leagues are 128-tick
		PROCESS_ACCESS_FLAGS = WinNT.PROCESS_QUERY_INFORMATION or WinNT.PROCESS_VM_READ // all we need
		GARBAGE_COLLECT_ON_MAP_START = true // get rid of traces
	}
 	clearScreen()
	
	val scanner = Scanner(System.`in`)
	while (!Thread.interrupted()) {
 		System.out.println()
 		System.out.print("> ")
		when (scanner.nextLine()) {
			"exit", "quit", "e", "q" -> System.exit(0)
			"reload", "r" -> loadSettings()
			"reset" -> resetToggles()
			"toggles", "t" -> printToggles()
			"cls", "clear", "c" -> clearScreen()
            "ranks", "ra" -> getRanks()
		}
	}
}

private fun loadSettings() {
	setIdeaIoUseFallback()
	
	File(SETTINGS_DIRECTORY).listFiles().forEach {
		FileReader(it).use {
			Dojo.script(it
					.readLines()
					.joinToString("\n"))
		}
	}

	System.out.println("Loaded settings.")
	
	val needsOverlay = ENABLE_BOMB_TIMER or (ENABLE_ESP and (SKELETON_ESP or BOX_ESP))
	if (!Overlay.opened && needsOverlay) Overlay.open()
}

private fun resetToggles() {
	ENABLE_AIM = false
	ENABLE_BUNNY_HOP = false
	ENABLE_ESP = false
	ENABLE_RCS = false
	ENABLE_BONE_TRIGGER = false
	
	System.out.println("All togglables disabled.")
}

private fun printToggles(){
	System.out.println("AIM      = " + ENABLE_AIM)
	System.out.println("BunnyHop = " + ENABLE_BUNNY_HOP)
	System.out.println("ESP      = " + ENABLE_ESP)
	System.out.println("RCS      = " + ENABLE_RCS)
	System.out.println("Trigger  = " + ENABLE_BONE_TRIGGER)
}

private fun clearScreen() {
	repeat(512) { _ ->
		System.out.print("\n")
	}
	System.out.println("   Command     | Alias  | Function");
	System.out.println("  =============+========+=========================")
	System.out.println(" | clear       | cls, c | clears console screen   |")
	System.out.println(" | exit / quit | e, q   | stop MOUN:PRO           |")
	System.out.println(" | reload      | r      | reloads /settings       |")
	System.out.println(" | reset       |        | disables all toggles    |")
	System.out.println(" | toggles     | t      | show what is toggled    |")
	System.out.println(" | ranks       | ra     | show Ranks              |")
	System.out.println("  =============+========+=========================")
	System.out.println()
}