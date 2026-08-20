package com.cannolicatfish.rankine.blocks.alloys;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class AlloyBlock extends BaseEntityBlock {
    public static final MapCodec<AlloyBlock> CODEC = simpleCodec(AlloyBlock::new);

    @Override
    protected MapCodec<AlloyBlock> codec() {
        return CODEC;
    }

    public AlloyBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new AlloyBlockTile(p_153215_, p_153216_);
    }

    @Override
    protected RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    // No setPlacedBy override: 1.18.2 needed one to copy the stack's NBT onto the tile, but 1.21.1's
    // BlockItem#place already calls BlockEntity#applyComponentsFromItemStack (immediately before
    // setPlacedBy), and that parks the placing stack's component patch — composition, alloy recipe,
    // name override, colour — on the AlloyBlockTile, where the chunk save keeps it. See
    // AlloyBlockTile's javadoc.

    @Override
    public BlockState playerWillDestroy(Level levelIn, BlockPos pos, BlockState state, Player player) {
        if (!player.isCreative() && levelIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            if (levelIn.getBlockEntity(pos) instanceof AlloyBlockTile alloyBlockTile) {
                if (canHarvestBlock(state,levelIn,pos,player)) {
                    ItemStack itemstack = new ItemStack(state.getBlock());
                    // 1.21.1 equivalent of the old itemstack.setTag(alloyBlockTile.getAlloyData()):
                    // saveToItem re-applies the components the block entity is holding. The previous
                    // code wrote CUSTOM_DATA, which nothing on an alloy stack reads or writes, so
                    // every placed-then-broken alloy block came back with its composition wiped and
                    // reset to the item's default on the next inventory tick.
                    alloyBlockTile.saveToItem(itemstack, levelIn.registryAccess());
                    popResource(levelIn, pos, itemstack);
                }
            }
        }

        return super.playerWillDestroy(levelIn,pos,state,player);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        if (level.getBlockEntity(pos) instanceof AlloyBlockTile alloyBlockTile) {
            ItemStack itemstack = new ItemStack(state.getBlock());
            alloyBlockTile.saveToItem(itemstack, level.registryAccess());
            return itemstack;
        }
        return super.getCloneItemStack(state, target, level, pos, player);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return Collections.emptyList();
    }
}
