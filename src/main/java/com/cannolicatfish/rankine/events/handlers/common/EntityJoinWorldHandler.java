package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.entities.goals.EatGrassGoalModified;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EntityJoinWorldHandler {

    public static void onLightningEvent( EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof LightningBoltEntity) {
            LightningBoltEntity entity = (LightningBoltEntity) event.getEntity();
            World worldIn = event.getWorld();
            BlockPos startPos = entity.getPosition().down();
            if (!worldIn.isRemote && Config.GENERAL.LIGHTNING_CONVERSION.get()) {
                Iterable<BlockPos> positions = BlockPos.getProximitySortedBoxPositionsIterator(startPos,2,2,2);
                for (BlockPos pos : positions) {
                    double rand;
                    if (startPos.getX() == pos.getX() && startPos.getZ() == pos.getZ()) {
                        rand = 1/(1f + Math.abs(startPos.getY() - pos.getY()));
                    } else {
                        rand = pos.distanceSq(startPos.getX(),startPos.getY(),startPos.getZ(),true);
                    }

                    Block BLK = worldIn.getBlockState(pos).getBlock();
                    if (worldIn.getRandom().nextFloat() < 1/rand && BLK.isIn(RankineTags.Blocks.LIGHTNING_VITRIFIED)) {
                        worldIn.setBlockState(pos, RankineBlocks.FULGURITE.get().getDefaultState(), 3);
                    } else if (worldIn.getRandom().nextFloat() < 1/rand && BLK.matchesBlock(Blocks.SAND) || BLK.matchesBlock(RankineBlocks.SILT.get()) || BLK.matchesBlock(RankineBlocks.DESERT_SAND.get())) {
                        worldIn.setBlockState(pos,RankineBlocks.LIGHTNING_GLASS.get().getDefaultState(),3);
                    } else if (worldIn.getRandom().nextFloat() < 1/rand && BLK.matchesBlock(Blocks.RED_SAND)) {
                        worldIn.setBlockState(pos,RankineBlocks.RED_LIGHTNING_GLASS.get().getDefaultState(),3);
                    } else if (worldIn.getRandom().nextFloat() < 1/rand && BLK.matchesBlock(Blocks.SOUL_SAND)) {
                        worldIn.setBlockState(pos,RankineBlocks.SOUL_LIGHTNING_GLASS.get().getDefaultState(),3);
                    } else if (worldIn.getRandom().nextFloat() < 1/rand && BLK.matchesBlock(RankineBlocks.BLACK_SAND.get())) {
                        worldIn.setBlockState(pos,RankineBlocks.BLACK_LIGHTNING_GLASS.get().getDefaultState(),3);
                    } else if (worldIn.getRandom().nextFloat() < 1/rand && BLK.matchesBlock(RankineBlocks.WHITE_SAND.get())) {
                        worldIn.setBlockState(pos,RankineBlocks.WHITE_LIGHTNING_GLASS.get().getDefaultState(),3);
                    }
                }
            }
        }
    }

    public static void onSheepJoinWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof SheepEntity) {
            SheepEntity ent = (SheepEntity) entity;
            ent.goalSelector.removeGoal(new EatGrassGoal(ent));
            ent.goalSelector.addGoal(5,new EatGrassGoalModified(ent));
            ent.goalSelector.removeGoal(new TemptGoal(ent, 1.1D, Ingredient.fromItems(Items.WHEAT), false));
            ent.goalSelector.addGoal(3,new TemptGoal(ent, 1.1D, Ingredient.fromTag(RankineTags.Items.BREEDABLES_SHEEP), false));
        } else if (entity instanceof CowEntity) {
            CowEntity ent = (CowEntity) entity;
            //ent.goalSelector.removeGoal(new TemptGoal(ent, 1.1D, Ingredient.fromItems(Items.WHEAT), false));
            ent.goalSelector.addGoal(3,new TemptGoal(ent, 1.25D, Ingredient.fromTag(RankineTags.Items.BREEDABLES_COW), false));
        } else if (entity instanceof PigEntity) {
            PigEntity ent = (PigEntity) entity;
            //ent.goalSelector.removeGoal(new TemptGoal(ent, 1.1D, Ingredient.fromItems(Items.WHEAT), false));
            ent.goalSelector.addGoal(4,new TemptGoal(ent, 1.2D, Ingredient.fromTag(RankineTags.Items.BREEDABLES_PIG), false));
        } else if (entity instanceof ChickenEntity) {
            ChickenEntity ent = (ChickenEntity) entity;
            //ent.goalSelector.removeGoal(new TemptGoal(ent, 1.1D, Ingredient.fromItems(Items.WHEAT), false));
            ent.goalSelector.addGoal(3,new TemptGoal(ent, 1.0D, Ingredient.fromTag(RankineTags.Items.BREEDABLES_CHICKEN), false));
        } else if (entity instanceof RabbitEntity) {
            RabbitEntity ent = (RabbitEntity) entity;
            //ent.goalSelector.removeGoal(new TemptGoal(ent, 1.1D, Ingredient.fromItems(Items.WHEAT), false));
            ent.goalSelector.addGoal(3,new TemptGoal(ent, 1.0D, Ingredient.fromTag(RankineTags.Items.BREEDABLES_RABBIT), false));
        }
    }
}
