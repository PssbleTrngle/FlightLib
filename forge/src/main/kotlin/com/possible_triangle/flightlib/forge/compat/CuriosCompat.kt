package com.possible_triangle.flightlib.forge.compat

import com.possible_triangle.flightlib.api.IFlightApi
import com.possible_triangle.flightlib.api.ISource
import com.possible_triangle.flightlib.api.sources.CuriosSource
import com.possible_triangle.flightlib.platform.Services
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import top.theillusivec4.curios.api.CuriosApi

object CuriosCompat {

    @JvmStatic
    fun register() {
        if (!Services.PLATFORM.isModLoaded("curios")) return

        IFlightApi.INSTANCE.addSourceProvider(::getCuriosStacks)
    }

    private fun getCuriosStacks(entity: LivingEntity): List<Pair<ItemStack, ISource>> {
        return CuriosApi.getCuriosInventory(entity).map {
            it.curios.entries.flatMap { (slot, handler) ->
                val slots = 0 until handler.slots
                slots.map { index ->
                    val stack = handler.stacks.getStackInSlot(index)
                    stack to (CuriosSource(slot, index, stack))
                }
            }
        }.orElseGet(::emptyList)
    }

}