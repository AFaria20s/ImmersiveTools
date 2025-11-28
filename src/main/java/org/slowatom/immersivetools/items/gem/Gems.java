package org.slowatom.immersivetools.items.gem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import org.slowatom.immersivetools.items.Items;

public class Gems extends Items {
    public static final RegistryObject<Item> LIGHTWEIGHT_GEM = ITEMS.register(
            "lightweight_gem",
            () -> new Item(new Item.Properties()
                    .group(ItemGroup.TOOLS)
                    .rarity(Rarity.UNCOMMON)
            )
    );
    public static final RegistryObject<Item> REINFORCED_GEM = ITEMS.register(
            "reinforced_gem",
            () -> new Item(new Item.Properties()
                    .group(ItemGroup.TOOLS)
                    .rarity(Rarity.RARE)
            )
    );

    public static void register() {}
}