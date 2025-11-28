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

    // Você pode sobrescrever o tier máximo aqui se for diferente do padrão
    @Override
    public int getMaxTier() {
        return 3; // Ex: Este perk pode ir até ao Tier 3
    }

    @Override
    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        // A sua lógica de tier já funciona perfeitamente com isto
        float multiplier = 1.0f + (0.5f * this.tier);
        event.setNewSpeed(event.getOriginalSpeed() * multiplier);
    }
}