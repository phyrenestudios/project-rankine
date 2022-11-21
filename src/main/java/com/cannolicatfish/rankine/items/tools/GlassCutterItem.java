package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class GlassCutterItem extends DiggerItem {

    public GlassCutterItem(Properties properties) {
        super(1.0f, -3.0f, RankineToolMaterials.PEWTER, RankineTags.Blocks.GLASS_CUTTER, properties);
    }

    /**
     * Check whether this Item can harvest the given Block
     */
    @Override
    public boolean isCorrectToolForDrops(BlockState blockIn) {
        Material material = blockIn.getMaterial();
        return material == Material.GLASS;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return state.is(RankineTags.Blocks.GLASS_CUTTER) ? this.speed : 0.1F;
    }

    @Nonnull
    public InteractionResult useOn(UseOnContext context) {
        Player playerentity = context.getPlayer();
        Level levelIn = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = levelIn.getBlockState(blockpos);
        if (playerentity != null) {
            context.getItemInHand().hurtAndBreak(1, playerentity, (p_219998_1_) -> {
                p_219998_1_.broadcastBreakEvent(context.getHand());
            });
        }
        if (blockstate.is(Tags.Blocks.GLASS)) {
            Block b = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockstate.getBlock().getRegistryName().toString() + "_pane"));
            if (b != null && b != Blocks.AIR) {
                levelIn.playSound(playerentity, blockpos, SoundEvents.BEE_STING, SoundSource.BLOCKS, 1.0F, levelIn.getRandom().nextFloat() * 0.4F + 1.8F);
                levelIn.setBlockAndUpdate(blockpos,b.defaultBlockState());
                Block.popResource(levelIn, blockpos, new ItemStack(b,2));
                return InteractionResult.SUCCESS;
            }
        } else {
            return InteractionResult.FAIL;
        }
        return InteractionResult.FAIL;
    }

    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (group == CreativeModeTab.TAB_SEARCH || group == ProjectRankine.setup.rankineTools) {
            ItemStack stack = new ItemStack(this.asItem(),1);
            stack.enchant(Enchantments.SILK_TOUCH,1);
            items.add(stack);
        }
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH,stack) != 1){
            stack.enchant(Enchantments.SILK_TOUCH,1);
        }
        super.onCraftedBy(stack, worldIn, playerIn);
    }
}
