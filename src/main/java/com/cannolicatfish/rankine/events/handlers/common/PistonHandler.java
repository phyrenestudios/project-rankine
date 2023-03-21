package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.blocks.MiningHeadBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.event.world.PistonEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PistonHandler {

    public static Map<Block, Tier> crushingMap = new HashMap<>();
    public static Map<Block, Tier> miningMap = new HashMap<>();
    static {
        crushingMap.put(RankineBlocks.WOOD_TIER_CRUSHING_HEAD.get(), Tiers.WOOD);
        crushingMap.put(RankineBlocks.STONE_TIER_CRUSHING_HEAD.get(), Tiers.STONE);
        crushingMap.put(RankineBlocks.IRON_TIER_CRUSHING_HEAD.get(), Tiers.IRON);
        crushingMap.put(RankineBlocks.DIAMOND_TIER_CRUSHING_HEAD.get(), Tiers.DIAMOND);
        crushingMap.put(RankineBlocks.NETHERITE_TIER_CRUSHING_HEAD.get(), Tiers.NETHERITE);
        miningMap.put(RankineBlocks.WOOD_TIER_MINING_HEAD.get(), Tiers.WOOD);
        miningMap.put(RankineBlocks.STONE_TIER_MINING_HEAD.get(), Tiers.STONE);
        miningMap.put(RankineBlocks.IRON_TIER_MINING_HEAD.get(), Tiers.IRON);
        miningMap.put(RankineBlocks.DIAMOND_TIER_MINING_HEAD.get(), Tiers.DIAMOND);
        miningMap.put(RankineBlocks.NETHERITE_TIER_MINING_HEAD.get(), Tiers.NETHERITE);
    }

    public static void onPistonExtendPre(PistonEvent.Pre event) {
        if (!event.getPistonMoveType().equals(PistonEvent.PistonMoveType.EXTEND)) return;
        Direction dir = event.getDirection();
        BlockPos facePos = event.getFaceOffsetPos();
        LevelAccessor levelIn = event.getWorld();
        BlockState headState = levelIn.getBlockState(facePos);
        if (levelIn.isClientSide() || !levelIn.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) || levelIn.getServer() == null) return;
        if (levelIn.getBlockState(facePos.relative(dir,2)).isAir()) return;

        if (crushingMap.containsKey(headState.getBlock())) {
            Tier currentTier = crushingMap.get(levelIn.getBlockState(facePos).getBlock());
            BlockState state = levelIn.getBlockState(facePos.relative(dir, 2));
            if (!TierSortingRegistry.isCorrectTierForDrops(currentTier, state)) return;
            int power = headState.getValue(MiningHeadBlock.POWER);
            power += Math.floor(4/state.getBlock().defaultDestroyTime()) + (levelIn.getRandom().nextFloat() <= Math.sqrt(4/state.getBlock().defaultDestroyTime() % 1) ? 1 : 0);
            if (power == headState.getValue(MiningHeadBlock.POWER)) return;
            if (power >= 15) {
                levelIn.destroyBlock(facePos.relative(dir,2), false);
                power = 0;
                for (CrushingRecipe recipe : levelIn.getServer().getRecipeManager().getAllRecipesFor(RankineRecipeTypes.CRUSHING)) {
                    for (ItemStack s : recipe.getIngredientAsStackList().clone()) {
                        if (s.getItem() == state.getBlock().asItem()) {
                            List<ItemStack> results = recipe.getResults(currentTier, levelIn.getRandom(), PeriodicTableUtils.getInstance().getCrushingAmountFromTier(currentTier)  + 1);
                            for (ItemStack t : results) {
                                Block.popResource((ServerLevel) levelIn,facePos.relative(dir,2),t.copy());
                            }
                            break;
                        }
                    }
                }
            }
            levelIn.setBlock(facePos, headState.setValue(MiningHeadBlock.POWER, power),3);


        } else if (miningMap.containsKey(headState.getBlock())) {
            Tier currentTier = miningMap.get(levelIn.getBlockState(facePos).getBlock());
            BlockState state = levelIn.getBlockState(facePos.relative(dir, 2));
            if (!TierSortingRegistry.isCorrectTierForDrops(currentTier, state)) return;
            int power = headState.getValue(MiningHeadBlock.POWER);
            power += Math.floor(4f/state.getDestroySpeed(levelIn, facePos.relative(dir,2))) + (levelIn.getRandom().nextFloat() <= Math.sqrt(4f/state.getDestroySpeed(levelIn, facePos.relative(dir,2)) % 1) ? 1 : 0);
            if (power == headState.getValue(MiningHeadBlock.POWER)) return;
            if (power >= 15) {
                levelIn.destroyBlock(facePos.relative(dir,2), true);
                power = 0;
            }
            levelIn.setBlock(facePos, headState.setValue(MiningHeadBlock.POWER, power),3);
        }


    }

}
