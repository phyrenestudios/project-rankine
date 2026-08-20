package com.cannolicatfish.rankine.blocks.alloys;

import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * Holds the alloy data of a placed alloy block.
 *
 * <p>PORT NOTE (1.21.1): 1.18.2 kept a private {@code CompoundTag alloyData} here, filled from
 * {@code stack.getTag()} in {@code AlloyBlock#setPlacedBy} and written back onto the drop with
 * {@code itemstack.setTag(...)}. Data components replaced stack NBT, and vanilla now does both
 * halves of that copy itself: {@code BlockItem#place} calls
 * {@link BlockEntity#applyComponentsFromItemStack(net.minecraft.world.item.ItemStack)} before
 * {@code setPlacedBy} runs, which parks the placing stack's whole component patch on this block
 * entity, and {@code BlockEntity#saveWithoutMetadata} / {@code loadWithComponents} persist it with
 * the chunk. So the alloy composition, recipe, name override and colour ride on the block entity's
 * own component map — there is nothing left for a hand-rolled NBT field to carry, and the break
 * path only has to ask for it back via {@link BlockEntity#saveToItem}.
 *
 * <p>The old "AlloyData" tag is intentionally not read back: nothing on this branch ever wrote a
 * non-empty one (it was only ever filled from the stack's CUSTOM_DATA component, which alloy items
 * do not use), so there is no saved state to migrate.
 */
public class AlloyBlockTile extends BlockEntity {
    public AlloyBlockTile(BlockPos posIn, BlockState stateIn) {
        super(RankineBlockEntityTypes.ALLOY_BLOCK.get(), posIn, stateIn);
    }
}
