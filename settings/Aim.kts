

import com.moun.settings.*

///////////////////////////////////////////////////////////////////////////////
//                             --- GENERAL ---                               //
///////////////////////////////////////////////////////////////////////////////

/**
 * Whether or not to aim when using the [FIRE_KEY] (by default left click).
 *
 * You should disable this if you don't want aim to activate when left clicking.
 */
ACTIVATE_FROM_FIRE_KEY = true

/**
 * This will allow you to shoot teammates with aimbot when turned on.
 */
TEAMMATES_ARE_ENEMIES = false

/**
 * The key code of the force aim button.
 *
 * By default, this uses the backward mouse button
 * (button 5, the button on the bottom left of gaming mice).
 */
FORCE_AIM_KEY = 5

/**
 * The field of view of the aimbot, in degrees (0 to 360).
 */
AIM_FOV = 190

/**
 * The aimbot's "playback" speed, the higher the value the slower the playback.
 *
 * The minimum value is 1, and max must always be greater than min.
 */
AIM_SPEED_MIN = 28
AIM_SPEED_MAX = 36

/**
 * The strictness, or "stickiness" of the aimbot; the higher the number, the
 * less strict the aimbot will stick to targets.
 *
 * The minimum value is 1.0
 */
AIM_STRICTNESS = 1.0



///////////////////////////////////////////////////////////////////////////////
//                           --- PERFECT AIM ---                             //
///////////////////////////////////////////////////////////////////////////////

/**
 * Whether or not to use perfect aim, which will instantaneously snap
 * to the aim bone once you are within the [PERFECT_AIM_FOV].
 */
PERFECT_AIM = false

/**
 * The FOV, in degrees (0 to 360) to snap for perfect aim.
 */
PERFECT_AIM_FOV = 27

/**
 * The chance, from 1% to 100% (0 to 100) for perfect aim to activate.
 */
PERFECT_AIM_CHANCE = 100



///////////////////////////////////////////////////////////////////////////////
//                            --- AIM ASSIST ---                             //
///////////////////////////////////////////////////////////////////////////////

/**
 * Enables "aim assist" mode, which has no stickiness, and gives you small
 * extra movements towards the aim bone.
 *
 * This setting should be used by high-level players who are experienced aimers.
 */
AIM_ASSIST_MODE = false

/**
 * The amount of strictness for the aim assist mode, with a minimum value of 1.
 */
AIM_ASSIST_STRICTNESS = 40



///////////////////////////////////////////////////////////////////////////////
//                          --- MISCELLANEOUS ---                            //
///////////////////////////////////////////////////////////////////////////////

/**
 * The duration in milliseconds at which aimbot paths are recalculated.
 */
AIM_DURATION = 1

/**
 * The amount of sprayed shots until the aimbot shifts to aiming at the [SHOULDER_BONE].
 */
SHIFT_TO_SHOULDER_SHOTS = 4

/**
 * The amount of sprayed shots until the aimbot shifts to aiming at the [BODY_BONE].
 */
SHIFT_TO_BODY_SHOTS = 7