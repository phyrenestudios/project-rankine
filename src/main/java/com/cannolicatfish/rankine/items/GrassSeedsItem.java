package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class GrassSeedsItem extends Item {
    public GrassSeedsItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (!worldIn.isClientSide) {
            Block grass;
            BlockState target_bs = worldIn.getBlockState(pos);
            if (target_bs.is(Blocks.DIRT)) {
                grass = Blocks.GRASS_BLOCK;
            } else if (RankineLists.SOIL_BLOCKS.contains(target_bs.getBlock())) {
                grass = RankineLists.GRASS_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(target_bs.getBlock()));
            } else {
                return super.useOn(context);
            }
            worldIn.setBlockAndUpdate(pos, grass.defaultBlockState());
            worldIn.playSound(context.getPlayer(), pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 0.7F, worldIn.getRandom().nextFloat() * 0.4F + 0.5F);
            context.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Use on soil blocks to grow grass").withStyle(ChatFormatting.GRAY));
    }




}
