package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FumaroleBlock extends Block {
    GasUtilsEnum gas;
    public FumaroleBlock(GasUtilsEnum gas, Properties properties) {
        super(properties);
        this.gas = gas;
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return Config.GASES.ENABLE_GAS_VENTS.get();
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (Config.GASES.ENABLE_GAS_VENTS.get()) {
            List<BlockPos> surround = Arrays.asList(pos.up(),pos.down(),pos.west(),pos.east(),pos.north(),pos.south());
            if (!worldIn.isRemote) {
                int airCount = (int) surround.stream().filter(worldIn::isAirBlock).count();
                for (int i = 0; i < airCount; i++) {
                    BlockPos close = BlockPos.getClosestMatchingPosition(pos,3,3,B -> worldIn.isAirBlock(B) && !(worldIn.getBlockState(B).getBlock() instanceof GasBlock)
                            && BlockPos.getClosestMatchingPosition(B,1,1,P -> worldIn.getBlockState(P).getBlock() instanceof GasBlock || worldIn.getBlockState(P).getBlock() instanceof FumaroleBlock).orElse(null) != null).orElse(null);
                    if (close == null) {
                        break;
                    } else {
                        worldIn.setBlockState(close,getGas().getDefaultState(),3);
                    }
                }
                if (random.nextFloat() < 0.05) {
                    BlockPos deposit = new BlockPos(pos.getX()+random.nextInt(5)-2,pos.getY()+random.nextInt(5)-3,pos.getZ()+random.nextInt(5)-2);
                    if (worldIn.getBlockState(deposit).isIn(RankineTags.Blocks.FUMAROLE_DEPOSIT)) {
                        worldIn.setBlockState(deposit,RankineBlocks.FUMAROLE_DEPOSIT.get().getDefaultState(),3);
                    }
                }
            }
        }


        super.randomTick(state, worldIn, pos, random);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextFloat() < 0.01) {
            Random random = worldIn.getRandom();
            BasicParticleType basicparticletype = ParticleTypes.SMOKE;
            worldIn.addOptionalParticle(basicparticletype, true, (double)pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
        }
    }

    public GasUtilsEnum getGasEnum() {
        return gas;
    }

    public Block getGas() {
        switch (gas) {
            case HYDROGEN:
                return RankineBlocks.HYDROGEN_GAS_BLOCK.get();
            case HELIUM:
                return RankineBlocks.HELIUM_GAS_BLOCK.get();
            case NITROGEN:
                return RankineBlocks.NITROGEN_GAS_BLOCK.get();
            case OXYGEN:
                return RankineBlocks.OXYGEN_GAS_BLOCK.get();
            case FLUORINE:
                return RankineBlocks.FLUORINE_GAS_BLOCK.get();
            case NEON:
                return RankineBlocks.NEON_GAS_BLOCK.get();
            case CHLORINE:
                return RankineBlocks.CHLORINE_GAS_BLOCK.get();
            case ARGON:
                return RankineBlocks.ARGON_GAS_BLOCK.get();
            case KRYPTON:
                return RankineBlocks.KRYPTON_GAS_BLOCK.get();
            case XENON:
                return RankineBlocks.XENON_GAS_BLOCK.get();
            case RADON:
                return RankineBlocks.RADON_GAS_BLOCK.get();
            case OGANESSON:
                return RankineBlocks.OGANESSON_GAS_BLOCK.get();
            case AMMONIA:
                return RankineBlocks.AMMONIA_GAS_BLOCK.get();
            case CARBON_DIOXIDE:
                return RankineBlocks.CARBON_DIOXIDE_GAS_BLOCK.get();
            case HYDROGEN_CHLORIDE:
                return RankineBlocks.HYDROGEN_CHLORIDE_GAS_BLOCK.get();
            case HYDROGEN_SULFIDE:
                return RankineBlocks.HYDROGEN_SULFIDE_GAS_BLOCK.get();
            case SULFUR_DIOXIDE:
                return RankineBlocks.SULFUR_DIOXIDE_GAS_BLOCK.get();
            default:
                return RankineBlocks.FLUORINE_GAS_BLOCK.get();
        }
    }
}
