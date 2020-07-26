package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.ModTags;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;

import java.util.Set;

public class GlassCutterItem extends ToolItem {

    private static final Set<Block> EFFECTIVE_ON = ImmutableSet.of(Blocks.GLASS);

    public GlassCutterItem(Properties properties) {
        super(1.0f, -3.0f, RankineToolMaterials.FLINT, EFFECTIVE_ON, properties);
    }

    /**
     * Check whether this Item can harvest the given Block
     */
    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        int i = this.getTier().getHarvestLevel();
        if (blockIn.getBlock().isIn(ModTags.GLASS)) {
            return i >= blockIn.getHarvestLevel();
        }
        Material material = blockIn.getMaterial();
        return material == Material.GLASS;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (getToolTypes(stack).stream().anyMatch(state::isToolEffective)) return efficiency;
        return state.getBlock().isIn(ModTags.GLASS) ? this.efficiency : 0.5F;
    }

}
