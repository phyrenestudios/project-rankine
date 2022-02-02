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
        super(Block.Properties.create(Material.IRON, MaterialColor.AIR).sound(SoundType.METAL).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(5.0F, 6.0F));
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.SOUTH).with(POWERED, Boolean.FALSE));
        this.type = type;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite()).with(POWERED, Boolean.FALSE);
    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isRemote) {
            boolean flag = state.get(POWERED);
            if (flag != worldIn.isBlockPowered(pos)) {
                if (flag) {
                    worldIn.getPendingBlockTicks().scheduleTick(pos, this, 4);
                } else {
                    worldIn.setBlockState(pos, state.cycleValue(POWERED), 2);
                    Direction direction = state.get(FACING);
                    BlockPos blockpos = pos.offset(direction);
                    if (worldIn.isAirBlock(blockpos)) {
                        List<BlockPos> blockPosList = BlockPos.getAllInBox(blockpos,blockpos.add(direction.getDirectionVec().offset(direction, type * Config.MACHINES.ELECTROMAGNET_RANGE.get() - 1))).map(BlockPos::toImmutable).collect(Collectors.toList());
                        if(direction == Direction.NORTH || direction == Direction.WEST || direction == Direction.DOWN) {
                            Collections.reverse(blockPosList);
                        }
                        for (BlockPos b : blockPosList) {
                            BlockState BS = worldIn.getBlockState(b);
                            if (!BS.isIn(RankineTags.Blocks.MAGNET_BANNED) && worldIn.getTileEntity(b) == null && worldIn.getFluidState(b).isEmpty()) {
                                if (!worldIn.isAirBlock(b) && BS.getMaterial().blocksMovement()) {
                                    if (BS.getMaterial() == Material.IRON || !Config.MACHINES.ELECTROMAGNET_MATERIAL_REQ.get()) {
                                        List<LivingEntity> entities = worldIn.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(blockpos, b).expand(1, 1, 1), (e) -> e instanceof MobEntity || e instanceof PlayerEntity);
                                        for (LivingEntity i : entities) {
                                            i.attackEntityFrom(DamageSource.FLY_INTO_WALL, MathHelper.sqrt(b.distanceSq(blockpos)));
                                        }

                                        List<LivingEntity> entitiesOnBlock = worldIn.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(b, b.up()).expand(1, 1, 1), (e) -> e instanceof MobEntity || e instanceof PlayerEntity);
                                        for (LivingEntity i : entitiesOnBlock) {
                                            i.setPositionAndUpdate(blockpos.getX() - (b.getX() - i.getPosX()), blockpos.getY() + 1, blockpos.getZ() - (b.getZ() - i.getPosZ()));
                                        }

                                        worldIn.setBlockState(blockpos, BS, 3);
                                        worldIn.setBlockState(b, Blocks.AIR.getDefaultState(), 3);
                                        for (BlockPos toBreak : BlockPos.getAllInBoxMutable(blockpos, b)) {
                                            if (!worldIn.getBlockState(toBreak).getMaterial().blocksMovement()) {
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
        if (state.get(POWERED) && !worldIn.isBlockPowered(pos)) {
            worldIn.setBlockState(pos, state.cycleValue(POWERED), 2);
        }
    }
}
