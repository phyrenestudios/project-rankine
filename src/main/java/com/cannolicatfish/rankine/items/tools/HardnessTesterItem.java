package com.cannolicatfish.rankine.items.tools;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;
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
            List<Tier> tiers = TierSortingRegistry.getSortedTiers();
            Tier currentTier = Tiers.WOOD;
            for (Tier tier : tiers) {
                if (TierSortingRegistry.isTierSorted(tier) && TierSortingRegistry.isCorrectTierForDrops(tier,b)) {
                    currentTier = tier;
                    break;
                }
            }

            ResourceLocation rs = TierSortingRegistry.getName(currentTier);
            String cons;
            if (rs != null) {
                cons = rs.getPath().toUpperCase(Locale.ROOT).charAt(0) + rs.getPath().substring(1);
            } else {
                cons = "None";
            }
            player.sendMessage(new TextComponent("Harvest Level: " + cons),player.getUUID());
        }


        return InteractionResult.SUCCESS;
    }
}
