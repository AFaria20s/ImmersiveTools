package org.slowatom.immersivetools.perks.perkTypes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.apache.logging.log4j.Logger;
import org.slowatom.immersivetools.items.gem.Gems;
import org.slowatom.immersivetools.perks.BasePerk;

public class VoidLeapPerk extends BasePerk {

    @Override
    public Item getCatalystItem() {
        return Gems.VOID_GEM.get();
    }

    @Override
    public int getMaxTier() {
        return 5;
    }

    @Override
    public String getId() {
        return "voidleap";
    }

    @Override
    public String getDisplayName() {
        return new TranslationTextComponent("immersivetools.perk.voidleap").getString();
    }

    @Override
    public boolean canApplyTo(ItemStack stack) {
        return stack.getItem() instanceof SwordItem ||
                stack.getItem() instanceof AxeItem  ;
    }

    @Override
    public void onRightClick(PlayerInteractEvent.RightClickItem event) {
        PlayerEntity player = event.getPlayer();
        ItemStack tool = event.getItemStack();
        int durabilityCost = 2 * this.tier;

        if (player.world.isRemote || !(player instanceof ServerPlayerEntity)) return;
        if (!player.isSneaking()) return;
        if (tool.getDamage() + durabilityCost >= tool.getMaxDamage()) return;

        // getLookVec() gives the direction (x, y, z)
        Vector3d lookVector = player.getLookVec();

        double forwardForce = 1.0;
        double upwardForce = 0.6;

        player.addVelocity(
                lookVector.x * forwardForce,
                upwardForce,
                lookVector.z * forwardForce
        );

        player.velocityChanged = true;
        player.fallDistance = 0.0F;

        tool.damageItem(durabilityCost, (ServerPlayerEntity) player, (p) -> p.sendBreakAnimation(event.getHand()));

        player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(),
                SoundEvents.ENTITY_ENDER_DRAGON_FLAP, SoundCategory.PLAYERS, 0.5F, 1.2F);

        event.setCanceled(true);
    }
}
