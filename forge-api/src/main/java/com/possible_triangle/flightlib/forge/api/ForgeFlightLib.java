package com.possible_triangle.flightlib.forge.api;

import com.possible_triangle.flightlib.api.Constants;
import com.possible_triangle.flightlib.api.IJetpack;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import org.jetbrains.annotations.Nullable;

public class ForgeFlightLib {

    public static final ItemCapability<IJetpack, @Nullable Void> ITEM_CAPABILITY = ItemCapability.createVoid(
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "jetpack"),
            IJetpack.class
    );

    public static final EntityCapability<IJetpack, @Nullable Void> ENTITY_CAPABILITY = EntityCapability.createVoid(
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "jetpack"),
            IJetpack.class
    );

}
