package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.blocks.GrassySoilBlock;
import com.cannolicatfish.rankine.blocks.plants.DoubleCropsBlock;
import com.cannolicatfish.rankine.blocks.plants.TripleCropsBlock;
import com.cannolicatfish.rankine.blocks.states.TripleBlockSection;
import com.cannolicatfish.rankine.blocks.tilledsoil.TilledSoilBlock;
import com.cannolicatfish.rankine.blocks.plants.RankinePlantBlock;
import com.cannolicatfish.rankine.blocks.states.TilledSoilTypes;
import com.cannolicatfish.rankine.compatibility.Patchouli;
import com.cannolicatfish.rankine.entities.goals.EatGrassGoalModified;
import com.cannolicatfish.rankine.init.RankineFluids;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.blocks.CharcoalPitBlock;
import com.cannolicatfish.rankine.blocks.LEDBlock;
import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.blocks.beehiveoven.BeehiveOvenPitBlock;
import com.cannolicatfish.rankine.commands.CreateAlloyCommand;
import com.cannolicatfish.rankine.commands.GiveTagCommand;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.items.InformationItem;
import com.cannolicatfish.rankine.items.alloys.*;
import com.cannolicatfish.rankine.items.tools.CrowbarItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.items.tools.KnifeItem;
import com.cannolicatfish.rankine.items.tools.SpearItem;
import com.cannolicatfish.rankine.potion.RankineEffects;
import com.cannolicatfish.rankine.recipe.RockGeneratorRecipe;
import com.cannolicatfish.rankine.recipe.SluicingRecipe;
import com.cannolicatfish.rankine.recipe.helper.FluidHelper;
import com.cannolicatfish.rankine.util.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.patchouli.api.PatchouliAPI;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.*;
import java.util.stream.Stream;

import static net.minecraft.block.Block.spawnAsEntity;

@Mod.EventBusSubscriber
public class RankineEventHandler {


    @SubscribeEvent
    public static void onItemPickup(PlayerEvent.ItemPickupEvent event) {

        // Totem of Cobbling
        if ((event.getStack().getItem().getTags().contains(new ResourceLocation("forge:stone")) || event.getStack().getItem() == Items.COBBLESTONE) && (event.getPlayer().getHeldItemMainhand().getItem() == RankineItems.TOTEM_OF_COBBLING.get() || event.getPlayer().getHeldItemOffhand().getItem() == RankineItems.TOTEM_OF_COBBLING.get())) {
            PlayerEntity player = event.getPlayer();
            ItemStack totem = player.getHeldItemMainhand().getItem() == RankineItems.TOTEM_OF_COBBLING.get() ? player.getHeldItemMainhand() : player.getHeldItemOffhand();
            if (totem.getDamage() != 0) {
                int x = totem.getDamage() - event.getStack().copy().getCount();
                player.inventory.getStackInSlot(event.getPlayer().inventory.getSlotFor(event.getStack())).shrink(totem.getDamage());
                totem.setDamage(Math.max(x,0));

            }
        }
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CreateAlloyCommand.register(event.getDispatcher());
        GiveTagCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void addWandererTrades(WandererTradesEvent event) {
        if (Config.GENERAL.VILLAGER_TRADES.get()) {
            event.getGenericTrades().add(new BasicTrade(1,new ItemStack(RankineItems.PINEAPPLE.get(), 1),4,1,0.5f));
            event.getGenericTrades().add(new BasicTrade(1,new ItemStack(RankineBlocks.LIMESTONE.get(), 8),8,1,0.05f));
            event.getRareTrades().add(new BasicTrade(3,new ItemStack(RankineItems.METEORIC_IRON.get()),6,1,0.5f));
        }
    }

    @SubscribeEvent
    public static void addVillagerTrades(VillagerTradesEvent event) {
        List<VillagerTrades.ITrade> level1 = event.getTrades().get(1);
        List<VillagerTrades.ITrade> level2 = event.getTrades().get(2);
        List<VillagerTrades.ITrade> level3 = event.getTrades().get(3);
        List<VillagerTrades.ITrade> level4 = event.getTrades().get(4);
        List<VillagerTrades.ITrade> level5 = event.getTrades().get(5);

        if (event.getType() == RankineVillagerProfessions.METALLURGIST) {
            level1.add(new BasicTrade(1, new ItemStack(RankineItems.ALLOY_TEMPLATE.get()),12,1,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.TIN_INGOT.get(), 8), new ItemStack(Items.EMERALD),12,2,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.COPPER_INGOT.get(), 4), new ItemStack(Items.EMERALD),12,2,0.05f));
            level2.add(new BasicTrade(1, new ItemStack(RankineItems.ZINC_INGOT.get(), 2),12,10,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.METEORIC_IRON.get(), 4), new ItemStack(Items.EMERALD),12,10,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.COIN.get(), 16), new ItemStack(Items.EMERALD),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.MANGANESE_INGOT.get(), 2),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.MOLYBDENUM_INGOT.get(), 2),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.VANADIUM_INGOT.get(), 2),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.NIOBIUM_INGOT.get(), 2),12,10,0.05f));
            level4.add(new BasicTrade(6, new ItemStack(RankineItems.ELEMENT_INDEXER.get()),12,15,0.05f));
            level5.add(new BasicTrade(10, new ItemStack(RankineItems.ORE_DETECTOR.get()),12,30,0.05f));
        } else if (event.getType() == RankineVillagerProfessions.MINERALOGIST) {
            level1.add(new BasicTrade(1, new ItemStack(RankineItems.STIBNITE.get()),12,1,0.05f));
            level1.add(new BasicTrade(1, new ItemStack(RankineItems.PROSPECTING_STICK.get()),12,1,0.05f));
            level1.add(new BasicTrade(1, new ItemStack(RankineItems.HARDNESS_TESTER.get()),12,1,0.05f));
            level2.add(new BasicTrade(1, new ItemStack(RankineItems.CHALCOPYRITE.get()),12,5,0.05f));
            level2.add(new BasicTrade(1, new ItemStack(RankineItems.BORAX.get()),12,5,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.MICA.get(), 4), new ItemStack(Items.EMERALD),12,10,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.AMPHIBOLE.get(), 4), new ItemStack(Items.EMERALD),12,10,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.PLAGIOCLASE_FELDSPAR.get(), 4), new ItemStack(Items.EMERALD),12,10,0.05f));
            level3.add(new RankineVillagerTrades.EnchantedAlloyItemForEmeraldsTrade(RankineItems.INVAR_HAMMER.get(),"90Fe-10Ni",8,3,10,0.2f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.ZIRCON.get()),12,15,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.BAUXITE.get()),12,15,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.MAGNESITE.get()),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(RankineItems.VANADINITE.get()),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(RankineItems.PETALITE.get()),12,15,0.05f));
            level4.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.OLIVINE.get(), 4), new ItemStack(Items.EMERALD),12,20,0.05f));
            level4.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.PYROXENE.get(), 4), new ItemStack(Items.EMERALD),12,20,0.05f));
            level5.add(new BasicTrade(1, new ItemStack(RankineItems.WOLFRAMITE.get()),12,30,0.05f));
            level5.add(new BasicTrade(1, new ItemStack(RankineItems.COBALTITE.get()),12,30,0.05f));
            level5.add(new RankineVillagerTrades.EnchantedAlloyItemForEmeraldsTrade(RankineItems.STEEL_HAMMER.get(),"99Fe-1C",15,3,30,0.2f));
        } else if (event.getType() == RankineVillagerProfessions.BOTANIST) {
            level1.addAll(RankineVillagerTrades.returnTagTrades(new ResourceLocation("rankine:flowers"),Items.DANDELION,3,1,12,10,0.05f));
            level1.addAll(RankineVillagerTrades.returnTagTrades(new ResourceLocation("minecraft:tall_flowers"),Items.ROSE_BUSH,2,1,12,10,0.05f));
            level1.addAll(RankineVillagerTrades.returnTagTrades(new ResourceLocation("forge:berries"),RankineItems.ELDERBERRIES.get(),2,1,12,10,0.05f));
            level2.addAll(RankineVillagerTrades.returnTagTrades(new ResourceLocation("minecraft:saplings"),Items.OAK_SAPLING,2,1,12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.BAMBOO, 4),12,15,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.VINE, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.LILY_PAD, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.RED_MUSHROOM, 4),12,15,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.BROWN_MUSHROOM, 4),12,15,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.SEA_PICKLE, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.KELP, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.SUGAR_CANE, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.CACTUS, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.DEAD_BUSH, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.NETHER_WART, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.POISONOUS_POTATO, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.ALOE.get(), 4),12,30,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(Items.WARPED_NYLIUM, 1),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(Items.CRIMSON_NYLIUM, 1),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(Items.BRAIN_CORAL, 2),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(Items.BUBBLE_CORAL, 2),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(Items.FIRE_CORAL, 2),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(Items.HORN_CORAL, 2),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(Items.TUBE_CORAL, 2),12,15,0.05f));
            level5.add(new BasicTrade(1, new ItemStack(Items.SHROOMLIGHT, 2),12,15,0.05f));
            level5.add(new BasicTrade(1, new ItemStack(Items.MYCELIUM, 1),12,30,0.05f));
            level5.add(new BasicTrade(5, new ItemStack(Items.CHORUS_FLOWER, 1),12,30,0.05f));
            level5.add(new BasicTrade(10, new ItemStack(Items.WITHER_ROSE),12,30,0.05f));

        } else if (event.getType() == RankineVillagerProfessions.GEM_CUTTER) {
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.AQUAMARINE.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.OPAL.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.GARNET.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.RUBY.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.SAPPHIRE.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.PERIDOT.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.TOPAZ.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.PEARL.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.TOURMALINE.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.TIGER_IRON.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.LABRADORITE.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.RHODONITE.get(), 1), new ItemStack(Items.EMERALD, 3),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.RHODOCHROSITE.get(), 1), new ItemStack(Items.EMERALD, 3),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.CHROME_ENSTATITE.get(), 1), new ItemStack(Items.EMERALD, 3),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.FLUORITE.get(), 3), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            List<Block> geodes = BlockTags.getCollection().get(new ResourceLocation("rankine:geodes")).getAllElements();
            if (!geodes.isEmpty()) {
                for (Block geode : geodes) {
                    level3.add((entity, rand) -> new MerchantOffer(new ItemStack(RankineItems.UNCUT_GEODE.get(), 1), new ItemStack(Items.EMERALD, 5), new ItemStack(geode.asItem(), 1), 3, 20, 0.05f));
                }
            }
            level4.addAll(RankineVillagerTrades.returnTagTrades(new ResourceLocation("forge:gems"),RankineItems.OPAL.get(),1,12,16,10,0.05f));
            level5.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.LONSDALEITE_DIAMOND.get(), 1), new ItemStack(Items.EMERALD, 6),12,20,0.05f));
            level5.add(new BasicTrade(20, new ItemStack(RankineItems.LONSDALEITE_DIAMOND.get(), 1),12,50,0.05f));
            level5.add((entity,rand) -> new MerchantOffer(new ItemStack(Items.NETHER_STAR, 1), new ItemStack(Items.EMERALD, 64),12,50,0.05f));

        } else if (event.getType() == RankineVillagerProfessions.ROCK_COLLECTOR) {
            level1.addAll(RankineVillagerTrades.returnTagTrades(new ResourceLocation("forge:stone"),RankineItems.ANORTHOSITE.get(),16,1,16,10,0.05f));
            List<Block> rocks = BlockTags.getCollection().get(new ResourceLocation("forge:stone")).getAllElements();
            if (!rocks.isEmpty()) {
                for (Block rock : rocks) {
                    level2.add((entity, rand) -> new MerchantOffer(new ItemStack(rock.asItem(), 24), new ItemStack(Items.EMERALD, 1), 16, 10, 0.05f));
                }
            } else {
                level2.add((entity, rand) -> new MerchantOffer(new ItemStack(Items.SANDSTONE, 24), new ItemStack(Items.EMERALD, 1), 16, 10, 0.05f));
                level2.add((entity, rand) -> new MerchantOffer(new ItemStack(Items.RED_SANDSTONE, 12), new ItemStack(Items.EMERALD, 1), 16, 10, 0.05f));
            }
            level3.add(new BasicTrade(1, new ItemStack(Items.OBSIDIAN, 1),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.NETHERRACK, 8),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.END_STONE, 2),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.PURPUR_BLOCK, 2),12,10,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(RankineItems.PHOSPHORITE.get(), 2),12,10,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(RankineItems.IRONSTONE.get(), 2),12,10,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(RankineItems.METEORITE.get(), 2),12,10,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(RankineItems.ENSTATITE.get(), 2),12,10,0.05f));
            level5.add(new BasicTrade(1, new ItemStack(RankineItems.ROMAN_CONCRETE.get(), 1),24,10,0.05f));
            level5.add(new BasicTrade(64, new ItemStack(RankineItems.UNCUT_GEODE.get(), 1),1,10,0.05f));
        }

        if (Config.GENERAL.VILLAGER_TRADES.get()) {
            if (event.getType() == VillagerProfession.MASON) {
                event.getTrades().get(1).add(new BasicTrade(1,new ItemStack(RankineItems.MORTAR.get(), 16),16,1,0.05f));
                event.getTrades().get(1).add(new BasicTrade(1,new ItemStack(RankineItems.REFRACTORY_BRICK.get(), 10),16,1,0.05f));
            } else if (event.getType() == VillagerProfession.CLERIC) {
                event.getTrades().get(1).add(new BasicTrade(1, new ItemStack(RankineItems.SALTPETER.get(),2),12,1,0.05f));
            }
        }

    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World world = player.world;
        BlockPos pos = player.isSneaking() ? player.getPosition() : player.getPosition().up();

        // Tools
        if (world.getDayTime()%5==0 && !world.isRemote()) {
            if (!Config.GENERAL.DISABLE_COMPASS.get() && (player.getHeldItemOffhand().getItem() == Items.COMPASS || player.getHeldItemMainhand().getItem() == Items.COMPASS)) {
                switch (player.getHorizontalFacing()) {
                    case NORTH:
                        player.sendStatusMessage(new StringTextComponent("Facing North with coordinates: X =" + new DecimalFormat("###,###").format(pos.getX()) + " Z =" + new DecimalFormat("###,###").format(pos.getZ())).mergeStyle(TextFormatting.GOLD), true);
                        break;
                    case EAST:
                        player.sendStatusMessage(new StringTextComponent("Facing East with coordinates: X =" + new DecimalFormat("###,###").format(pos.getX()) + " Z =" + new DecimalFormat("###,###").format(pos.getZ())).mergeStyle(TextFormatting.GOLD), true);
                        break;
                    case SOUTH:
                        player.sendStatusMessage(new StringTextComponent("Facing South with coordinates: X =" + new DecimalFormat("###,###").format(pos.getX()) + " Z =" + new DecimalFormat("###,###").format(pos.getZ())).mergeStyle(TextFormatting.GOLD), true);
                        break;
                    case WEST:
                        player.sendStatusMessage(new StringTextComponent("Facing West with coordinates: X =" + new DecimalFormat("###,###").format(pos.getX()) + " Z =" + new DecimalFormat("###,###").format(pos.getZ())).mergeStyle(TextFormatting.GOLD), true);
                        break;
                }
            } else if (!Config.GENERAL.DISABLE_CLOCK.get() && (player.getHeldItemOffhand().getItem() == Items.CLOCK || player.getHeldItemMainhand().getItem() == Items.CLOCK)) {
                double hours = ((Math.floor(world.getDayTime() / 1000f)) + 6) % 24;
                double minutes = ((world.getDayTime() / 1000f) % 1) * 60;
                player.sendStatusMessage(new StringTextComponent("Time = " + new DecimalFormat("00").format(hours) + ":" + new DecimalFormat("00").format(minutes) + " (" + world.getDayTime() + ")").mergeStyle(TextFormatting.GOLD), true);
            } else if (!Config.GENERAL.DISABLE_THERMOMETER.get() && (player.getHeldItemOffhand().getItem() == RankineItems.THERMOMETER.get() || player.getHeldItemMainhand().getItem() == RankineItems.THERMOMETER.get())) {
                float temp = world.getBiome(pos).getTemperature(pos);
                if (temp < 0.0) {
                    player.sendStatusMessage(new StringTextComponent("Temperature = " + new DecimalFormat("#.###").format(temp)).mergeStyle(TextFormatting.LIGHT_PURPLE, TextFormatting.BOLD), true);
                } else if (temp < 0.15) {
                    player.sendStatusMessage(new StringTextComponent("Temperature = " + new DecimalFormat("#.###").format(temp)).mergeStyle(TextFormatting.AQUA, TextFormatting.BOLD), true);
                } else if (temp < 0.8) {
                    player.sendStatusMessage(new StringTextComponent("Temperature = " + new DecimalFormat("#.###").format(temp)).mergeStyle(TextFormatting.GREEN, TextFormatting.BOLD), true);
                } else if (temp < 0.95) {
                    player.sendStatusMessage(new StringTextComponent("Temperature = " + new DecimalFormat("#.###").format(temp)).mergeStyle(TextFormatting.YELLOW, TextFormatting.BOLD), true);
                } else {
                    player.sendStatusMessage(new StringTextComponent("Temperature = " + new DecimalFormat("#.###").format(temp)).mergeStyle(TextFormatting.RED, TextFormatting.BOLD), true);
                }
            } else if (!Config.GENERAL.DISABLE_ALTIMETER.get() && (player.getHeldItemOffhand().getItem() == RankineItems.ALTIMETER.get() || player.getHeldItemMainhand().getItem() == RankineItems.ALTIMETER.get())) {
                int y = pos.getY();
                if (y < 0) {
                    player.sendStatusMessage(new StringTextComponent("Altitude: Y = " + new DecimalFormat("###,###").format(y)).mergeStyle(TextFormatting.WHITE, TextFormatting.BOLD), true);
                } else if (y < 64) {
                    player.sendStatusMessage(new StringTextComponent("Altitude: Y = " + new DecimalFormat("###,###").format(y)).mergeStyle(TextFormatting.DARK_PURPLE, TextFormatting.BOLD), true);
                } else if (y < 128) {
                    player.sendStatusMessage(new StringTextComponent("Altitude: Y = " + new DecimalFormat("###,###").format(y)).mergeStyle(TextFormatting.DARK_AQUA, TextFormatting.BOLD), true);
                } else {
                    player.sendStatusMessage(new StringTextComponent("Altitude: Y = " + new DecimalFormat("###,###").format(y)).mergeStyle(TextFormatting.AQUA, TextFormatting.BOLD), true);
                }
            } else if (!Config.GENERAL.DISABLE_PHOTOMETER.get() && (player.getHeldItemOffhand().getItem() == RankineItems.PHOTOMETER.get() || player.getHeldItemMainhand().getItem() == RankineItems.PHOTOMETER.get())) {
                int SLL = world.getLightFor(LightType.SKY,pos);
                int BLL = world.getLightFor(LightType.BLOCK,pos);

                if (world.getBlockState(player.getPosition()).getBlock().canCreatureSpawn(world.getBlockState(player.getPosition()), world, player.getPosition(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, EntityType.ZOMBIE)) {
                    player.sendStatusMessage(new StringTextComponent("Light Levels: Sky = " + new DecimalFormat("##").format(SLL) + " Block = " + new DecimalFormat("##").format(BLL)).mergeStyle(TextFormatting.RED, TextFormatting.BOLD), true);
                } else {
                    player.sendStatusMessage(new StringTextComponent("Light Levels: Sky = " + new DecimalFormat("##").format(SLL) + " Block = " + new DecimalFormat("##").format(BLL)).mergeStyle(TextFormatting.GREEN, TextFormatting.BOLD), true);
                }
            } else if (!Config.GENERAL.DISABLE_BIOMETER.get() && (player.getHeldItemOffhand().getItem() == RankineItems.BIOMETER.get() || player.getHeldItemMainhand().getItem() == RankineItems.BIOMETER.get())) {
                player.sendStatusMessage(new StringTextComponent("Biome = " + new TranslationTextComponent(Util.makeTranslationKey("biome",world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(world.getBiome(pos)))).getString()).mergeStyle(TextFormatting.GOLD), true);
            }
        }
        if (!Config.GENERAL.DISABLE_SPEEDOMETER.get() && world.isRemote() && world.getDayTime()%5==0 && (player.getHeldItemOffhand().getItem() == RankineItems.SPEEDOMETER.get() || player.getHeldItemMainhand().getItem() == RankineItems.SPEEDOMETER.get())) {
            Entity ent = player;
            if (player.getRidingEntity() != null) {
                ent = player.getRidingEntity();
            }

            double d0 = Math.abs(ent.getPosX() - player.lastTickPosX);
            double d1 = Math.abs(ent.getPosZ() - player.lastTickPosZ);
            //double d2 = Math.abs(player.getPosY() - player.lastTickPosY);
            double speed = Math.sqrt(Math.pow(d0, 2) + Math.pow(d1, 2));
            Item mainhand = player.getHeldItemMainhand().getItem();
            Item offhand = player.getHeldItemOffhand().getItem();
            if ((offhand == RankineItems.SPEEDOMETER.get() && !(mainhand.getItem() instanceof InformationItem || mainhand.getItem() == Items.COMPASS || mainhand.getItem() == Items.CLOCK)) ||
                    (mainhand == RankineItems.SPEEDOMETER.get() && !(offhand instanceof InformationItem || offhand.getItem() == Items.COMPASS || offhand.getItem() == Items.CLOCK)))
            player.sendStatusMessage(new StringTextComponent("Speed = " + new DecimalFormat("#.##").format(speed * 20) + " blocks per second").mergeStyle(TextFormatting.GOLD), true);

        }


        // Armor
        if (player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == RankineItems.DIVING_HELMET.get()) {
            int headSlot = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == RankineItems.DIVING_HELMET.get() ? 1 : 0;
            int chestSlot = player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == RankineItems.DIVING_CHESTPLATE.get() ? 1 : 0;
            int legsSlot = player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == RankineItems.DIVING_LEGGINGS.get() ? 1 : 0;
            int feetSlot = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == RankineItems.DIVING_BOOTS.get() ? 1 : 0;

            if (!player.isInWater()) {
                player.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 200 * (headSlot+chestSlot+legsSlot+feetSlot), 0, false, false, true));
            }
        } else if (player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == RankineItems.CONDUIT_DIVING_HELMET.get() && !player.areEyesInFluid(FluidTags.WATER) && player.isInWater()) {
            int headSlot = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == RankineItems.CONDUIT_DIVING_HELMET.get() ? 1 : 0;
            int chestSlot = player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == RankineItems.CONDUIT_DIVING_CHESTPLATE.get() ? 1 : 0;
            int legsSlot = player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == RankineItems.CONDUIT_DIVING_LEGGINGS.get() ? 1 : 0;
            int feetSlot = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == RankineItems.CONDUIT_DIVING_BOOTS.get() ? 1 : 0;

            player.addPotionEffect(new EffectInstance(Effects.CONDUIT_POWER, 400 * (headSlot+chestSlot+legsSlot+feetSlot), 0, false, false, true));
        }


        if (player.getEntityWorld().getGameTime() % 1200L == 0) {
            int count = 0;
            for (ItemStack s : player.getArmorInventoryList()) {
                if (s.isEnchanted() && EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.GUARD, s) > 0) {
                    count+=2;
                }
            }
            if (player.getAbsorptionAmount() < count) {
                player.setAbsorptionAmount(count);
            }
        }

        ItemStack ghast = ItemStack.EMPTY;
        for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            if (!itemstack.isEmpty() && EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.GHAST_REGENERATION, itemstack) > 0 && itemstack.getDamage() != 0) {
                ghast = itemstack;
                break;
            }
        }

        EffectInstance eff = player.getActivePotionEffect(Effects.REGENERATION);
        if (ghast != ItemStack.EMPTY && eff != null) {
            int k = 50 >> eff.getAmplifier();
            if (eff.getDuration() % k == 0) {
                ghast.setDamage(Math.max(0,ghast.getDamage() - 1));
            }
        }

        ModifiableAttributeInstance att = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (player.getHeldItemOffhand().getItem() == RankineItems.SPEED_PENDANT.get() && att != null && !att.hasModifier(RankineAttributes.SPEED_PENDANT_MS)) {
            att.applyNonPersistentModifier(RankineAttributes.SPEED_PENDANT_MS);
        }

        ModifiableAttributeInstance att2 = player.getAttribute(Attributes.MAX_HEALTH);
        if (player.getHeldItemOffhand().getItem() == RankineItems.HEALTH_PENDANT.get() && att2 != null && !att2.hasModifier(RankineAttributes.HEALTH_PENDANT)) {
            att2.applyNonPersistentModifier(RankineAttributes.HEALTH_PENDANT);
        }
    }

    @SubscribeEvent
    public static void onLightningEvent(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof LightningBoltEntity) {
            LightningBoltEntity entity = (LightningBoltEntity) event.getEntity();
            World worldIn = event.getWorld();
            ITag<Block> fulgurite = RankineTags.Blocks.LIGHTNING_VITRIFIED;
            ITag<Block> lightningGlass = BlockTags.SAND;
            BlockPos startPos = entity.getPosition().down();
            if (fulgurite.contains(worldIn.getBlockState(startPos).getBlock()) || lightningGlass.contains(worldIn.getBlockState(startPos).getBlock())) {
                Iterable<BlockPos> positions = BlockPos.getProximitySortedBoxPositionsIterator(startPos,2,2,2);
                for (BlockPos pos : positions) {
                    double rand;
                    if (startPos.getX() == pos.getX() && startPos.getZ() == pos.getZ()) {
                        rand = 1/(1f + Math.abs(startPos.getY() - pos.getY()));
                    } else {
                        rand = pos.distanceSq(startPos.getX(),startPos.getY(),startPos.getZ(),true);
                    }

                    if (worldIn.getRandom().nextFloat() < 1/rand && fulgurite.contains(worldIn.getBlockState(pos).getBlock())) {
                        worldIn.setBlockState(pos,RankineBlocks.FULGURITE.get().getDefaultState(),3);
                    } else if (worldIn.getRandom().nextFloat() < 1/rand && lightningGlass.contains(worldIn.getBlockState(pos).getBlock())) {
                        worldIn.setBlockState(pos,RankineBlocks.LIGHTNING_GLASS.get().getDefaultState(),3);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamaged(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            World worldIn = player.getEntityWorld();
            for (int i = 0; i < player.inventory.armorInventory.size(); ++i) {
                ItemStack s = player.inventory.armorInventory.get(i);
                if (!event.getSource().isProjectile() || EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ENDOBIOTIC,s) == 0) {
                    if (s.getItem() instanceof IAlloyArmor) {
                        IAlloyArmor armor = (IAlloyArmor) s.getItem();
                        if (worldIn.getRandom().nextFloat() > armor.getHeatResist(s) && (player.isInLava() || player.getFireTimer() > 0 || worldIn.getDimensionKey() == World.THE_NETHER)) {
                            int finalI = i;
                            s.damageItem(1,player,(entity) -> {
                                entity.sendBreakAnimation(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, finalI));
                            });
                        } else if ((worldIn.getRandom().nextFloat() > armor.getCorrResist(s) && player.isWet())) {
                            int finalI1 = i;
                            s.damageItem(1 + EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ENDOBIOTIC,s)*3,player,(entity) -> {
                                entity.sendBreakAnimation(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, finalI1));
                            });
                        }
                    }
                } else if (event.getSource().isProjectile() && EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ENDOBIOTIC,s) > 0) {
                    if (!worldIn.isRemote) {
                        double d0 = player.getPosX();
                        double d1 = player.getPosY();
                        double d2 = player.getPosZ();
                        if (s.getItem() instanceof IAlloyArmor) {
                            IAlloyArmor armor = (IAlloyArmor) s.getItem();
                            if ((worldIn.getRandom().nextFloat() > armor.getCorrResist(s) && player.isWet())) {
                                int finalI1 = i;
                                s.damageItem(1 + EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ENDOBIOTIC,s)*3,player,(entity) -> {
                                    entity.sendBreakAnimation(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, finalI1));
                                });
                            }
                        }

                        for(int j = 0; j < 16; ++j) {
                            double d3 = player.getPosX() + (player.getRNG().nextDouble() - 0.5D) * 16.0D;
                            double d4 = MathHelper.clamp(player.getPosY() + (double)(player.getRNG().nextInt(16) - 8), 0.0D, (double)(worldIn.func_234938_ad_() - 1));
                            double d5 = player.getPosZ() + (player.getRNG().nextDouble() - 0.5D) * 16.0D;
                            if (player.isPassenger()) {
                                player.stopRiding();
                            }

                            if (player.attemptTeleport(d3, d4, d5, true)) {
                                SoundEvent soundevent = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                                worldIn.playSound((PlayerEntity)null, d0, d1, d2, soundevent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                                player.playSound(soundevent, 1.0F, 1.0F);
                                break;
                            }
                        }
                        event.setAmount(0);
                    }
                }

            }
            if (!(event.getSource() == DamageSource.WITHER && event.getSource() == DamageSource.MAGIC)) {
                boolean wither = false;
                for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                    ItemStack itemstack = player.inventory.getStackInSlot(i);
                    if (!itemstack.isEmpty() && EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.WITHERING_CURSE, itemstack) > 0) {
                        wither = true;
                        break;
                    }
                }
                if (wither) {
                    player.addPotionEffect(new EffectInstance(Effects.WITHER,100));
                }
            }
        }


    }
    /*@SubscribeEvent
    public static void onLeafImpact (ProjectileImpactEvent event) {
        World worldIn = event.getEntity().getEntityWorld();
        if (event.getEntity() instanceof SpearEntity && event.getRayTraceResult().getType() == RayTraceResult.Type.BLOCK) {
            BlockRayTraceResult e = (BlockRayTraceResult) event.getRayTraceResult();
            Stream<BlockPos> pos = BlockPos.getAllInBox(event.getEntity().getBoundingBox());
            BlockPos hit = new BlockPos(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ());
            if ((worldIn.getBlockState(hit).getBlock() instanceof LeavesBlock)) {
                if (!worldIn.isRemote) {
                    worldIn.destroyBlock(hit,true);
                    for (BlockPos p : pos.toArray(BlockPos[]::new)) {
                        if (worldIn.getBlockState(p).getBlock() instanceof LeavesBlock) {
                            worldIn.destroyBlock(hit,true);
                        }
                    }

                }

                event.setCanceled(true);
            }
        }

    }*/

    @SubscribeEvent
    public static void onEnvironmentEffect(LivingEvent.LivingUpdateEvent event) {
        LivingEntity ent = event.getEntityLiving();
        World world = ent.getEntityWorld();
        FluidState fluidstate = world.getFluidState(ent.getPosition());
        boolean flag = (ent instanceof PlayerEntity && ((PlayerEntity) ent).isCreative());
        ModifiableAttributeInstance maxHealth = ent.getAttribute(Attributes.MAX_HEALTH);
        ModifiableAttributeInstance movementSpeed = ent.getAttribute(Attributes.MOVEMENT_SPEED);
        if (fluidstate.getFluid().getRegistryName() != null && fluidstate.getFluid().getRegistryName().getNamespace().equals("rankine")) {
            Entity entity = ent.isBeingRidden() && ent.getControllingPassenger() != null ? ent.getControllingPassenger() : ent;
            float f = entity == ent ? 0.35F : 0.4F;
            Vector3d v = (FluidHelper.handleFluidAcceleration(ent,0.114D));
            float f1 = MathHelper.sqrt(v.x * v.x * (double)0.2F + v.y * v.y + v.z * v.z * (double)0.2F) * f;
            if (f1 > 1.0F) {
                f1 = 1.0F;
            }
            entity.playSound(SoundEvents.ENTITY_GENERIC_SWIM, f1, 1.0F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.4F);
            entity.getMotion().add(v);
        }
        if (fluidstate.getFluid() == RankineFluids.LIQUID_MERCURY || fluidstate.getFluid() == RankineFluids.LIQUID_MERCURY_FLOWING) {
            if (!flag) {
                EffectInstance cur = ent.getActivePotionEffect(RankineEffects.MERCURY_POISONING);
                ent.addPotionEffect(new EffectInstance(RankineEffects.MERCURY_POISONING, Math.min(1600,cur == null ? 5 : cur.getDuration() + 5), 0, false, false, true));
                if (cur != null && cur.getDuration() >= 1600 && maxHealth != null && !maxHealth.hasModifier(RankineAttributes.MERCURY_HEALTH)) {
                    maxHealth.applyNonPersistentModifier(RankineAttributes.MERCURY_HEALTH);
                }
            }
        }

        if (Config.HARD_MODE.RADIOACTIVE.get()) {
            EffectInstance rad = ent.getActivePotionEffect(RankineEffects.RADIATION_POISONING);
            if (rad != null) {
                int duration = rad.getDuration();
                if (duration >= 400 && maxHealth != null && !maxHealth.hasModifier(RankineAttributes.MINOR_RADIATION_POISONING)) {
                    maxHealth.applyPersistentModifier(RankineAttributes.MINOR_RADIATION_POISONING);
                }

                if (duration >= 1600 && maxHealth != null && !maxHealth.hasModifier(RankineAttributes.RADIATION_POISONING)) {
                    maxHealth.applyPersistentModifier(RankineAttributes.RADIATION_POISONING);
                }

                if (duration >= 3200 && maxHealth != null && !maxHealth.hasModifier(RankineAttributes.EXTREME_RADIATION_POISONING)) {
                    maxHealth.applyPersistentModifier(RankineAttributes.EXTREME_RADIATION_POISONING);
                }
            } else {
                if (maxHealth != null && maxHealth.hasModifier(RankineAttributes.MINOR_RADIATION_POISONING)) {
                    maxHealth.removeModifier(RankineAttributes.MINOR_RADIATION_POISONING);
                }
                if (maxHealth != null && maxHealth.hasModifier(RankineAttributes.RADIATION_POISONING)) {
                    maxHealth.removeModifier(RankineAttributes.RADIATION_POISONING);
                }
                if (maxHealth != null && maxHealth.hasModifier(RankineAttributes.EXTREME_RADIATION_POISONING)) {
                    maxHealth.removeModifier(RankineAttributes.EXTREME_RADIATION_POISONING);
                }
            }
        }


    }




    @SubscribeEvent
    public static void fuelValues(FurnaceFuelBurnTimeEvent event) {
        if (Config.GENERAL.FUEL_VALUES.get()) {
            Item Fuel = event.getItemStack().getItem();
            String path = Fuel.getRegistryName().getPath();
            if (Fuel.getTags().contains(new ResourceLocation("minecraft:logs_that_burn"))) {
                if (path.contains("douglas_fir")) {
                    event.setBurnTime(460);
                } else if (path.contains("ancient")) {
                    event.setBurnTime(400);
                } else if (path.contains("archwood")) {
                    event.setBurnTime(400);
                } else if (path.contains("dead")) {
                    event.setBurnTime(300);
                } else if (path.contains("pinyon_pine")) {
                    event.setBurnTime(520);
                } else if (path.contains("redwood")) {
                    event.setBurnTime(440);
                } else if (path.contains("alder")) {
                    event.setBurnTime(420);
                } else if (path.contains("apple")) {
                    event.setBurnTime(520);
                } else if (path.contains("ash")) {
                    event.setBurnTime(470);
                } else if (path.contains("aspen")) {
                    event.setBurnTime(430);
                } else if (path.contains("basswood")) {
                    event.setBurnTime(390);
                } else if (path.contains("beech")) {
                    event.setBurnTime(530);
                } else if (path.contains("elder")) {
                    event.setBurnTime(430);
                } else if (path.contains("cherry")) {
                    event.setBurnTime(450);
                } else if (path.contains("chestnut")) {
                    event.setBurnTime(430);
                } else if (path.contains("black_birch")) {
                    event.setBurnTime(470);
                } else if (path.contains("yellow_birch")) {
                    event.setBurnTime(490);
                } else if (path.contains("birch")) {
                    event.setBurnTime(450);
                } else if (path.contains("dogwood")) {
                    event.setBurnTime(600);
                } else if (path.contains("elm")) {
                    event.setBurnTime(450);
                } else if (path.contains("cottonwood")) {
                    event.setBurnTime(410);
                } else if (path.contains("fir")) {
                    event.setBurnTime(390);
                } else if (path.contains("silver_maple")) {
                    event.setBurnTime(440);
                } else if (path.contains("mulberry")) {
                    event.setBurnTime(510);
                } else if (path.contains("poplar")) {
                    event.setBurnTime(350);
                } else if (path.contains("maple")) {
                    event.setBurnTime(500);
                } else if (path.contains("cedar")) {
                    event.setBurnTime(410);
                } else if (path.contains("white_pine")) {
                    event.setBurnTime(410);
                } else if (path.contains("spruce")) {
                    event.setBurnTime(410);
                } else if (path.contains("white_oak")) {
                    event.setBurnTime(540);
                } else if (path.contains("red_oak")) {
                    event.setBurnTime(500);
                } else if (path.contains("oak")) {
                    event.setBurnTime(520);
                } else if (path.contains("jungle")) {
                    event.setBurnTime(450);
                } else if (path.contains("walnut")) {
                    event.setBurnTime(470);
                } else if (path.contains("pine")) {
                    event.setBurnTime(420);
                } else if (path.contains("willow")) {
                    event.setBurnTime(420);
                } else if (path.contains("sycamore")) {
                    event.setBurnTime(440);
                } else if (path.contains("hickory")) {
                    event.setBurnTime(530);
                } else if (path.contains("coconut")) {
                    event.setBurnTime(450);
                } else if (path.contains("juniper")) {
                    event.setBurnTime(480);
                } else if (path.contains("acacia")) {
                    event.setBurnTime(500);
                } else if (path.contains("magnolia")) {
                    event.setBurnTime(450);
                } else if (path.contains("hemlock")) {
                    event.setBurnTime(440);
                } else if (path.contains("larch")) {
                    event.setBurnTime(440);
                } else if (path.contains("robinia")) {
                    event.setBurnTime(550);
                } else if (path.contains("eucalyptus")) {
                    event.setBurnTime(570);
                } else if (path.contains("ironwood")) {
                    event.setBurnTime(510);
                } else if (path.contains("locust")) {
                    event.setBurnTime(550);
                } else {
                    event.setBurnTime(300);
                }
            } else if (Fuel.getTags().contains(new ResourceLocation("minecraft:planks"))) {
                event.setBurnTime(100);
            } else if (Fuel.getTags().contains(new ResourceLocation("minecraft:wooden_buttons"))) {
                event.setBurnTime(100);
            } else if (Fuel.getTags().contains(new ResourceLocation("forge:rods/wooden"))) {
                event.setBurnTime(50);
            } else if (Fuel.getTags().contains(new ResourceLocation("minecraft:wooden_slabs"))) {
                event.setBurnTime(50);
            } else if (Fuel.getTags().contains(new ResourceLocation("minecraft:wooden_stairs"))) {
                event.setBurnTime(75);
            } else if (Fuel.getTags().contains(new ResourceLocation("minecraft:wooden_pressure_plates"))) {
                event.setBurnTime(200);
            } else if (Fuel.getTags().contains(new ResourceLocation("minecraft:wooden_fences"))) {
                event.setBurnTime(167);
            } else if (Fuel.getTags().contains(new ResourceLocation("minecraft:wooden_fence_gates"))) {
                event.setBurnTime(400);
            } else if (Fuel.getTags().contains(new ResourceLocation("minecraft:wooden_trapdoors"))) {
                event.setBurnTime(300);
            } else if (Fuel.getTags().contains(new ResourceLocation("minecraft:wooden_doors"))) {
                event.setBurnTime(200);
            } else if (Fuel.getTags().contains(new ResourceLocation("minecraft:saplings"))) {
                event.setBurnTime(100);
            } else if (Fuel.getItem() == Items.CHARCOAL) {
                event.setBurnTime(800);
            }
        }
    }

    @SubscribeEvent
    public static void movementModifier(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World world = event.player.world;
        BlockPos pos;
        if (player.getPosY() % 1 < 0.5) {
            pos = player.getPosition().down();
        } else {
            pos = player.getPosition();
        }
        Block ground = world.getBlockState(pos).getBlock();


        // Path Creation
        if (Config.GENERAL.PATH_CREATION.get() && !player.isCreative() && player.ticksExisted%(Config.GENERAL.PATH_CREATION_TIME.get()*20)==0 && !world.isRemote) {
            if (VanillaIntegration.pathBlocks_map.get(ground.getBlock()) != null) {
                world.setBlockState(pos, VanillaIntegration.pathBlocks_map.get(ground).getDefaultState(),2);
            }

        }


        ModifiableAttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

        //movementSpeed.applyNonPersistentModifier(new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fb5"), "rankine:block_ms", 0.0D, AttributeModifier.Operation.ADDITION));


        // Movement Modifiersa
        if (Config.GENERAL.MOVEMENT_MODIFIERS.get()) {
            List<AttributeModifier> mods = Arrays.asList(RankineAttributes.BRICKS_MS, RankineAttributes.CONCRETE_MS, RankineAttributes.GRASS_PATH_MS, RankineAttributes.ROMAN_CONCRETE_MS, RankineAttributes.DIRT_MS, RankineAttributes.MUD_MS, RankineAttributes.POLISHED_STONE_MS, RankineAttributes.SAND_MS, RankineAttributes.SNOW_MS, RankineAttributes.WOODEN_MS);
            if (player.isCreative() || player.isElytraFlying()) {
                for (AttributeModifier m : mods) {
                    movementSpeed.removeModifier(m);
                }
            } else if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/grass_path")) && !movementSpeed.hasModifier(RankineAttributes.GRASS_PATH_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.GRASS_PATH_MS);
                }
            } else if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/grass_path")) && movementSpeed.hasModifier(RankineAttributes.GRASS_PATH_MS)) {
                    movementSpeed.removeModifier(RankineAttributes.GRASS_PATH_MS);
            } else if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/sand")) && !movementSpeed.hasModifier(RankineAttributes.SAND_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.SAND_MS);
                }
            } else if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/sand")) && movementSpeed.hasModifier(RankineAttributes.SAND_MS) && ground != Blocks.AIR) {
                    movementSpeed.removeModifier(RankineAttributes.SAND_MS);
            } else if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/mud")) && !movementSpeed.hasModifier(RankineAttributes.MUD_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.MUD_MS);
                }
            } else if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/mud")) && movementSpeed.hasModifier(RankineAttributes.MUD_MS) && ground != Blocks.AIR) {
                    movementSpeed.removeModifier(RankineAttributes.MUD_MS);
            } else if ((world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow")) || world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow"))) && !movementSpeed.hasModifier(RankineAttributes.SNOW_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.SNOW_MS);
                }
            } else if ((!world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow")) || !world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow"))) && movementSpeed.hasModifier(RankineAttributes.SNOW_MS) && ground != Blocks.AIR) {
                    movementSpeed.removeModifier(RankineAttributes.SNOW_MS);
            } else if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/dirt")) && !movementSpeed.hasModifier(RankineAttributes.DIRT_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.DIRT_MS);
                }
            } else if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/dirt")) && movementSpeed.hasModifier(RankineAttributes.DIRT_MS) && ground != Blocks.AIR) {
                    movementSpeed.removeModifier(RankineAttributes.DIRT_MS);
            } else if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/wooden")) && !movementSpeed.hasModifier(RankineAttributes.WOODEN_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.WOODEN_MS);
                }
            } else if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/wooden")) && movementSpeed.hasModifier(RankineAttributes.WOODEN_MS) && ground != Blocks.AIR) {
                    movementSpeed.removeModifier(RankineAttributes.WOODEN_MS);
            } else if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/polished_stone")) && !movementSpeed.hasModifier(RankineAttributes.POLISHED_STONE_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.POLISHED_STONE_MS);
                }
            } else if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/polished_stone")) && movementSpeed.hasModifier(RankineAttributes.POLISHED_STONE_MS) && ground != Blocks.AIR) {
                    movementSpeed.removeModifier(RankineAttributes.POLISHED_STONE_MS);
            } else if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/bricks")) && !movementSpeed.hasModifier(RankineAttributes.BRICKS_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.BRICKS_MS);
                }
            } else if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/bricks")) && movementSpeed.hasModifier(RankineAttributes.BRICKS_MS) && ground != Blocks.AIR) {
                    movementSpeed.removeModifier(RankineAttributes.BRICKS_MS);
            } else if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/concrete")) && !movementSpeed.hasModifier(RankineAttributes.CONCRETE_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.CONCRETE_MS);
                }
            } else if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/concrete")) && movementSpeed.hasModifier(RankineAttributes.CONCRETE_MS) && ground != Blocks.AIR) {
                    movementSpeed.removeModifier(RankineAttributes.CONCRETE_MS);
            } else if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/roman_concrete")) && !movementSpeed.hasModifier(RankineAttributes.ROMAN_CONCRETE_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.ROMAN_CONCRETE_MS);
                }
            } else if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/roman_concrete")) && movementSpeed.hasModifier(RankineAttributes.ROMAN_CONCRETE_MS) && ground != Blocks.AIR) {
                    movementSpeed.removeModifier(RankineAttributes.ROMAN_CONCRETE_MS);
            }
        }
        if (ground == Blocks.ICE) {
            if (new Random().nextFloat() < Config.GENERAL.ICE_BREAK.get() && !(EnchantmentHelper.getMaxEnchantmentLevel(RankineEnchantments.SPEED_SKATER, player) > 0)) {
                for (BlockPos B : BlockPos.getAllInBoxMutable(pos.add(-2, -1, -2), pos.add(2, -1, 2))) {
                    if (world.getBlockState(B).getBlock() == Blocks.ICE) {
                        world.setBlockState(B, Blocks.FROSTED_ICE.getDefaultState().with(FrostedIceBlock.AGE, 2));
                    }
                }
            }
        }
        if (EnchantmentHelper.getMaxEnchantmentLevel(RankineEnchantments.DUNE_WALKER, player) > 0 || player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == RankineItems.SANDALS.get()) {
            if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/sand")) && !movementSpeed.hasModifier(RankineAttributes.DUNE_WALKER)) {
                movementSpeed.applyNonPersistentModifier(RankineAttributes.DUNE_WALKER);
                player.stepHeight = 1.0f;
            }
        } else if (EnchantmentHelper.getMaxEnchantmentLevel(RankineEnchantments.DUNE_WALKER, player) <= 0 && player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() != RankineItems.SANDALS.get() && movementSpeed.hasModifier(RankineAttributes.DUNE_WALKER)) {
            movementSpeed.removeModifier(RankineAttributes.DUNE_WALKER);
            player.stepHeight = 0.5f;
        }
        if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/sand")) && ground != Blocks.AIR && movementSpeed.hasModifier(RankineAttributes.DUNE_WALKER)) {
            movementSpeed.removeModifier(RankineAttributes.DUNE_WALKER);
            player.stepHeight = 0.5f;
        }
        if (EnchantmentHelper.getMaxEnchantmentLevel(RankineEnchantments.SNOW_DRIFTER, player) > 0 || player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == RankineItems.SNOWSHOES.get()) {
            if ((world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow")) || world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow"))) && !movementSpeed.hasModifier(RankineAttributes.SNOW_DRIFTER)) {
                movementSpeed.applyNonPersistentModifier(RankineAttributes.SNOW_DRIFTER);
                player.stepHeight = 1.0f;
            }
        } else  if (EnchantmentHelper.getMaxEnchantmentLevel(RankineEnchantments.SNOW_DRIFTER, player) <= 0 && player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() != RankineItems.SNOWSHOES.get() && movementSpeed.hasModifier(RankineAttributes.SNOW_DRIFTER)) {
            movementSpeed.removeModifier(RankineAttributes.SNOW_DRIFTER);
            player.stepHeight = 0.5f;
        }
        if ((!world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow")) && !world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow"))) && ground != Blocks.AIR && movementSpeed.hasModifier(RankineAttributes.SNOW_DRIFTER)) {
            movementSpeed.removeModifier(RankineAttributes.SNOW_DRIFTER);
            player.stepHeight = 0.5f;
        }
        if (EnchantmentHelper.getMaxEnchantmentLevel(RankineEnchantments.SPEED_SKATER, player) > 0 || player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == RankineItems.ICE_SKATES.get()) {
            if ((world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/ice")) || world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/ice"))) && !movementSpeed.hasModifier(RankineAttributes.SPEED_SKATER)) {
                movementSpeed.applyNonPersistentModifier(RankineAttributes.SPEED_SKATER);
                player.stepHeight = 1.0f;
            }
        } else  if (EnchantmentHelper.getMaxEnchantmentLevel(RankineEnchantments.SPEED_SKATER, player) <= 0 && player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() != RankineItems.ICE_SKATES.get() && movementSpeed.hasModifier(RankineAttributes.SPEED_SKATER)) {
            movementSpeed.removeModifier(RankineAttributes.SPEED_SKATER);
            player.stepHeight = 0.5f;
        }
        if ((!world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/ice")) && !world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/ice"))) && ground != Blocks.AIR && movementSpeed.hasModifier(RankineAttributes.SPEED_SKATER)) {
            movementSpeed.removeModifier(RankineAttributes.SPEED_SKATER);
            player.stepHeight = 0.5f;
        }

    }


    @SubscribeEvent
    public static void specialEnchants(AnvilUpdateEvent event) {
        ItemStack input = event.getLeft();
        if (event.getRight().getItem() == RankineItems.SANDALS.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getEquipmentSlot() == EquipmentSlotType.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.DUNE_WALKER,event.getOutput()) != 1) {
                event.getOutput().addEnchantment(RankineEnchantments.DUNE_WALKER, 1);
                event.setCost(20);
            }
        } else if (event.getRight().getItem() == RankineItems.SNOWSHOES.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getEquipmentSlot() == EquipmentSlotType.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.SNOW_DRIFTER,event.getOutput()) != 1) {
                event.getOutput().addEnchantment(RankineEnchantments.SNOW_DRIFTER, 1);
                event.setCost(20);
            }
        } else if (event.getRight().getItem() == RankineItems.ICE_SKATES.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getEquipmentSlot() == EquipmentSlotType.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.SPEED_SKATER,event.getOutput()) != 1) {
                event.getOutput().addEnchantment(RankineEnchantments.SPEED_SKATER, 1);
                event.setCost(20);
            }
        } else if (event.getRight().getItem() == RankineItems.GAS_MASK.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getEquipmentSlot() == EquipmentSlotType.HEAD) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.GAS_PROTECTION,event.getOutput()) != 1) {
                event.getOutput().addEnchantment(RankineEnchantments.GAS_PROTECTION, 1);
                event.setCost(20);
            }
        }
    }


    @SubscribeEvent
    public static void onFluidInteraction(BlockEvent.FluidPlaceBlockEvent event)
    {
        if (event.getState() == Blocks.COBBLESTONE.getDefaultState() && Config.GENERAL.IGNEOUS_COBBLE_GEN.get())
        {
            World worldIn = (World) event.getWorld();
            BlockPos pos = event.getPos();
            List<Block> adjPos = Arrays.asList(worldIn.getBlockState(pos.up()).getBlock(),worldIn.getBlockState(pos.south()).getBlock(),worldIn.getBlockState(pos.north()).getBlock(),
                    worldIn.getBlockState(pos.west()).getBlock(),worldIn.getBlockState(pos.east()).getBlock(),worldIn.getBlockState(pos.down()).getBlock());
            ItemStack[] items = adjPos.stream().map(ItemStack::new).toArray(ItemStack[]::new);
            RockGeneratorRecipe recipe = worldIn.getRecipeManager().getRecipesForType(RankineRecipeTypes.ROCK_GENERATOR).stream().flatMap((r) -> {
                if (r.getGenType().equals(RockGeneratorUtils.RockGenType.INTRUSIVE_IGNEOUS)) {
                    return Util.streamOptional(RankineRecipeTypes.ROCK_GENERATOR.matches(r, worldIn, new Inventory(items)));
                }
                return null;
            }).findFirst().orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getRecipeOutput();
                if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                    event.setNewState(((BlockItem) output.getItem()).getBlock().getDefaultState());
                }
            }

        } else if (event.getState() == Blocks.BASALT.getDefaultState() && Config.GENERAL.IGNEOUS_COBBLE_GEN.get())
        {
            World worldIn = (World) event.getWorld();
            BlockPos pos = event.getPos();
            List<Block> adjPos = Arrays.asList(worldIn.getBlockState(pos.up()).getBlock(),worldIn.getBlockState(pos.south()).getBlock(),worldIn.getBlockState(pos.north()).getBlock(),
                    worldIn.getBlockState(pos.west()).getBlock(),worldIn.getBlockState(pos.east()).getBlock());
            ItemStack[] items = adjPos.stream().map(ItemStack::new).toArray(ItemStack[]::new);
            RockGeneratorRecipe recipe = worldIn.getRecipeManager().getRecipesForType(RankineRecipeTypes.ROCK_GENERATOR).stream().flatMap((r) -> {
                if (r.getGenType().equals(RockGeneratorUtils.RockGenType.EXTRUSIVE_IGNEOUS)) {
                    return Util.streamOptional(RankineRecipeTypes.ROCK_GENERATOR.matches(r, worldIn, new Inventory(items)));
                }
                return null;
            }).findFirst().orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getRecipeOutput();
                if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                    event.setNewState(((BlockItem) output.getItem()).getBlock().getDefaultState());
                }
            } else {
                event.setNewState(Blocks.BLACKSTONE.getDefaultState());
            }
        } else if (event.getState() == Blocks.STONE.getDefaultState()) {
            World worldIn = (World) event.getWorld();
            BlockPos pos = event.getPos();
            List<Block> adjPos = Arrays.asList(worldIn.getBlockState(pos.south()).getBlock(),worldIn.getBlockState(pos.north()).getBlock(),
                    worldIn.getBlockState(pos.west()).getBlock(),worldIn.getBlockState(pos.east()).getBlock(),worldIn.getBlockState(pos.down()).getBlock());
            ItemStack[] items = adjPos.stream().map(ItemStack::new).toArray(ItemStack[]::new);
            RockGeneratorRecipe recipe = worldIn.getRecipeManager().getRecipesForType(RankineRecipeTypes.ROCK_GENERATOR).stream().flatMap((r) -> {
                if (r.getGenType().equals(RockGeneratorUtils.RockGenType.METAMORPHIC)) {
                    return Util.streamOptional(RankineRecipeTypes.ROCK_GENERATOR.matches(r, worldIn, new Inventory(items)));
                }
                return null;
            }).findFirst().orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getRecipeOutput();
                if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                    event.setNewState(((BlockItem) output.getItem()).getBlock().getDefaultState());
                }
            } else {
                event.setNewState(RankineBlocks.SKARN.get().getDefaultState());
            }
        } else if (event.getState() == Blocks.OBSIDIAN.getDefaultState()) {
            World worldIn = (World) event.getWorld();
            BlockPos pos = event.getPos();
            List<Block> adjPos = Arrays.asList(worldIn.getBlockState(pos.south()).getBlock(),worldIn.getBlockState(pos.north()).getBlock(),
                    worldIn.getBlockState(pos.west()).getBlock(),worldIn.getBlockState(pos.east()).getBlock(),worldIn.getBlockState(pos.down()).getBlock());
            ItemStack[] items = adjPos.stream().map(ItemStack::new).toArray(ItemStack[]::new);
            RockGeneratorRecipe recipe = worldIn.getRecipeManager().getRecipesForType(RankineRecipeTypes.ROCK_GENERATOR).stream().flatMap((r) -> {
                if (r.getGenType().equals(RockGeneratorUtils.RockGenType.VOLCANIC)) {
                    return Util.streamOptional(RankineRecipeTypes.ROCK_GENERATOR.matches(r, worldIn, new Inventory(items)));
                }
                return null;
            }).findFirst().orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getRecipeOutput();
                if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                    event.setNewState(((BlockItem) output.getItem()).getBlock().getDefaultState());
                }
            }
        }
    }
/*
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (event.getBlockSnapshot().getReplacedBlock().getBlock() instanceof GasBlock && event.getPlacedBlock().getBlock() instanceof AbstractFireBlock) {
            event.setCanceled(true);
        }
    }*/

    private static final String NBT_KEY = "rankine.firstjoin";
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (Config.GENERAL.REFRESH_ALLOYS.get()) {
            for(int i = 0; i < event.getPlayer().inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = event.getPlayer().inventory.getStackInSlot(i);
                if (!itemstack.isEmpty() && itemstack.getItem() instanceof IAlloyItem) {
                    IAlloyItem.setRefresh(itemstack);
                }
            }
        }

        if (Config.GENERAL.STARTING_BOOK.get() && !event.getPlayer().getEntityWorld().isRemote && Patchouli.isInstalled()) {

            CompoundNBT data = event.getPlayer().getPersistentData();
            CompoundNBT persistent;
            if (!data.contains(PlayerEntity.PERSISTED_NBT_TAG)) {
                data.put(PlayerEntity.PERSISTED_NBT_TAG, (persistent = new CompoundNBT()));
            } else {
                persistent = data.getCompound(PlayerEntity.PERSISTED_NBT_TAG);
            }

            if (!persistent.contains(NBT_KEY)) {
                persistent.putBoolean(NBT_KEY, true);
                event.getPlayer().inventory.addItemStackToInventory(PatchouliAPI.get().getBookStack(new ResourceLocation("rankine:rankine_journal")));
            }
        }
    }

    @SubscribeEvent
    public static void onParryEvent(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            ItemStack stack = player.getHeldItemOffhand().getItem() instanceof KnifeItem ? player.getHeldItemOffhand() : ItemStack.EMPTY;
            if (!stack.isEmpty()) {
                int i = stack.getItem().getUseDuration(stack) - player.getItemInUseCount();
                if (i < (10 + EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.PREPARATION,stack))) {


                    if (!event.getSource().isUnblockable()) {
                        player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,1.0f, 1.0f);
                        if (event.getSource().getTrueSource() instanceof LivingEntity && EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.RETALIATE,stack) >= 1) {
                            LivingEntity ent = (LivingEntity) event.getSource().getTrueSource();
                            ent.attackEntityFrom(event.getSource(),event.getAmount());
                        } else if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.RETREAT,stack) >= 1) {
                            player.addPotionEffect(new EffectInstance(Effects.INVISIBILITY,60));
                        }
                        event.setCanceled(true);
                    }
                }
            }
        }
    }





    @SubscribeEvent
    public static void onBlockHarvest(PlayerEvent.HarvestCheck event) {
        Material mat = event.getTargetBlock().getMaterial();
        boolean flag = mat == Material.ROCK || mat == Material.IRON || mat == Material.ANVIL;
        if (flag && (event.getPlayer().getHeldItemMainhand().getItem() instanceof AlloyPickaxeItem || event.getPlayer().getHeldItemMainhand().getItem() instanceof AlloyPickaxeItem)) {
            ItemStack stack = event.getPlayer().getHeldItemMainhand();
            Item item = event.getPlayer().getHeldItemMainhand().getItem();
            boolean bool = item.canHarvestBlock(stack,event.getTargetBlock());
            event.setCanHarvest(bool);
        }
    }

    @SubscribeEvent
    public static void onItemAttributeModification(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof IAlloyTool && event.getSlotType() == EquipmentSlotType.MAINHAND)
        {

            IAlloyTool alloyTool = (IAlloyTool) stack.getItem();

            event.addModifier(Attributes.ATTACK_DAMAGE,new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fc1"), "Rankine Damage modifier",
                    alloyTool.getAlloyAttackDamage(stack),
                    AttributeModifier.Operation.ADDITION));
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fc2"), "Rankine Attspeed modifier",
                    alloyTool.getAlloyAttackSpeed(stack),
                    AttributeModifier.Operation.ADDITION));
        }

        if (stack.getItem() instanceof IAlloyArmor && ((IAlloyArmor) stack.getItem()).isAlloyInit(stack) && stack.getItem() instanceof ArmorItem && event.getSlotType() == ((ArmorItem)stack.getItem()).getEquipmentSlot())
        {
            IAlloyArmor alloyArmor = (IAlloyArmor) stack.getItem();
            int slot1 = event.getSlotType().getSlotIndex() * 2;
            int slot2 = slot1 + 1;
            int tough = alloyArmor.getAlloyArmorToughness(stack);
            int def = alloyArmor.getAlloyDamageReduceAmount(stack);
            event.addModifier(Attributes.ARMOR_TOUGHNESS,new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe1fa"+slot1), "Rankine Armor Toughness modifier",
                    tough,
                    AttributeModifier.Operation.ADDITION));
            event.addModifier(Attributes.ARMOR, new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe1fa"+slot2), "Rankine Armor modifier",
                    def,
                    AttributeModifier.Operation.ADDITION));
        }
        if ((stack.getItem() instanceof HammerItem)) {
            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.SWING,stack) > 0 && event.getSlotType() == EquipmentSlotType.MAINHAND) {
                event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fc3"), "Rankine Swing modifier",
                        0.5D * EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.SWING,stack),
                        AttributeModifier.Operation.ADDITION));
            }
        }

        if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ANTIQUATED,stack) > 0 && (event.getSlotType() == EquipmentSlotType.MAINHAND || event.getSlotType() == EquipmentSlotType.OFFHAND)) {
            event.addModifier(Attributes.LUCK, new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fd1"), "Rankine Antiquated modifier",
                    EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ANTIQUATED,stack),
                    AttributeModifier.Operation.ADDITION));
        }
        if (stack.getItem() instanceof SpearItem && event.getSlotType() == EquipmentSlotType.MAINHAND) {
            event.addModifier(RankineAttributes.REACH_DISTANCE, new AttributeModifier(RankineAttributes.SPEAR_REACH_MODIFIER,"Weapon modifier", 1, AttributeModifier.Operation.ADDITION));
        }
        if (stack.getItem() instanceof KnifeItem && event.getSlotType() == EquipmentSlotType.MAINHAND) {
            event.addModifier(RankineAttributes.REACH_DISTANCE, new AttributeModifier(RankineAttributes.KNIFE_REACH_MODIFIER,"Weapon modifier", -2, AttributeModifier.Operation.ADDITION));
        }
    }
    
    @SubscribeEvent
    public static void onToolUse(BlockEvent.BlockToolInteractEvent event) {
        if (Config.GENERAL.DISABLE_WOODEN_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_SWORD) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_WOODEN_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_AXE) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_WOODEN_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_SHOVEL) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_WOODEN_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_PICKAXE) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_WOODEN_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_HOE) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_STONE_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_SWORD) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_STONE_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_AXE) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_STONE_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_SHOVEL) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_STONE_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_PICKAXE) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_STONE_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_HOE) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_IRON_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_SWORD) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_IRON_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_AXE) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_IRON_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_SHOVEL) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_IRON_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_PICKAXE) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_IRON_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_HOE) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_GOLDEN_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_SWORD) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_GOLDEN_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_AXE) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_GOLDEN_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_SHOVEL) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_GOLDEN_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_PICKAXE) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_GOLDEN_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_HOE) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_DIAMOND_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_SWORD) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_DIAMOND_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_AXE) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_DIAMOND_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_SHOVEL) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_DIAMOND_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_PICKAXE) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_DIAMOND_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_HOE) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_NETHERITE_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_SWORD) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_NETHERITE_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_AXE) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_NETHERITE_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_SHOVEL) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_NETHERITE_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_PICKAXE) { event.setCanceled(true); }
        if (Config.GENERAL.DISABLE_NETHERITE_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_HOE) { event.setCanceled(true); }
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event)
    {
        Item heldItem = event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem();

        if (!(heldItem instanceof AxeItem) && event.getState().getBlock().getTags().contains(new ResourceLocation("minecraft:logs")) && Config.GENERAL.MANDATORY_AXE.get()) { event.setNewSpeed(0f); }
        if (heldItem instanceof HammerItem) { event.setNewSpeed(0f); }
        if (heldItem instanceof CrowbarItem) { event.setNewSpeed(0f); }

        if (Config.GENERAL.DISABLE_WOODEN_SWORD.get() && heldItem == Items.WOODEN_SWORD) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_WOODEN_AXE.get() && heldItem == Items.WOODEN_AXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_WOODEN_SHOVEL.get() && heldItem == Items.WOODEN_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_WOODEN_PICKAXE.get() && heldItem == Items.WOODEN_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_WOODEN_HOE.get() && heldItem == Items.WOODEN_HOE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_STONE_SWORD.get() && heldItem == Items.STONE_SWORD) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_STONE_AXE.get() && heldItem == Items.STONE_AXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_STONE_SHOVEL.get() && heldItem == Items.STONE_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_STONE_PICKAXE.get() && heldItem == Items.STONE_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_STONE_HOE.get() && heldItem == Items.STONE_HOE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_IRON_SWORD.get() && heldItem == Items.IRON_SWORD) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_IRON_AXE.get() && heldItem == Items.IRON_AXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_IRON_SHOVEL.get() && heldItem == Items.IRON_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_IRON_PICKAXE.get() && heldItem == Items.IRON_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_IRON_HOE.get() && heldItem == Items.IRON_HOE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_GOLDEN_SWORD.get() && heldItem == Items.GOLDEN_SWORD) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_GOLDEN_AXE.get() && heldItem == Items.GOLDEN_AXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_GOLDEN_SHOVEL.get() && heldItem == Items.GOLDEN_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_GOLDEN_PICKAXE.get() && heldItem == Items.GOLDEN_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_GOLDEN_HOE.get() && heldItem == Items.GOLDEN_HOE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_DIAMOND_SWORD.get() && heldItem == Items.DIAMOND_SWORD) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_DIAMOND_AXE.get() && heldItem == Items.DIAMOND_AXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_DIAMOND_SHOVEL.get() && heldItem == Items.DIAMOND_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_DIAMOND_PICKAXE.get() && heldItem == Items.DIAMOND_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_DIAMOND_HOE.get() && heldItem == Items.DIAMOND_HOE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_NETHERITE_SWORD.get() && heldItem == Items.NETHERITE_SWORD) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_NETHERITE_AXE.get() && heldItem == Items.NETHERITE_AXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_NETHERITE_SHOVEL.get() && heldItem == Items.NETHERITE_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_NETHERITE_PICKAXE.get() && heldItem == Items.NETHERITE_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_NETHERITE_HOE.get() && heldItem == Items.NETHERITE_HOE) { event.setNewSpeed(0f); }

        if (event.getPlayer().getHeldItemOffhand().getItem() == RankineItems.HASTE_PENDANT.get()) {
            event.setNewSpeed(event.getNewSpeed() + 3);
        }

        if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.QUAKE,event.getPlayer().getHeldItemMainhand()) > 0) {
            int enchant = EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.QUAKE,event.getPlayer().getHeldItemMainhand());
            int height = event.getPos().getY();

            float maxPercent = .40f + (enchant - 1) * .10f;
            int minHeight = 10;
            float finalSpeed;
            int maxHeight = 45 + (enchant - 1) * 35;
            if (height < minHeight) {
                event.setNewSpeed(event.getNewSpeed() + event.getNewSpeed() * maxPercent);
            } else if (height < maxHeight){
                float minPercent = .10f + (enchant - 1) * 0.05f;

                float[] s = RankineMathHelper.linspace(maxPercent,minPercent,maxHeight-minHeight);
                event.setNewSpeed(event.getNewSpeed() + event.getNewSpeed() * s[height - 10]);
            }
        }

    }

    @SubscribeEvent
    public static void onDamageEntity(LivingDamageEvent event) {
        if (event.getSource().getTrueSource() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
            if (Config.GENERAL.DISABLE_WOODEN_SWORD.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_SWORD) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_WOODEN_AXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_AXE) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_WOODEN_SHOVEL.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_SHOVEL) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_WOODEN_PICKAXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_PICKAXE) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_WOODEN_HOE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_HOE) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_STONE_SWORD.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_SWORD) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_STONE_AXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_AXE) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_STONE_SHOVEL.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_SHOVEL) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_STONE_PICKAXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_PICKAXE) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_STONE_HOE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_HOE) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_IRON_SWORD.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_SWORD) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_IRON_AXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_AXE) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_IRON_SHOVEL.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_SHOVEL) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_IRON_PICKAXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_PICKAXE) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_IRON_HOE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_HOE) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_GOLDEN_SWORD.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_SWORD) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_GOLDEN_AXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_AXE) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_GOLDEN_SHOVEL.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_SHOVEL) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_GOLDEN_PICKAXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_PICKAXE) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_GOLDEN_HOE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_HOE) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_DIAMOND_SWORD.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_SWORD) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_DIAMOND_AXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_AXE) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_DIAMOND_SHOVEL.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_SHOVEL) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_DIAMOND_PICKAXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_PICKAXE) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_DIAMOND_HOE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_HOE) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_NETHERITE_SWORD.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_SWORD) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_NETHERITE_AXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_AXE) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_NETHERITE_SHOVEL.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_SHOVEL) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_NETHERITE_PICKAXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_PICKAXE) { event.setAmount(1f); }
            if (Config.GENERAL.DISABLE_NETHERITE_HOE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_HOE) { event.setAmount(1f); }

            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.CLEANSE,player.getHeldItem(Hand.MAIN_HAND)) >= 1 && !player.world.isRemote) {
                LivingEntity receiver = event.getEntityLiving();
                float damage = EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.CLEANSE,player.getHeldItem(Hand.MAIN_HAND)) * receiver.getActivePotionEffects().size();
                event.setAmount(event.getAmount() + damage);
                boolean flag = damage >= 1;
                if (flag) {
                    receiver.clearActivePotions();
                    receiver.playSound(SoundEvents.BLOCK_GRINDSTONE_USE,1.0f, 1.0f);
                }
            }

            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.BACKSTAB,player.getHeldItem(Hand.MAIN_HAND)) >= 1 && !player.world.isRemote) {
                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);

                LivingEntity receiver = event.getEntityLiving();
                if (receiver.getHorizontalFacing().equals(player.getHorizontalFacing())) {
                    receiver.playSound(SoundEvents.ITEM_TRIDENT_HIT,1.0f, 1.0f);
                    float damage = event.getAmount() + event.getAmount() * EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.BACKSTAB,stack);
                    event.setAmount(damage);
                }
            }

            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.LEVERAGE,player.getHeldItem(Hand.MAIN_HAND)) >= 1 && !player.world.isRemote) {
                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);

                LivingEntity receiver = event.getEntityLiving();
                float size = receiver.getSize(receiver.getPose()).height * receiver.getSize(receiver.getPose()).width;
                int lvl = EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.LEVERAGE,stack);
                System.out.println(size);
                float mod = -2 + lvl;
                float damage = event.getAmount() + Math.max(0,Math.min(size + mod,1.5f*EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.LEVERAGE,stack)));
                System.out.println("damageOut: " + damage);
                event.setAmount(damage);
            }

            for (ItemStack armor : event.getEntityLiving().getArmorInventoryList()) {
                if (armor.getItem() instanceof IAlloyArmor ) {
                    EquipmentSlotType slot = armor.getEquipmentSlot() != null ? armor.getEquipmentSlot() : EquipmentSlotType.HEAD;
                    int i = ((IAlloyArmor) armor.getItem()).calcDurabilityLoss(armor,event.getEntity().getEntityWorld(),event.getEntityLiving(),true);
                    armor.damageItem(i,player, (p_220287_1_) -> {
                        p_220287_1_.sendBreakAnimation(slot);
                    });
                }
            }
        }

    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onTooltipCheck(ItemTooltipEvent event) {
        if (Config.GENERAL.DISABLE_WOODEN_SWORD.get() && event.getItemStack().getItem() == Items.WOODEN_SWORD) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_WOODEN_AXE.get() && event.getItemStack().getItem() == Items.WOODEN_AXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_WOODEN_SHOVEL.get() && event.getItemStack().getItem() == Items.WOODEN_SHOVEL) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_WOODEN_PICKAXE.get() && event.getItemStack().getItem() == Items.WOODEN_PICKAXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_WOODEN_HOE.get() && event.getItemStack().getItem() == Items.WOODEN_HOE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_STONE_SWORD.get() && event.getItemStack().getItem() == Items.STONE_SWORD) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_STONE_AXE.get() && event.getItemStack().getItem() == Items.STONE_AXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_STONE_SHOVEL.get() && event.getItemStack().getItem() == Items.STONE_SHOVEL) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_STONE_PICKAXE.get() && event.getItemStack().getItem() == Items.STONE_PICKAXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_STONE_HOE.get() && event.getItemStack().getItem() == Items.STONE_HOE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_IRON_SWORD.get() && event.getItemStack().getItem() == Items.IRON_SWORD) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_IRON_AXE.get() && event.getItemStack().getItem() == Items.IRON_AXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_IRON_SHOVEL.get() && event.getItemStack().getItem() == Items.IRON_SHOVEL) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_IRON_PICKAXE.get() && event.getItemStack().getItem() == Items.IRON_PICKAXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_IRON_HOE.get() && event.getItemStack().getItem() == Items.IRON_HOE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_GOLDEN_SWORD.get() && event.getItemStack().getItem() == Items.GOLDEN_SWORD) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_GOLDEN_AXE.get() && event.getItemStack().getItem() == Items.GOLDEN_AXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_GOLDEN_SHOVEL.get() && event.getItemStack().getItem() == Items.GOLDEN_SHOVEL) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_GOLDEN_PICKAXE.get() && event.getItemStack().getItem() == Items.GOLDEN_PICKAXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_GOLDEN_HOE.get() && event.getItemStack().getItem() == Items.GOLDEN_HOE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_DIAMOND_SWORD.get() && event.getItemStack().getItem() == Items.DIAMOND_SWORD) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_DIAMOND_AXE.get() && event.getItemStack().getItem() == Items.DIAMOND_AXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_DIAMOND_SHOVEL.get() && event.getItemStack().getItem() == Items.DIAMOND_SHOVEL) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_DIAMOND_PICKAXE.get() && event.getItemStack().getItem() == Items.DIAMOND_PICKAXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_DIAMOND_HOE.get() && event.getItemStack().getItem() == Items.DIAMOND_HOE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_NETHERITE_SWORD.get() && event.getItemStack().getItem() == Items.NETHERITE_SWORD) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_NETHERITE_AXE.get() && event.getItemStack().getItem() == Items.NETHERITE_AXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_NETHERITE_SHOVEL.get() && event.getItemStack().getItem() == Items.NETHERITE_SHOVEL) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_NETHERITE_PICKAXE.get() && event.getItemStack().getItem() == Items.NETHERITE_PICKAXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.GENERAL.DISABLE_NETHERITE_HOE.get() && event.getItemStack().getItem() == Items.NETHERITE_HOE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
    }

    @SubscribeEvent
    public static void onLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getPlayer().getHeldItemMainhand().getItem() instanceof HammerItem) {
            ItemStack stack = event.getPlayer().getHeldItemMainhand();
            HammerItem hammer = (HammerItem) stack.getItem();
            World worldIn = event.getWorld();
            BlockPos pos = event.getPos();
            PlayerEntity player = event.getPlayer();

            if (event.getPlayer().getCooledAttackStrength(0) >= (1f)) {
                event.getPlayer().resetCooldown();
                if (HammerItem.getExcavateModifier(stack) != 0)
                {
                    hammer.getExcavationResult(pos,worldIn,player,stack);
                } else {
                    hammer.onBlockDestroyed(stack,worldIn,worldIn.getBlockState(pos),pos, player);
                }
            } else {
                event.getPlayer().resetCooldown();
            }
        } else if (event.getPlayer().getHeldItemMainhand().getItem() instanceof CrowbarItem) {
            ItemStack stack = event.getPlayer().getHeldItemMainhand();
            CrowbarItem crowbar = (CrowbarItem) stack.getItem();
            World worldIn = event.getWorld();
            BlockPos pos = event.getPos();
            PlayerEntity player = event.getPlayer();

            if (event.getPlayer().getCooledAttackStrength(0) >= (1f)) {
                event.getPlayer().resetCooldown();
                crowbar.onBlockDestroyed(stack,worldIn,worldIn.getBlockState(pos),pos, player);
            } else {
                event.getPlayer().resetCooldown();
            }
        }
    }

/*
    @SubscribeEvent
    public static void onPistonCrush(PistonEvent event)
    {
        Direction pistonDir = event.getDirection();
        BlockPos end = event.getFaceOffsetPos();
        BlockPos barrier = event.getFaceOffsetPos().offset(pistonDir,1);
        IWorld world = event.getWorld();
        World worldIn = world.getWorld();

        if (event.getPistonMoveType() == PistonEvent.PistonMoveType.EXTEND && !PistonCrusherRecipes.getInstance().getPrimaryResult(new ItemStack(world.getBlockState(end).getBlock())).getKey().isEmpty() && event.getState().getBlock() != Blocks.STICKY_PISTON && world.getBlockState(barrier).getBlock() == ModBlocks.CAST_IRON_BLOCK)
        {
            world.destroyBlock(end, false);
            if (!worldIn.isRemote() && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots)
            {
                Pair<ItemStack, Float[]> p = PistonCrusherRecipes.getInstance().getPrimaryResult(new ItemStack(world.getBlockState(end).getBlock()));
                float f = 0.5F;
                Random rand = new Random();
                double d0 = (double)(rand.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double)(rand.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double)(rand.nextFloat() * 0.5F) + 0.25D;
                ItemEntity itementity = new ItemEntity(worldIn, (double)end.getX() + d0, (double)end.getY() + d1, (double)end.getZ() + d2, new ItemStack(p.getKey().getItem(),p.getValue()[0].intValue()));
                itementity.setDefaultPickupDelay();
                worldIn.addEntity(itementity);
            }
        }
    }

    @SubscribeEvent
    public static void glassCheck(ProjectileImpactEvent event)
    {
        World worldIn = event.getEntity().getEntityWorld();
        RayTraceResult e = event.getRayTraceResult(); // Only works at certain angle/look position
        BlockPos hit = new BlockPos(e.getHitVec().getX(), e.getHitVec().getY(), e.getHitVec().getZ());
        System.out.println(hit);
        System.out.println(worldIn.getBlockState(hit).getBlock());
        if (worldIn.getBlockState(hit).getBlock() instanceof GlassBlock && !worldIn.isRemote() && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots)
        {
            System.out.println("SUCCESS");
            double d0 = (double)hit.getX() + 0.5D;
            double d1 = (double)hit.getY();
            double d2 = (double)hit.getZ() + 0.5D;
            worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            worldIn.removeBlock(new BlockPos(hit),false);
            float f = 0.5F;
            Random rand = new Random();
            double e0 = (double)(rand.nextFloat() * 0.5F) + 0.25D;
            double e1 = (double)(rand.nextFloat() * 0.5F) + 0.25D;
            double e2 = (double)(rand.nextFloat() * 0.5F) + 0.25D;
            ItemEntity itementity = new ItemEntity(worldIn, (double)hit.getX() + e0, (double)hit.getY() + e1, (double)hit.getZ() + e2, new ItemStack(Items.GLASS,1));
            itementity.setDefaultPickupDelay();
            worldIn.addEntity(itementity);
        }

    }

 */


    @SubscribeEvent
    public static void worldDye(PlayerInteractEvent.RightClickBlock event) {
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

    @SubscribeEvent
    public static void flintFire(PlayerInteractEvent.RightClickBlock event) {
        if (Config.GENERAL.FLINT_FIRE.get() && event.getFace() != null) {
            BlockPos pos = event.getPos();
            World world = event.getWorld();
            PlayerEntity player = event.getPlayer();
            BlockPos blockpos1 = event.getPos().offset(event.getFace());
            if (player.getHeldItemMainhand().getItem() == Items.FLINT && player.getHeldItemOffhand().getItem() == Items.FLINT) {
                if (world.getBlockState(pos) == RankineBlocks.CHARCOAL_PIT.get().getDefaultState().with(CharcoalPitBlock.LIT, false)) {
                    for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(0, -Config.MACHINES.CHARCOAL_PIT_HEIGHT.get(), 0), pos.add(0, Config.MACHINES.CHARCOAL_PIT_HEIGHT.get(), 0))) {
                        if (world.getBlockState(blockpos).getBlock() == RankineBlocks.CHARCOAL_PIT.get() && !world.isRemote) {
                            world.setBlockState(blockpos, world.getBlockState(blockpos).with(BlockStateProperties.LIT, Boolean.TRUE), 2);
                        }
                    }
                    player.swingArm(Hand.MAIN_HAND);
                    if (new Random().nextFloat() < Config.GENERAL.FLINT_FIRE_CHANCE.get()) {
                        player.getHeldItem(Hand.MAIN_HAND).shrink(1);
                        player.getHeldItem(Hand.OFF_HAND).shrink(1);
                    }
                    world.playSound(player, blockpos1, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, new Random().nextFloat() * 0.4F + 0.8F);
                } else if (world.getBlockState(pos) == RankineBlocks.BEEHIVE_OVEN_PIT.get().getDefaultState().with(BlockStateProperties.LIT, false) ||
                        world.getBlockState(pos) == RankineBlocks.HIGH_BEEHIVE_OVEN_PIT.get().getDefaultState().with(BlockStateProperties.LIT, false) ||
                        world.getBlockState(pos) == RankineBlocks.ULTRA_HIGH_BEEHIVE_OVEN_PIT.get().getDefaultState().with(BlockStateProperties.LIT, false)) {
                    if (!world.isRemote()) {
                        world.setBlockState(pos, world.getBlockState(pos).with(BlockStateProperties.LIT, Boolean.TRUE), 2);
                        player.swingArm(Hand.MAIN_HAND);
                        if (new Random().nextFloat() < Config.GENERAL.FLINT_FIRE_CHANCE.get()) {
                            player.getHeldItem(Hand.MAIN_HAND).shrink(1);
                            player.getHeldItem(Hand.OFF_HAND).shrink(1);
                        }
                    }
                    world.playSound(player, blockpos1, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, new Random().nextFloat() * 0.4F + 0.8F);
                } else if (AbstractFireBlock.canLightBlock(world, blockpos1, event.getFace()) && !world.isRemote && !(world.getBlockState(pos).getBlock() instanceof BeehiveOvenPitBlock) &&
                        world.getBlockState(pos) != RankineBlocks.CHARCOAL_PIT.get().getDefaultState().with(CharcoalPitBlock.LIT, true)) {
                    world.setBlockState(blockpos1, AbstractFireBlock.getFireForPlacement(world, blockpos1), 11);
                    player.swingArm(Hand.MAIN_HAND);
                    if (new Random().nextFloat() < Config.GENERAL.FLINT_FIRE_CHANCE.get()) {
                        player.getHeldItem(Hand.MAIN_HAND).shrink(1);
                        player.getHeldItem(Hand.OFF_HAND).shrink(1);
                    }
                }
                world.playSound(player, blockpos1, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, new Random().nextFloat() * 0.4F + 0.8F);
            }
        }
    }

    @SubscribeEvent
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
            if ((target instanceof GrassySoilBlock || target.matchesBlock(Blocks.GRASS_BLOCK)) && direction.equals(Direction.UP)) {
                world.playSound(player, pos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                if (VanillaIntegration.grass_dirt_map.get(target) != null) {
                    world.setBlockState(pos, VanillaIntegration.grass_dirt_map.get(target).getDefaultState(), 3);
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
                if (!world.isRemote && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !world.restoringBlockSnapshots) {

                    double d0 = (double) (world.rand.nextFloat() * 0.5F) + 0.25D;
                    double d1 = (double) (world.rand.nextFloat() * 0.5F) + 0.25D;
                    double d2 = (double) (world.rand.nextFloat() * 0.5F) + 0.25D;
                    ItemEntity itementity = new ItemEntity(world, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, out);
                    itementity.setDefaultPickupDelay();
                    world.addEntity(itementity);
                    player.getCooldownTracker().setCooldown(stack.getItem(), recipe.getCooldownTicks());
                    if (stack.getItem().isDamageable()) {
                        player.getHeldItemMainhand().damageItem(1, player, (p_220038_0_) -> {
                            p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                        });
                    }
                }
            }
        }

    }

    @SubscribeEvent
    public static void axeStrip(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        World world = event.getWorld();
        Direction direction = event.getFace();
        BlockPos pos = event.getPos();
        PlayerEntity player = event.getPlayer();
        BlockState targetBS = world.getBlockState(pos);
        Block b = targetBS.getBlock();
        boolean Creative = player.isCreative();

        if(item instanceof AxeItem) {
            ItemStack strip = null;
            if (Config.GENERAL.STRIPPABLES_PAPER.get() && b == Blocks.BIRCH_LOG || b == RankineBlocks.YELLOW_BIRCH_LOG.get() || b == RankineBlocks.BLACK_BIRCH_LOG.get()) {
                if (world.getRandom().nextFloat() < 0.3) {
                    strip = new ItemStack(Items.PAPER, 1);
                }
            } else if (Config.GENERAL.STRIPPABLES_CORK.get() && b == RankineBlocks.CORK_OAK_LOG.get()) {
                strip = new ItemStack(RankineItems.CORK.get(), 1);
            } else if (Config.GENERAL.STRIPPABLES_CINNAMON.get() && b == RankineBlocks.CINNAMON_LOG.get()) {
                strip = new ItemStack(RankineItems.CINNAMON.get(), 1);
            } else if (Config.GENERAL.STRIPPABLES_STICKS.get() && b.isIn(BlockTags.LOGS) && !b.getRegistryName().toString().contains("stripped")) {
                if (world.getRandom().nextFloat() < 0.3) {
                    strip = new ItemStack(Items.STICK, 1);
                }
            }
            if (!world.isRemote && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !world.restoringBlockSnapshots && strip != null) {
                spawnAsEntity(event.getWorld(), event.getPos(), strip);
            }

            if(VanillaIntegration.stripping_map.get(b) != null) {
                if(b instanceof RotatedPillarBlock) {
                    Direction.Axis axis = targetBS.get(RotatedPillarBlock.AXIS);
                    world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    world.setBlockState(pos, VanillaIntegration.stripping_map.get(b).getDefaultState().with(RotatedPillarBlock.AXIS, axis), 2);
                    stack.damageItem(1, player, (entity) -> {
                        entity.sendBreakAnimation(event.getHand());
                    });
                    player.swingArm(event.getHand());
                    event.setResult(Event.Result.ALLOW);
                }
            }
        } else if (item instanceof ShovelItem) {
            if (VanillaIntegration.pathBlocks_map.get(b) != null) {
                world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, VanillaIntegration.pathBlocks_map.get(b).getDefaultState(), 2);
                stack.damageItem(1, player, (entity) -> {
                    entity.sendBreakAnimation(event.getHand());
                });
                player.swingArm(event.getHand());
                event.setResult(Event.Result.ALLOW);
            }
        } else if (item instanceof HoeItem) {
            if (VanillaIntegration.hoeables_map.get(b) != null) {
                world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, RankineBlocks.TILLED_SOIL.get().getDefaultState().with(TilledSoilBlock.MOISTURE, 0).with(TilledSoilBlock.SOIL_TYPE, VanillaIntegration.hoeables_map.get(b)), 2);
                stack.damageItem(1, player, (entity) -> {
                    entity.sendBreakAnimation(event.getHand());
                });
                player.swingArm(event.getHand());
                event.setResult(Event.Result.ALLOW);
            } else if (b instanceof DoubleCropsBlock && !Creative) {
                if (targetBS.get(DoubleCropsBlock.AGE) == 7) {
                    if (targetBS.get(DoubleCropsBlock.SECTION) == DoubleBlockHalf.LOWER) {
                        world.destroyBlock(pos,true);
                        world.setBlockState(pos,b.getDefaultState().with(CropsBlock.AGE, 0));
                    } else if (targetBS.get(DoubleCropsBlock.SECTION) == DoubleBlockHalf.UPPER) {
                        world.destroyBlock(pos.down(),true);
                        world.setBlockState(pos.down(),b.getDefaultState().with(CropsBlock.AGE, 0));
                    }
                }
            } else if (b instanceof TripleCropsBlock && !Creative) {
                if (targetBS.get(DoubleCropsBlock.AGE) == 7) {
                    if (targetBS.get(TripleCropsBlock.SECTION) == TripleBlockSection.BOTTOM) {
                        world.destroyBlock(pos,true);
                        world.setBlockState(pos,b.getDefaultState().with(CropsBlock.AGE, 0));
                    } else if (targetBS.get(TripleCropsBlock.SECTION) == TripleBlockSection.MIDDLE) {
                        world.destroyBlock(pos.down(),true);
                        world.setBlockState(pos.down(),b.getDefaultState().with(CropsBlock.AGE, 0));
                    } else if (targetBS.get(TripleCropsBlock.SECTION) == TripleBlockSection.TOP) {
                        world.destroyBlock(pos.down(2),true);
                        world.setBlockState(pos.down(2),b.getDefaultState().with(CropsBlock.AGE, 0));
                    }
                }
            } else if (b instanceof CropsBlock && !Creative) {
                if (targetBS.get(CropsBlock.AGE) == 7) {
                    world.destroyBlock(pos,true);
                    world.setBlockState(pos,b.getDefaultState().with(CropsBlock.AGE, 0));
                }
            }
        }
    }

    @SubscribeEvent
    public static void noWater(BlockEvent.CreateFluidSourceEvent event) {
        /*
        List<ResourceLocation> waterBiomes = WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), true);

        for (String b : Config.GENERAL.INFI_WATER_BIOMES.get()) {
            List<String> biomeName = Arrays.asList(b.split(":"));
            if (biomeName.size() > 1) {
                waterBiomes.add(ResourceLocation.tryCreate(b));
            } else {
                waterBiomes.addAll(WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.byName(b)), true));
            }
        }
        if (Config.GENERAL.DISABLE_WATER.get() && !waterBiomes.contains(event.getWorld().getBiome(event.getPos()).toString())) {
            event.setResult(Event.Result.DENY);
        }

         */
        if (event.getPos().getY() > WorldgenUtils.waterTableHeight((World) event.getWorld(), event.getPos())) {
            event.setResult(Event.Result.DENY);
        }

    }

    @SubscribeEvent
    public static void blockBreakingEvents(BlockEvent.BreakEvent event) {
        ServerWorld worldIn = (ServerWorld) event.getWorld();
        PlayerEntity player = event.getPlayer();
        BlockPos pos = event.getPos();
        Block target = worldIn.getBlockState(pos).getBlock();
        Item mainHandItem = player.getHeldItemMainhand().getItem();
        Item offHandItem = player.getHeldItemOffhand().getItem();
        float CHANCE = worldIn.getRandom().nextFloat();


        if (!player.abilities.isCreativeMode) {
            //Luck Pendant
            if (offHandItem == RankineItems.LUCK_PENDANT.get()) {
                if (event.getState().isIn(RankineTags.Blocks.LUCK_PENDANT)) {
                    if (new Random().nextFloat() < 0.2f) {
                        for (ItemStack i : Block.getDrops(event.getState(), (ServerWorld) event.getWorld(), event.getPos(), null)) {
                            spawnAsEntity(worldIn, pos, new ItemStack(i.getItem(), 1));
                        }
                    }
                }
            }

            //Alloy shovel perk
            if (target instanceof FallingBlock && mainHandItem instanceof AlloyShovelItem) {
                for (int i = 1; i <=5; ++i) {
                    if (mainHandItem.canHarvestBlock(worldIn.getBlockState(pos.up(i)))) {
                        worldIn.destroyBlock(pos.up(i), true);
                    }
                }
            }


            //Nugget Drops
            if (Tags.Blocks.STONE.contains(target)) {
                if (mainHandItem instanceof PickaxeItem) {
                    BlockPos foundPos = null;
                    for (int x = 1; x < Config.GENERAL.NUGGET_DISTANCE.get(); x++) {
                        if (worldIn.getBlockState(pos.down(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.down(x);
                        } else if (worldIn.getBlockState(pos.up(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.up(x);
                        } else if (worldIn.getBlockState(pos.south(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.south(x);
                        } else if (worldIn.getBlockState(pos.north(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.north(x);
                        } else if (worldIn.getBlockState(pos.east(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.east(x);
                        } else if (worldIn.getBlockState(pos.west(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.west(x);
                        }
                        if (foundPos != null && new Random().nextFloat() < Config.GENERAL.NUGGET_CHANCE.get() && !worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) {
                            Block b = worldIn.getBlockState(foundPos).getBlock();
                            ItemStack nug = ItemStack.EMPTY;
                            if (b == RankineBlocks.MAGNETITE_ORE.get()) {
                                nug = new ItemStack(Items.IRON_NUGGET);
                            } else if (b == RankineBlocks.MALACHITE_ORE.get()) {
                                nug = new ItemStack(RankineItems.COPPER_NUGGET.get());
                            } else if (b == RankineBlocks.CHALCOCITE_ORE.get()) {
                                nug = new ItemStack(RankineItems.COPPER_NUGGET.get());
                            } else if (b == RankineBlocks.BAUXITE_ORE.get()) {
                                nug = new ItemStack(RankineItems.ALUMINUM_NUGGET.get());
                            } else if (b == RankineBlocks.CASSITERITE_ORE.get()) {
                                nug = new ItemStack(RankineItems.TIN_NUGGET.get());
                            } else if (b == RankineBlocks.SPHALERITE_ORE.get()) {
                                nug = new ItemStack(RankineItems.ZINC_NUGGET.get());
                            } else if (b == RankineBlocks.PENTLANDITE_ORE.get()) {
                                nug = new ItemStack(RankineItems.NICKEL_NUGGET.get());
                            } else if (b == RankineBlocks.INTERSPINIFEX_ORE.get()) {
                                nug = new ItemStack(RankineItems.NICKEL_NUGGET.get());
                            } else if (b == RankineBlocks.MAGNESITE_ORE.get()) {
                                nug = new ItemStack(RankineItems.MAGNESIUM_NUGGET.get());
                            } else if (b == RankineBlocks.ILMENITE_ORE.get()) {
                                nug = new ItemStack(RankineItems.TITANIUM_NUGGET.get());
                            } else if (b == RankineBlocks.GALENA_ORE.get()) {
                                nug = new ItemStack(RankineItems.LEAD_NUGGET.get());
                            } else if (b == RankineBlocks.BISMUTHINITE_ORE.get()) {
                                nug = new ItemStack(RankineItems.BISMUTH_NUGGET.get());
                            } else if (b == RankineBlocks.ACANTHITE_ORE.get()) {
                                nug = new ItemStack(RankineItems.SILVER_NUGGET.get());
                            } else if (b == RankineBlocks.MOLYBDENITE_ORE.get()) {
                                nug = new ItemStack(RankineItems.MOLYBDENUM_NUGGET.get());
                            } else if (b == RankineBlocks.PYROLUSITE_ORE.get()) {
                                nug = new ItemStack(RankineItems.MANGANESE_NUGGET.get());
                            } else if (b == RankineBlocks.CHROMITE_ORE.get()) {
                                nug = new ItemStack(RankineItems.CHROMIUM_NUGGET.get());
                            } else if (b == RankineBlocks.COLTAN_ORE.get()) {
                                nug = new ItemStack(RankineItems.NIOBIUM_NUGGET.get());
                            } else if (b == RankineBlocks.WOLFRAMITE_ORE.get()) {
                                nug = new ItemStack(RankineItems.TUNGSTEN_NUGGET.get());
                            } else if (b == RankineBlocks.GREENOCKITE_ORE.get()) {
                                nug = new ItemStack(RankineItems.CADMIUM_NUGGET.get());
                            } else if (b == RankineBlocks.XENOTIME_ORE.get()) {
                                nug = new ItemStack(RankineItems.CERIUM_NUGGET.get());
                            } else if (b == RankineBlocks.BADDELEYITE_ORE.get()) {
                                nug = new ItemStack(RankineItems.ZIRCONIUM_NUGGET.get());
                            } else if (b == RankineBlocks.URANINITE_ORE.get()) {
                                nug = new ItemStack(RankineItems.URANIUM_NUGGET.get());
                            }

                            if (!nug.isEmpty()) {
                                spawnAsEntity(worldIn, pos, nug);
                                break;
                            }
                        }
                    }

                    //Geodes
                    if (worldIn.getRandom().nextFloat() <= Config.GENERAL.GEODE_CHANCE.get() && !worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) {
                        spawnAsEntity(worldIn, pos, new ItemStack(RankineItems.UNCUT_GEODE.get(), 1));
                    }

                }//end pick check

                //Flint drop
                if (player.getHeldItem(Hand.MAIN_HAND).getItem().isIn(RankineTags.Items.CRUDE_TOOLS)) {
                    if (CHANCE < Config.GENERAL.FLINT_DROP_CHANCE.get()) {
                        spawnAsEntity(worldIn,pos,new ItemStack(Items.FLINT,1));
                    }
                }

            } //end stone check

            //knife stuff
            if (mainHandItem.isIn(RankineTags.Items.KNIVES)) {
                ItemStack drops = null;

                if (target == Blocks.GRASS) {
                    drops = new ItemStack(Items.GRASS, 1);
                } else if (target == Blocks.TALL_GRASS) {
                    drops = new ItemStack(Items.GRASS, 2);
                } else if (target == Blocks.FERN) {
                    drops = new ItemStack(Items.FERN, 1);
                } else if (target == Blocks.LARGE_FERN) {
                    drops = new ItemStack(Items.LARGE_FERN, 1);
                } else if (target == Blocks.VINE) {
                    drops = new ItemStack(Items.VINE, 1);
                } else if (target == Blocks.TWISTING_VINES) {
                    drops = new ItemStack(Items.TWISTING_VINES, 1);
                } else if (target == Blocks.WEEPING_VINES_PLANT) {
                    drops = new ItemStack(Items.WEEPING_VINES, 1);
                } else if (target == RankineBlocks.SHORT_GRASS.get()) {
                    drops = new ItemStack(RankineItems.SHORT_GRASS.get(), 1);
                } else if (target == RankineBlocks.STINGING_NETTLE.get()) {
                    drops = new ItemStack(RankineItems.STINGING_NETTLE.get(), 1);
                } else if (target == RankineBlocks.YELLOW_CLOVER.get()) {
                    drops = new ItemStack(RankineItems.YELLOW_CLOVER.get(), 1);
                } else if (target == RankineBlocks.WHITE_CLOVER.get()) {
                    drops = new ItemStack(RankineItems.WHITE_CLOVER.get(), 1);
                } else if (target == RankineBlocks.CRIMSON_CLOVER.get()) {
                    drops = new ItemStack(RankineItems.CRIMSON_CLOVER.get(), 1);
                } else if (target == RankineBlocks.RED_CLOVER.get()) {
                    drops = new ItemStack(RankineItems.RED_CLOVER.get(), 1);
                } else if (target == Blocks.DEAD_BUSH || target instanceof RankinePlantBlock || target instanceof SweetBerryBushBlock) {
                    drops = new ItemStack(Items.STICK, 2 + worldIn.getRandom().nextInt(4));
                }
                if (drops != null && !worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) {
                    spawnAsEntity(worldIn, pos, drops);
                }
                if (drops != null && !worldIn.isRemote) {
                    player.getHeldItemMainhand().damageItem(1, player, (p_220038_0_) -> {
                        p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                    });
                }
            }

            //Foraging Enchantment
            if (target.isIn(Tags.Blocks.DIRT)) {
                ItemStack heldItemStack = player.getHeldItem(Hand.MAIN_HAND);

                if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.FORAGING, heldItemStack) > 0) {
                    ItemStack FOOD;
                    Biome.Category cat = worldIn.getBiome(event.getPos()).getCategory();
                    List<Item> possibleItems;
                    switch (cat) {
                        case JUNGLE:
                            possibleItems = Arrays.asList(RankineItems.SALTPETER.get(),Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT, RankineItems.PINEAPPLE.get(), Items.COCOA_BEANS, Items.MELON_SEEDS);
                            break;
                        case SWAMP:
                        case MUSHROOM:
                            possibleItems = Arrays.asList(RankineItems.SALTPETER.get(),Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT,Items.BROWN_MUSHROOM,Items.RED_MUSHROOM);
                            break;
                        case EXTREME_HILLS:
                            possibleItems = Arrays.asList(RankineItems.SALTPETER.get(),Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT, RankineItems.FIRE_CLAY_BALL.get(), RankineItems.SNOWBERRIES.get());
                            break;
                        case RIVER:
                            possibleItems = Arrays.asList(RankineItems.SALTPETER.get(),Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT,Items.CLAY_BALL,Items.KELP,Items.SUGAR_CANE);
                            break;
                        case PLAINS:
                            possibleItems = Arrays.asList(RankineItems.SALTPETER.get(),Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT,Items.PUMPKIN_SEEDS,Items.FEATHER,Items.LEATHER);
                            break;
                        case DESERT:
                            possibleItems = Arrays.asList(RankineItems.SALTPETER.get(), RankineItems.SALTPETER.get(),Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT,Items.RABBIT_HIDE,Items.BONE);
                            break;
                        default:
                            possibleItems = Arrays.asList(RankineItems.SALTPETER.get(),Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT);
                            break;
                    }

                    if (CHANCE < Config.GENERAL.FORAGING_CHANCE.get()) {
                        FOOD = new ItemStack(possibleItems.get(event.getWorld().getRandom().nextInt(possibleItems.size())));
                    } else {
                        return;
                    }
                    spawnAsEntity(worldIn,pos,FOOD);
                }
            }


        } //end creative check


    }

    @SubscribeEvent
    public static void onSheepJoinWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof SheepEntity) {
            SheepEntity sheepEntity = (SheepEntity) entity;
            sheepEntity.goalSelector.removeGoal(new EatGrassGoal(sheepEntity));
            sheepEntity.goalSelector.addGoal(5,new EatGrassGoalModified(sheepEntity));
        }
    }

    @SubscribeEvent
    public static void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
        if (event.getEntityLiving() instanceof MonsterEntity && event.getTarget() != null) {
            if (event.getTarget().getHeldItemOffhand().getItem() == RankineItems.REPULSION_PENDANT.get() || event.getEntityLiving().getActivePotionEffect(RankineEffects.MERCURY_POISONING) != null) {
                ((MobEntity) event.getEntityLiving()).setAttackTarget(null);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof MonsterEntity && event.getEntityLiving().getRevengeTarget() != null) {
            if (event.getEntityLiving().getRevengeTarget().getHeldItemOffhand().getItem() == RankineItems.REPULSION_PENDANT.get() || event.getEntityLiving().getActivePotionEffect(RankineEffects.MERCURY_POISONING) != null) {
                event.getEntityLiving().setRevengeTarget(null);
            }
        }
    }


}
