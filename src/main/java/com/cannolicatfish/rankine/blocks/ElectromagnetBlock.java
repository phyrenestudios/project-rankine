package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ElectromagnetBlock extends DirectionalBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    int type;

    public ElectromagnetBlock(int type) {
        super(Block.Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(5.0F, 6.0F));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.SOUTH).setValue(POWERED, Boolean.FALSE));
        this.type = type;
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite()).setValue(POWERED, Boolean.FALSE);
    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
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
                                        List<LivingEntity> entities = worldIn.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(blockpos, b).expandTowards(1, 1, 1), (e) -> e instanceof MobEntity || e instanceof PlayerEntity);
                                        for (LivingEntity i : entities) {
                                            i.hurt(DamageSource.FLY_INTO_WALL, MathHelper.sqrt(b.distSqr(blockpos)));
                                        }

                                        List<LivingEntity> entitiesOnBlock = worldIn.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(b, b.above()).expandTowards(1, 1, 1), (e) -> e instanceof MobEntity || e instanceof PlayerEntity);
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

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (state.getValue(POWERED) && !worldIn.hasNeighborSignal(pos)) {
            worldIn.setBlock(pos, state.cycle(POWERED), 2);
        }
    }
}
