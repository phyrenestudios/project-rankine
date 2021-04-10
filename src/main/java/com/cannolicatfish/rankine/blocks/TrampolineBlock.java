package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.states.TreeTapFluids;
import com.cannolicatfish.rankine.blocks.tap.TapBarrelBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class TrampolineBlock extends Block {
    public TrampolineBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.makeCuboidShape(0.0D, 12.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    }


    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if (entityIn.isSuppressingBounce()) {
            super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
        } else {
            entityIn.onLivingFall(fallDistance, 0.0F);
        }

    }

    /**
     * Called when an Entity lands on this Block. This method *must* update motionY because the entity will not do that
     * on its own
     */
    public void onLanded(IBlockReader worldIn, Entity entityIn) {
        if (entityIn.isSuppressingBounce()) {
            super.onLanded(worldIn, entityIn);
        } else {
            this.bounceEntity(entityIn, worldIn);
        }

    }

    private void bounceEntity(Entity entity, IBlockReader worldIn) {
        Vector3d vector3d = entity.getMotion();
        if (vector3d.y < 0.0D) {
            double size = trampolineSize(entity, worldIn);
            entity.setMotion(vector3d.x, -vector3d.y * size, vector3d.z);
        }

    }

    private double trampolineSize(Entity entity, IBlockReader worldIn) {

        if (entity instanceof LivingEntity) {

            if (Config.GENERAL.TRAMPOLINE_SIZE.get() == 0) {
                return 1.3D;
            }

            Set<BlockPos> checkedBlocks = new HashSet<>();
            Stack<BlockPos> toCheck = new Stack<>();

            toCheck.add(entity.getPosition());
            while (!toCheck.isEmpty()) {
                BlockPos cp = toCheck.pop();
                if (!checkedBlocks.contains(cp)) {
                    checkedBlocks.add(cp);

                    if (worldIn.getBlockState(cp.north()).getBlock().matchesBlock(RankineBlocks.TRAMPOLINE.get())) {
                        toCheck.add(cp.north());
                    }
                    if (worldIn.getBlockState(cp.east()).getBlock().matchesBlock(RankineBlocks.TRAMPOLINE.get())) {
                        toCheck.add(cp.east());
                    }
                    if (worldIn.getBlockState(cp.south()).getBlock().matchesBlock(RankineBlocks.TRAMPOLINE.get())) {
                        toCheck.add(cp.south());
                    }
                    if (worldIn.getBlockState(cp.west()).getBlock().matchesBlock(RankineBlocks.TRAMPOLINE.get())) {
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
