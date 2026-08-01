package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nonnull;

// PORT-TODO(init): creative-tab entry (GlassCutter pre-enchanted with Silk Touch I, formerly in
// fillItemCategory for TAB_SEARCH + rankineTools) must move to RankineCreativeTabs.
public class GlassCutterItem extends DiggerItem {

    public GlassCutterItem(Properties properties) {
        super(RankineToolMaterials.PEWTER, RankineTags.Blocks.GLASS_CUTTER,
                properties.attributes(DiggerItem.createAttributes(RankineToolMaterials.PEWTER, 1.0f, -3.0f)));
    }

    /**
     * Check whether this Item can harvest the given Block
     */
    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState blockIn) {
        // PORT-TODO: Material.GLASS approximation (was material == Material.GLASS)
        return blockIn.is(Tags.Blocks.GLASS_BLOCKS);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return state.is(RankineTags.Blocks.GLASS_CUTTER) ? RankineToolMaterials.PEWTER.getSpeed() : 0.1F;
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player playerentity = context.getPlayer();
        Level levelIn = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = levelIn.getBlockState(blockpos);
        if (playerentity != null) {
            context.getItemInHand().hurtAndBreak(1, playerentity, LivingEntity.getSlotForHand(context.getHand()));
        }
        if (blockstate.is(Tags.Blocks.GLASS_BLOCKS)) {
            Block b = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(BuiltInRegistries.BLOCK.getKey(blockstate.getBlock()).toString() + "_pane"));
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

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        Holder<Enchantment> silkTouch = worldIn.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.SILK_TOUCH);
        if (EnchantmentHelper.getItemEnchantmentLevel(silkTouch,stack) != 1){
            stack.enchant(silkTouch,1);
        }
        super.onCraftedBy(stack, worldIn, playerIn);
    }
}
