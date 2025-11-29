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
                    .group(ItemGroup.MISC)
                    .rarity(Rarity.UNCOMMON)
            )
    );
    public static final RegistryObject<Item> REINFORCED_GEM = ITEMS.register(
            "reinforced_gem",
            () -> new Item(new Item.Properties()
                    .group(ItemGroup.MISC)
                    .rarity(Rarity.RARE)
            )
    );
    public static final RegistryObject<Item> BLAZE_GEM = ITEMS.register(
            "blaze_gem",
            () -> new Item(new Item.Properties()
                    .group(ItemGroup.MISC)
                    .rarity(Rarity.RARE)
            )
    );
    public static final RegistryObject<Item> VOID_GEM = ITEMS.register(
            "void_gem",
            () -> new Item(new Item.Properties()
                    .group(ItemGroup.MISC)
                    .rarity(Rarity.EPIC)
            )
    );

    public static void register() {}
}