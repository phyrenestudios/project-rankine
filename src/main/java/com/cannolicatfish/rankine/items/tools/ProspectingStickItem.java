package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;
import java.util.Locale;

public class ProspectingStickItem extends Item {

    public ProspectingStickItem(Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level worldIn = context.getLevel();
        Direction searchDir = context.getClickedFace().getOpposite();
        BlockGetter reader = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState blockstate = worldIn.getBlockState(pos);

        if (blockstate.is(RankineTags.Blocks.COBBLES)) {
            if (worldIn.getRandom().nextFloat() < Config.TOOLS.SAMPLE_CHANCE.get()/2f) {
                for (int i = 1; i < pos.getY(); ++i) {
                    Block target = worldIn.getBlockState(pos.below(i)).getBlock();
                    if (VanillaIntegration.oreNuggetMap.containsKey(target) && player != null && !worldIn.isClientSide()) {
                        player.displayClientMessage(Component.translatable("item.rankine.prospecting_stick_cobbles.message", Component.translatable(target.getDescriptionId()).getString()), true);
                        if (this.canBeDepleted() && !worldIn.isClientSide()) {
                            context.getItemInHand().hurtAndBreak(1, player, (p_219998_1_) -> {
                                p_219998_1_.broadcastBreakEvent(context.getHand());
                            });
                        }
                    }
                }
            }
            if (!worldIn.isClientSide) {
                worldIn.destroyBlock(pos, false);
            }
            return InteractionResult.SUCCESS;
        }

        if (worldIn.getRandom().nextFloat() < Config.TOOLS.SAMPLE_CHANCE.get()) {
            for (int x = 0; x <= Config.TOOLS.PROSPECTING_STICK_RANGE.get(); x++) {
                if (reader.getBlockState(pos.relative(searchDir, x)).is(Tags.Blocks.ORES)) {
                    BlockState ORE = reader.getBlockState(pos.relative(searchDir, x));
                    if (player != null) {
                        worldIn.playSound(player, pos, SoundEvents.NOTE_BLOCK_BELL.get(), SoundSource.PLAYERS, 1.0F, worldIn.getRandom().nextFloat() * 0.4F + 0.8F);
                        if (!worldIn.isClientSide()) {
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
                            player.displayClientMessage(Component.translatable("item.rankine.prospecting_stick.message", Component.translatable(ORE.getBlock().getDescriptionId()), cons), true);
                            context.getItemInHand().hurtAndBreak(1, player, (p) -> {
                                p.broadcastBreakEvent(context.getHand());
                            });
                        }
                    }
                    return InteractionResult.SUCCESS;
                }

            }
        }

        return InteractionResult.SUCCESS;
    }
}
