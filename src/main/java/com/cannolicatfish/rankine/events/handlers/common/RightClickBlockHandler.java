package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.blocks.LEDBlock;
import com.cannolicatfish.rankine.blocks.beehiveoven.BeehiveOvenPitBlock;
import com.cannolicatfish.rankine.blocks.charcoalpit.CharcoalPitBlock;
import com.cannolicatfish.rankine.blocks.plants.DoubleCropsBlock;
import com.cannolicatfish.rankine.blocks.plants.TripleCropsBlock;
import com.cannolicatfish.rankine.blocks.states.TripleBlockSection;
import com.cannolicatfish.rankine.blocks.tilledsoil.TilledSoilBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.items.alloys.AlloyHoeItem;
import com.cannolicatfish.rankine.recipe.SluicingRecipe;
import com.cannolicatfish.rankine.recipe.StrippingRecipe;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

import static net.minecraft.block.Block.spawnAsEntity;

public class RightClickBlockHandler {
    public static void worldDye( PlayerInteractEvent.RightClickBlock event) {
        Set<ResourceLocation> target = event.getWorld().getBlockState(event.getPos()).getBlock().getTags();
        Set<ResourceLocation> hand = event.getItemStack().getItem().getTags();
        BlockState oldBlock = event.getWorld().getBlockState(event.getPos());

        BlockState newBlock = null;
        if (Config.GENERAL.COLOR_WORLD.get() && !event.getWorld().isRemote()) {
            if (hand.contains(new ResourceLocation("forge:dyes/red"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.RED_LED.get().getDefaultState().with(LEDBlock.LIT, oldBlock.get(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.RED_CONCRETE.getDefaultState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.RED_WOOL.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.RED_MINERAL_WOOL.get().getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.RED_STAINED_GLASS.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.RED_STAINED_GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, oldBlock.get(PaneBlock.NORTH)).with(PaneBlock.WEST, oldBlock.get(PaneBlock.WEST)).with(PaneBlock.EAST, oldBlock.get(PaneBlock.EAST)).with(PaneBlock.SOUTH, oldBlock.get(PaneBlock.SOUTH)).with(PaneBlock.WATERLOGGED, oldBlock.get(PaneBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.RED_CONCRETE_POWDER.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.RED_TERRACOTTA.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.RED_GLAZED_TERRACOTTA.getDefaultState().with(GlazedTerracottaBlock.HORIZONTAL_FACING, oldBlock.get(GlazedTerracottaBlock.HORIZONTAL_FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/orange"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.ORANGE_LED.get().getDefaultState().with(LEDBlock.LIT, oldBlock.get(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.ORANGE_CONCRETE.getDefaultState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.ORANGE_WOOL.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.ORANGE_MINERAL_WOOL.get().getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.ORANGE_STAINED_GLASS.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.ORANGE_STAINED_GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, oldBlock.get(PaneBlock.NORTH)).with(PaneBlock.WEST, oldBlock.get(PaneBlock.WEST)).with(PaneBlock.EAST, oldBlock.get(PaneBlock.EAST)).with(PaneBlock.SOUTH, oldBlock.get(PaneBlock.SOUTH)).with(PaneBlock.WATERLOGGED, oldBlock.get(PaneBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.ORANGE_CONCRETE_POWDER.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.ORANGE_TERRACOTTA.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.ORANGE_GLAZED_TERRACOTTA.getDefaultState().with(GlazedTerracottaBlock.HORIZONTAL_FACING, oldBlock.get(GlazedTerracottaBlock.HORIZONTAL_FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/yellow"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.YELLOW_LED.get().getDefaultState().with(LEDBlock.LIT, oldBlock.get(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.YELLOW_CONCRETE.getDefaultState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.YELLOW_WOOL.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.YELLOW_MINERAL_WOOL.get().getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.YELLOW_STAINED_GLASS.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.YELLOW_STAINED_GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, oldBlock.get(PaneBlock.NORTH)).with(PaneBlock.WEST, oldBlock.get(PaneBlock.WEST)).with(PaneBlock.EAST, oldBlock.get(PaneBlock.EAST)).with(PaneBlock.SOUTH, oldBlock.get(PaneBlock.SOUTH)).with(PaneBlock.WATERLOGGED, oldBlock.get(PaneBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.YELLOW_CONCRETE_POWDER.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.YELLOW_TERRACOTTA.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.YELLOW_GLAZED_TERRACOTTA.getDefaultState().with(GlazedTerracottaBlock.HORIZONTAL_FACING, oldBlock.get(GlazedTerracottaBlock.HORIZONTAL_FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/lime"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.LIME_LED.get().getDefaultState().with(LEDBlock.LIT, oldBlock.get(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.LIME_CONCRETE.getDefaultState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.LIME_WOOL.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.LIME_MINERAL_WOOL.get().getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.LIME_STAINED_GLASS.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.LIME_STAINED_GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, oldBlock.get(PaneBlock.NORTH)).with(PaneBlock.WEST, oldBlock.get(PaneBlock.WEST)).with(PaneBlock.EAST, oldBlock.get(PaneBlock.EAST)).with(PaneBlock.SOUTH, oldBlock.get(PaneBlock.SOUTH)).with(PaneBlock.WATERLOGGED, oldBlock.get(PaneBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.LIME_CONCRETE_POWDER.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.LIME_TERRACOTTA.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.LIME_GLAZED_TERRACOTTA.getDefaultState().with(GlazedTerracottaBlock.HORIZONTAL_FACING, oldBlock.get(GlazedTerracottaBlock.HORIZONTAL_FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/green"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.GREEN_LED.get().getDefaultState().with(LEDBlock.LIT, oldBlock.get(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.GREEN_CONCRETE.getDefaultState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.GREEN_WOOL.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.GREEN_MINERAL_WOOL.get().getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.GREEN_STAINED_GLASS.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.GREEN_STAINED_GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, oldBlock.get(PaneBlock.NORTH)).with(PaneBlock.WEST, oldBlock.get(PaneBlock.WEST)).with(PaneBlock.EAST, oldBlock.get(PaneBlock.EAST)).with(PaneBlock.SOUTH, oldBlock.get(PaneBlock.SOUTH)).with(PaneBlock.WATERLOGGED, oldBlock.get(PaneBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.GREEN_CONCRETE_POWDER.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.GREEN_TERRACOTTA.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.GREEN_GLAZED_TERRACOTTA.getDefaultState().with(GlazedTerracottaBlock.HORIZONTAL_FACING, oldBlock.get(GlazedTerracottaBlock.HORIZONTAL_FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/cyan"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.CYAN_LED.get().getDefaultState().with(LEDBlock.LIT, oldBlock.get(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.CYAN_CONCRETE.getDefaultState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.CYAN_WOOL.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.CYAN_MINERAL_WOOL.get().getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.CYAN_STAINED_GLASS.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.CYAN_STAINED_GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, oldBlock.get(PaneBlock.NORTH)).with(PaneBlock.WEST, oldBlock.get(PaneBlock.WEST)).with(PaneBlock.EAST, oldBlock.get(PaneBlock.EAST)).with(PaneBlock.SOUTH, oldBlock.get(PaneBlock.SOUTH)).with(PaneBlock.WATERLOGGED, oldBlock.get(PaneBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.CYAN_CONCRETE_POWDER.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.CYAN_TERRACOTTA.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.CYAN_GLAZED_TERRACOTTA.getDefaultState().with(GlazedTerracottaBlock.HORIZONTAL_FACING, oldBlock.get(GlazedTerracottaBlock.HORIZONTAL_FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/blue"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.BLUE_LED.get().getDefaultState().with(LEDBlock.LIT, oldBlock.get(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.BLUE_CONCRETE.getDefaultState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.BLUE_WOOL.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.BLUE_MINERAL_WOOL.get().getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.BLUE_STAINED_GLASS.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.BLUE_STAINED_GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, oldBlock.get(PaneBlock.NORTH)).with(PaneBlock.WEST, oldBlock.get(PaneBlock.WEST)).with(PaneBlock.EAST, oldBlock.get(PaneBlock.EAST)).with(PaneBlock.SOUTH, oldBlock.get(PaneBlock.SOUTH)).with(PaneBlock.WATERLOGGED, oldBlock.get(PaneBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.BLUE_CONCRETE_POWDER.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.BLUE_TERRACOTTA.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.BLUE_GLAZED_TERRACOTTA.getDefaultState().with(GlazedTerracottaBlock.HORIZONTAL_FACING, oldBlock.get(GlazedTerracottaBlock.HORIZONTAL_FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/light_blue"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.LIGHT_BLUE_LED.get().getDefaultState().with(LEDBlock.LIT, oldBlock.get(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.LIGHT_BLUE_CONCRETE.getDefaultState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.LIGHT_BLUE_WOOL.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.LIGHT_BLUE_MINERAL_WOOL.get().getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.LIGHT_BLUE_STAINED_GLASS.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.LIGHT_BLUE_STAINED_GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, oldBlock.get(PaneBlock.NORTH)).with(PaneBlock.WEST, oldBlock.get(PaneBlock.WEST)).with(PaneBlock.EAST, oldBlock.get(PaneBlock.EAST)).with(PaneBlock.SOUTH, oldBlock.get(PaneBlock.SOUTH)).with(PaneBlock.WATERLOGGED, oldBlock.get(PaneBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.LIGHT_BLUE_CONCRETE_POWDER.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.LIGHT_BLUE_TERRACOTTA.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA.getDefaultState().with(GlazedTerracottaBlock.HORIZONTAL_FACING, oldBlock.get(GlazedTerracottaBlock.HORIZONTAL_FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/magenta"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.MAGENTA_LED.get().getDefaultState().with(LEDBlock.LIT, oldBlock.get(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.MAGENTA_CONCRETE.getDefaultState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.MAGENTA_WOOL.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.MAGENTA_MINERAL_WOOL.get().getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.MAGENTA_STAINED_GLASS.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.MAGENTA_STAINED_GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, oldBlock.get(PaneBlock.NORTH)).with(PaneBlock.WEST, oldBlock.get(PaneBlock.WEST)).with(PaneBlock.EAST, oldBlock.get(PaneBlock.EAST)).with(PaneBlock.SOUTH, oldBlock.get(PaneBlock.SOUTH)).with(PaneBlock.WATERLOGGED, oldBlock.get(PaneBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.MAGENTA_CONCRETE_POWDER.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.MAGENTA_TERRACOTTA.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.MAGENTA_GLAZED_TERRACOTTA.getDefaultState().with(GlazedTerracottaBlock.HORIZONTAL_FACING, oldBlock.get(GlazedTerracottaBlock.HORIZONTAL_FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/purple"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.PURPLE_LED.get().getDefaultState().with(LEDBlock.LIT, oldBlock.get(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.PURPLE_CONCRETE.getDefaultState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.PURPLE_WOOL.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.PURPLE_MINERAL_WOOL.get().getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.PURPLE_STAINED_GLASS.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.PURPLE_STAINED_GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, oldBlock.get(PaneBlock.NORTH)).with(PaneBlock.WEST, oldBlock.get(PaneBlock.WEST)).with(PaneBlock.EAST, oldBlock.get(PaneBlock.EAST)).with(PaneBlock.SOUTH, oldBlock.get(PaneBlock.SOUTH)).with(PaneBlock.WATERLOGGED, oldBlock.get(PaneBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.PURPLE_CONCRETE_POWDER.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.PURPLE_TERRACOTTA.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.PURPLE_GLAZED_TERRACOTTA.getDefaultState().with(GlazedTerracottaBlock.HORIZONTAL_FACING, oldBlock.get(GlazedTerracottaBlock.HORIZONTAL_FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/pink"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.PINK_LED.get().getDefaultState().with(LEDBlock.LIT, oldBlock.get(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.PINK_CONCRETE.getDefaultState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.PINK_WOOL.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.PINK_MINERAL_WOOL.get().getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.PINK_STAINED_GLASS.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.PINK_STAINED_GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, oldBlock.get(PaneBlock.NORTH)).with(PaneBlock.WEST, oldBlock.get(PaneBlock.WEST)).with(PaneBlock.EAST, oldBlock.get(PaneBlock.EAST)).with(PaneBlock.SOUTH, oldBlock.get(PaneBlock.SOUTH)).with(PaneBlock.WATERLOGGED, oldBlock.get(PaneBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.PINK_CONCRETE_POWDER.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.PINK_TERRACOTTA.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.PINK_GLAZED_TERRACOTTA.getDefaultState().with(GlazedTerracottaBlock.HORIZONTAL_FACING, oldBlock.get(GlazedTerracottaBlock.HORIZONTAL_FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/brown"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.BROWN_LED.get().getDefaultState().with(LEDBlock.LIT, oldBlock.get(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.BROWN_CONCRETE.getDefaultState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.BROWN_WOOL.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.BROWN_MINERAL_WOOL.get().getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.BROWN_STAINED_GLASS.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:eglass_panes"))) {
                    newBlock = Blocks.BROWN_STAINED_GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, oldBlock.get(PaneBlock.NORTH)).with(PaneBlock.WEST, oldBlock.get(PaneBlock.WEST)).with(PaneBlock.EAST, oldBlock.get(PaneBlock.EAST)).with(PaneBlock.SOUTH, oldBlock.get(PaneBlock.SOUTH)).with(PaneBlock.WATERLOGGED, oldBlock.get(PaneBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.BROWN_CONCRETE_POWDER.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.BROWN_TERRACOTTA.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.BROWN_GLAZED_TERRACOTTA.getDefaultState().with(GlazedTerracottaBlock.HORIZONTAL_FACING, oldBlock.get(GlazedTerracottaBlock.HORIZONTAL_FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/black"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.BLACK_LED.get().getDefaultState().with(LEDBlock.LIT, oldBlock.get(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.BLACK_CONCRETE.getDefaultState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.BLACK_WOOL.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.BLACK_MINERAL_WOOL.get().getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.BLACK_STAINED_GLASS.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.BLACK_STAINED_GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, oldBlock.get(PaneBlock.NORTH)).with(PaneBlock.WEST, oldBlock.get(PaneBlock.WEST)).with(PaneBlock.EAST, oldBlock.get(PaneBlock.EAST)).with(PaneBlock.SOUTH, oldBlock.get(PaneBlock.SOUTH)).with(PaneBlock.WATERLOGGED, oldBlock.get(PaneBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.BLACK_CONCRETE_POWDER.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.BLACK_TERRACOTTA.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.BLACK_GLAZED_TERRACOTTA.getDefaultState().with(GlazedTerracottaBlock.HORIZONTAL_FACING, oldBlock.get(GlazedTerracottaBlock.HORIZONTAL_FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/white"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.WHITE_LED.get().getDefaultState().with(LEDBlock.LIT, oldBlock.get(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.WHITE_CONCRETE.getDefaultState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.WHITE_WOOL.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.WHITE_MINERAL_WOOL.get().getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.WHITE_STAINED_GLASS.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.WHITE_STAINED_GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, oldBlock.get(PaneBlock.NORTH)).with(PaneBlock.WEST, oldBlock.get(PaneBlock.WEST)).with(PaneBlock.EAST, oldBlock.get(PaneBlock.EAST)).with(PaneBlock.SOUTH, oldBlock.get(PaneBlock.SOUTH)).with(PaneBlock.WATERLOGGED, oldBlock.get(PaneBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.WHITE_CONCRETE_POWDER.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.WHITE_TERRACOTTA.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.WHITE_GLAZED_TERRACOTTA.getDefaultState().with(GlazedTerracottaBlock.HORIZONTAL_FACING, oldBlock.get(GlazedTerracottaBlock.HORIZONTAL_FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/gray"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.GRAY_LED.get().getDefaultState().with(LEDBlock.LIT, oldBlock.get(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.GRAY_CONCRETE.getDefaultState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.GRAY_WOOL.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.GRAY_MINERAL_WOOL.get().getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.GRAY_STAINED_GLASS.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.GRAY_STAINED_GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, oldBlock.get(PaneBlock.NORTH)).with(PaneBlock.WEST, oldBlock.get(PaneBlock.WEST)).with(PaneBlock.EAST, oldBlock.get(PaneBlock.EAST)).with(PaneBlock.SOUTH, oldBlock.get(PaneBlock.SOUTH)).with(PaneBlock.WATERLOGGED, oldBlock.get(PaneBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.GRAY_CONCRETE_POWDER.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.GRAY_TERRACOTTA.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.GRAY_GLAZED_TERRACOTTA.getDefaultState().with(GlazedTerracottaBlock.HORIZONTAL_FACING, oldBlock.get(GlazedTerracottaBlock.HORIZONTAL_FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/light_gray"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.LIGHT_GRAY_LED.get().getDefaultState().with(LEDBlock.LIT, oldBlock.get(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.LIGHT_GRAY_CONCRETE.getDefaultState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.LIGHT_GRAY_WOOL.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.LIGHT_GRAY_MINERAL_WOOL.get().getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.LIGHT_GRAY_STAINED_GLASS.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.LIGHT_GRAY_STAINED_GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, oldBlock.get(PaneBlock.NORTH)).with(PaneBlock.WEST, oldBlock.get(PaneBlock.WEST)).with(PaneBlock.EAST, oldBlock.get(PaneBlock.EAST)).with(PaneBlock.SOUTH, oldBlock.get(PaneBlock.SOUTH)).with(PaneBlock.WATERLOGGED, oldBlock.get(PaneBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.LIGHT_GRAY_CONCRETE_POWDER.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.LIGHT_GRAY_TERRACOTTA.getDefaultState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA.getDefaultState().with(GlazedTerracottaBlock.HORIZONTAL_FACING, oldBlock.get(GlazedTerracottaBlock.HORIZONTAL_FACING));
                }
            }
            if (newBlock != null) {
                event.getWorld().setBlockState(event.getPos(), newBlock, 3);
                if (!event.getPlayer().isCreative()) {
                    event.getItemStack().shrink(1);
                }
            }
        }
    }


    public static void flintFire(PlayerInteractEvent.RightClickBlock event) {
        if (Config.GENERAL.FLINT_FIRE.get() && event.getFace() != null) {
            BlockPos pos = event.getPos();
            World world = event.getWorld();
            Random rand = world.rand;
            PlayerEntity player = event.getPlayer();
            BlockPos blockpos1 = event.getPos().offset(event.getFace());
            if (player.getHeldItemMainhand().getItem() == Items.FLINT && player.getHeldItemOffhand().getItem() == Items.FLINT) {
                if (world.getBlockState(pos) == RankineBlocks.CHARCOAL_PIT.get().getDefaultState().with(CharcoalPitBlock.LIT, false) && !world.isRemote) {
                    for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(0, -Config.MACHINES.CHARCOAL_PIT_HEIGHT.get(), 0), pos.add(0, Config.MACHINES.CHARCOAL_PIT_HEIGHT.get(), 0))) {
                        if (world.getBlockState(blockpos).getBlock() == RankineBlocks.CHARCOAL_PIT.get()) {
                            world.setBlockState(blockpos, world.getBlockState(blockpos).with(BlockStateProperties.LIT, Boolean.TRUE), 3);
                        }
                    }
                    player.swingArm(Hand.MAIN_HAND);
                    if (rand.nextFloat() < Config.GENERAL.FLINT_FIRE_CHANCE.get()) {
                        player.getHeldItem(Hand.MAIN_HAND).shrink(1);
                        player.getHeldItem(Hand.OFF_HAND).shrink(1);
                    }
                    world.playSound(player, blockpos1, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
                } else if (world.getBlockState(pos) == RankineBlocks.BEEHIVE_OVEN_PIT.get().getDefaultState().with(BlockStateProperties.LIT, false)) {
                    if (!world.isRemote()) {
                        world.setBlockState(pos, world.getBlockState(pos).with(BlockStateProperties.LIT, Boolean.TRUE), 3);
                        player.swingArm(Hand.MAIN_HAND);
                        if (rand.nextFloat() < Config.GENERAL.FLINT_FIRE_CHANCE.get()) {
                            player.getHeldItem(Hand.MAIN_HAND).shrink(1);
                            player.getHeldItem(Hand.OFF_HAND).shrink(1);
                        }
                    }
                    world.playSound(player, blockpos1, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
                } else if (AbstractFireBlock.canLightBlock(world, blockpos1, event.getFace()) && !world.isRemote && !(world.getBlockState(pos).getBlock() instanceof BeehiveOvenPitBlock ) &&
                        world.getBlockState(pos) != RankineBlocks.CHARCOAL_PIT.get().getDefaultState().with(CharcoalPitBlock.LIT, true)) {
                    world.setBlockState(blockpos1, AbstractFireBlock.getFireForPlacement(world, blockpos1), 11);
                    player.swingArm(Hand.MAIN_HAND);
                    if (rand.nextFloat() < Config.GENERAL.FLINT_FIRE_CHANCE.get()) {
                        player.getHeldItem(Hand.MAIN_HAND).shrink(1);
                        player.getHeldItem(Hand.OFF_HAND).shrink(1);
                    }
                }
                world.playSound(player, blockpos1, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
            }
        }
    }


    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        World world = event.getWorld();
        Direction direction = event.getFace();
        Hand hand = event.getHand();
        BlockPos pos = event.getPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = event.getPlayer();

        if (RankineTags.Items.KNIVES.contains(stack.getItem()) && direction != null && hand == Hand.MAIN_HAND) {
            Block target = state.getBlock();
            if ((target.isIn(RankineTags.Blocks.GRASS_BLOCKS)) && direction.equals(Direction.UP)) {
                world.playSound(player, pos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                if (RankineLists.GRASS_BLOCKS.contains(target)) {
                    world.setBlockState(pos, RankineLists.SOIL_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(target)).getDefaultState(), 3);
                } else {
                    world.setBlockState(pos, Blocks.DIRT.getDefaultState(), 3);
                }
                player.swingArm(hand);
                if (!world.isRemote && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !world.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                    spawnAsEntity(world, pos.up(), new ItemStack(Items.GRASS, 1));
                }
                if (!world.isRemote) {
                    player.getHeldItem(hand).damageItem(1, player, (p_220038_0_) -> {
                        p_220038_0_.sendBreakAnimation(hand);
                    });
                }
            } else if (target == RankineBlocks.AGED_CHEESE.get()) {
                player.swingArm(hand);
                if (state.get(CakeBlock.BITES) < 6) {
                    world.setBlockState(pos, state.with(CakeBlock.BITES, state.get(CakeBlock.BITES) + 1));
                    player.addItemStackToInventory(new ItemStack(RankineItems.CHEESE.get(), 1));
                } else {
                    player.addItemStackToInventory(new ItemStack(RankineItems.CHEESE.get(), 1));
                    world.removeBlock(pos, false);
                }
                player.getHeldItem(hand).damageItem(1, player, (p_220040_1_) -> {
                    p_220040_1_.sendBreakAnimation(hand);
                });
                world.playSound(player, pos, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS, 0.7F, world.getRandom().nextFloat() * 0.4F + 0.5F);

            } else if (state.getBlock() == Blocks.CAKE) {
                player.swingArm(hand);
                if (state.get(CakeBlock.BITES) < 6) {
                    world.setBlockState(pos, state.with(CakeBlock.BITES, state.get(CakeBlock.BITES) + 1));
                    player.addItemStackToInventory(new ItemStack(RankineItems.CAKE_SLICE.get(), 1));
                } else {
                    player.addItemStackToInventory(new ItemStack(RankineItems.CAKE_SLICE.get(), 1));
                    world.removeBlock(pos, false);
                }
                player.getHeldItemMainhand().damageItem(1, player, (p_220040_1_) -> {
                    p_220040_1_.sendBreakAnimation(player.swingingHand);
                });
                world.playSound(player, pos, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS, 0.7F, world.getRandom().nextFloat() * 0.4F + 0.5F);

            }
        } else if (RankineTags.Items.SLUICING_TOOLS.contains(stack.getItem()) && direction != null && !player.getCooldownTracker().hasCooldown(stack.getItem())) {
            SluicingRecipe recipe = world.getRecipeManager().getRecipe(RankineRecipeTypes.SLUICING, new Inventory(new ItemStack(world.getBlockState(pos).getBlock()), stack), world).orElse(null);
            if (recipe != null) {
                float r = world.getRandom().nextFloat();
                world.playSound(player, pos, SoundEvents.BLOCK_SAND_FALL, SoundCategory.BLOCKS, 1.0F, r * 0.4F + 0.8F);
                world.playSound(player, pos, SoundEvents.BLOCK_SAND_FALL, SoundCategory.BLOCKS, 1.0F, r * 0.6F + 0.8F);
                world.playSound(player, pos, SoundEvents.BLOCK_SAND_FALL, SoundCategory.BLOCKS, 1.0F, r * 0.2F + 0.8F);
                ItemStack out = recipe.getSluicingResult(world);
                world.removeBlock(pos, false);
                player.swing(hand,true);
                if (!world.isRemote) {
                    Block.spawnAsEntity(world,pos,out);
                    if (Config.GENERAL.SLUICING_COOLDOWN.get()) {
                        player.getCooldownTracker().setCooldown(stack.getItem(), recipe.getCooldownTicks());
                    }

                    if (stack.getItem().isDamageable()) {
                        player.getHeldItemMainhand().damageItem(1, player, (p_220038_0_) -> {
                            p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                        });
                    }
                }
            }
        }

    }


    public static void axeStrip(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        World worldIn = event.getWorld();
        BlockPos pos = event.getPos();
        PlayerEntity player = event.getPlayer();
        BlockState targetBS = worldIn.getBlockState(pos);
        Block b = targetBS.getBlock();

        if(item instanceof AxeItem) {
            //Extra items from stripping recipe
            StrippingRecipe irecipe = worldIn.getRecipeManager().getRecipe(RankineRecipeTypes.STRIPPING, new Inventory(new ItemStack(b)), worldIn).orElse(null);
            if (irecipe != null) {
                if (worldIn.getRandom().nextFloat() < irecipe.getChance()) {
                    spawnAsEntity(event.getWorld(), event.getPos(), irecipe.getResult());
                }
            }
            if (b.isIn(BlockTags.LOGS) && !b.getRegistryName().toString().contains("stripped") && Config.GENERAL.STRIPPABLES_STICKS.get() && worldIn.getRandom().nextFloat() < 0.3) {
                spawnAsEntity(event.getWorld(), event.getPos(), new ItemStack(Items.STICK, 1));
            }

            if(VanillaIntegration.stripping_map.get(b) != null) {
                if(b instanceof RotatedPillarBlock) {
                    Direction.Axis axis = targetBS.get(RotatedPillarBlock.AXIS);
                    worldIn.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    worldIn.setBlockState(pos, VanillaIntegration.stripping_map.get(b).getDefaultState().with(RotatedPillarBlock.AXIS, axis), 2);
                    stack.damageItem(1, player, (entity) -> {
                        entity.sendBreakAnimation(event.getHand());
                    });
                    player.swingArm(event.getHand());
                    event.setResult(Event.Result.ALLOW);
                }
            }
        } else if (item instanceof ShovelItem) {
            if (VanillaIntegration.pathBlocks_map.get(b) != null) {
                worldIn.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                worldIn.setBlockState(pos, VanillaIntegration.pathBlocks_map.get(b).getDefaultState(), 2);
                stack.damageItem(1, player, (entity) -> {
                    entity.sendBreakAnimation(event.getHand());
                });
                player.swingArm(event.getHand());
                event.setResult(Event.Result.ALLOW);
            }
        } else if (item instanceof HoeItem) {
            if (VanillaIntegration.hoeables_map.get(b) != null) {
                worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                worldIn.setBlockState(pos, RankineBlocks.TILLED_SOIL.get().getDefaultState().with(TilledSoilBlock.MOISTURE, 0).with(TilledSoilBlock.SOIL_TYPE, VanillaIntegration.hoeables_map.get(b)), 3);
                stack.damageItem(1, player, (entity) -> {
                    entity.sendBreakAnimation(event.getHand());
                });
                player.swingArm(event.getHand());
                event.setResult(Event.Result.ALLOW);
            } else if (b instanceof DoubleCropsBlock && item instanceof AlloyHoeItem) {
                if (targetBS.hasProperty(CropsBlock.AGE) && targetBS.get(DoubleCropsBlock.AGE) == 7) {
                    if (targetBS.get(DoubleCropsBlock.SECTION) == DoubleBlockHalf.LOWER) {
                        worldIn.destroyBlock(pos,true);
                        if (targetBS.getBlock().isValidPosition(b.getDefaultState().with(CropsBlock.AGE, 0),worldIn,pos)) {
                            worldIn.setBlockState(pos, b.getDefaultState().with(CropsBlock.AGE, 0));
                        }
                    } else if (targetBS.get(DoubleCropsBlock.SECTION) == DoubleBlockHalf.UPPER) {
                        worldIn.destroyBlock(pos.down(),true);
                        if (targetBS.getBlock().isValidPosition(b.getDefaultState().with(CropsBlock.AGE, 0),worldIn,pos.down())) {
                            worldIn.setBlockState(pos.down(), b.getDefaultState().with(CropsBlock.AGE, 0));
                        }
                    }
                    if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ENDOSPORE, stack) > 0 && worldIn.getRandom().nextFloat() < (0.2f + Math.min(player.getLuck()/20f, 0.3))) {
                        Optional<BlockPos> bp = BlockPos.getClosestMatchingPosition(pos, 3, 3, blockPos ->!blockPos.equals(pos) && worldIn.isAirBlock(blockPos) && targetBS.getBlock().isValidPosition(b.getDefaultState().with(CropsBlock.AGE, 0), worldIn, blockPos));
                        bp.ifPresent(blockPos -> worldIn.setBlockState(blockPos, b.getDefaultState().with(CropsBlock.AGE, CropsBlock.AGE.getAllowedValues().stream().max(Integer::compareTo).orElse(0))));
                    }
                }
            } else if (b instanceof TripleCropsBlock && item instanceof AlloyHoeItem) {
                if (targetBS.hasProperty(CropsBlock.AGE) && targetBS.get(DoubleCropsBlock.AGE) == 7) {
                    if (targetBS.get(TripleCropsBlock.SECTION) == TripleBlockSection.BOTTOM) {
                        worldIn.destroyBlock(pos,true);
                        if (targetBS.getBlock().isValidPosition(b.getDefaultState().with(CropsBlock.AGE, 0),worldIn,pos)) {
                            worldIn.setBlockState(pos, b.getDefaultState().with(CropsBlock.AGE, 0));
                        }
                    } else if (targetBS.get(TripleCropsBlock.SECTION) == TripleBlockSection.MIDDLE) {
                        worldIn.destroyBlock(pos.down(),true);
                        if (targetBS.getBlock().isValidPosition(b.getDefaultState().with(CropsBlock.AGE, 0),worldIn,pos.down())) {
                            worldIn.setBlockState(pos.down(), b.getDefaultState().with(CropsBlock.AGE, 0));
                        }
                    } else if (targetBS.get(TripleCropsBlock.SECTION) == TripleBlockSection.TOP) {
                        worldIn.destroyBlock(pos.down(2),true);
                        if (targetBS.getBlock().isValidPosition(b.getDefaultState().with(CropsBlock.AGE, 0),worldIn,pos.down(2))) {
                            worldIn.setBlockState(pos.down(2), b.getDefaultState().with(CropsBlock.AGE, 0));
                        }
                    }
                    if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ENDOSPORE,stack) > 0 && worldIn.getRandom().nextFloat() < (0.2f + Math.min(player.getLuck()/20f,0.3))) {
                        Optional<BlockPos> bp = BlockPos.getClosestMatchingPosition(pos,3,3,blockPos -> !blockPos.equals(pos) && worldIn.isAirBlock(blockPos) && targetBS.getBlock().isValidPosition(b.getDefaultState().with(CropsBlock.AGE, 0),worldIn,blockPos));
                        bp.ifPresent(blockPos -> worldIn.setBlockState(blockPos, b.getDefaultState().with(CropsBlock.AGE, CropsBlock.AGE.getAllowedValues().stream().max(Integer::compareTo).orElse(0))));
                    }
                }
            } else if (b instanceof CropsBlock && item instanceof AlloyHoeItem) {

                if (targetBS.hasProperty(CropsBlock.AGE) && targetBS.get(CropsBlock.AGE) == 7) {
                    worldIn.destroyBlock(pos,true);
                    if (targetBS.getBlock().isValidPosition(b.getDefaultState().with(CropsBlock.AGE, 0),worldIn,pos)) {
                        worldIn.setBlockState(pos,b.getDefaultState().with(CropsBlock.AGE, 0));
                    }
                    if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ENDOSPORE,stack) > 0 && worldIn.getRandom().nextFloat() < (0.2f + Math.min(player.getLuck()/20f,0.3))) {
                        Optional<BlockPos> bp = BlockPos.getClosestMatchingPosition(pos,3,3,blockPos -> !blockPos.equals(pos) && worldIn.isAirBlock(blockPos) && targetBS.getBlock().isValidPosition(b.getDefaultState().with(CropsBlock.AGE, 0),worldIn,blockPos));
                        bp.ifPresent(blockPos -> worldIn.setBlockState(blockPos, b.getDefaultState().with(CropsBlock.AGE, CropsBlock.AGE.getAllowedValues().stream().max(Integer::compareTo).orElse(0))));
                    }
                }
            }
        }
    }

}
