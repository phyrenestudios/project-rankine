package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockRayTraceResult;

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
    public ActionResultType onItemUse(ItemUseContext context) {
        if (Minecraft.getInstance().objectMouseOver != null && context.getPlayer() != null) {
            ActionResultType actionresulttype = ((BlockItem) getGas().asItem()).tryPlace(new BlockItemUseContext(
                    new ItemUseContext(context.getWorld(),context.getPlayer(),context.getHand(), context.getItem(),(BlockRayTraceResult) Minecraft.getInstance().objectMouseOver)));
            return !actionresulttype.isSuccessOrConsume() && this.isFood() ? this.onItemRightClick(context.getWorld(), context.getPlayer(), context.getHand()).getType() : actionresulttype;
        } else {
            return ActionResultType.PASS;
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
