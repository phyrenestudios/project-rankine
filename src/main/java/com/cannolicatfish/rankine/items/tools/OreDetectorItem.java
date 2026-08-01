package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;

import java.util.Optional;

public class OreDetectorItem extends Item {

    public OreDetectorItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();

        Optional<BlockPos> b = BlockPos.findClosestMatch(pos, Config.TOOLS.ORE_DETECTOR_RANGE.get(), Config.TOOLS.ORE_DETECTOR_RANGE.get(), (p) -> worldIn.getBlockState(p).is(Tags.Blocks.ORES));
        if (player != null && b.isPresent()) {
            worldIn.playSound(player,pos, SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.PLAYERS,1.0F, worldIn.getRandom().nextFloat() * 0.4F + 0.8F);

            if (!worldIn.isClientSide()) {
                BlockState ORE = worldIn.getBlockState(b.get());
                String cons = HardnessTesterItem.getHarvestTierName(ORE);
                player.displayClientMessage(Component.translatable("item.rankine.ore_detector.message", Component.translatable(ORE.getBlock().getDescriptionId()).getString(), cons, b.get().getX(), b.get().getY(), b.get().getZ()), false);
                context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
            }
        }
        return InteractionResult.SUCCESS;
    }
}
