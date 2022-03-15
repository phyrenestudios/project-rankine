package com.cannolicatfish.rankine.blocks.beehiveoven;

import com.cannolicatfish.rankine.blocks.groundtap.GroundTapTile;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.recipe.BeehiveOvenRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

public class BeehiveOvenPitBlock extends Block {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    private final Block blockType;

    public BeehiveOvenPitBlock(Block blockType, Properties properties) {
        super(properties);
        this.blockType = blockType;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(LIT, false);
    }

    @Override
    public boolean isRandomlyTicking(BlockState stateIn) {
        return stateIn.getValue(LIT);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.getValue(BlockStateProperties.LIT) ? super.getLightValue(state,world,pos) : 0;
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        ItemStack itemstack = player.getItemInHand(handIn);
        Item item = itemstack.getItem();
        if (item == Items.FLINT_AND_STEEL || item == RankineItems.SPARK_LIGHTER.get()) {
            itemstack.hurtAndBreak(1, player, (p_220287_1_) -> {
                p_220287_1_.broadcastBreakEvent(handIn);
            });
            worldIn.setBlock(pos, worldIn.getBlockState(pos).setValue(BlockStateProperties.LIT, Boolean.TRUE), 2);
        } else if (item == Items.FIRE_CHARGE) {
            itemstack.shrink(1);
            worldIn.setBlock(pos, worldIn.getBlockState(pos).setValue(BlockStateProperties.LIT, Boolean.TRUE), 2);
        } else if (player.getMainHandItem().getItem() == Items.BLAZE_POWDER && state.getValue((LIT))) {
            boolean flag = true;
            for (BlockPos p: BlockPos.betweenClosed(pos.offset(-1,1,-1),pos.offset(1,2,1))) {
                BeehiveOvenRecipe recipe = worldIn.getRecipeManager().getRecipeFor(RankineRecipeTypes.BEEHIVE, new Inventory(new ItemStack(worldIn.getBlockState(p).getBlock())), worldIn).orElse(null);
                if (recipe != null) {
                    ItemStack output = recipe.getResultItem();
                    if (!output.isEmpty()) {
                        if (output.getItem() instanceof BlockItem) {
                            worldIn.setBlock(p, ((BlockItem) output.getItem()).getBlock().defaultBlockState(), 2);
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if (flag) {
                worldIn.setBlock(pos, worldIn.getBlockState(pos).setValue(BlockStateProperties.LIT, Boolean.FALSE), 2);
            }
            if (!player.isCreative()) {
                player.getMainHandItem().shrink(1);
            }
            worldIn.playLocalSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), SoundEvents.FIRECHARGE_USE, SoundCategory.BLOCKS, 0.5F, 0.7F + 0.6F, false);
        }

        return ActionResultType.SUCCESS;
    }

    public static void spawnSmokeParticles(World worldIn, BlockPos pos, boolean spawnExtraSmoke) {
        Random random = worldIn.getRandom();
        BasicParticleType basicparticletype = ParticleTypes.CAMPFIRE_COSY_SMOKE;
        worldIn.addAlwaysVisibleParticle(basicparticletype, true, (double)pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
        if (spawnExtraSmoke) {
            worldIn.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + 0.25D + random.nextDouble() / 2.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.4D, (double)pos.getZ() + 0.25D + random.nextDouble() / 2.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.getValue(LIT)) {
            spawnSmokeParticles(worldIn, pos, true);
            if (rand.nextInt(10) == 0) {
                worldIn.playLocalSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), SoundEvents.CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
            }

            if (rand.nextInt(5) == 0) {
                for(int i = 0; i < rand.nextInt(1) + 1; ++i) {
                    worldIn.addParticle(ParticleTypes.LAVA, (double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), (double)(rand.nextFloat() / 2.0F), 5.0E-5D, (double)(rand.nextFloat() / 2.0F));
                }
            }

        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BeehiveOvenTile();
    }


}
