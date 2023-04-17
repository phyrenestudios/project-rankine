package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.items.MagnetItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.phys.BlockHitResult;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ElectromagnetBlock extends DirectionalBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty REPULSION_MODE = BooleanProperty.create("repulsion_mode");
    int type;

    public ElectromagnetBlock(int type) {
        super(Block.Properties.of(Material.METAL, MaterialColor.NONE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.EAST).setValue(POWERED, Boolean.FALSE).setValue(REPULSION_MODE, Boolean.FALSE));
        this.type = type;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, REPULSION_MODE);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite()).setValue(POWERED, Boolean.FALSE).setValue(REPULSION_MODE, Boolean.FALSE);
    }

    public void tick(BlockState p_55661_, ServerLevel p_55662_, BlockPos p_55663_, Random p_55664_) {
        if (p_55661_.getValue(POWERED) && !p_55662_.hasNeighborSignal(p_55663_) && !p_55662_.hasNeighborSignal(p_55663_.above())) {
            p_55662_.setBlock(p_55663_, p_55661_.cycle(POWERED), 2);
        }
    }

    @Override
    public InteractionResult use(BlockState stateIn, Level levelIn, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult p_151974_) {
        if (playerIn.getItemInHand(handIn).getItem() instanceof MagnetItem) {
            levelIn.setBlockAndUpdate(pos,stateIn.cycle(REPULSION_MODE));
            return InteractionResult.sidedSuccess(levelIn.isClientSide);
        }
        return InteractionResult.PASS;
    }

    public void neighborChanged(BlockState stateIn, Level levelIn, BlockPos posIn, Block p_52703_, BlockPos p_52704_, boolean p_52705_) {
        boolean flagSignal = levelIn.hasNeighborSignal(posIn) || levelIn.hasNeighborSignal(posIn.above());
        boolean flagPowered = stateIn.getValue(POWERED);
        if (flagSignal == flagPowered) return;
        if (flagPowered) {
            levelIn.scheduleTick(posIn, this, 4);
            return;
        }
        levelIn.setBlock(posIn, stateIn.cycle(POWERED), 2);
        Direction direction = stateIn.getValue(FACING);
        if (stateIn.getValue(REPULSION_MODE)) {
            List<BlockPos> blockPosList = BlockPos.betweenClosedStream(posIn.relative(direction),posIn.offset(direction.getNormal().relative(direction, 1+type * Config.MACHINES.ELECTROMAGNET_RANGE.get()))).map(BlockPos::immutable).collect(Collectors.toList());
            if (direction.getAxisDirection() == Direction.AxisDirection.POSITIVE) {Collections.reverse(blockPosList);}
            for (BlockPos b : blockPosList) {
                pushBlock(levelIn, posIn, b.immutable(), direction);
            }
        } else {
            List<BlockPos> blockPosList = BlockPos.betweenClosedStream(posIn.relative(direction,2),posIn.offset(direction.getNormal().relative(direction, type * Config.MACHINES.ELECTROMAGNET_RANGE.get()))).map(BlockPos::immutable).collect(Collectors.toList());
            if (direction.getAxisDirection() == Direction.AxisDirection.NEGATIVE) {Collections.reverse(blockPosList);}
            for (BlockPos b : blockPosList) {
                pullBlock(levelIn, posIn, b.immutable(), direction);
            }
        }
    }

    private void pullBlock(Level levelIn, BlockPos posIn, BlockPos targetPosIn, Direction dirIn) {
        BlockState pullTargetBS = levelIn.getBlockState(targetPosIn);
        if (!pullTargetBS.getMaterial().blocksMotion() || !levelIn.getFluidState(targetPosIn).isEmpty() || pullTargetBS.is(RankineTags.Blocks.MAGNET_BANNED) || levelIn.getBlockEntity(targetPosIn) != null) return;
        if (Config.MACHINES.ELECTROMAGNET_MATERIAL_REQ.get() && pullTargetBS.getMaterial() != Material.METAL) return;

        // Find the closest open space and move the block
        BlockPos endPos = null;
        List<BlockPos> blockPosList = BlockPos.betweenClosedStream(targetPosIn.relative(dirIn.getOpposite()), posIn.relative(dirIn)).map(BlockPos::immutable).collect(Collectors.toList());
        if (dirIn.getAxisDirection() == Direction.AxisDirection.POSITIVE) {Collections.reverse(blockPosList);}
        for (BlockPos path : blockPosList) {
            if (isMagnetBreakable(levelIn, path)) {
                levelIn.destroyBlock(path, true);
                continue;
            }
            endPos = path.relative(dirIn);
            break;
        }
        if (endPos == targetPosIn) return;
        if (endPos == null) endPos = posIn.relative(dirIn);

        levelIn.setBlockAndUpdate(targetPosIn, Blocks.AIR.defaultBlockState());
        levelIn.setBlockAndUpdate(endPos, pullTargetBS);
        levelIn.blockUpdated(endPos, pullTargetBS.getBlock());
        entityHandler(levelIn, targetPosIn, endPos);
    }

    private void pushBlock(Level levelIn, BlockPos posIn, BlockPos targetPosIn, Direction dirIn) {
        BlockState pullTargetBS = levelIn.getBlockState(targetPosIn);
        if (!pullTargetBS.getMaterial().blocksMotion() || !levelIn.getFluidState(targetPosIn).isEmpty() || pullTargetBS.is(RankineTags.Blocks.MAGNET_BANNED) || levelIn.getBlockEntity(targetPosIn) != null) return;
        if (Config.MACHINES.ELECTROMAGNET_MATERIAL_REQ.get() && pullTargetBS.getMaterial() != Material.METAL) return;

        // Find the closest open space and move the block
        BlockPos endPos = null;
        List<BlockPos> blockPosList = BlockPos.betweenClosedStream(targetPosIn.relative(dirIn), posIn.relative(dirIn,type * Config.MACHINES.ELECTROMAGNET_RANGE.get())).map(BlockPos::immutable).collect(Collectors.toList());
        if (dirIn.getAxisDirection() == Direction.AxisDirection.NEGATIVE) {Collections.reverse(blockPosList);}
        for (BlockPos path : blockPosList) {
            if (isMagnetBreakable(levelIn, path)) {
                levelIn.destroyBlock(path, true);
                continue;
            }
            endPos = path.relative(dirIn.getOpposite());
            break;
        }
        if (endPos == targetPosIn) return;
        if (endPos == null) endPos = posIn.relative(dirIn,type * Config.MACHINES.ELECTROMAGNET_RANGE.get());

        levelIn.setBlockAndUpdate(targetPosIn, Blocks.AIR.defaultBlockState());
        levelIn.setBlockAndUpdate(endPos, pullTargetBS);
        levelIn.blockUpdated(endPos, pullTargetBS.getBlock());
        entityHandler(levelIn, targetPosIn, endPos);
    }

    private void entityHandler(Level levelIn, BlockPos targetPosIn, BlockPos endPos) {
        //Damage entities along the path
        List<LivingEntity> entities = levelIn.getEntitiesOfClass(LivingEntity.class, new AABB(endPos, targetPosIn).expandTowards(1, 1, 1), (e) -> e instanceof Mob || e instanceof Player);
        for (LivingEntity i : entities) {
            i.hurt(levelIn.damageSources().flyIntoWall(), Mth.sqrt(type * (float) targetPosIn.distSqr(i.getOnPos())));
        }
        //Teleport entities on top of the moving block
        List<LivingEntity> entitiesOnBlock = levelIn.getEntitiesOfClass(LivingEntity.class, new AABB(targetPosIn, targetPosIn.above()).expandTowards(1, 1, 1), (e) -> e instanceof Mob || e instanceof Player);
        for (LivingEntity i : entitiesOnBlock) {
            i.teleportTo(endPos.getX() - (targetPosIn.getX() - i.getX()), endPos.getY() + 1, endPos.getZ() - (targetPosIn.getZ() - i.getZ()));
        }
    }

    private boolean isMagnetBreakable(Level levelIn, BlockPos posIn) {
        BlockState stateIn = levelIn.getBlockState(posIn);
        return stateIn.is(Blocks.BAMBOO) || stateIn.is(RankineTags.Blocks.COBBLES) || !stateIn.getMaterial().blocksMotion();
    }
}
