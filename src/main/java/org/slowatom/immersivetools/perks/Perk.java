package org.slowatom.immersivetools.perks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

public interface Perk {
    Item getCatalystItem();
    String getId();
    String getDisplayName();
    int getTier();
    Perk withTier(int tier);
    int getMaxTier();
    boolean canApplyTo(ItemStack stack);

    // Event Methods
    void onBlockBreak(PlayerEntity player, ItemStack tool, BlockEvent.BreakEvent event);
    void onAttack(PlayerEntity player, ItemStack tool, AttackEntityEvent event);
    void onBreakSpeed(PlayerEvent.BreakSpeed event);
    void onRightClick(PlayerInteractEvent.RightClickItem event);
}