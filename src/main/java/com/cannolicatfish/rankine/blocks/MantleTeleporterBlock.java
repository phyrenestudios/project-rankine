package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.dimension.ModDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class MantleTeleporterBlock extends Block {
    public MantleTeleporterBlock(Properties properties) {
        super(properties);
    }


    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) { // PREP MANTLE BEFORE TELEPORT
        if (!worldIn.isRemote && !entityIn.isPassenger() && !entityIn.isBeingRidden() && entityIn.isNonBoss() && entityIn.isCrouching()) {
            entityIn.changeDimension(worldIn.dimension.getType() == ModDimensions.MANTLE_DIMENSION ? DimensionType.OVERWORLD : ModDimensions.MANTLE_DIMENSION, new ITeleporter() {
                @Override
                public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                    return repositionEntity.apply(false);
                }
            });
            BlockPos x = new BlockPos(entityIn.getPosX(),entityIn.getPosY() - 1, entityIn.getPosZ());
            IWorld newworld = entityIn.getEntityWorld();
            if (newworld.getBlockState(x) != this.getDefaultState() && newworld.getDimension().getType() == ModDimensions.MANTLE_DIMENSION)
            {
                BlockPos x2 = new BlockPos(entityIn.getPosX(),entityIn.getPosY(),entityIn.getPosZ());
                BlockPos x3 = new BlockPos(entityIn.getPosX(),entityIn.getPosY() + 1,entityIn.getPosZ());
                BlockPos x4 = new BlockPos(entityIn.getPosX(),entityIn.getPosY() + 2,entityIn.getPosZ());

                for(int k = 0; k < 3; k++) {
                    for(int s = 0; s < 3; s++) {
                        newworld.setBlockState(new BlockPos(entityIn.getPosX() + k,entityIn.getPosY() - 1,entityIn.getPosZ() + s), ModBlocks.PERIDOTITE.getDefaultState(),2);
                        newworld.setBlockState(new BlockPos(entityIn.getPosX() - k,entityIn.getPosY() - 1,entityIn.getPosZ() - s), ModBlocks.PERIDOTITE.getDefaultState(),2);
                    }
                }
                for(int k = 0; k < 3; k++) {
                    for(int s = 0; s < 3; s++) {
                        for (int y = 0; y < 3; y++)
                        {
                            newworld.setBlockState(new BlockPos(entityIn.getPosX() + k,entityIn.getPosY() + y,entityIn.getPosZ() + k), Blocks.AIR.getDefaultState(),2);
                            newworld.setBlockState(new BlockPos(entityIn.getPosX() - k,entityIn.getPosY() + y,entityIn.getPosZ() - k), Blocks.AIR.getDefaultState(),2);
                        }
                    }
                }

                newworld.setBlockState(x2, Blocks.AIR.getDefaultState(),2);
                newworld.setBlockState(x3, Blocks.AIR.getDefaultState(),2);
                newworld.setBlockState(x4, Blocks.AIR.getDefaultState(),2);
                newworld.setBlockState(x, this.getDefaultState(),2);
            }

        }
    }
}
