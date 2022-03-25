package com.cannolicatfish.rankine.items.tools;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.BlockGetter;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class RockDrillItem extends Item {

    public RockDrillItem(Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext context) {
        BlockGetter reader = context.getLevel();
        BlockPos pos = context.getClickedPos();
        List<Block> stones = new ArrayList<Block>();

        for (int i=0; i<= pos.getY(); ++i) {
            Block stone = reader.getBlockState(pos.below(i)).getBlock();
            if (!stones.contains(stone) && stone.getTags().contains(new ResourceLocation("forge:stone"))) {
                stones.add(stone);
            }
        }
        if (!context.getLevel().isClientSide && context.getPlayer() != null) {
            Player player = context.getPlayer();
            if (!stones.isEmpty()) {
                player.sendMessage(new TextComponent("== STONES LIST ==").withStyle(ChatFormatting.GOLD),player.getUUID());
                for (Block s : stones) {
                    player.sendMessage(new TranslatableComponent(s.getDescriptionId()),player.getUUID());
                }
            } else {
                player.sendMessage(new TextComponent("No stones detected"),player.getUUID());
            }
            context.getItemInHand().hurtAndBreak(1,player,(p_220040_1_) -> {
                p_220040_1_.broadcastBreakEvent(context.getHand());
            });
            player.getCooldowns().addCooldown(this, 60);
        }


        return InteractionResult.SUCCESS;

    }


}
