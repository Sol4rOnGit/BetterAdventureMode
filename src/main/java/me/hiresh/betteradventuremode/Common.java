package me.hiresh.betteradventuremode;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;

public class Common {
    public static void Register() {
        PlayerBlockBreakEvents.BEFORE.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            ItemStack heldItem = playerEntity.getMainHandStack();

            if (!heldItem.isSuitableFor(blockState)){
                playerEntity.sendMessage(Text.literal("You need the correct tool!"), true);
                return false;
            }

            return true;
        });
    }
}
