package org.slowatom.immersivetools.items;

import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slowatom.immersivetools.ImmersiveTools;
import org.slowatom.immersivetools.items.essence.Essences;
import org.slowatom.immersivetools.items.gem.Gems;

public class Items {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ImmersiveTools.MOD_ID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);

        Gems.register();
        Essences.register();
    }

}
