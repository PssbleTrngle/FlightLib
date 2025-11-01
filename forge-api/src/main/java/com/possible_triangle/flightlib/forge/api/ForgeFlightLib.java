package com.possible_triangle.flightlib.forge.api;

import com.possible_triangle.flightlib.api.IJetpack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ForgeFlightLib {

    public static final Capability<IJetpack> JETPACK_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

}
