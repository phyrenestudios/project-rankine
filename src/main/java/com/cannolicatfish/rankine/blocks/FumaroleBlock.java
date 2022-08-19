package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.gases.GasBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
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
    public boolean isRandomlyTicking(BlockState state) {
        return Config.GASES.ENABLE_GAS_VENTS.get();
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (Config.GASES.ENABLE_GAS_VENTS.get()) {
            List<BlockPos> surround = Arrays.asList(pos.above(),pos.below(),pos.west(),pos.east(),pos.north(),pos.south());
            if (!worldIn.isClientSide) {
                int airCount = (int) surround.stream().filter(worldIn::isEmptyBlock).count();
                for (int i = 0; i < airCount; i++) {
                    BlockPos close = BlockPos.findClosestMatch(pos,3,3,B -> worldIn.isEmptyBlock(B) && !(worldIn.getBlockState(B).getBlock() instanceof GasBlock)
                            && BlockPos.findClosestMatch(B,1,1,P -> worldIn.getBlockState(P).getBlock() instanceof GasBlock || worldIn.getBlockState(P).getBlock() instanceof FumaroleBlock).orElse(null) != null).orElse(null);
                    if (close == null) {
                        break;
                    } else {
                        worldIn.setBlock(close,getGas().defaultBlockState(),3);
                    }
                }
                if (random.nextFloat() < Config.GENERAL.FUMAROLE_DEPOSIT_RATE.get()) {
                    BlockPos deposit = new BlockPos(pos.getX()+random.nextInt(5)-2,pos.getY()+random.nextInt(5)-3,pos.getZ()+random.nextInt(5)-2);
                    if (worldIn.getBlockState(deposit).is(RankineTags.Blocks.FUMAROLE_DEPOSIT)) {
                        worldIn.setBlock(deposit,RankineBlocks.FUMAROLE_DEPOSIT.get().defaultBlockState(),3);
                    }
                }
            }
        }


        super.randomTick(state, worldIn, pos, random);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
        if (rand.nextFloat() < 0.01) {
            Random random = worldIn.getRandom();
            SimpleParticleType basicparticletype = ParticleTypes.SMOKE;
            worldIn.addAlwaysVisibleParticle(basicparticletype, true, (double)pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
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
