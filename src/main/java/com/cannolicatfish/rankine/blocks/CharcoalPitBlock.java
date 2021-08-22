package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CharcoalPitBlock extends Block {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public CharcoalPitBlock(Properties properties) {
        super(properties);
    }

    int TICKS = 1;
    int MAX_HEIGHT = Config.MACHINES.CHARCOAL_PIT_HEIGHT.get();
    int RADIUS = Config.MACHINES.CHARCOAL_PIT_RADIUS.get();


    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(LIT, false);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        ItemStack itemstack = player.getHeldItem(handIn);
        Item item = itemstack.getItem();
        if (item == Items.FLINT_AND_STEEL || item == RankineItems.SPARK_LIGHTER.get()) {
            itemstack.damageItem(1, player, (p_220287_1_) -> {
                p_220287_1_.sendBreakAnimation(handIn);
            });
            for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(0,-MAX_HEIGHT,0), pos.add(0, MAX_HEIGHT,0))) {
                if (worldIn.getBlockState(blockpos).getBlock() == RankineBlocks.CHARCOAL_PIT.get()) {
                    worldIn.setBlockState(blockpos, worldIn.getBlockState(blockpos).with(BlockStateProperties.LIT, Boolean.TRUE), 2);
                }
            }
            return ActionResultType.SUCCESS;
        } else if (item == Items.FIRE_CHARGE) {
            itemstack.shrink(1);
            for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(0,-MAX_HEIGHT,0), pos.add(0, MAX_HEIGHT,0))) {
                if (worldIn.getBlockState(blockpos).getBlock() == RankineBlocks.CHARCOAL_PIT.get()) {
                    worldIn.setBlockState(blockpos, RankineBlocks.CHARCOAL_PIT.get().getDefaultState().with(BlockStateProperties.LIT, Boolean.TRUE), 2);
                }
            }
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.FAIL;
    }

    @Override
    public boolean ticksRandomly(BlockState stateIn) {
        return stateIn.get(LIT);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }


    private boolean connected(World worldIn, BlockPos pos, List<BlockPos> logs) {
        List<BlockPos> Sides = Arrays.asList(pos.down(), pos.north(), pos.east(), pos.west(), pos.south(), pos.up());
        for (BlockPos Side : Sides) {
            if (logs.contains(Side) && !logs.contains(pos) && worldIn.getBlockState(pos).getBlock().getTags().contains(new ResourceLocation("minecraft:logs_that_burn"))) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        super.tick(state, worldIn, pos, rand);
        int quality;
        int HEIGHT = heightCheck(worldIn, pos);
        List<BlockPos> Connected = new ArrayList<>();
        if (state.get(LIT)) {
            if (worldIn.getBlockState(pos.down()) == this.getDefaultState().with(LIT, true)) {
                return;
            }
            if (TICKS % Config.MACHINES.CHARCOAL_PIT_SPEED.get() == 0) {
                for (int y = 0; y <= HEIGHT; ++y) {
                    Connected.add(pos.up(y));
                    if (worldIn.getBlockState(pos.up(y).north()).getBlock().getTags().contains(new ResourceLocation("minecraft:logs_that_burn"))) {
                        Connected.add(pos.up(y).north());
                    }
                    if (worldIn.getBlockState(pos.up(y).east()).getBlock().getTags().contains(new ResourceLocation("minecraft:logs_that_burn"))) {
                        Connected.add(pos.up(y).east());
                    }
                    if (worldIn.getBlockState(pos.up(y).south()).getBlock().getTags().contains(new ResourceLocation("minecraft:logs_that_burn"))) {
                        Connected.add(pos.up(y).south());
                    }
                    if (worldIn.getBlockState(pos.up(y).west()).getBlock().getTags().contains(new ResourceLocation("minecraft:logs_that_burn"))) {
                        Connected.add(pos.up(y).west());
                    }
                }
                for (int R = 1; R <= 3; ++R) {
                    for (int y = 0; y <= HEIGHT; ++y) {
                        for (BlockPos B : BlockPos.getAllInBoxMutable(pos.add(-R,y,-R), pos.add(R,y,R))) {
                            if (connected(worldIn, B, Connected)) {
                                Connected.add(B.toImmutable());
                            }
                        }
                    }
                }


                for (BlockPos blockpos : Connected) {
                    String path = worldIn.getBlockState(blockpos).getBlock().getRegistryName().getPath();
                    List<BlockPos> Sides = Arrays.asList(blockpos.down(), blockpos.north(), blockpos.east(), blockpos.west(), blockpos.south(), blockpos.up());
                    boolean valid = true;
                    for (BlockPos Side : Sides) {
                        if (!worldIn.getBlockState(Side).isOpaqueCube(worldIn, Side)) {
                            valid = false;
                            break;
                        }
                    }
                    if (valid) {
                        if (path.contains("douglas_fir")) {
                            quality = 460;
                        } else if (path.contains("ancient")) {
                            quality = 400;
                        } else if (path.contains("archwood")) {
                            quality = 400;
                        } else if (path.contains("dead")) {
                            quality = 300;
                        } else if (path.contains("pinyon_pine")) {
                            quality = 520;
                        } else if (path.contains("redwood")) {
                            quality = 440;
                        } else if (path.contains("alder")) {
                            quality = 420;
                        } else if (path.contains("apple")) {
                            quality = 520;
                        } else if (path.contains("ash")) {
                            quality = 470;
                        } else if (path.contains("aspen")) {
                            quality = 430;
                        } else if (path.contains("basswood")) {
                            quality = 390;
                        } else if (path.contains("beech")) {
                            quality = 530;
                        } else if (path.contains("elder")) {
                            quality = 430;
                        } else if (path.contains("cherry")) {
                            quality = 450;
                        } else if (path.contains("chestnut")) {
                            quality = 430;
                        } else if (path.contains("black_birch")) {
                            quality = 470;
                        } else if (path.contains("yellow_birch")) {
                            quality = 490;
                        } else if (path.contains("birch")) {
                            quality = 450;
                        } else if (path.contains("dogwood")) {
                            quality = 600;
                        } else if (path.contains("elm")) {
                            quality = 450;
                        } else if (path.contains("cottonwood")) {
                            quality = 410;
                        } else if (path.contains("fir")) {
                            quality = 390;
                        } else if (path.contains("silver_maple")) {
                            quality = 440;
                        } else if (path.contains("mulberry")) {
                            quality = 510;
                        } else if (path.contains("poplar")) {
                            quality = 350;
                        } else if (path.contains("maple")) {
                            quality = 500;
                        } else if (path.contains("cedar")) {
                            quality = 410;
                        } else if (path.contains("white_pine")) {
                            quality = 410;
                        } else if (path.contains("spruce")) {
                            quality = 410;
                        } else if (path.contains("white_oak")) {
                            quality = 540;
                        } else if (path.contains("red_oak")) {
                            quality = 500;
                        } else if (path.contains("oak")) {
                            quality = 520;
                        } else if (path.contains("jungle")) {
                            quality = 450;
                        } else if (path.contains("walnut")) {
                            quality = 470;
                        } else if (path.contains("pine")) {
                            quality = 420;
                        } else if (path.contains("willow")) {
                            quality = 420;
                        } else if (path.contains("sycamore")) {
                            quality = 440;
                        } else if (path.contains("hickory")) {
                            quality = 530;
                        } else if (path.contains("coconut")) {
                            quality = 450;
                        } else if (path.contains("juniper")) {
                            quality = 480;
                        } else if (path.contains("acacia")) {
                            quality = 500;
                        } else if (path.contains("magnolia")) {
                            quality = 450;
                        } else if (path.contains("hemlock")) {
                            quality = 440;
                        } else if (path.contains("larch")) {
                            quality = 440;
                        } else if (path.contains("robinia")) {
                            quality = 550;
                        } else if (path.contains("eucalyptus")) {
                            quality = 570;
                        } else if (path.contains("ironwood")) {
                            quality = 510;
                        } else if (path.contains("locust")) {
                            quality = 550;
                        } else {
                            quality = 300;
                        }
                        int count;
                        if (new Random().nextFloat() < (quality % 100) / 250f) {
                            count = (int) Math.floor(quality / 100f) - 3 + 1;
                        } else {
                            count = (int) Math.floor(quality / 100f) - 3;
                        }
                        if (count > 3) {
                            count =3;
                        } else if (count < 0) {
                            count = 0;
                        }
                        worldIn.setBlockState(blockpos, RankineBlocks.CHARCOAL_BLOCK.get().getDefaultState().with(RankineEightLayerBlock.LAYERS, count));
                    }
                }
            }
            TICKS += 1;
        }
    }


    private int heightCheck(ServerWorld worldIn, BlockPos pos) {
        int HEIGHT = 0;
        for (int i = 1; i <= Config.MACHINES.CHARCOAL_PIT_HEIGHT.get()-1; ++i) {
            if (worldIn.getBlockState(pos.up(i)) == this.getDefaultState()) {
                ++HEIGHT;
            } else {
                break;
            }
        }
        return HEIGHT;
    }


    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextFloat() < 0.7 && stateIn.get(LIT)) {
            Random random = worldIn.getRandom();
            BasicParticleType basicparticletype = ParticleTypes.CAMPFIRE_COSY_SMOKE;
            worldIn.addOptionalParticle(basicparticletype, true, (double)pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
        }
    }

}
