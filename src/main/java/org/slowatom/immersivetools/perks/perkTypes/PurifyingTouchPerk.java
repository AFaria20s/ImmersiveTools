package org.slowatom.immersivetools.perks.perkTypes;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.BlockEvent;
import org.slowatom.immersivetools.items.gem.Gems;
import org.slowatom.immersivetools.perks.BasePerk;

public class PurifyingTouchPerk extends BasePerk {
    @Override
    public Item getCatalystItem() {
        return Gems.BLAZE_GEM.get();
    }

    @Override
    public String getId() {
        return "purifyingtouch";
    }

    @Override
    public String getDisplayName() {
        return new TranslationTextComponent("immersivetools.perk.purifyingtouch").getString();
    }

    @Override
    public int getMaxTier() {
        return 4;
    }

    /**
     * Define a chance de preservar a durabilidade com base no Tier.
     */
    private float getSmelterChance(int tier) {
        switch (tier) {
            case 1:
                return 0.10f; // 10%
            case 2:
                return 0.20f; // 30%
            case 3:
                return 0.30f; // 50%
            case 4:
                return 0.40f; // 75%
            //case 5: return 0.50f; // 100%
            default:
                return 0.0f;
        }
    }

    private float getFireaspectChance(int tier) {
        switch (tier) {
            case 1:
                return 0.10f; // 10%
            case 2:
                return 0.20f; // 20%
            case 3:
                return 0.30f; // 30%
            case 4:
                return 0.40f; // 40%
            //case 5: return 0.50f; // 50%
            default:
                return 0.0f;
        }
    }

    @Override
    public void onBlockBreak(PlayerEntity player, ItemStack tool, BlockEvent.BreakEvent event) {
        if (player.world.isRemote || !(player instanceof ServerPlayerEntity)) {
            return;
        }

        if (event.isCanceled()) return;

        World world = player.world;
        float chance = getSmelterChance(this.tier);

        if (RANDOM.nextFloat() < chance) {
            ItemStack inputStack = new ItemStack(event.getState().getBlock());

            world.getRecipeManager().getRecipe(IRecipeType.SMELTING, player.inventory, world)
                    .ifPresent(recipe -> {
                        if (recipe instanceof FurnaceRecipe) {
                            ItemStack resultStack = ((FurnaceRecipe) recipe).getRecipeOutput();

                            if (!resultStack.isEmpty()) {
                                BlockPos pos = event.getPos();
                                event.setCanceled(true);

                                if (!world.isRemote) {
                                    net.minecraft.inventory.InventoryHelper.spawnItemStack(
                                            world, pos.getX(), pos.getY(), pos.getZ(), resultStack.copy()
                                    );
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onAttack(PlayerEntity player, ItemStack tool, AttackEntityEvent event) {
        if (player.world.isRemote || !(player instanceof ServerPlayerEntity)) return;
        if(!(event.getTarget() instanceof LivingEntity)) return;
        if(RANDOM.nextFloat() < getFireaspectChance(this.tier)) event.getTarget().setFire(this.tier*3); // Max = 4*3=12
    }
}
