package org.slowatom.immersivetools.perks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.Random;

public abstract class BasePerk implements Perk {
    protected static final Random RANDOM = new Random();
    protected int tier = 1;

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public Perk withTier(int tier) {
        this.tier = tier;
        return this;
    }

    @Override
    public int getMaxTier() {
        return 3;
    }

    // Event Methods
    public void onBlockBreak(PlayerEntity player, ItemStack tool, BlockEvent.BreakEvent event) {
    }

    public void onAttack(PlayerEntity player, ItemStack tool, AttackEntityEvent event) {
    }

    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
    }

    public void onRightClick(PlayerInteractEvent.RightClickItem event) {
    }
}