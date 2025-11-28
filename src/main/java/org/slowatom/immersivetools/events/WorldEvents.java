package org.slowatom.immersivetools.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.passive.horse.MuleEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slowatom.immersivetools.ImmersiveTools;
import org.slowatom.immersivetools.items.essence.Essences;

@Mod.EventBusSubscriber(modid = ImmersiveTools.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WorldEvents {
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (event.getEntity().world.isRemote) return;

        if (event.getEntity() instanceof GuardianEntity) {
            if (event.getEntity().world.rand.nextFloat() < 0.3f) {
                ItemStack essence = new ItemStack(Essences.ESSENCE.get(), 1);

                event.getDrops().add(new ItemEntity(event.getEntity().world,
                        event.getEntity().getPosX(),
                        event.getEntity().getPosY(),
                        event.getEntity().getPosZ(),
                        essence));
            }
        }
    }
}
