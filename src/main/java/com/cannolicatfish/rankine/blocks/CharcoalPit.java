package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.init.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CharcoalPit extends Block {
    public CharcoalPit(Properties properties) {
        super(properties);
    }

    int TICKS = 1;
    int RADIUS = Config.CHARCOAL_PIT_RADIUS.get();
    int HEIGHT = 0;


    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState();
    }

    @Override
    public boolean ticksRandomly(BlockState stateIn) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        super.tick(state, worldIn, pos, rand);
        if (pileCheck(worldIn,pos)) {
            if (TICKS % Config.CHARCOAL_PIT_SPEED.get() == 0) {
                for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-RADIUS, 0, -RADIUS), pos.add(RADIUS, heightCheck(worldIn, pos), RADIUS))) {
                    if (worldIn.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("minecraft:logs_that_burn"))) {
                        Item LogItem = worldIn.getBlockState(blockpos).getBlock().asItem();
                        if (LogItem.getBurnTime(new ItemStack(LogItem,1)) > 500) {
                            worldIn.setBlockState(blockpos, ModBlocks.CHARRED_WOOD.getDefaultState().with(CharredWood.TYPE,3));
                        } else if (LogItem.getBurnTime(new ItemStack(LogItem,1)) > 400) {
                            worldIn.setBlockState(blockpos, ModBlocks.CHARRED_WOOD.getDefaultState().with(CharredWood.TYPE,2));
                        } else if (LogItem.getBurnTime(new ItemStack(LogItem,1)) > 300) {
                            worldIn.setBlockState(blockpos, ModBlocks.CHARRED_WOOD.getDefaultState().with(CharredWood.TYPE,1));
                        } else {
                            worldIn.setBlockState(blockpos, ModBlocks.CHARRED_WOOD.getDefaultState().with(CharredWood.TYPE,0));
                        }
                    }
                }
                for (int i = 0; i <= Config.CHARCOAL_PIT_HEIGHT.get(); ++i) {
                    if (worldIn.getBlockState(pos.up(i)) == this.getDefaultState()) {
                        worldIn.setBlockState(pos.up(i), ModBlocks.CHARRED_WOOD.getDefaultState());
                    }
                }
            }
            TICKS += 1;
        }
    }


    private int heightCheck(ServerWorld worldIn, BlockPos pos) {
        for (int i = 1; i <= Config.CHARCOAL_PIT_HEIGHT.get(); ++i) {
            if (worldIn.getBlockState(pos.up(i)) == this.getDefaultState()) {
                ++HEIGHT;
            } else {
                break;
            }
        }
        return HEIGHT;
    }

    private boolean pileCheck(ServerWorld worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos.down()) == this.getDefaultState()) {
            return false;
        }
        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-RADIUS, 0, -RADIUS), pos.add(RADIUS, heightCheck(worldIn, pos), RADIUS))) {
            if (worldIn.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("minecraft:logs_that_burn")) || worldIn.getBlockState(blockpos) == this.getDefaultState()) {
                if (!worldIn.getBlockState(blockpos.up()).isOpaqueCube(worldIn, blockpos.up()) || !worldIn.getBlockState(blockpos.down()).isOpaqueCube(worldIn, blockpos.down()) || !worldIn.getBlockState(blockpos.east()).isOpaqueCube(worldIn, blockpos.east()) || !worldIn.getBlockState(blockpos.south()).isOpaqueCube(worldIn, blockpos.south()) || !worldIn.getBlockState(blockpos.north()).isOpaqueCube(worldIn, blockpos.north()) || !worldIn.getBlockState(blockpos.west()).isOpaqueCube(worldIn, blockpos.west())) {
                    return false;
                }
            }
        }
        return true;
    }


}
