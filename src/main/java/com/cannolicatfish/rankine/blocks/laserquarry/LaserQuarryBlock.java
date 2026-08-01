package com.cannolicatfish.rankine.blocks.laserquarry;
// PORT-TODO(init): LASER_QUARRY BlockEntityType/MenuType not registered — init agent must add or keep this class dormant
// PORT-TODO(init): RankineBlocks.LASER_QUARRY block itself is also unregistered (commented out in RankineBlocks/RankineItems since 1.18);
// the class below has been modernized to 1.21.1 NeoForge but stays commented out to match its dormant 1.18 state.
/*
import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class LaserQuarryBlock extends BaseEntityBlock {
    public static final com.mojang.serialization.MapCodec<LaserQuarryBlock> CODEC = simpleCodec(LaserQuarryBlock::new);

    @Override
    protected com.mojang.serialization.MapCodec<LaserQuarryBlock> codec() {
        return CODEC;
    }

    public LaserQuarryBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LaserQuarryTile(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState blockStateIn, BlockEntityType<T> blockEntityTypeIn) {
        // PORT-TODO(init): LASER_QUARRY BlockEntityType/MenuType not registered — init agent must add or keep this class dormant
        return worldIn.isClientSide ? null : createTickerHelper(blockEntityTypeIn, RankineBlockEntityTypes.LASER_QUARRY.get(), LaserQuarryTile::tick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult result) {
        if (!world.isClientSide) {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof MenuProvider) {
                ((ServerPlayer) player).openMenu((MenuProvider) tileEntity, tileEntity.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
            return InteractionResult.CONSUME;
        } else
        {
            return InteractionResult.SUCCESS;
        }
    }

}
*/
