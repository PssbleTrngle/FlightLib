package com.possible_triangle.flightlib.init

import com.possible_triangle.flightlib.api.*
import com.possible_triangle.flightlib.logic.ControlManager
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level

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
        val level = entity.level() ?: return null
        val pose = FlyingPose.get(entity)
        return getAll(entity)
            .asSequence()
            .map { it.source to it.provider() }
            .filter { (_, jetpack) -> jetpack != null }
            .map { (source, jetpack) -> IJetpack.Context(jetpack!!, entity, level, pose, source) }
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

    override fun currentAction(context: IJetpack.Context): FlightAction? =
        when (context.pose) {
            FlyingPose.SUPERMAN -> context.boostingAction()
            FlyingPose.UPRIGHT -> context.uprightAction()
        }

    private fun IJetpack.Context.boostingAction(): FlightAction? {
        if (entity.isUnderWater) return FlightAction.BOOST_SWIMMING

        val boost = jetpack.elytraBoost()
        if (boost <= 0.0) return null

        if (!entity.isFallFlying) return null
        if (entity !is Player || !FlightKey.UP.isPressed(entity)) return null

        return FlightAction.BOOST_ELYTRA
    }

    private fun IJetpack.Context.uprightAction(): FlightAction? {
        if (entity.vehicle != null) return null

        val maxHeightAboveGround = jetpack.heightAboveGroundLimit(this)
        if (maxHeightAboveGround != null && missingGroundBelow(level, entity.blockPosition(), maxHeightAboveGround)) {
            return null
        }

        val buttonUp = FlightKey.UP.isPressed(entity)
        val buttonDown = entity.isShiftKeyDown

        if (entity.onGround() && !buttonUp) return null

        val hovering =
            IFlightApi.INSTANCE.isActive(
                jetpack.hoverType(this),
                FlightKey.TOGGLE_HOVER,
                entity,
            )

        return when {
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

    private fun missingGroundBelow(
        level: Level,
        pos: BlockPos,
        range: Int,
    ): Boolean {
        val mutable = pos.mutable()
        for (y in pos.y downTo (pos.y - range)) {
            mutable.y = y
            val state = level.getBlockState(mutable)
            if (state.isFaceSturdy(level, mutable, Direction.UP)) return false
        }

        return true
    }
}
