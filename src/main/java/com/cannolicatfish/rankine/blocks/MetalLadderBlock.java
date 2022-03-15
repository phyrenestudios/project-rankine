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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
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
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.block.AbstractBlock.Properties;

public class MetalLadderBlock extends LadderBlock {

    private boolean teleport;
    private boolean autoPlace;
    public MetalLadderBlock(Boolean teleport, Boolean autoPlace, Properties builder) {
        super(builder);
        this.teleport = teleport;
        this.autoPlace = autoPlace;
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        return canAttachTo(worldIn, pos.relative(direction.getOpposite()), direction) || worldIn.getBlockState(pos.below()).is(this);
    }

    private boolean canAttachTo(IBlockReader blockReader, BlockPos pos, Direction direction) {
        BlockState blockstate = blockReader.getBlockState(pos);
        return blockstate.isFaceSturdy(blockReader, pos, direction);
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (this.teleport) {
            int n = 1;
            while (world.getBlockState(pos.above(n)).getBlock() == this.getBlock()) {
                n += 1;
            }
            if (!world.isClientSide) {
                player.teleportTo(pos.getX() + .5f, pos.getY() + n, pos.getZ() + .5f);
                return ActionResultType.PASS;
            }
        }
        if (this.autoPlace) {
            if (world.isEmptyBlock(pos.above()) && player.inventory.contains(new ItemStack(state.getBlock()))) {
                world.setBlock(pos.above(), state, 2);

            }
            return ActionResultType.PASS;
        }
        return super.use(state, world, pos, player, hand, result);
    }

}
