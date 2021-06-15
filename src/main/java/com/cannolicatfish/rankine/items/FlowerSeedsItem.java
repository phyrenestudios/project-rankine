package com.cannolicatfish.rankine.items;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class FlowerSeedsItem extends Item {
    public FlowerSeedsItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World worldIn = context.getWorld();
        BlockPos pos = context.getPos();
        Block b = worldIn.getBlockState(pos).getBlock();
        ITag<Block> tag =  BlockTags.getCollection().get(new ResourceLocation("rankine:flowers"));
        if (!worldIn.isRemote && tag != null && b.getTags().contains(new ResourceLocation("forge:dirt")) && worldIn.getBlockState(pos.up()).isAir()) {
            Block flower = tag.getRandomElement(worldIn.getRandom());
            //worldIn.playSound(context.getPlayer(), pos,SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS, 0.4f, 0.5f);
            if (Minecraft.getInstance().objectMouseOver != null && context.getPlayer() != null) {
                ActionResultType actionresulttype = ((BlockItem) flower.asItem()).tryPlace(new BlockItemUseContext(
                        new ItemUseContext(context.getWorld(),context.getPlayer(),context.getHand(), context.getItem(),(BlockRayTraceResult) Minecraft.getInstance().objectMouseOver)));
                return !actionresulttype.isSuccessOrConsume() && this.isFood() ? this.onItemRightClick(context.getWorld(), context.getPlayer(), context.getHand()).getType() : actionresulttype;
            } else {
                return ActionResultType.PASS;
            }
        }

        return super.onItemUse(context);
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
            tooltip.add(new StringTextComponent("Plants a random flower").mergeStyle(TextFormatting.GRAY));
    }


}
