package com.possible_triangle.flightlib.fabric.mixins;

import com.possible_triangle.flightlib.api.FlightKey;
import com.possible_triangle.flightlib.fabric.FabricDataAttachments;
import com.possible_triangle.flightlib.logic.ISettingsStorage;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Map;

@Mixin(Player.class)
public class PlayerMixin implements ISettingsStorage {

    @Override
    public void flightlib$set(Map<FlightKey, Boolean> settings) {
        var self = (Player) (Object) this;
        self.setAttached(FabricDataAttachments.SETTINGS_ATTACHMENT, settings);
    }

    @Override
    public Map<FlightKey, Boolean> flightlib$get() {
        var self = (Player) (Object) this;
        return self.getAttached(FabricDataAttachments.SETTINGS_ATTACHMENT);
    }

}
