package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineTags;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ToolItem;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Set;

import net.minecraft.item.Item.Properties;

public class GlassCutterItem extends ToolItem {

    private static final Set<Block> EFFECTIVE_ON = ImmutableSet.of(Blocks.GLASS);

    public GlassCutterItem(Properties properties) {
        super(1.0f, -3.0f, RankineToolMaterials.PEWTER, EFFECTIVE_ON, properties);
    }

    /**
     * Check whether this Item can harvest the given Block
     */
    @Override
    public boolean isCorrectToolForDrops(BlockState blockIn) {
        int i = this.getTier().getLevel();
        if (blockIn.is(RankineTags.Blocks.GLASS_CUTTER)) {
            return i >= blockIn.getHarvestLevel();
        }
        Material material = blockIn.getMaterial();
        return material == Material.GLASS;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (getToolTypes(stack).stream().anyMatch(state::isToolEffective)) return speed;
        return state.is(RankineTags.Blocks.GLASS_CUTTER) ? this.speed : 0.1F;
    }

    @Nonnull
    public ActionResultType useOn(ItemUseContext context) {
        PlayerEntity playerentity = context.getPlayer();
        IWorld iworld = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = iworld.getBlockState(blockpos);
        if (playerentity != null) {
            context.getItemInHand().hurtAndBreak(1, playerentity, (p_219998_1_) -> {
                p_219998_1_.broadcastBreakEvent(context.getHand());
            });
        }
        if (blockstate.getBlock().getTags().contains(new ResourceLocation("forge:glass"))) {
            Block b = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockstate.getBlock().getRegistryName().toString() + "_pane"));
            if (b != null)
            {
                iworld.playSound(playerentity, blockpos, SoundEvents.BEE_STING, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 1.8F);
                iworld.setBlock(blockpos,b.defaultBlockState(),3);


                float f = 0.5F;
                double d0 = (double)(context.getLevel().random.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double)(context.getLevel().random.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double)(context.getLevel().random.nextFloat() * 0.5F) + 0.25D;
                ItemEntity itementity = new ItemEntity(context.getLevel(), (double)blockpos.getX() + d0, (double)blockpos.getY() + d1, (double)blockpos.getZ() + d2, new ItemStack(b,2));
                itementity.setDefaultPickUpDelay();
                context.getLevel().addFreshEntity(itementity);
                return ActionResultType.SUCCESS;
            }
        }
        else
        {
            return ActionResultType.FAIL;
        }
        return ActionResultType.FAIL;
    }

    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if (group == ItemGroup.TAB_SEARCH || group == ProjectRankine.setup.rankineTools) {
            ItemStack stack = new ItemStack(this.asItem(),1);
            stack.enchant(Enchantments.SILK_TOUCH,1);
            items.add(stack);
        }
    }

    @Override
    public void onCraftedBy(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH,stack) != 1){
            stack.enchant(Enchantments.SILK_TOUCH,1);
        }
        super.onCraftedBy(stack, worldIn, playerIn);
    }
}
