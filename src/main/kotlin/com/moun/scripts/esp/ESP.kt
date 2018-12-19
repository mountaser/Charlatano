

package com.moun.scripts.esp

import com.moun.settings.ENABLE_ESP

fun esp() {
	if (!ENABLE_ESP) return

	glowEsp()
	boxEsp()
	skeletonEsp()
}