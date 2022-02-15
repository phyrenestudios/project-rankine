package com.cannolicatfish.rankine.blocks.charcoalpit;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class CharcoalPitBlock extends Block {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    int MAX_HEIGHT = Config.MACHINES.CHARCOAL_PIT_HEIGHT.get();

    public CharcoalPitBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CharcoalPitTile();
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(LIT, false);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }


    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        ItemStack itemstack = player.getHeldItem(handIn);
        Item item = itemstack.getItem();
        boolean flag = false;
        if (item == Items.FLINT_AND_STEEL || item == RankineItems.SPARK_LIGHTER.get()) {
            itemstack.damageItem(1, player, (p_220287_1_) -> {
                p_220287_1_.sendBreakAnimation(handIn);
            });
            flag = true;
        } else if (item == Items.FIRE_CHARGE) {
            itemstack.shrink(1);
            flag = true;
        }
        if (flag) {
            for (int i = 0; i <= MAX_HEIGHT; ++i) {
                if (!worldIn.getBlockState(pos.up(i+1)).matchesBlock(RankineBlocks.CHARCOAL_PIT.get())) {
                    worldIn.setBlockState(pos.up(i), RankineBlocks.CHARCOAL_PIT.get().getDefaultState().with(BlockStateProperties.LIT, Boolean.TRUE), 3);
                    break;
                }
            }
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.FAIL;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextFloat() < 0.7 && stateIn.get(LIT)) {
            Random random = worldIn.getRandom();
            BasicParticleType basicparticletype = ParticleTypes.CAMPFIRE_COSY_SMOKE;
            worldIn.addOptionalParticle(basicparticletype, true, (double)pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
            worldIn.addOptionalParticle(basicparticletype, true, (double)pos.getX() + 0.5D + (random.nextInt(3)-1) * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5D + (random.nextInt(3)-1) * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
        }
    }

}
