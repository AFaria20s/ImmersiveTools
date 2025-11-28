package org.slowatom.immersivetools.perks.perkTypes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.BlockEvent;
import org.slowatom.immersivetools.items.gem.Gems;
import org.slowatom.immersivetools.perks.BasePerk;

public class ReinforcedPerk extends BasePerk {

    @Override
    public Item getCatalystItem() {
        return Gems.REINFORCED_GEM.get();
    }

    @Override
    public String getId() {
        return "reinforced";
    }

    @Override
    public String getDisplayName() {
        return new TranslationTextComponent("immersivetools.perk.reinforced").getString();
    }

    @Override
    public int getMaxTier() {
        return 5; // Confirma o máximo de T5 para este Perk
    }

    /**
     * Define a chance de preservar a durabilidade com base no Tier.
     */
    private float getPreservationChance(int tier) {
        switch (tier) {
            case 1: return 0.10f; // 10%
            case 2: return 0.20f; // 20%
            case 3: return 0.30f; // 30%
            case 4: return 0.40f; // 40%
            case 5: return 0.50f; // 50%
            default: return 0.0f;
        }
    }

    @Override
    public void onBlockBreak(PlayerEntity player, ItemStack tool, BlockEvent.BreakEvent event) {
        // Esta lógica deve rodar APENAS no servidor para evitar dessincronização.
        if (player.world.isRemote || !(player instanceof ServerPlayerEntity)) {
            return;
        }

        // Obtém a chance de preservação
        float chance = getPreservationChance(this.tier);

        // Se a chance for 0 ou a ferramenta não foi danificada (ex: quebrar terra com pá)
        // Não fazemos nada.
        if (chance <= 0.0f) return;

        // Verifica a chance
        if (Math.random() < chance) {

            // O dano JÁ FOI causado pelo evento de quebra.
            // Para simular que o dano não ocorreu, reparamos 1 ponto de durabilidade.

            // Verifica se a ferramenta já não está totalmente reparada
            if (tool.getDamage() > 0) {
                // Diminui o dano em 1 (repara)
                tool.setDamage(tool.getDamage() - 1);

                // Dá feedback auditivo ao jogador
                player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(),
                        SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5F, 1.5F);
            }
        }
    }

   @Override
    public void onAttack(PlayerEntity player, ItemStack tool, AttackEntityEvent event) {
        // Mesma lógica que onBlockBreak, mas executada no evento de ataque
        if (player.world.isRemote || !(player instanceof ServerPlayerEntity)) {
            return;
        }

        float chance = getPreservationChance(this.tier);

        if (Math.random() < chance && tool.getDamage() > 0) {
            // Repara o dano de combate
            tool.setDamage(tool.getDamage() - 1);
            player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(),
                    SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5F, 1.5F);
        }
    }
}