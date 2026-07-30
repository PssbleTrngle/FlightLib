package com.possible_triangle.flightlib.api

import net.minecraft.core.particles.ParticleOptions
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

interface IJetpack {
    data class Context(
        val jetpack: IJetpack,
        val entity: LivingEntity,
        val world: Level,
        val pose: FlyingPose,
        val source: ISource,
    )

    fun activeType(context: Context): ControlType = ControlType.ALWAYS

    fun horizontalSpeed(context: Context): Double

    fun verticalSpeed(context: Context): Double

    fun acceleration(context: Context): Double

    fun hoverType(context: Context): ControlType

    fun hoverSpeed(context: Context): Double

    fun hoverVerticalSpeed(context: Context): Double = verticalSpeed(context) * 0.8

    fun hoverHorizontalSpeed(context: Context): Double = horizontalSpeed(context) * 0.8

    fun swimModifier(context: Context): Double

    @Deprecated("exact boost amount is now configurable")
    fun boostsElytra(): Boolean = elytraBoost() > 0.0

    fun elytraBoost(): Double = 1.25

    fun isValid(context: Context): Boolean

    fun isUsable(context: Context): Boolean

    fun onUse(
        context: Context,
        action: FlightAction,
    ) = onUse(context)

    @Deprecated("replace with more specific useOn", replaceWith = ReplaceWith("useOn(Context, FlightAction)"))
    fun onUse(context: Context) {
    }

    /**
     * Used to display the particles
     * Return `null` if you don't want particles to be added
     */
    fun getThrusters(context: Context): List<Vec3>?

    fun isHovering(context: Context): Boolean = IFlightApi.INSTANCE.isActive(hoverType(context), FlightKey.TOGGLE_HOVER, context.entity)

    @Deprecated("check currentAction != null instead", replaceWith = ReplaceWith("IFlightApi.currentAction(context)"))
    fun isThrusting(context: Context): Boolean {
        return IFlightApi.INSTANCE.currentAction(context) != null
        /*
        val entity = context.entity
        if (entity.vehicle != null) return false
        if (!IFlightApi.INSTANCE.isActive(
                context.jetpack.activeType(context),
                FlightKey.TOGGLE_ACTIVE,
                entity,
            )
        ) {
            return false
        }

        return true
         */
    }

    fun createParticles(): ParticleOptions
}
