package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.blocks.gases.GasBlock;
import com.cannolicatfish.rankine.init.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class GasDetectorItem extends Item {
    public GasDetectorItem(Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();

        Optional<BlockPos> b = BlockPos.findClosestMatch(pos, Config.TOOLS.ORE_DETECTOR_RANGE.get(), Config.TOOLS.ORE_DETECTOR_RANGE.get(), (p) -> worldIn.getBlockState(p).getBlock() instanceof GasBlock);
        if (player != null && b.isPresent()) {
            worldIn.playSound(player,pos, SoundEvents.NOTE_BLOCK_BELL, SoundSource.PLAYERS,1.0F, worldIn.getRandom().nextFloat() * 0.4F + 0.8F);

            if (!worldIn.isClientSide()) {
                BlockState GAS = worldIn.getBlockState(b.get());

                player.displayClientMessage(new TranslatableComponent("item.rankine.gas_detector.message", new TranslatableComponent(GAS.getBlock().getDescriptionId()).getString(), b.get().getX(), b.get().getY(), b.get().getZ()), false);
                context.getItemInHand().hurtAndBreak(1, player, (p) -> {
                    p.broadcastBreakEvent(context.getHand());
                });
            }
        }
        return InteractionResult.SUCCESS;
    }
}
