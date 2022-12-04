package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.AABB;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ElectromagnetBlock extends DirectionalBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    int type;

    public ElectromagnetBlock(int type) {
        super(Block.Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.EAST).setValue(POWERED, Boolean.FALSE));
        this.type = type;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite()).setValue(POWERED, Boolean.FALSE);
    }

    public void neighborChanged(BlockState stateIn, Level levelIn, BlockPos posIn, Block p_52703_, BlockPos p_52704_, boolean p_52705_) {
        boolean flagSignal = levelIn.hasNeighborSignal(posIn) || levelIn.hasNeighborSignal(posIn.above());
        boolean flagPowered = stateIn.getValue(POWERED);
        if (flagSignal != flagPowered) {
            if (flagPowered) {
                levelIn.scheduleTick(posIn, this, 4);
            } else {
                Direction direction = stateIn.getValue(FACING);
                BlockPos blockpos1 = posIn.relative(direction);
                if (levelIn.isEmptyBlock(blockpos1) || !levelIn.getBlockState(blockpos1).getMaterial().blocksMotion()) {
                    List<BlockPos> blockPosList = BlockPos.betweenClosedStream(blockpos1,blockpos1.offset(direction.getNormal().relative(direction, type * Config.MACHINES.ELECTROMAGNET_RANGE.get() - 1))).map(BlockPos::immutable).collect(Collectors.toList());
                    if(direction == Direction.NORTH || direction == Direction.WEST || direction == Direction.DOWN) {
                        Collections.reverse(blockPosList);
                    }
                    for (BlockPos b : blockPosList) {
                        BlockState BS = levelIn.getBlockState(b);
                        if (levelIn.isEmptyBlock(b) || !BS.getMaterial().blocksMotion() || !levelIn.getFluidState(b).isEmpty()) continue;
                        if (BS.is(RankineTags.Blocks.MAGNET_BANNED) || levelIn.getBlockEntity(b) != null) break;

                        if (BS.getMaterial() == Material.METAL || !Config.MACHINES.ELECTROMAGNET_MATERIAL_REQ.get()) {
                            List<LivingEntity> entities = levelIn.getEntitiesOfClass(LivingEntity.class, new AABB(blockpos1, b).expandTowards(1, 1, 1), (e) -> e instanceof Mob || e instanceof Player);
                            for (LivingEntity i : entities) {
                                i.hurt(DamageSource.FLY_INTO_WALL, Mth.sqrt(type * (float) b.distSqr(i.getOnPos())));
                            }

                            List<LivingEntity> entitiesOnBlock = levelIn.getEntitiesOfClass(LivingEntity.class, new AABB(b, b.above()).expandTowards(1, 1, 1), (e) -> e instanceof Mob || e instanceof Player);
                            for (LivingEntity i : entitiesOnBlock) {
                                i.teleportTo(blockpos1.getX() - (b.getX() - i.getX()), blockpos1.getY() + 1, blockpos1.getZ() - (b.getZ() - i.getZ()));
                            }

                            levelIn.destroyBlock(blockpos1, false);
                            levelIn.setBlockAndUpdate(blockpos1, BS);
                            levelIn.setBlockAndUpdate(b, Blocks.AIR.defaultBlockState());
                            for (BlockPos toBreak : BlockPos.betweenClosed(blockpos1, b)) {
                                if (!levelIn.getBlockState(toBreak).getMaterial().blocksMotion()) {
                                    levelIn.destroyBlock(toBreak, true);
                                }
                                /*
                                if (levelIn.isClientSide()) {
                                    for (int i = 0; i < 2; ++i) {
                                        levelIn.addParticle(ParticleTypes.PORTAL, toBreak.getX() + 0.5D, toBreak.getY() + 0.5D, toBreak.getZ() + 0.5D, 0.2D, 0.2D, 0.2D);
                                    }
                                } */
                            }
                        }
                        break;

                    }

                }
                levelIn.setBlock(posIn, stateIn.cycle(POWERED), 2);
            }

        }
    }

    public void tick(BlockState p_55661_, ServerLevel p_55662_, BlockPos p_55663_, Random p_55664_) {
        if (p_55661_.getValue(POWERED) && !p_55662_.hasNeighborSignal(p_55663_) && !p_55662_.hasNeighborSignal(p_55663_.above())) {
            p_55662_.setBlock(p_55663_, p_55661_.cycle(POWERED), 2);
        }

    }

}
