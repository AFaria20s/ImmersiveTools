package org.slowatom.immersivetools.perks;

import java.util.HashMap;
import java.util.Map;

public class PerkHandler {
    private static final HashMap<String, Perk> PERKS = new HashMap<>();

    public static void register(Perk perk) {
        PERKS.put(perk.getId(), perk);
    }

    public static Perk get(String id) {
        return PERKS.get(id);
    }

    public static Map<String, Perk> all() {
        return PERKS;
    }
}
