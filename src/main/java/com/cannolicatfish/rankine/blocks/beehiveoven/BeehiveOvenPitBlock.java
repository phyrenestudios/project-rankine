package com.cannolicatfish.rankine.blocks.beehiveoven;

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

public class BeehiveOvenPitBlock extends Block {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    private final Block blockType;

    public BeehiveOvenPitBlock(Block blockType, Properties properties) {
        super(properties);
        this.blockType = blockType;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(LIT, false);
    }

    @Override
    public boolean ticksRandomly(BlockState stateIn) {
        return stateIn.get(LIT);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.get(BlockStateProperties.LIT) ? super.getLightValue(state,world,pos) : 0;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        ItemStack itemstack = player.getHeldItem(handIn);
        Item item = itemstack.getItem();
        if (item == Items.FLINT_AND_STEEL || item == RankineItems.SPARK_LIGHTER.get()) {
            itemstack.damageItem(1, player, (p_220287_1_) -> {
                p_220287_1_.sendBreakAnimation(handIn);
            });
            getBeehiveOven(worldIn,pos);
        } else if (item == Items.FIRE_CHARGE) {
            itemstack.shrink(1);
            getBeehiveOven(worldIn,pos);
        } else if (player.getHeldItemMainhand().getItem() == Items.BLAZE_POWDER && state.get((LIT))) {
            boolean flag = true;
            for (BlockPos p: BlockPos.getAllInBoxMutable(pos.add(-1,1,-1),pos.add(1,2,1))) {
                BeehiveOvenRecipe recipe = worldIn.getRecipeManager().getRecipe(RankineRecipeTypes.BEEHIVE, new Inventory(new ItemStack(worldIn.getBlockState(p).getBlock())), worldIn).orElse(null);
                if (recipe != null) {
                    ItemStack output = recipe.getRecipeOutput();
                    if (!output.isEmpty()) {
                        if (output.getItem() instanceof BlockItem) {
                            worldIn.setBlockState(p, ((BlockItem) output.getItem()).getBlock().getDefaultState(), 2);
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if (flag) {
                worldIn.setBlockState(pos, worldIn.getBlockState(pos).with(BlockStateProperties.LIT, Boolean.FALSE), 2);
            }
            if (!player.isCreative()) {
                player.getHeldItemMainhand().shrink(1);
            }
            worldIn.playSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 0.5F, 0.7F + 0.6F, false);
        }

        return ActionResultType.SUCCESS;
    }


    int height;

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        super.tick(state, worldIn, pos, rand);
        World world = worldIn.getWorld();
        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        getBeehiveOven(world,pos);
        if (state.get((LIT))) {
            height = 2;
            float chance = structureCheck(worldIn, pos);
            if (rand.nextFloat() <= chance) {
                boolean flag = true;
                for (BlockPos p: BlockPos.getAllInBoxMutable(pos.add(-1,1,-1),pos.add(1,height,1))) {

                    BeehiveOvenRecipe recipe = worldIn.getRecipeManager().getRecipe(RankineRecipeTypes.BEEHIVE, new Inventory(new ItemStack(world.getBlockState(p).getBlock())), worldIn).orElse(null);
                    if (recipe != null) {
                        ItemStack output = recipe.getRecipeOutput();
                        if (!output.isEmpty()) {
                            if (output.getItem() instanceof BlockItem) {
                                world.setBlockState(p, ((BlockItem) output.getItem()).getBlock().getDefaultState(), 2);
                                flag = false;
                                if (chance > 1.0) {
                                    chance -= 1.0;
                                    if (rand.nextFloat() < chance) {
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                    }

                }
                if (flag) {
                    world.setBlockState(pos, world.getBlockState(pos).with(BlockStateProperties.LIT, Boolean.FALSE), 3);
                }
            }
        }
    }

    private float structureCheck(World world, BlockPos pos) {
        List<BlockPos> oven = Arrays.asList(
            pos.add(-1,0,-1),
            pos.add(-1,0,0),
            pos.add(-1,0,1),
            pos.add(1,0,-1),
            pos.add(1,0,0),
            pos.add(1,0,1),
            pos.add(0,0,-1),
            pos.add(0,0,1),

            pos.add(-2,0,-1),
            pos.add(-2,0,0),
            pos.add(-2,0,1),
            pos.add(2,0,-1),
            pos.add(2,0,0),
            pos.add(2,0,1),
            pos.add(-1,0,-2),
            pos.add(0,0,-2),
            pos.add(1,0,-2),
            pos.add(-1,0,2),
            pos.add(0,0,2),
            pos.add(1,0,2),

            pos.add(-2,1,-1),
            pos.add(-2,1,1),
            pos.add(2,1,-1),
            pos.add(2,1,1),
            pos.add(-1,1,-2),
            pos.add(1,1,-2),
            pos.add(-1,1,2),
            pos.add(1,1,2),

            pos.add(-2,2,-1),
            pos.add(-2,2,1),
            pos.add(2,2,-1),
            pos.add(2,2,1),
            pos.add(-1,2,-2),
            pos.add(1,2,-2),
            pos.add(-1,2,2),
            pos.add(1,2,2),

            pos.add(-1,3,-1),
            pos.add(-2,3,0),
            pos.add(-1,3,1),
            pos.add(1,3,-1),
            pos.add(2,3,0),
            pos.add(1,3,1),
            pos.add(0,3,-2),
            pos.add(0,3,2),
            pos.add(0,3,-1),
            pos.add(0,3,1),
            pos.add(-1,3,0),
            pos.add(1,3,0)
        );

        int count = 1;
        for (BlockPos b : oven) {
            if (world.getBlockState(b) == RankineBlocks.REFRACTORY_BRICKS.get().getDefaultState()) {
                count += 1;
            } else if (this.blockType != RankineBlocks.REFRACTORY_BRICKS.get() && world.getBlockState(b) == RankineBlocks.HIGH_REFRACTORY_BRICKS.get().getDefaultState()) {
                count += 2;
            } else if (this.blockType == RankineBlocks.ULTRA_HIGH_REFRACTORY_BRICKS.get() && world.getBlockState(b) == RankineBlocks.ULTRA_HIGH_REFRACTORY_BRICKS.get().getDefaultState()) {
                count += 4;
            }
        }
        return (float) count/97;
    }

    private void getBeehiveOven(World world, BlockPos pos) {
        boolean canSeeSky = true;
        if (Config.MACHINES.BEEHIVE_OVEN_SKYLIGHT.get()) {
            for (int i = 1; i <= 8; i++) {
                if (!world.isAirBlock(pos.up(i))) {
                    canSeeSky = false;
                }
            }
        }
        if (canSeeSky && world.getBlockState(pos.north()).getBlock() == blockType && world.getBlockState(pos.south()).getBlock() == blockType
                && world.getBlockState(pos.east()).getBlock() == blockType && world.getBlockState(pos.west()).getBlock() == blockType
                && world.getBlockState(pos.north().west()).getBlock() == blockType && world.getBlockState(pos.north().east()).getBlock() == blockType
                && world.getBlockState(pos.south().west()).getBlock() == blockType && world.getBlockState(pos.south().east()).getBlock() == blockType)
        {
            world.setBlockState(pos, world.getBlockState(pos).with(BlockStateProperties.LIT, Boolean.TRUE), 2);
        } else {
            world.setBlockState(pos, world.getBlockState(pos).with(BlockStateProperties.LIT, Boolean.FALSE), 2);
        }
    }

    public static void spawnSmokeParticles(World worldIn, BlockPos pos, boolean spawnExtraSmoke) {
        Random random = worldIn.getRandom();
        BasicParticleType basicparticletype = ParticleTypes.CAMPFIRE_COSY_SMOKE;
        worldIn.addOptionalParticle(basicparticletype, true, (double)pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
        if (spawnExtraSmoke) {
            worldIn.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + 0.25D + random.nextDouble() / 2.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.4D, (double)pos.getZ() + 0.25D + random.nextDouble() / 2.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(LIT)) {
            spawnSmokeParticles(worldIn, pos, true);
            if (rand.nextInt(10) == 0) {
                worldIn.playSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
            }

            if (rand.nextInt(5) == 0) {
                for(int i = 0; i < rand.nextInt(1) + 1; ++i) {
                    worldIn.addParticle(ParticleTypes.LAVA, (double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), (double)(rand.nextFloat() / 2.0F), 5.0E-5D, (double)(rand.nextFloat() / 2.0F));
                }
            }

        }
    }

}
