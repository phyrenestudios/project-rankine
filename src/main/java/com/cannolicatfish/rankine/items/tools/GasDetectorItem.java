package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.RankineTags;
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

public class GasDetectorItem extends Item {
    public GasDetectorItem(Properties properties) {
        super(properties);
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        World worldIn = context.getWorld();
        BlockPos pos = context.getPos();

        Optional<BlockPos> b = BlockPos.getClosestMatchingPosition(pos, Config.TOOLS.ORE_DETECTOR_RANGE.get(), Config.TOOLS.ORE_DETECTOR_RANGE.get(), (p) -> RankineLists.GAS_BLOCKS.contains(worldIn.getBlockState(p).getBlock()));
        if (player != null && b.isPresent()) {
            worldIn.playSound(player,pos, SoundEvents.BLOCK_NOTE_BLOCK_BELL, SoundCategory.PLAYERS,1.0F, random.nextFloat() * 0.4F + 0.8F);

            if (!worldIn.isRemote()) {
                BlockState GAS = worldIn.getBlockState(b.get());
                player.sendStatusMessage(new TranslationTextComponent("item.rankine.gas_detector.message", new TranslationTextComponent(GAS.getBlock().getTranslationKey()).getString(), Integer.toString(GAS.getBlock().getHarvestLevel(GAS)), b.get().getX(), b.get().getY(), b.get().getZ()), false);

                context.getItem().damageItem(1, player, (p) -> {
                    p.sendBreakAnimation(context.getHand());
                });
            }
        }
        return ActionResultType.SUCCESS;
    }
}
