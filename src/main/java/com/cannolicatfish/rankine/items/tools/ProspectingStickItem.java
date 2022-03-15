package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import net.minecraft.item.Item.Properties;

public class ProspectingStickItem extends Item {

    private final int range = Config.TOOLS.PROSPECTING_STICK_RANGE.get();
    public ProspectingStickItem(Properties properties) {
        super(properties);
    }

    public ActionResultType useOn(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        World worldIn = context.getLevel();
        Direction searchDir = context.getClickedFace().getOpposite();
        IBlockReader reader = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState blockstate = worldIn.getBlockState(pos);

        if (blockstate.is(RankineTags.Blocks.COBBLES)) {
            if (worldIn.getRandom().nextFloat() < Config.TOOLS.SAMPLE_CHANCE.get()/2f) {
                for (int i = 1; i < pos.getY(); ++i) {
                    Block target = worldIn.getBlockState(pos.below(i)).getBlock();
                    if (VanillaIntegration.oreNuggetMap.containsKey(target) && player != null && !worldIn.isClientSide()) {
                        player.displayClientMessage(new TranslationTextComponent("item.rankine.prospecting_stick_cobbles.message", target.getName()), true);
                        if (this.canBeDepleted() && !worldIn.isClientSide()) {
                            context.getItemInHand().hurtAndBreak(1, player, (p_219998_1_) -> {
                                p_219998_1_.broadcastBreakEvent(context.getHand());
                            });
                        }
                    }
                }
            }
            if (!worldIn.isClientSide) {
                worldIn.destroyBlock(pos, false);
            }
            return ActionResultType.SUCCESS;
        }

        if (worldIn.getRandom().nextFloat() < Config.TOOLS.SAMPLE_CHANCE.get()) {
            for (int x = 0; x <= range; x++) {
                if (reader.getBlockState(pos.relative(searchDir, x)).is(Tags.Blocks.ORES)) {
                    BlockState ORE = reader.getBlockState(pos.relative(searchDir, x));
                    if (player != null) {
                        worldIn.playSound(player, pos, SoundEvents.NOTE_BLOCK_BELL, SoundCategory.PLAYERS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
                        if (!worldIn.isClientSide()) {
                            player.displayClientMessage(new TranslationTextComponent("item.rankine.prospecting_stick.message", ORE.getBlock().getName(), Integer.toString(ORE.getBlock().getHarvestLevel(ORE))), true);
                            context.getItemInHand().hurtAndBreak(1, player, (p) -> {
                                p.broadcastBreakEvent(context.getHand());
                            });
                        }
                    }
                    return ActionResultType.SUCCESS;
                }

            }
        }

        return ActionResultType.SUCCESS;
    }
}
