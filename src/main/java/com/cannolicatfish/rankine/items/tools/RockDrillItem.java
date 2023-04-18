package com.cannolicatfish.rankine.items.tools;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.List;

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
            if (!stones.contains(stone) && reader.getBlockState(context.getClickedPos()).is(Tags.Blocks.STONE)) {
                stones.add(stone);
            }
        }
        if (!context.getLevel().isClientSide && context.getPlayer() != null) {
            Player player = context.getPlayer();
            if (!stones.isEmpty()) {
                player.sendSystemMessage(Component.literal("== STONES LIST ==").withStyle(ChatFormatting.GOLD));
                for (Block s : stones) {
                    player.sendSystemMessage(Component.translatable(s.getDescriptionId()));
                }
            } else {
                player.sendSystemMessage(Component.literal("No stones detected"));
            }
            context.getItemInHand().hurtAndBreak(1,player,(p_220040_1_) -> {
                p_220040_1_.broadcastBreakEvent(context.getHand());
            });
            player.getCooldowns().addCooldown(this, 60);
        }


        return InteractionResult.SUCCESS;

    }


}
