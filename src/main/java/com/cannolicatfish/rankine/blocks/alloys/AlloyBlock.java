package com.cannolicatfish.rankine.blocks.alloys;

import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
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

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @org.jetbrains.annotations.Nullable LivingEntity placer, ItemStack stack) {
        if (!IAlloyItem.getAlloyComposition(stack).isEmpty()) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            // PORT-TODO(datacomponents): stack.getTag() is gone — the old code copied the whole stack NBT into the
            // tile. Alloy data now round-trips through the CUSTOM_DATA component; items agent may switch this to a
            // dedicated alloy data component.
            CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
            if (tileentity instanceof AlloyBlockTile && customData != null) {
                ((AlloyBlockTile) tileentity).writeAlloyData(customData.copyTag());
            }
        }
        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public BlockState playerWillDestroy(Level levelIn, BlockPos pos, BlockState state, Player player) {
        if (!player.isCreative() && levelIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            if (levelIn.getBlockEntity(pos) instanceof AlloyBlockTile alloyBlockTile) {
                if (canHarvestBlock(state,levelIn,pos,player)) {
                    ItemStack itemstack = new ItemStack(state.getBlock());
                    // PORT-TODO(datacomponents): was itemstack.setTag(alloyBlockTile.getAlloyData())
                    itemstack.set(DataComponents.CUSTOM_DATA, CustomData.of(alloyBlockTile.getAlloyData()));
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
            // PORT-TODO(datacomponents): was itemstack.setTag(alloyBlockTile.getAlloyData())
            itemstack.set(DataComponents.CUSTOM_DATA, CustomData.of(alloyBlockTile.getAlloyData()));
            return itemstack;
        }
        return super.getCloneItemStack(state, target, level, pos, player);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return Collections.emptyList();
    }
}
