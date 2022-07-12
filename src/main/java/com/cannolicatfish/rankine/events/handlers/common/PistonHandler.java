package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.LevelData;
import net.minecraftforge.event.world.PistonEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PistonHandler {

    public static Map<Block, Tier> crushingMap = new HashMap<>();
    static {
        crushingMap.put(RankineBlocks.ROSE_METAL_BLOCK.get(), Tiers.WOOD);
        crushingMap.put(RankineBlocks.BRASS_BLOCK.get(), Tiers.STONE);
        crushingMap.put(RankineBlocks.CAST_IRON_BLOCK.get(), Tiers.IRON);
        crushingMap.put(RankineBlocks.TITANIUM_ALLOY_BLOCK.get(), Tiers.DIAMOND);
        crushingMap.put(RankineBlocks.TUNGSTEN_BLOCK.get(), Tiers.NETHERITE);
    }

    public static void onPistonExtend(PistonEvent.Post event) {

        if (event.getPistonMoveType().equals(PistonEvent.PistonMoveType.EXTEND) && crushingMap.containsKey(event.getWorld().getBlockState(event.getFaceOffsetPos().offset(event.getDirection().getNormal()).offset(event.getDirection().getNormal())).getBlock())) {
            LevelAccessor levelIn = event.getWorld();
            if (!levelIn.isClientSide() && levelIn.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && levelIn.getServer() != null) {
                BlockPos pos = event.getFaceOffsetPos().offset(event.getDirection().getNormal());
                BlockState state = levelIn.getBlockState(pos);
                Tier currentTier = crushingMap.get(event.getWorld().getBlockState(pos.offset(event.getDirection().getNormal())).getBlock());
                for (CrushingRecipe recipe : levelIn.getServer().getRecipeManager().getAllRecipesFor(RankineRecipeTypes.CRUSHING)) {
                    for (ItemStack s : recipe.getIngredientAsStackList().clone()) {
                        if (s.getItem() == state.getBlock().asItem()) {
                            double d0 = (double) (levelIn.getRandom().nextFloat() * 0.5F) + 0.25D;
                            double d1 = (double) (levelIn.getRandom().nextFloat() * 0.5F) + 0.25D;
                            double d2 = (double) (levelIn.getRandom().nextFloat() * 0.5F) + 0.25D;
                            List<ItemStack> results = recipe.getResults(currentTier, levelIn.getRandom(), PeriodicTableUtils.getInstance().getCrushingAmountFromTier(currentTier)  + 1);
                            for (ItemStack t : results) {
                                ItemEntity itementity = new ItemEntity(levelIn.getServer().overworld(), (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, t.copy());
                                itementity.setDefaultPickUpDelay();
                                levelIn.addFreshEntity(itementity);
                            }
                            levelIn.destroyBlock(pos, false);
                            break;
                        }
                    }
                }

            }
        }
    }

}
