package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.InteractionResult;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class FlowerSeedsItem extends Item {
    public FlowerSeedsItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Block b = worldIn.getBlockState(pos).getBlock();
        Tag<Block> tag =  RankineTags.Blocks.FLOWER_SEEDS;
        if (b.getTags().contains(new ResourceLocation("forge:dirt")) && worldIn.getBlockState(pos.above()).isAir()) {
            Block flower = tag.getRandomElement(worldIn.getRandom());
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
