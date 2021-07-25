package com.cannolicatfish.rankine.items.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
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
            PlayerEntity player = context.getPlayer();
            if (!stones.isEmpty()) {
                player.sendMessage(new StringTextComponent("== STONES LIST ==").mergeStyle(TextFormatting.GOLD),player.getUniqueID());
                for (Block s : stones) {
                    player.sendMessage(new TranslationTextComponent(s.getTranslationKey()),player.getUniqueID());
                }
            } else {
                player.sendMessage(new StringTextComponent("No stones detected"),player.getUniqueID());
            }
            context.getItem().damageItem(1,player,(p_220040_1_) -> {
                p_220040_1_.sendBreakAnimation(context.getHand());
            });
            player.getCooldownTracker().setCooldown(this, 60);
        }


        return ActionResultType.SUCCESS;

    }


}
