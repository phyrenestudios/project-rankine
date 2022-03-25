package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class ElectromagnetBlock extends DirectionalBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    int type;

    public ElectromagnetBlock(int type) {
        super(Block.Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.SOUTH).setValue(POWERED, Boolean.FALSE));
        this.type = type;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite()).setValue(POWERED, Boolean.FALSE);
    }

    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isClientSide) {
            boolean flag = state.getValue(POWERED);
            if (flag != worldIn.hasNeighborSignal(pos)) {
                if (flag) {
                    worldIn.getBlockTicks().scheduleTick(pos, this, 4);
                } else {
                    worldIn.setBlock(pos, state.cycle(POWERED), 2);
                    Direction direction = state.getValue(FACING);
                    BlockPos blockpos = pos.relative(direction);
                    if (worldIn.isEmptyBlock(blockpos)) {
                        List<BlockPos> blockPosList = BlockPos.betweenClosedStream(blockpos,blockpos.offset(direction.getNormal().relative(direction, type * Config.MACHINES.ELECTROMAGNET_RANGE.get() - 1))).map(BlockPos::immutable).collect(Collectors.toList());
                        if(direction == Direction.NORTH || direction == Direction.WEST || direction == Direction.DOWN) {
                            Collections.reverse(blockPosList);
                        }
                        for (BlockPos b : blockPosList) {
                            BlockState BS = worldIn.getBlockState(b);
                            if (!BS.is(RankineTags.Blocks.MAGNET_BANNED) && worldIn.getBlockEntity(b) == null && worldIn.getFluidState(b).isEmpty()) {
                                if (!worldIn.isEmptyBlock(b) && BS.getMaterial().blocksMotion()) {
                                    if (BS.getMaterial() == Material.METAL || !Config.MACHINES.ELECTROMAGNET_MATERIAL_REQ.get()) {
                                        List<LivingEntity> entities = worldIn.getEntitiesOfClass(LivingEntity.class, new AABB(blockpos, b).expandTowards(1, 1, 1), (e) -> e instanceof Mob || e instanceof Player);
                                        for (LivingEntity i : entities) {
                                            i.hurt(DamageSource.FLY_INTO_WALL, Mth.sqrt((float) b.distSqr(blockpos)));
                                        }

                                        List<LivingEntity> entitiesOnBlock = worldIn.getEntitiesOfClass(LivingEntity.class, new AABB(b, b.above()).expandTowards(1, 1, 1), (e) -> e instanceof Mob || e instanceof Player);
                                        for (LivingEntity i : entitiesOnBlock) {
                                            i.teleportTo(blockpos.getX() - (b.getX() - i.getX()), blockpos.getY() + 1, blockpos.getZ() - (b.getZ() - i.getZ()));
                                        }

                                        worldIn.setBlock(blockpos, BS, 3);
                                        worldIn.setBlock(b, Blocks.AIR.defaultBlockState(), 3);
                                        for (BlockPos toBreak : BlockPos.betweenClosed(blockpos, b)) {
                                            if (!worldIn.getBlockState(toBreak).getMaterial().blocksMotion()) {
                                                worldIn.destroyBlock(toBreak, true);
                                            }
                                        }
                                    }
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
            }

        }
    }

    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
        if (state.getValue(POWERED) && !worldIn.hasNeighborSignal(pos)) {
            worldIn.setBlock(pos, state.cycle(POWERED), 2);
        }
    }
}
