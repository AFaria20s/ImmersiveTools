package org.slowatom.immersivetools.items.essence;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import org.slowatom.immersivetools.items.Items;

public class Essences extends Items {
    public static final RegistryObject<Item> ESSENCE = ITEMS.register(
            "essence",
            () -> new Item(new Item.Properties()
                    .group(ItemGroup.BREWING)
                    .rarity(Rarity.COMMON)
            )
    );
    public static final RegistryObject<Item> REFINED_ESSENCE = ITEMS.register(
            "refined_essence",
            () -> new Item(new Item.Properties()
                    .group(ItemGroup.BREWING)
                    .rarity(Rarity.UNCOMMON)
            )
    );

    public static void register() {}
}
