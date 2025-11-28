package org.slowatom.immersivetools.perks.perkTypes;

import net.minecraft.item.Item;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.slowatom.immersivetools.items.gem.Gems;
import org.slowatom.immersivetools.perks.BasePerk;

public class LightWeightPerk extends BasePerk {

    @Override
    public Item getCatalystItem() {
        return Gems.LIGHTWEIGHT_GEM.get();
    }

    @Override
    public String getId() { return "lightweight"; }

    @Override
    public String getDisplayName() {
        return new TranslationTextComponent("immersivetools.perk.lightweight").getString();
    }

    @Override
    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        float multiplier = 1.0f + (0.5f * this.tier);
        event.setNewSpeed(event.getOriginalSpeed() * multiplier);
    }
}