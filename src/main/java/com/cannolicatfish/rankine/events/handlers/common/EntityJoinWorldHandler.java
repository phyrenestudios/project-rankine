package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.entities.goals.EatGrassGoalModified;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityJoinWorldHandler {
    public static void onLightningEvent(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof LightningBolt) {
            LightningBolt entity = (LightningBolt) event.getEntity();
            Level worldIn = event.getWorld();
            BlockPos startPos = entity.blockPosition().below();
            if (!worldIn.isClientSide && Config.GENERAL.LIGHTNING_CONVERSION.get()) {
                Iterable<BlockPos> positions = BlockPos.withinManhattan(startPos,2,2,2);
                for (BlockPos pos : positions) {
                    double rand;
                    if (startPos.getX() == pos.getX() && startPos.getZ() == pos.getZ()) {
                        rand = 1/(1f + Math.abs(startPos.getY() - pos.getY()));
                    } else {
                        rand = pos.distSqr(new Vec3i(startPos.getX(),startPos.getY(),startPos.getZ()));
                    }

                    Block BLK = worldIn.getBlockState(pos).getBlock();
                    if (worldIn.getRandom().nextFloat() < 1/rand && ForgeRegistries.BLOCKS.tags().getTag(RankineTags.Blocks.LIGHTNING_VITRIFIED).contains(BLK)) {
                        worldIn.setBlock(pos, RankineBlocks.FULGURITE.get().defaultBlockState(),3);
                    } else if (worldIn.getRandom().nextFloat() < 1/rand && BLK.equals(Blocks.SAND) || BLK.equals(RankineBlocks.SILT.get()) || BLK.equals(RankineBlocks.DESERT_SAND.get())) {
                        worldIn.setBlock(pos,RankineBlocks.LIGHTNING_GLASS.get().defaultBlockState(),3);
                    } else if (worldIn.getRandom().nextFloat() < 1/rand && BLK.equals(Blocks.RED_SAND)) {
                        worldIn.setBlock(pos,RankineBlocks.RED_LIGHTNING_GLASS.get().defaultBlockState(),3);
                    } else if (worldIn.getRandom().nextFloat() < 1/rand && BLK.equals(Blocks.SOUL_SAND)) {
                        worldIn.setBlock(pos,RankineBlocks.SOUL_LIGHTNING_GLASS.get().defaultBlockState(),3);
                    } else if (worldIn.getRandom().nextFloat() < 1/rand && BLK.equals(RankineBlocks.BLACK_SAND.get())) {
                        worldIn.setBlock(pos,RankineBlocks.BLACK_LIGHTNING_GLASS.get().defaultBlockState(),3);
                    } else if (worldIn.getRandom().nextFloat() < 1/rand && BLK.equals(RankineBlocks.WHITE_SAND.get())) {
                        worldIn.setBlock(pos,RankineBlocks.WHITE_LIGHTNING_GLASS.get().defaultBlockState(),3);
                    }
                }
            }
        }
    }

    public static void onSheepJoinWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Sheep ent) {
            ent.goalSelector.removeGoal(new EatBlockGoal(ent));
            ent.goalSelector.addGoal(5,new EatGrassGoalModified(ent));
            ent.goalSelector.removeGoal(new TemptGoal(ent, 1.1D, Ingredient.of(Items.WHEAT), false));
            ent.goalSelector.addGoal(3,new TemptGoal(ent, 1.1D, Ingredient.of(RankineTags.Items.BREEDABLES_SHEEP), false));
        } else if (entity instanceof Cow ent) {
            ent.goalSelector.addGoal(3,new TemptGoal(ent, 1.25D, Ingredient.of(RankineTags.Items.BREEDABLES_COW), false));
        } else if (entity instanceof Pig ent) {
            ent.goalSelector.addGoal(4,new TemptGoal(ent, 1.2D, Ingredient.of(RankineTags.Items.BREEDABLES_PIG), false));
        } else if (entity instanceof Chicken ent) {
            ent.goalSelector.addGoal(3,new TemptGoal(ent, 1.0D, Ingredient.of(RankineTags.Items.BREEDABLES_CHICKEN), false));
        } else if (entity instanceof Rabbit ent) {
            ent.goalSelector.addGoal(3,new TemptGoal(ent, 1.0D, Ingredient.of(RankineTags.Items.BREEDABLES_RABBIT), false));
        }
    }
}
