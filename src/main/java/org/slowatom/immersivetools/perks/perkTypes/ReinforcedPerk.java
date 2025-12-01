package org.slowatom.immersivetools.perks.perkTypes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.BlockEvent;
import org.slowatom.immersivetools.items.gem.Gems;
import org.slowatom.immersivetools.perks.BasePerk;

public class ReinforcedPerk extends BasePerk {

    @Override
    public Item getCatalystItem() {
        return Gems.REINFORCED_GEM.get();
    }

    @Override
    public String getId() {
        return "reinforced";
    }

    @Override
    public String getDisplayName() {
        return new TranslationTextComponent("immersivetools.perk.reinforced").getString();
    }

    @Override
    public int getMaxTier() {
        return 5; // Confirma o máximo de T5 para este Perk
    }

    /**
     * Define a chance de preservar a durabilidade com base no Tier.
     */
    private float getPreservationChance(int tier) {
        switch (tier) {
            case 1: return 0.10f; // 10%
            case 2: return 0.20f; // 20%
            case 3: return 0.30f; // 30%
            case 4: return 0.40f; // 40%
            case 5: return 0.50f; // 50%
            default: return 0.0f;
        }
    }

    @Override
    public void onBlockBreak(PlayerEntity player, ItemStack tool, BlockEvent.BreakEvent event) {
        if (player.world.isRemote || !(player instanceof ServerPlayerEntity)) return;

        float chance = getPreservationChance(this.tier);
        if (chance <= 0.0f) return;

        if (RANDOM.nextFloat() < chance) {
            if (tool.getDamage() > 0) {
                tool.setDamage(tool.getDamage() - 1);
            }
        }
    }

   @Override
    public void onAttack(PlayerEntity player, ItemStack tool, AttackEntityEvent event) {
        if (player.world.isRemote || !(player instanceof ServerPlayerEntity)) {
            return;
        }

        float chance = getPreservationChance(this.tier);

        if (RANDOM.nextFloat() < chance && tool.getDamage() > 0) {
            tool.setDamage(tool.getDamage() - 1);
        }
    }
}