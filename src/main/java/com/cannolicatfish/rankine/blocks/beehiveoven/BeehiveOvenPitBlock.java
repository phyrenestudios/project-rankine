package com.cannolicatfish.rankine.blocks.beehiveoven;

import com.cannolicatfish.rankine.blocks.groundtap.GroundTapTile;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.recipe.BeehiveOvenRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.SimpleContainer;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;

public class BeehiveOvenPitBlock extends Block {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    private final Block blockType;

    public BeehiveOvenPitBlock(Block blockType, Properties properties) {
        super(properties);
        this.blockType = blockType;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(LIT, false);
    }

    @Override
    public boolean isRandomlyTicking(BlockState stateIn) {
        return stateIn.getValue(LIT);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult p_225533_6_) {
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
                BeehiveOvenRecipe recipe = worldIn.getRecipeManager().getRecipeFor(RankineRecipeTypes.BEEHIVE, new SimpleContainer(new ItemStack(worldIn.getBlockState(p).getBlock())), worldIn).orElse(null);
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
            worldIn.playLocalSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 0.5F, 0.7F + 0.6F, false);
        }

        return InteractionResult.SUCCESS;
    }

    public static void spawnSmokeParticles(Level worldIn, BlockPos pos, boolean spawnExtraSmoke) {
        Random random = worldIn.getRandom();
        SimpleParticleType basicparticletype = ParticleTypes.CAMPFIRE_COSY_SMOKE;
        worldIn.addAlwaysVisibleParticle(basicparticletype, true, (double)pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
        if (spawnExtraSmoke) {
            worldIn.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + 0.25D + random.nextDouble() / 2.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.4D, (double)pos.getZ() + 0.25D + random.nextDouble() / 2.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
        if (stateIn.getValue(LIT)) {
            spawnSmokeParticles(worldIn, pos, true);
            if (rand.nextInt(10) == 0) {
                worldIn.playLocalSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
            }

            if (rand.nextInt(5) == 0) {
                for(int i = 0; i < rand.nextInt(1) + 1; ++i) {
                    worldIn.addParticle(ParticleTypes.LAVA, (double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), (double)(rand.nextFloat() / 2.0F), 5.0E-5D, (double)(rand.nextFloat() / 2.0F));
                }
            }

        }
    }


}
