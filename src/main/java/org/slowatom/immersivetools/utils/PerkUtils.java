package org.slowatom.immersivetools.utils;

import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import org.slowatom.immersivetools.items.essence.Essences; // <-- IMPORTADO
import org.slowatom.immersivetools.perks.Perk;
import org.slowatom.immersivetools.perks.PerkHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PerkUtils {
    private static final String PERK_TAG = "immersivetools_perks";

    /**
     * Define quais itens de upgrade correspondem a quais tiers.
     * Esta é a chave para a escalabilidade.
     */
    private static final Map<Item, Integer> UPGRADE_CATALYSTS = Map.of(
            Essences.REFINED_ESSENCE.get(), 2,
            Essences.PURE_ESSENCE.get(), 3,
            Essences.LEGENDARY_ESSENCE.get(), 4,
            Essences.DIVINE_ESSENCE.get(), 5
    );

    /**
     * Helper para verificar se o item é uma ferramenta válida.
     */
    private static boolean isTool(ItemStack tool) {
        return (tool.getItem() instanceof SwordItem ||
                tool.getItem() instanceof PickaxeItem ||
                tool.getItem() instanceof AxeItem ||
                tool.getItem() instanceof ShovelItem ||
                tool.getItem() instanceof HoeItem);
    }

    /**
     * Tenta APLICAR um novo Perk (Tier 1) à ferramenta.
     * Usa o Catalisador "Gem" específico do Perk.
     */
    public static boolean tryApplyPerk(ItemStack tool, ItemStack catalyst) {
        if (!isTool(tool)) return false;

        // Se o catalisador for um item de upgrade (Essence), não é para aplicar, é para atualizar
        if (UPGRADE_CATALYSTS.containsKey(catalyst.getItem())) {
            return false;
        }

        for (Perk perk : PerkHandler.all().values()) {
            // Verifica se o catalisador é a "Gema" e se a ferramenta ainda não tem o perk
            if (perk.getCatalystItem().equals(catalyst.getItem()) &&
                    !hasPerk(tool, perk.getId())) {

                if(!perk.canApplyTo(tool)) return false;

                // Aplica o Nível 1
                PerkUtils.addPerk(tool, perk.getId(), 1);
                return true;
            }
        }
        return false;
    }

    /**
     * Tenta DAR UPGRADE nos Perks existentes na ferramenta.
     * Usa os Catalisadores "Essence" universais.
     */
    public static boolean tryUpgradePerk(ItemStack tool, ItemStack catalyst) {
        if (!isTool(tool)) return false;

        // 1. Verifica se o catalisador é um item de upgrade (Ex: REFINED_ESSENCE)
        if (!UPGRADE_CATALYSTS.containsKey(catalyst.getItem())) {
            return false; // Não é um item de upgrade
        }

        int targetTier = UPGRADE_CATALYSTS.get(catalyst.getItem());
        int requiredTier = targetTier - 1;

        boolean upgradedSomething = false;
        List<Perk> currentPerks = getPerks(tool); // Pega todos os perks da ferramenta

        if (currentPerks.isEmpty()) return false; // Ferramenta não tem perks para atualizar

        for (Perk perk : currentPerks) {
            // 2. Verifica se o perk está no tier anterior (Ex: Tier 1)
            // E se o perk pode ser atualizado (não atingiu o maxTier)
            if (perk.getTier() == requiredTier && perk.getTier() < perk.getMaxTier()) {

                // 3. Atualiza o perk para o targetTier (Ex: Tier 2)
                addPerk(tool, perk.getId(), targetTier);
                upgradedSomething = true;
            }
        }

        return upgradedSomething;
    }


    public static void addPerk(ItemStack stack, String perkId, int tier) {
        CompoundNBT mainTag = stack.getOrCreateTag();
        CompoundNBT perkTag = mainTag.getCompound(PERK_TAG);

        int currentTier = perkTag.getInt(perkId);

        // A sua lógica original está perfeita! Só salva se o tier for maior.
        if (tier > currentTier) {
            perkTag.putInt(perkId, tier);
        }

        mainTag.put(PERK_TAG, perkTag);
    }
    public static List<Perk> getPerks(ItemStack stack) {
        List<Perk> perks = new ArrayList<>();

        if (!stack.hasTag()) return perks;
        CompoundNBT mainTag = stack.getTag();
        if (mainTag == null || !mainTag.contains(PERK_TAG)) return perks;

        CompoundNBT perkTag = mainTag.getCompound(PERK_TAG);

        for (String key : perkTag.keySet()) {
            int tier = perkTag.getInt(key);

            Perk perk = PerkHandler.get(key);
            if (perk != null) {
                // Importante: .withTier() retorna uma NOVA instância (ou modifica a atual)
                // com o tier correto lido do NBT.
                // O seu código original estava a criar uma instância nova, o que é problemático.
                // Esta implementação assume que PerkHandler.get(key) retorna um "protótipo"
                // e .withTier() clona e define o tier.
                perks.add(perk.withTier(tier));
            }
        }

        return perks;
    }
    public static boolean hasPerk(ItemStack stack, String perkId) {
        if (!stack.hasTag()) return false;

        CompoundNBT mainTag = stack.getTag();
        if (mainTag == null || !mainTag.contains(PERK_TAG)) return false;

        CompoundNBT perkTag = mainTag.getCompound(PERK_TAG);

        return perkTag.contains(perkId);
    }
    public static int getPerkTier(ItemStack stack, String perkId) {
        if (!stack.hasTag()) return 0;

        CompoundNBT mainTag = stack.getTag();
        if (mainTag == null || !mainTag.contains(PERK_TAG)) return 0;

        CompoundNBT perkTag = mainTag.getCompound(PERK_TAG);

        return perkTag.getInt(perkId);
    }
    public static void removePerk(ItemStack stack, String perkId) {
        if (!stack.hasTag()) return;

        CompoundNBT mainTag = stack.getTag();
        if (mainTag == null || !mainTag.contains(PERK_TAG)) return;

        CompoundNBT perkTag = mainTag.getCompound(PERK_TAG);

        perkTag.remove(perkId);

        mainTag.put(PERK_TAG, perkTag);
    }
}