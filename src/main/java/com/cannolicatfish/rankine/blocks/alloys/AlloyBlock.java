package com.cannolicatfish.rankine.blocks.alloys;

import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class AlloyBlock extends BaseEntityBlock {
    public AlloyBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new AlloyBlockTile(p_153215_, p_153216_);
    }

    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @org.jetbrains.annotations.Nullable LivingEntity placer, ItemStack stack) {
        if (!IAlloyItem.getAlloyComposition(stack).isEmpty()) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof AlloyBlockTile && stack.getTag() != null) {
                ((AlloyBlockTile) tileentity).writeAlloyData(stack.getTag());
            }
        }
        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void playerWillDestroy(Level levelIn, BlockPos pos, BlockState state, Player player) {
        if (!player.isCreative() && levelIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            if (levelIn.getBlockEntity(pos) instanceof AlloyBlockTile alloyBlockTile) {
                if (canHarvestBlock(state,levelIn,pos,player)) {
                    ItemStack itemstack = new ItemStack(state.getBlock());
                    itemstack.setTag(alloyBlockTile.getAlloyData());
                    popResource(levelIn, pos, itemstack);
                }
            }
        }

        super.playerWillDestroy(levelIn,pos,state,player);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        if (level.getBlockEntity(pos) instanceof AlloyBlockTile alloyBlockTile) {
            ItemStack itemstack = new ItemStack(state.getBlock());
            itemstack.setTag(alloyBlockTile.getAlloyData());
            return itemstack;
        }
        return super.getCloneItemStack(state, target, level, pos, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        return Collections.emptyList();
    }
}
