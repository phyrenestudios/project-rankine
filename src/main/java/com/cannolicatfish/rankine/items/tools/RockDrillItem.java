package com.cannolicatfish.rankine.items.tools;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
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
        if (!context.getWorld().isRemote && context.getPlayer() != null) {
            if (!stones.isEmpty()) {
                context.getPlayer().sendMessage(new StringTextComponent("== STONES LIST ==").mergeStyle(TextFormatting.GOLD),context.getPlayer().getUniqueID());
                for (Block s : stones) {
                    context.getPlayer().sendMessage(new TranslationTextComponent(s.getTranslationKey()),context.getPlayer().getUniqueID());
                }
            } else {
                context.getPlayer().sendMessage(new StringTextComponent("No stones detected"),context.getPlayer().getUniqueID());
            }
            context.getItem().damageItem(1,context.getPlayer(),(p_220040_1_) -> {
                p_220040_1_.sendBreakAnimation(context.getHand());
            });
        }


        return ActionResultType.SUCCESS;

    }


}
