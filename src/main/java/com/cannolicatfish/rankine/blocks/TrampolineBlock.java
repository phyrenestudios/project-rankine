package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class TrampolineBlock extends Block {
    public TrampolineBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Block.box(0.0D, 12.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    }


    public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {
        if (entityIn.isSuppressingBounce()) {
            super.fallOn(worldIn, state, pos, entityIn, fallDistance);
        } else {
            entityIn.causeFallDamage(fallDistance, 0.0f, worldIn.damageSources().fall());
        }

    }

    /**
     * Called when an Entity lands on this Block. This method *must* update motionY because the entity will not do that
     * on its own
     */
    public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn) {
        if (entityIn.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(worldIn, entityIn);
        } else {
            this.bounceEntity(entityIn, worldIn);
        }

    }

    private void bounceEntity(Entity entity, BlockGetter worldIn) {
        Vec3 vector3d = entity.getDeltaMovement();
        if (vector3d.y < 0.0D) {
            double size = trampolineSize(entity, worldIn);
            entity.setDeltaMovement(vector3d.x, -vector3d.y * size, vector3d.z);
        }

    }

    private double trampolineSize(Entity entity, BlockGetter worldIn) {

        if (entity instanceof LivingEntity) {

            if (Config.GENERAL.TRAMPOLINE_SIZE.get() == 0) {
                return 1.3D;
            }

            Set<BlockPos> checkedBlocks = new HashSet<>();
            Stack<BlockPos> toCheck = new Stack<>();

            toCheck.add(entity.blockPosition());
            while (!toCheck.isEmpty()) {
                BlockPos cp = toCheck.pop();
                if (!checkedBlocks.contains(cp)) {
                    checkedBlocks.add(cp);

                    if (worldIn.getBlockState(cp.north()).getBlock().equals(RankineBlocks.TRAMPOLINE.get())) {
                        toCheck.add(cp.north());
                    }
                    if (worldIn.getBlockState(cp.east()).getBlock().equals(RankineBlocks.TRAMPOLINE.get())) {
                        toCheck.add(cp.east());
                    }
                    if (worldIn.getBlockState(cp.south()).getBlock().equals(RankineBlocks.TRAMPOLINE.get())) {
                        toCheck.add(cp.south());
                    }
                    if (worldIn.getBlockState(cp.west()).getBlock().equals(RankineBlocks.TRAMPOLINE.get())) {
                        toCheck.add(cp.west());
                    }

                    if (checkedBlocks.size() >= Config.GENERAL.TRAMPOLINE_SIZE.get()) {
                        break;
                    }
                }
            }
            return Math.min(Math.exp((double) checkedBlocks.size() / Config.GENERAL.TRAMPOLINE_SIZE.get()), 2.0D);
        } else {
            return 0.8D;
        }
    }

}
