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

public class OreDetectorItem extends Item {

    public OreDetectorItem(Properties properties) {
        super(properties);
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        World worldIn = context.getWorld();
        BlockPos pos = context.getPos();

        Optional<BlockPos> b = BlockPos.getClosestMatchingPosition(pos, Config.TOOLS.ORE_DETECTOR_RANGE.get(), Config.TOOLS.ORE_DETECTOR_RANGE.get(), (p) -> worldIn.getBlockState(p).isIn(Tags.Blocks.ORES));
        if (player != null && b.isPresent()) {
            worldIn.playSound(player,pos, SoundEvents.BLOCK_NOTE_BLOCK_BELL, SoundCategory.PLAYERS,1.0F, random.nextFloat() * 0.4F + 0.8F);

            if (!worldIn.isRemote()) {
                BlockState ORE = worldIn.getBlockState(b.get());
                player.sendStatusMessage(new TranslationTextComponent("item.rankine.ore_detector.message", ORE.getBlock().getTranslatedName(), Integer.toString(ORE.getBlock().getHarvestLevel(ORE)), b.get().getX(), b.get().getY(), b.get().getZ()), false);

                context.getItem().damageItem(1, player, (p) -> {
                    p.sendBreakAnimation(context.getHand());
                });
            }
        }
        return ActionResultType.SUCCESS;
    }
}
