package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.Config;
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
import net.minecraftforge.event.world.PistonEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PistonHandler {

    public static Map<Block, Tier> crushingMap = new HashMap<>();
    static {
        crushingMap.put(RankineBlocks.WOOD_TIER_CRUSHING_HEAD.get(), Tiers.WOOD);
        crushingMap.put(RankineBlocks.STONE_TIER_CRUSHING_HEAD.get(), Tiers.STONE);
        crushingMap.put(RankineBlocks.IRON_TIER_CRUSHING_HEAD.get(), Tiers.IRON);
        crushingMap.put(RankineBlocks.DIAMOND_TIER_CRUSHING_HEAD.get(), Tiers.DIAMOND);
        crushingMap.put(RankineBlocks.NETHERITE_TIER_CRUSHING_HEAD.get(), Tiers.NETHERITE);
    }

    public static void onPistonExtendPre(PistonEvent.Pre event) {

        if (event.getWorld().getRandom().nextFloat() > Config.MACHINES.CRUSHING_SUCCESS_CHANCE.get()) return;

        Direction dir = event.getDirection();
        BlockPos facePos = event.getFaceOffsetPos();
        LevelAccessor levelIn = event.getWorld();

        if (event.getPistonMoveType().equals(PistonEvent.PistonMoveType.EXTEND) && crushingMap.containsKey(levelIn.getBlockState(facePos).getBlock())) {
            if (!levelIn.isClientSide() && levelIn.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && levelIn.getServer() != null) {
                BlockState state = levelIn.getBlockState(facePos.relative(dir,2));
                Tier currentTier = crushingMap.get(levelIn.getBlockState(facePos).getBlock());
                for (CrushingRecipe recipe : levelIn.getServer().getRecipeManager().getAllRecipesFor(RankineRecipeTypes.CRUSHING)) {
                    for (ItemStack s : recipe.getIngredientAsStackList().clone()) {
                        if (s.getItem() == state.getBlock().asItem()) {
                            List<ItemStack> results = recipe.getResults(currentTier, levelIn.getRandom(), PeriodicTableUtils.getInstance().getCrushingAmountFromTier(currentTier)  + 1);

                            levelIn.destroyBlock(facePos.relative(dir,2), false);
                            for (ItemStack t : results) {
                                Block.popResource((ServerLevel) levelIn,facePos.relative(dir,2),t.copy());
                            }
                            break;
                        }
                    }
                }

            }
        }


    }

}
