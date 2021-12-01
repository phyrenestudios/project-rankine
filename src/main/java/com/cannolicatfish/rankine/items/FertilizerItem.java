package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.blocks.GrassySoilBlock;
import com.cannolicatfish.rankine.init.Config;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
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
import java.util.Random;

public class FertilizerItem extends Item {
    public FertilizerItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World worldIn = context.getWorld();
        BlockPos pos = context.getPos();

        int radius = Config.GENERAL.HERBICIDE_RANGE.get();

        if (!worldIn.isRemote) {
            for (BlockPos b : BlockPos.getAllInBoxMutable(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
                Block blk = worldIn.getBlockState(b).getBlock();
                if (blk instanceof GrassySoilBlock && b.distanceSq(pos) <= radius*radius) {
                    worldIn.setBlockState(b, blk.getDefaultState().with(GrassySoilBlock.DEAD, false), 2);
                }
            }
        }
        worldIn.playSound(null,pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS,0.5f,1.2f);
        context.getItem().shrink(1);
        return ActionResultType.SUCCESS;

    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
            tooltip.add(new StringTextComponent("Restores vegetation").mergeStyle(TextFormatting.GRAY));
    }


}
