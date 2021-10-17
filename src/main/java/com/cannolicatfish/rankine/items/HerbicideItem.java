package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.blocks.GrassySoilBlock;
import com.cannolicatfish.rankine.blocks.RankineVerticalSlabBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.*;

public class HerbicideItem extends Item {
    public HerbicideItem(Properties properties) {
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
                if (blk.isIn(RankineTags.Blocks.HERBICIDAL) && b.distanceSq(pos) <= radius*radius) {
                    worldIn.destroyBlock(b,false);
                } else if (blk instanceof GrassySoilBlock) {
                    worldIn.setBlockState(b, blk.getDefaultState().with(GrassySoilBlock.DEAD, true), 2);
                }
            }
        }
        worldIn.playSound(null,pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS,0.5f,0.8f);
        context.getItem().shrink(1);
        return ActionResultType.SUCCESS;

    }

    public static void spawnParticles(World worldIn, BlockPos pos) {
        Random random = worldIn.getRandom();
        BasicParticleType basicparticletype = ParticleTypes.WHITE_ASH;
        worldIn.addOptionalParticle(basicparticletype,  true, (double)pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
            tooltip.add(new StringTextComponent("Kills vegetation").mergeStyle(TextFormatting.GRAY));
    }


}
