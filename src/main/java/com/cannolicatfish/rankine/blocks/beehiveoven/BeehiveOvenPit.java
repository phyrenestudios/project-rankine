package com.cannolicatfish.rankine.blocks.beehiveoven;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BeehiveOvenPit extends Block {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    private float chance;
    private final Block blockType;
    public BeehiveOvenPit(Properties properties) {
        super(properties);
        this.chance = 0.25f;
        this.blockType = ModBlocks.REFRACTORY_BRICKS;
    }

    public BeehiveOvenPit(float chancePerRandomTick, Block blockType, Properties properties) {
        super(properties);
        this.chance = chancePerRandomTick;
        this.blockType = blockType;
    }


    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IWorld iworld = context.getWorld();
        BlockPos blockpos = context.getPos();
        return this.getDefaultState().with(LIT, false);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        ItemStack itemstack = player.getHeldItem(handIn);
        Item item = itemstack.getItem();
        if (item == Items.FLINT_AND_STEEL || item == Items.FIRE_CHARGE) {
            itemstack.damageItem(1, player, (p_220287_1_) -> {
                p_220287_1_.sendBreakAnimation(handIn);
            });
            getBeehiveOven(worldIn,pos);
        }


        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean ticksRandomly(BlockState stateIn) {
        return stateIn.get(LIT);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.get(BlockStateProperties.LIT) ? super.getLightValue(state,world,pos) : 0;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        super.tick(state, worldIn, pos, rand);
        World world = worldIn.getWorld();
        BlockPos uppos = pos.up();
        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        getBeehiveOven(world,pos);
        if (state.get((LIT)))
        {
            if (rand.nextFloat() <= chance)
            {
                boolean flag = true;
                /*List<Block> surroundingBlocks = Arrays.asList(world.getBlockState(uppos.north()).getBlock(),world.getBlockState(uppos.south()).getBlock(),world.getBlockState(uppos.east()).getBlock(),world.getBlockState(uppos.west()).getBlock(),
                        world.getBlockState(uppos.north().east()).getBlock(), world.getBlockState(uppos.north().west()).getBlock(),world.getBlockState(uppos.south().east()).getBlock(),world.getBlockState(uppos.south().west()).getBlock(),
                        world.getBlockState(uppos.north().up()).getBlock(),world.getBlockState(uppos.south().up()).getBlock(),world.getBlockState(uppos.east().up()).getBlock(),world.getBlockState(uppos.west().up()).getBlock(),
                        world.getBlockState(uppos.north().east().up()).getBlock(), world.getBlockState(uppos.north().west().up()).getBlock(),world.getBlockState(uppos.south().east().up()).getBlock(),world.getBlockState(uppos.south().west().up()).getBlock());
                */
                List<BlockPos> surroundingBlockPos = Arrays.asList(uppos.north(),uppos.south(),uppos.east(),uppos.west(),uppos.north().east(),
                        uppos.north().west(),uppos.south().east(),uppos.south().west(), uppos.north().up(),uppos.south().up(),uppos.east().up(),uppos.west().up(),uppos.north().east().up(),
                        uppos.north().west().up(),uppos.south().east().up(),uppos.south().west().up());

                for (BlockPos p: surroundingBlockPos)
                {
                    ItemStack output = ModRecipes.getBeehiveOutput(new ItemStack(world.getBlockState(p).getBlock()));
                    if (!output.isEmpty())
                    {
                        if (output.getItem() instanceof BlockItem)
                        {
                            world.setBlockState(p, ((BlockItem) output.getItem()).getBlock().getDefaultState(), 2);
                            flag = false;
                            break;
                        }

                    }
                }
                if (flag)
                {
                    world.setBlockState(pos, world.getBlockState(pos).with(BlockStateProperties.LIT, Boolean.FALSE), 2);
                }

            }
        }

    }

    private void getBeehiveOven(World world, BlockPos pos) {
        boolean canSeeSky = true;
        for (int i = 1; i <= 8; i++)
        {
            if (!world.isAirBlock(pos.up(i)))
            {
                canSeeSky = false;
            }
        }
        if (canSeeSky && world.getBlockState(pos.north()).getBlock() == blockType && world.getBlockState(pos.south()).getBlock() == blockType
                && world.getBlockState(pos.east()).getBlock() == blockType && world.getBlockState(pos.west()).getBlock() == blockType
                && world.getBlockState(pos.north().west()).getBlock() == blockType && world.getBlockState(pos.north().east()).getBlock() == blockType
                && world.getBlockState(pos.south().west()).getBlock() == blockType && world.getBlockState(pos.south().east()).getBlock() == blockType)
        {
            world.setBlockState(pos, world.getBlockState(pos).with(BlockStateProperties.LIT, Boolean.TRUE), 2);
        }
        else
        {
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
            spawnSmokeParticles(worldIn.getWorld(), pos, true);
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

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }
}
