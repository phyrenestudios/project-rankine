package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.Item.Properties;

public class FlowerSeedsItem extends Item {
    public FlowerSeedsItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Block b = worldIn.getBlockState(pos).getBlock();
        ITag<Block> tag =  RankineTags.Blocks.FLOWER_SEEDS;
        if (b.getTags().contains(new ResourceLocation("forge:dirt")) && worldIn.getBlockState(pos.above()).isAir()) {
            Block flower = tag.getRandomElement(worldIn.getRandom());
            if (!worldIn.isClientSide) {
                worldIn.setBlockAndUpdate(pos.above(), flower.defaultBlockState());
                worldIn.playSound(context.getPlayer(), pos, SoundEvents.GRASS_PLACE, SoundCategory.BLOCKS, 0.7F, worldIn.getRandom().nextFloat() * 0.4F + 0.5F);
                context.getItemInHand().shrink(1);
                return ActionResultType.SUCCESS;
            }
        }

        return super.useOn(context);
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("Plants a random flower").withStyle(TextFormatting.GRAY));
    }




}
