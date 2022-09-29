package com.cannolicatfish.rankine.blocks.evaporationtower;

import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class EvaporationTowerBlock extends BaseEntityBlock {
    public EvaporationTowerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!world.isClientSide) {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof MenuProvider) {
                NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) tileEntity, tileEntity.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
            return InteractionResult.CONSUME;
        } else
        {
            return InteractionResult.SUCCESS;
        }
    }


    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new EvaporationTowerTile(p_153215_,p_153216_);
    }

    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState blockStateIn, BlockEntityType<T> blockEntityTypeIn) {
        return worldIn.isClientSide ? null : createTickerHelper(blockEntityTypeIn, RankineBlockEntityTypes.EVAPORATION_TOWER.get(), EvaporationTowerTile::tick);
    }

    @Override
    public boolean isRandomlyTicking(BlockState p_49921_) {
        return true;
    }

    @Override
    public void randomTick(BlockState stateIn, ServerLevel levelIn, BlockPos posIn, Random rand) {
        if (rand.nextFloat() < 0.01) {
            EvaporationTowerTile tileIn = (EvaporationTowerTile) levelIn.getBlockEntity(posIn);
            if (tileIn.structureHeight(levelIn, posIn) > 2) {
                levelIn.removeBlock(tileIn.wallStructure(posIn).get(rand.nextInt(tileIn.wallStructure(posIn).size())).above(rand.nextInt(tileIn.structureHeight(levelIn, posIn)-3)+3),false);
            }
        }
        super.randomTick(stateIn, levelIn, posIn, rand);
    }
}

