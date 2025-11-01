package com.possible_triangle.flightlib.neoforge.mixins;

import com.possible_triangle.flightlib.api.FlightKey;
import com.possible_triangle.flightlib.logic.ISettingsStorage;
import com.possible_triangle.flightlib.neoforge.ForgeDataAttachment;
import java.util.Map;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public class PlayerMixin implements ISettingsStorage {

    @Override
    public void flightlib$set(Map<FlightKey, Boolean> settings) {
        var self = (Player) (Object) this;
        self.setData(ForgeDataAttachment.SETTINGS_ATTACHMENT.get(), settings);
    }

    @Override
    public Map<FlightKey, Boolean> flightlib$get() {
        var self = (Player) (Object) this;
        return self.getData(ForgeDataAttachment.SETTINGS_ATTACHMENT.get());
    }

}
