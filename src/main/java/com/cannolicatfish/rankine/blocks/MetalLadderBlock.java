package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.RedstoneOreBlock;
import net.minecraft.data.TagsProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Items;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.network.NetworkHooks;

public class MetalLadderBlock extends LadderBlock {
    private boolean teleport;
    public MetalLadderBlock(Boolean teleport, Properties builder) {
        super(builder);
        this.teleport = teleport;
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (this.teleport) {
            int n = 1;
            while (world.getBlockState(pos.up(n)).getBlock() == this.getBlock()) {
                n += 1;
            }
            if (!world.isRemote) {
                player.setPositionAndUpdate(pos.getX() + .5f, pos.getY() + n, pos.getZ() + .5f);
                return ActionResultType.FAIL;
            }
        }
        return super.onBlockActivated(state, world, pos, player, hand, result);
    }

}
