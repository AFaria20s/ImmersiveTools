package org.slowatom.immersivetools.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.*;
import net.minecraft.world.BossInfo;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slowatom.immersivetools.ImmersiveTools;
import org.slowatom.immersivetools.perks.Perk;
import org.slowatom.immersivetools.utils.PerkUtils;

import java.util.List;

@Mod.EventBusSubscriber(modid = ImmersiveTools.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PerkEvents {

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        PlayerEntity player = event.getPlayer();
        ItemStack catalyst = player.getHeldItem(Hand.OFF_HAND);
        ItemStack tool = player.getHeldItemMainhand();

        // Se a mão principal ou a secundária estiverem vazias, não faz nada.
        if (tool.isEmpty() || catalyst.isEmpty()) return;

        boolean success = false;
        String messageKey = "";

        // 1. Tenta APLICAR um novo perk (usando a Gema)
        if (PerkUtils.tryApplyPerk(tool, catalyst)) {
            success = true;
            messageKey = "immersivetools.perk_applied"; // Mensagem de "Aplicado"
        }
        // 2. Se falhar, tenta DAR UPGRADE nos perks (usando a Essência)
        else if (PerkUtils.tryUpgradePerk(tool, catalyst)) {
            success = true;
            messageKey = "immersivetools.perk_upgraded"; // Mensagem de "Atualizado"
        }

        // Se uma das ações teve sucesso...
        if (success) {
            // Envia notificação de sucesso
            player.sendStatusMessage(
                    new StringTextComponent(TextFormatting.GREEN + "")
                            .appendSibling(new TranslationTextComponent(messageKey)),
                    true);

            // Toca som de sucesso
            event.getPlayer().world.playSound(
                    null,
                    player.getPosition(),
                    SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,
                    player.getSoundCategory(),
                    1f,
                    4f
            );

            // Reduz a contagem do item catalisador
            if (!player.isCreative()) { // Não consome no criativo
                player.getHeldItemOffhand().shrink(1);
            }

            event.setCanceled(true);
            return; // Termina o evento aqui
        }

        // Se nenhuma ação de aplicação/upgrade foi feita, executa a lógica normal dos perks
        List<Perk> perks = PerkUtils.getPerks(tool);
        for (Perk perk : perks) {
            perk.onRightClick(event);
        }
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        PlayerEntity player = event.getPlayer();
        ItemStack tool = player.getHeldItemMainhand();

        List<Perk> perks = PerkUtils.getPerks(tool);

        for (Perk perk : perks) {
            perk.onBreakSpeed(event);
        }
    }

    @SubscribeEvent
    public static void onBreak(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        ItemStack tool = player.getHeldItemMainhand();

        List<Perk> perks = PerkUtils.getPerks(tool);

        for (Perk perk : perks) {
            perk.onBlockBreak(player, tool, event);
        }
    }

    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event) {
        PlayerEntity player = event.getPlayer();
        ItemStack tool = player.getHeldItemMainhand();

        List<Perk> perks = PerkUtils.getPerks(tool);

        for (Perk perk : perks) {
            perk.onAttack(player, tool, event);
        }
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<ITextComponent> tooltip = event.getToolTip();

        List<Perk> perks = PerkUtils.getPerks(stack);
        if (perks.isEmpty()) return;

        int insertIndex = 1;
        tooltip.add(insertIndex++, new StringTextComponent("§6═══ Perks ═══"));

        for (Perk perk : perks) {
            int maxTier = perk.getMaxTier();

            String tierDisplay = " §b" + perk.getTier();

            if (perk.getTier() < maxTier) {
                tierDisplay += " / " + maxTier;
            } else {
                tierDisplay = " §e" + perk.getTier() + " MAX";
            }
            tierDisplay += "§b";

            String line = "§e✦ " +
                    perk.getDisplayName() +
                    " " + tierDisplay;

            tooltip.add(insertIndex++, new StringTextComponent(line));
        }
        tooltip.add(insertIndex++, new StringTextComponent(""));
    }

}