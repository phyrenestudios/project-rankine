package com.cannolicatfish.rankine.items.tools;

import net.minecraft.world.item.Tiers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Locale;

public class HardnessTesterItem extends Item {
    public HardnessTesterItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        Player player = context.getPlayer();
        if (!world.isClientSide && player != null)
        {
            BlockState b = world.getBlockState(context.getClickedPos());
            player.sendSystemMessage(Component.literal("Harvest Level: " + getHarvestTierName(b)));
        }


        return InteractionResult.SUCCESS;
    }

    /**
     * Lowest tier whose "incorrect for drops" tag does not contain the block.
     * PORT-TODO: TierSortingRegistry was removed in 1.21 — only the vanilla tiers are
     * checked now (modded tiers registered via TierSortingRegistry are no longer reported).
     */
    static String getHarvestTierName(BlockState state) {
        Tiers currentTier = Tiers.WOOD;
        for (Tiers tier : new Tiers[]{Tiers.WOOD, Tiers.STONE, Tiers.IRON, Tiers.DIAMOND, Tiers.NETHERITE}) {
            if (!state.is(tier.getIncorrectBlocksForDrops())) {
                currentTier = tier;
                break;
            }
        }
        String path = currentTier.name().toLowerCase(Locale.ROOT);
        return path.toUpperCase(Locale.ROOT).charAt(0) + path.substring(1);
    }
}
