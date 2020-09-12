package com.cannolicatfish.rankine.blocks.evaporationtower;

import com.cannolicatfish.rankine.blocks.coalforge.CoalForgeTile;
import com.cannolicatfish.rankine.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class EvaporationTowerBlock extends Block {
    public EvaporationTowerBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings( "deprecation" )
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        super.tick(state, worldIn, pos, rand);
            int i = 0;
            for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-1, 0, -1), pos.add(1, 3, 1))) {
                if (worldIn.getBlockState(blockpos).getBlock().getTags().contains(new ResourceLocation("forge:functional_sheetmetals"))) {
                    ++i;
                }
            }
            if (i >= 32 && worldIn.getBlockState(pos.up(1)) == Blocks.WATER.getDefaultState() && worldIn.getBlockState(pos.up(2)) == Blocks.WATER.getDefaultState()) {
                spawnAsEntity(worldIn, pos.down(), new ItemStack(ModItems.SALT, 1));
            }
        }


    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new EvaporationTowerTile();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof INamedContainerProvider) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
            return ActionResultType.CONSUME;
        } else
        {
            return ActionResultType.SUCCESS;
        }
    }




}

