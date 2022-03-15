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

import net.minecraft.item.Item.Properties;

public class RockDrillItem extends Item {

    public RockDrillItem(Properties properties) {
        super(properties);
    }

    public ActionResultType useOn(ItemUseContext context) {
        IBlockReader reader = context.getLevel();
        BlockPos pos = context.getClickedPos();
        List<Block> stones = new ArrayList<Block>();

        for (int i=0; i<= pos.getY(); ++i) {
            Block stone = reader.getBlockState(pos.below(i)).getBlock();
            if (!stones.contains(stone) && stone.getTags().contains(new ResourceLocation("forge:stone"))) {
                stones.add(stone);
            }
        }
        if (!context.getLevel().isClientSide && context.getPlayer() != null) {
            PlayerEntity player = context.getPlayer();
            if (!stones.isEmpty()) {
                player.sendMessage(new StringTextComponent("== STONES LIST ==").withStyle(TextFormatting.GOLD),player.getUUID());
                for (Block s : stones) {
                    player.sendMessage(new TranslationTextComponent(s.getDescriptionId()),player.getUUID());
                }
            } else {
                player.sendMessage(new StringTextComponent("No stones detected"),player.getUUID());
            }
            context.getItemInHand().hurtAndBreak(1,player,(p_220040_1_) -> {
                p_220040_1_.broadcastBreakEvent(context.getHand());
            });
            player.getCooldowns().addCooldown(this, 60);
        }


        return ActionResultType.SUCCESS;

    }


}
