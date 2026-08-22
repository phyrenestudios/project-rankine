package com.cannolicatfish.rankine.blocks.alloys;

import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import com.cannolicatfish.rankine.init.RankineDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;

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
 *
 * <p>CLIENT SYNC (added in the release batch): {@link BlockEntity}'s defaults are an empty update
 * tag and a null update packet, so a client block entity held nothing at all — 1.18.2 was the same.
 * Everything the client wants from an alloy block is in that component map: the tint
 * ({@code AlloyBlockColor}), the pick-block stack ({@code AlloyBlock#getCloneItemStack}, a
 * client-side call) and the hover name. Both hooks below therefore carry the components, which is
 * exactly what {@code saveWithoutMetadata} writes and what NeoForge's default
 * {@code handleUpdateTag} / {@code onDataPacket} read back with {@code loadWithComponents}.
 */
public class AlloyBlockTile extends BlockEntity {
    public AlloyBlockTile(BlockPos posIn, BlockState stateIn) {
        super(RankineBlockEntityTypes.ALLOY_BLOCK.get(), posIn, stateIn);
    }

    /**
     * The placed block's alloy colour, or white (no tint) when it has none.
     *
     * <p>Same component and same "absent means 0xFFFFFF" rule as
     * {@code AlloyColorHelper#getColor} uses for the item, but returned as plain RGB: block tints go
     * through {@code BlockColors}, which ignores the alpha byte, whereas item tints are ARGB.
     */
    public int getAlloyColor() {
        Integer color = this.collectComponents().get(RankineDataComponents.COLOR.get());
        return color != null ? color : 16777215;
    }

    /** Chunk-load sync: the component map, which is where the whole alloy lives. */
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    /**
     * Placement / change sync. {@code ChunkHolder#broadcastBlockEntityIfNeeded} asks every changed
     * block-entity position for this packet and sends nothing when it is null, which is why a
     * freshly placed alloy block used to reach other players (and the placing player's own
     * authoritative copy) with no alloy data at all.
     */
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider registries) {
        super.onDataPacket(net, pkt, registries);
        refreshTint();
    }

    /**
     * Re-bake the section so {@code AlloyBlockColor} is asked again.
     *
     * <p>{@code BlockColors} is sampled while a chunk section is compiled, so a colour that arrives
     * after the section was built is invisible until something else rebuilds it. Two obvious calls
     * do NOT do this: {@code IBlockEntityExtension#requestModelDataUpdate} only invalidates
     * NeoForge's {@code ModelData} cache lazily and marks no section dirty, and
     * {@code Level#setBlocksDirty} routes to {@code LevelRenderer#setBlockDirty}, which first asks
     * {@code ModelManager#requiresRender(old, new)} — and that returns false whenever the two states
     * are the same object, which they are here. {@code sendBlockUpdated} reaches
     * {@code LevelRenderer#blockChanged}, which marks the surrounding sections dirty unconditionally.
     *
     * <p>Only needed on the packet path. The chunk-load path applies update tags in
     * {@code LevelChunk#replaceWithPacketData}, before the chunk is handed to the renderer.
     */
    private void refreshTint() {
        if (this.level != null && this.level.isClientSide) {
            BlockState state = this.getBlockState();
            this.level.sendBlockUpdated(this.worldPosition, state, state, Block.UPDATE_ALL);
        }
    }
}
