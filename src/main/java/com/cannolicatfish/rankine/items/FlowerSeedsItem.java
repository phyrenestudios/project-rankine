package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class FlowerSeedsItem extends Item {
    public FlowerSeedsItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        TagKey<Block> tag =  RankineTags.Blocks.FLOWER_SEEDS;
        if (worldIn.getBlockState(pos).is(BlockTags.DIRT) && worldIn.getBlockState(pos.above()).isAir()) {
            Block flower = ForgeRegistries.BLOCKS.tags().getTag(tag).getRandomElement(worldIn.getRandom()).orElse(Blocks.POPPY);
            if (!worldIn.isClientSide) {
                worldIn.setBlockAndUpdate(pos.above(), flower.defaultBlockState());
                worldIn.playSound(context.getPlayer(), pos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 0.7F, worldIn.getRandom().nextFloat() * 0.4F + 0.5F);
                context.getItemInHand().shrink(1);
                return InteractionResult.SUCCESS;
            }
        }

        return super.useOn(context);
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TextComponent("Plants a random flower").withStyle(ChatFormatting.GRAY));
    }




}
