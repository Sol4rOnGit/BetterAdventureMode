package me.hiresh.betteradventuremode.mixin;

//Mixin Imports
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

//Minecraft Imports
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    static {
        System.out.println("[BetterAdventureMode] PlayerEntityMixin loaded!");
    }

    @ModifyReturnValue(method = "getBlockBreakingSpeed", at = @At("RETURN"))
    private float modifyBreakSpeed(float originalSpeed, BlockState blockState){

        boolean applicable =
                //All of the blocks that require Tools
                (blockState.isIn(BlockTags.AXE_MINEABLE)
                || blockState.isIn(BlockTags.PICKAXE_MINEABLE)
                || blockState.isIn(BlockTags.SHOVEL_MINEABLE)
                || blockState.isIn(BlockTags.HOE_MINEABLE))
                //Exclude Ores
                && !(blockState.isIn(BlockTags.COAL_ORES)
                || blockState.isIn(BlockTags.DIAMOND_ORES)
                || blockState.isIn(BlockTags.GOLD_ORES)
                || blockState.isIn(BlockTags.IRON_ORES)
                || blockState.isIn(BlockTags.REDSTONE_ORES)
                || blockState.isIn(BlockTags.LAPIS_ORES));

        if (applicable){
            return  originalSpeed / 10f; //slower (Change the number to change divisor)
        }

        return originalSpeed; //instant blocks mine normal speed
    }
}
