package com.cannolicatfish.rankine.blocks.plants;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class StingingNettleBlock extends BushBlock {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    public StingingNettleBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            if (!worldIn.isClientSide && (entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ())) {
                double d0 = Math.abs(entityIn.getX() - entityIn.xOld);
                double d1 = Math.abs(entityIn.getZ() - entityIn.zOld);
                if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
                    //entityIn.attackEntityFrom(DamageSource.CACTUS, 2.0F);
                    ((LivingEntity) entityIn).addEffect(new MobEffectInstance(MobEffects.POISON,20*2,0));
                }
            }
        }
    }

    public OffsetType getOffsetType() {
        return OffsetType.XYZ;
    }

    // @Nullable
    @Override
    public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.DANGER_OTHER;
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return true;
    }

}
