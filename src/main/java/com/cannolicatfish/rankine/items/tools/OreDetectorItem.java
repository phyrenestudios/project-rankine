package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.Optional;

import net.minecraft.item.Item.Properties;

public class OreDetectorItem extends Item {

    public OreDetectorItem(Properties properties) {
        super(properties);
    }

    public ActionResultType useOn(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        World worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();

        Optional<BlockPos> b = BlockPos.findClosestMatch(pos, Config.TOOLS.ORE_DETECTOR_RANGE.get(), Config.TOOLS.ORE_DETECTOR_RANGE.get(), (p) -> worldIn.getBlockState(p).is(Tags.Blocks.ORES));
        if (player != null && b.isPresent()) {
            worldIn.playSound(player,pos, SoundEvents.NOTE_BLOCK_BELL, SoundCategory.PLAYERS,1.0F, random.nextFloat() * 0.4F + 0.8F);

            if (!worldIn.isClientSide()) {
                BlockState ORE = worldIn.getBlockState(b.get());
                player.displayClientMessage(new TranslationTextComponent("item.rankine.ore_detector.message", ORE.getBlock().getName(), Integer.toString(ORE.getBlock().getHarvestLevel(ORE)), b.get().getX(), b.get().getY(), b.get().getZ()), false);

                context.getItemInHand().hurtAndBreak(1, player, (p) -> {
                    p.broadcastBreakEvent(context.getHand());
                });
            }
        }
        return ActionResultType.SUCCESS;
    }
}
