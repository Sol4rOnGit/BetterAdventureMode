package me.hiresh.betteradventuremode;

//Events
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

//Minecraft Imports
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

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

        UseBlockCallback.EVENT.register((playerEntity, world, hand, blockHitResult) -> {
            //Empty & Non-main Hand early return
            if (hand != Hand.MAIN_HAND) return ActionResult.PASS;

            var stack = playerEntity.getMainHandStack();
            if (stack.isEmpty()) return  ActionResult.PASS;

            //Allowed item

            BlockState placing = null;
            try {
                placing = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
            } catch (ClassCastException e){
                return ActionResult.PASS;
            }

            if ( !(placing.isOf(Blocks.CRAFTING_TABLE)
                    //Furnaces
                    || placing.isOf(Blocks.FURNACE)
                    || placing.isOf(Blocks.BLAST_FURNACE)
                    || placing.isOf(Blocks.SMOKER)
                    //Beds
                    || placing.isIn(BlockTags.BEDS)
                    //Campfire
                    || placing.isOf(Blocks.CAMPFIRE)
                    || placing.isOf(Blocks.SOUL_CAMPFIRE)
                    //Lighting
                    || placing.isOf(Blocks.TORCH)
                    || placing.isOf(Blocks.LANTERN)
                    //Storage
                    || placing.isOf(Blocks.CHEST)
                    || placing.isOf(Blocks.BARREL)
                    || placing.isIn(BlockTags.SHULKER_BOXES)
                    || placing.isOf(Blocks.ENDER_CHEST)
                    //Obsidian
                    || placing.isOf(Blocks.OBSIDIAN)
                    //Climbing
                    || placing.isOf(Blocks.LADDER)
                    || placing.isOf(Blocks.SCAFFOLDING)
                    //Other Utility
                    || placing.isOf(Blocks.ANVIL)
                    || placing.isOf(Blocks.ENCHANTING_TABLE)
                    || placing.isOf(Blocks.BOOKSHELF)
                    || placing.isOf(Blocks.SMITHING_TABLE)
                    || placing.isOf(Blocks.GRINDSTONE)
                    //Farmable
                    || placing.isOf(Blocks.WHEAT)
                    || placing.isOf(Blocks.POTATOES)
                    || placing.isOf(Blocks.CARROTS)
                    || placing.isOf(Blocks.BEETROOTS)
                    || placing.isOf(Blocks.PUMPKIN_STEM)
                    || placing.isOf(Blocks.SUGAR_CANE)
                    || placing.isOf(Blocks.SWEET_BERRY_BUSH)
                    || placing.isOf(Blocks.CACTUS)
                    || placing.isOf(Blocks.KELP)
                    || placing.isOf(Blocks.NETHER_WART)
                    || placing.isOf(Blocks.MELON_STEM)
                    || placing.isOf(Blocks.CAKE)
            ))

            {
                playerEntity.sendMessage(Text.literal("You cannot place this block."), true);
                return ActionResult.FAIL;
            }

            return ActionResult.PASS;
        });
    }
}
