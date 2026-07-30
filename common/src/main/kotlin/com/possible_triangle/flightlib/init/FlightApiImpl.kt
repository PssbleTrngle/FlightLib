package com.possible_triangle.flightlib.init

import com.possible_triangle.flightlib.api.*
import com.possible_triangle.flightlib.logic.ControlManager
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player

object FlightApiImpl : IFlightApi {
    private val PROVIDERS = arrayListOf<ISource.Provider>()
    private val CASTERS = arrayListOf<ISource.Caster>()

    override fun addSourceProvider(provider: ISource.Provider) {
        PROVIDERS.add(provider)
    }

    override fun addSourceCaster(caster: ISource.Caster) {
        CASTERS.add(caster)
    }

    override fun getAll(entity: LivingEntity): List<ISource.ProviderEntry> {
        val objects = PROVIDERS.flatMap { it.get(entity) }
        return objects.flatMap { (value, source) ->
            CASTERS.flatMap { caster ->
                caster.get(value).map {
                    ISource.ProviderEntry(source, it)
                }
            }
        }
    }

    override fun findJetpack(entity: LivingEntity): IJetpack.Context? {
        val world = entity.level() ?: return null
        val pose = FlyingPose.get(entity)
        return getAll(entity)
            .asSequence()
            .map { it.source to it.provider() }
            .filter { (_, jetpack) -> jetpack != null }
            .map { (source, jetpack) -> IJetpack.Context(jetpack!!, entity, world, pose, source) }
            .firstOrNull { it.jetpack.isValid(it) }
    }

    override fun isActive(
        type: ControlType,
        key: FlightKey,
        entity: LivingEntity,
    ): Boolean =
        when (type) {
            ControlType.ALWAYS -> true
            ControlType.NEVER -> false
            ControlType.TOGGLE -> key.isPressed(entity)
        }

    private fun IJetpack.Context.isUsable(): Boolean = jetpack.isUsable(this) && !source.isDisabled(this)

    override fun findActiveJetpack(entity: LivingEntity): IJetpack.Context? {
        if (entity is Player && entity.abilities.flying) return null
        return findJetpack(entity)
            ?.takeIf {
                isActive(
                    it.jetpack.activeType(it),
                    FlightKey.TOGGLE_ACTIVE,
                    entity,
                )
            }?.takeIf { it.isUsable() }
    }

    override fun isPressed(
        key: FlightKey,
        entity: LivingEntity,
    ) = ControlManager.isPressed(key, entity)

    override fun currentAction(context: IJetpack.Context): FlightAction? {
        return when (context.pose) {
            FlyingPose.SUPERMAN -> {
                if (context.entity.isUnderWater) return FlightAction.BOOST_SWIMMING

                val boost = context.jetpack.elytraBoost()
                if (boost <= 0.0) return null

                if (!context.entity.isFallFlying) return null
                if (context.entity !is Player || !FlightKey.UP.isPressed(context.entity)) return null

                FlightAction.BOOST_ELYTRA
            }

            FlyingPose.UPRIGHT -> {
                val entity = context.entity
                val buttonUp = FlightKey.UP.isPressed(entity)
                val buttonDown = entity.isShiftKeyDown
                val hovering =
                    IFlightApi.INSTANCE.isActive(context.jetpack.hoverType(context), FlightKey.TOGGLE_HOVER, entity)

                if (context.entity.vehicle != null) return null
                if (context.entity.onGround() && !buttonUp) return null

                when {
                    buttonUp && !buttonDown -> {
                        FlightAction.UP
                    }

                    hovering -> {
                        if (buttonDown && !buttonUp) {
                            FlightAction.DOWN
                        } else {
                            FlightAction.HOVER
                        }
                    }

                    else -> {
                        null
                    }
                }
            }
        }
    }
}
