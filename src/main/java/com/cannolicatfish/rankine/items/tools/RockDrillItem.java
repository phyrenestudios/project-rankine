package com.cannolicatfish.rankine.items.tools;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import java.util.ArrayList;
import java.util.List;

public class RockDrillItem extends Item {

    public RockDrillItem(Properties properties) {
        super(properties);
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        IBlockReader reader = context.getWorld();
        BlockPos pos = context.getPos();
        List<Block> stones = new ArrayList<Block>();

        for (int i=0; i<= pos.getY(); ++i) {
            Block stone = reader.getBlockState(pos.down(i)).getBlock();
            if (!stones.contains(stone) && stone.getTags().contains(new ResourceLocation("forge:stone"))) {
                stones.add(stone);
            }
        }

        if (!stones.isEmpty()) {
            System.out.println(stones);
        } else {
            System.out.println("No stones detected");
        }
        context.getItem().shrink(1);
        return ActionResultType.SUCCESS;

    }


}
