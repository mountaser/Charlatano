

package com.moun.game.offsets

import com.moun.game.CSGO.clientDLL
import com.moun.utils.extensions.invoke
import com.moun.utils.get

object ClientOffsets {
	
	const val dwIndex = 0x64
	
	val bDormant by clientDLL(2, 8, subtract = false)(
			0x8A, 0x81, 0[4], 0xC3, 0x32, 0xC0
	)
	
	val decalname by clientDLL(read = false, subtract = false)(
			0x64, 0x65, 0x63, 0x61, 0x6C, 0x6E, 0x61, 0x6D, 0x65, 0x00
	)
	
	val dwFirstClass by lazy(LazyThreadSafetyMode.NONE) { findFirstClass() }
	
	val dwForceJump by clientDLL(2)(
			0x8B, 0x0D, 0[4], 0x8B, 0xD6, 0x8B, 0xC1, 0x83, 0xCA, 0x02
	)
	
	var dwLocalPlayer by clientDLL(3, 4)(
			0x8D, 0x34, 0x85, 0[4], 0x89, 0x15, 0[4], 0x8B, 0x41, 0x08, 0x8B, 0x48, 0x04, 0x83, 0xF9, 0xFF
	)
	val dwGlowObject by clientDLL(1, 4)(0xA1, 0[4], 0xA8, 0x01, 0x75, 0x4B)
	val dwEntityList by clientDLL(1)(0xBB, 0[4], 0x83, 0xFF, 0x01, 0x0F, 0x8C, 0[4], 0x3B, 0xF8)
	val dwViewMatrix by clientDLL(3, 176)(0x0F, 0x10, 0x05, 0[4], 0x8D, 0x85, 0[4], 0xB9)
	
	val dwSensitivity by clientDLL(2, 44)(
			0x81, 0xF9, 0[4], 0x75, 0x1D, 0xF3, 0x0F, 0x10, 0x05,
			0[4], 0xF3, 0x0F, 0x11, 0x44, 0x24, 0, 0x8B, 0x44, 0x24,
			0x18, 0x35, 0[4], 0x89, 0x44, 0x24, 0x0C, 0xEB, 0x0B
	)
	
	val dwSensitivityPtr by clientDLL(2)(
			0x81, 0xF9, 0[4], 0x75, 0x1D, 0xF3, 0x0F, 0x10, 0x05,
			0[4], 0xF3, 0x0F, 0x11, 0x44, 0x24, 0, 0x8B, 0x44, 0x24,
			0x18, 0x35, 0[4], 0x89, 0x44, 0x24, 0x0C, 0xEB, 0x0B
	)
	
	val dwPlayerResource by clientDLL(2)(
			0x8B, 0x3D, 0[4], 0x85, 0xFF, 0x0F, 0x84, 0[4], 0x81, 0xC7
	)
	
	val dwRadarBase by clientDLL(1)(
			0xA1, 0[4], 0x8B, 0x0C, 0xB0, 0x8B, 0x01, 0xFF, 0x50, 0, 0x46, 0x3B, 0x35, 0[4], 0x7C, 0xEA, 0x8B, 0x0D
	)
	
	val dwGameRulesProxy by clientDLL(1)(
			0xA1, 0[4], 0x85, 0xC0, 0x0F, 0x84, 0[4], 0x80, 0xB8, 0[5], 0x0F, 0x84, 0[4], 0x0F, 0x10, 0x05
	)
	
    val pStudioHdr by clientDLL(2, subtract = false)(
		    0x8B, 0xB6, 0[4], 0x85, 0xF6, 0x74, 0x05, 0x83, 0x3E, 0, 0x75, 0x02, 0x33, 0xF6, 0xF3, 0x0F, 0x10, 0x44, 0x24
    )
	
}