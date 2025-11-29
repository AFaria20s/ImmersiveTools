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

        int durabilityCost = 5 + this.tier;

        int cooldownTicks = 60 - (this.tier * 10);
        if (cooldownTicks < 5) cooldownTicks = 5;

        if (player.world.isRemote || !(player instanceof ServerPlayerEntity)) return;
        if (player.getCooldownTracker().hasCooldown(this.getCatalystItem())) return;
        if (!player.isOnGround()) return;
        if (!player.isSneaking()) return;
        if (tool.getDamage() + durabilityCost >= tool.getMaxDamage()) return;


        // --- CÁLCULO DE FORÇA (VETOR EQUILIBRADO) ---
        Vector3d lookVector = player.getLookVec();

        // Base de 0.8 + 0.15 por Tier (T5 = 0.8 + 0.75 = 1.55)
        double forwardForce = 0.8 + (this.tier * 0.15);

        // Base de 0.5 + 0.10 por Tier (T5 = 0.5 + 0.5 = 1.0)
        double upwardForce = 0.5 + (this.tier * 0.10);

        // Aplicar a velocidade
        player.addVelocity(
                lookVector.x * forwardForce,
                upwardForce,
                lookVector.z * forwardForce
        );

        player.getCooldownTracker().setCooldown(this.getCatalystItem(), cooldownTicks);

        player.velocityChanged = true;
        player.fallDistance = 0.0F;

        tool.damageItem(durabilityCost, (ServerPlayerEntity) player, (p) -> p.sendBreakAnimation(event.getHand()));

        player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(),
                SoundEvents.ENTITY_ENDER_DRAGON_FLAP, SoundCategory.PLAYERS, 0.5F, 1.2F);

        event.setCanceled(true);
    }
}
