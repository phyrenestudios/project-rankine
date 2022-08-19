package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class GasBottleItem extends Item {
    private final java.util.function.Supplier<? extends Block> gasSupplier;
    public GasBottleItem(Supplier<? extends Block> gas, Properties properties) {
        super(properties);
        this.gasSupplier = gas;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction opp = context.getClickedFace();
        if (context.getPlayer() != null) {
            worldIn.setBlock(pos.relative(opp), this.getGas().defaultBlockState(),3);
            context.getItemInHand().shrink(1);
            if (!context.getPlayer().getAbilities().instabuild) {
                context.getPlayer().getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }


    public Block getGas() { return gasSupplier.get(); }
}
