

package com.moun.game.offsets

import com.moun.game.CSGO.engineDLL
import com.moun.utils.extensions.invoke
import com.moun.utils.get

object EngineOffsets {

    val dwClientState by engineDLL(1)(0xA1, 0[4], 0x33, 0xD2, 0x6A, 0, 0x6A, 0, 0x33, 0xC9, 0x89, 0xB0)
    val dwInGame by engineDLL(2, subtract = false)(0x83, 0xB8, 0[5], 0x0F, 0x94, 0xC0, 0xC3)
    val dwGlobalVars by engineDLL(1)(0x68, 0[4], 0x68, 0[4], 0xFF, 0x50, 0x08, 0x85, 0xC0)
    val dwViewAngles by engineDLL(4, subtract = false)(0xF3, 0x0F, 0x11, 0x80, 0[4], 0xD9, 0x46, 0x04, 0xD9, 0x05)

    val dwSignOnState by engineDLL(2, subtract = false)(0x83, 0xB8, 0[5], 0x0F, 0x94, 0xC0, 0xC3)

    val pStudioModel by engineDLL(0x2)(0x8B, 0x0D, 0x00, 0x00, 0x00, 0x00, 0x0F, 0xB7, 0x80,
            0x00, 0x00, 0x00, 0x00, 0x8B, 0x11, 0x89, 0x45, 0x08, 0x5D, 0xFF, 0x62, 0x38, 0x33, 0xC0)

}
