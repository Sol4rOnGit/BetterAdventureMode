package me.hiresh.betteradventuremode;

//Events
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.

//Minecraft Imports
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;

public class Common {
    public static void Register() {
        PlayerBlockBreakEvents.BEFORE.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            ItemStack heldItem = playerEntity.getMainHandStack();

            if (!           (blockState.isIn(BlockTags.AXE_MINEABLE)
                            || blockState.isIn(BlockTags.PICKAXE_MINEABLE)
                            || blockState.isIn(BlockTags.SHOVEL_MINEABLE)
                            || blockState.isIn(BlockTags.HOE_MINEABLE)))
            { return true; }

            if (!heldItem.isSuitableFor(blockState)){
                playerEntity.sendMessage(Text.literal("You need the correct tool!"), true);
                return false;
            }

            return true;
        });

    }
}
