package org.slowatom.vanillahancer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(VanillaEnhancer.MOD_ID)
public class VanillaEnhancer {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "vancerstructures";

    public VanillaEnhancer() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void doClientStuff(final FMLClientSetupEvent event) {

    }
}
