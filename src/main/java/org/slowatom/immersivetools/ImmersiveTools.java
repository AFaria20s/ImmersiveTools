package org.slowatom.immersivetools;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slowatom.immersivetools.events.WorldEvents;
import org.slowatom.immersivetools.perks.Perk;
import org.slowatom.immersivetools.perks.PerkHandler;
import org.slowatom.immersivetools.perks.perkTypes.LightWeightPerk;
import org.slowatom.immersivetools.items.Items;
import org.slowatom.immersivetools.perks.perkTypes.PurifyingTouchPerk;
import org.slowatom.immersivetools.perks.perkTypes.ReinforcedPerk;

@Mod(ImmersiveTools.MOD_ID)
public class ImmersiveTools {
    public static final String MOD_ID = "immersivetools";

    public ImmersiveTools() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        Items.register(modEventBus);

        PerkHandler.register(new LightWeightPerk());
        PerkHandler.register(new ReinforcedPerk());
        PerkHandler.register(new PurifyingTouchPerk());

        modEventBus.addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void doClientStuff(final FMLClientSetupEvent event) {

    }
}
