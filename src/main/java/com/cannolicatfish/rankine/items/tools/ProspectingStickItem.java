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

public class ProspectingStickItem extends Item {

    private final int range = Config.TOOLS.PROSPECTING_STICK_RANGE.get();
    public ProspectingStickItem(Properties properties) {
        super(properties);
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        World worldIn = context.getWorld();
        Direction searchDir = context.getFace().getOpposite();
        IBlockReader reader = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState blockstate = worldIn.getBlockState(pos);

        if (blockstate.isIn(RankineTags.Blocks.COBBLES)) {
            if (worldIn.getRandom().nextFloat() < Config.TOOLS.SAMPLE_CHANCE.get()/2f) {
                for (int i = 1; i < pos.getY(); ++i) {
                    Block target = worldIn.getBlockState(pos.down(i)).getBlock();
                    if (VanillaIntegration.oreNuggetMap.containsKey(target) && player != null && !worldIn.isRemote()) {
                        player.sendStatusMessage(new TranslationTextComponent("item.rankine.prospecting_stick_cobbles.message", new TranslationTextComponent(target.getTranslationKey()).getString()), true);
                        if (this.isDamageable() && !worldIn.isRemote()) {
                            context.getItem().damageItem(1, player, (p_219998_1_) -> {
                                p_219998_1_.sendBreakAnimation(context.getHand());
                            });
                        }
                    }
                }
            }
            if (!worldIn.isRemote) {
                worldIn.destroyBlock(pos, false);
            }
            return ActionResultType.SUCCESS;
        }

        if (worldIn.getRandom().nextFloat() < Config.TOOLS.SAMPLE_CHANCE.get()) {
            for (int x = 0; x <= range; x++) {
                if (reader.getBlockState(pos.offset(searchDir, x)).isIn(Tags.Blocks.ORES)) {
                    BlockState ORE = reader.getBlockState(pos.offset(searchDir, x));
                    if (player != null) {
                        worldIn.playSound(player, pos, SoundEvents.BLOCK_NOTE_BLOCK_BELL, SoundCategory.PLAYERS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
                        if (!worldIn.isRemote()) {
                            player.sendStatusMessage(new TranslationTextComponent("item.rankine.prospecting_stick.message", ORE.getBlock().getTranslatedName(), Integer.toString(ORE.getBlock().getHarvestLevel(ORE))), true);
                            context.getItem().damageItem(1, player, (p) -> {
                                p.sendBreakAnimation(context.getHand());
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
