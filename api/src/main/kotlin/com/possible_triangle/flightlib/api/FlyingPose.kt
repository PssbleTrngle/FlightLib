package com.possible_triangle.flightlib.api

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player

enum class FlyingPose {
    UPRIGHT,
    SUPERMAN,
    ;

    companion object {
        fun get(entity: LivingEntity): FlyingPose =
            if (entity.isFallFlying) {
                SUPERMAN
            } else if (entity.isVisuallySwimming && entity.isSprinting && (entity !is Player || entity.isAffectedByFluids)) {
                SUPERMAN
            } else {
                UPRIGHT
            }
    }
}
