package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

import net.minecraft.world.item.context.UseOnContext;

public class GasElementItem extends ElementItem {
    GasUtilsEnum gas;
    public GasElementItem(float waterReactive, boolean canBreakBlocks, int radioactive, GasUtilsEnum gas, Properties properties) {
        super(waterReactive, canBreakBlocks, radioactive, properties);
        this.gas = gas;
    }

    public GasUtilsEnum getGasEnum() {
        return gas;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction opp = context.getClickedFace();
        if (context.getPlayer() != null) {
            worldIn.setBlockAndUpdate(pos.relative(opp), this.getGas().defaultBlockState());
            context.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
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
            default:
                return Blocks.AIR;
        }
    }
}
