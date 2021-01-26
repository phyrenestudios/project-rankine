package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ToolItem;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
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
        if (blockIn.getBlock().getTags().contains(new ResourceLocation("rankine:glass_cutter"))) {
            return i >= blockIn.getHarvestLevel();
        }
        Material material = blockIn.getMaterial();
        return material == Material.GLASS;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (getToolTypes(stack).stream().anyMatch(state::isToolEffective)) return efficiency;
        return state.getBlock().getTags().contains(new ResourceLocation("rankine:glass_cutter")) ? this.efficiency : 0.4F;
    }

    @Nonnull
    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity playerentity = context.getPlayer();
        IWorld iworld = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockState blockstate = iworld.getBlockState(blockpos);
        if (playerentity != null) {
            context.getItem().damageItem(1, playerentity, (p_219998_1_) -> {
                p_219998_1_.sendBreakAnimation(context.getHand());
            });
        }
        if (blockstate.getBlock().getTags().contains(new ResourceLocation("forge:glass"))) {
            Block b = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockstate.getBlock().getRegistryName().toString() + "_pane"));
            if (b != null)
            {
                iworld.playSound(playerentity, blockpos, SoundEvents.ENTITY_BEE_STING, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 1.8F);
                iworld.setBlockState(blockpos,b.getDefaultState(),3);


                float f = 0.5F;
                double d0 = (double)(context.getWorld().rand.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double)(context.getWorld().rand.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double)(context.getWorld().rand.nextFloat() * 0.5F) + 0.25D;
                ItemEntity itementity = new ItemEntity(context.getWorld(), (double)blockpos.getX() + d0, (double)blockpos.getY() + d1, (double)blockpos.getZ() + d2, new ItemStack(b,2));
                itementity.setDefaultPickupDelay();
                context.getWorld().addEntity(itementity);
                return ActionResultType.SUCCESS;
            }
        }
        else
        {
            return ActionResultType.FAIL;
        }
        return ActionResultType.FAIL;
    }

    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (group == ItemGroup.SEARCH || group == ProjectRankine.setup.rankineTools) {
            ItemStack stack = new ItemStack(this.asItem(),1);
            stack.addEnchantment(Enchantments.SILK_TOUCH,1);
            items.add(stack);
        }
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH,stack) != 1){
            stack.addEnchantment(Enchantments.SILK_TOUCH,1);
        }
        super.onCreated(stack, worldIn, playerIn);
    }
}
