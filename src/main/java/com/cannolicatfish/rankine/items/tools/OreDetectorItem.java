package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import net.minecraft.world.item.Item.Properties;

public class OreDetectorItem extends Item {

    public OreDetectorItem(Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();

        Optional<BlockPos> b = BlockPos.findClosestMatch(pos, Config.TOOLS.ORE_DETECTOR_RANGE.get(), Config.TOOLS.ORE_DETECTOR_RANGE.get(), (p) -> worldIn.getBlockState(p).is(Tags.Blocks.ORES));
        if (player != null && b.isPresent()) {
            worldIn.playSound(player,pos, SoundEvents.NOTE_BLOCK_BELL, SoundSource.PLAYERS,1.0F, worldIn.getRandom().nextFloat() * 0.4F + 0.8F);

            if (!worldIn.isClientSide()) {
                BlockState ORE = worldIn.getBlockState(b.get());
                List<Tier> tiers = TierSortingRegistry.getSortedTiers();
                Tier currentTier = Tiers.WOOD;
                for (Tier tier : tiers) {
                    if (TierSortingRegistry.isTierSorted(tier) && TierSortingRegistry.isCorrectTierForDrops(tier,ORE)) {
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
                player.displayClientMessage(new TranslatableComponent("item.rankine.ore_detector.message", new TranslatableComponent(ORE.getBlock().getDescriptionId()).getString(), cons, b.get().getX(), b.get().getY(), b.get().getZ()), false);
                context.getItemInHand().hurtAndBreak(1, player, (p) -> {
                    p.broadcastBreakEvent(context.getHand());
                });
            }
        }
        return InteractionResult.SUCCESS;
    }
}
