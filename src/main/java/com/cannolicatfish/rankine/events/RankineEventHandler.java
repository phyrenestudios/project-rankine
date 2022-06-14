package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.blocks.LEDBlock;
import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.blocks.beehiveoven.BeehiveOvenPitBlock;
import com.cannolicatfish.rankine.blocks.charcoalpit.CharcoalPitBlock;
import com.cannolicatfish.rankine.blocks.charcoalpit.CharcoalPitTile;
import com.cannolicatfish.rankine.blocks.plants.DoubleCropsBlock;
import com.cannolicatfish.rankine.blocks.plants.TripleCropsBlock;
import com.cannolicatfish.rankine.blocks.states.TripleBlockSection;
import com.cannolicatfish.rankine.blocks.tilledsoil.TilledSoilBlock;
import com.cannolicatfish.rankine.capabilities.ChunkRetrogenProvider;
import com.cannolicatfish.rankine.capabilities.IChunkRetrogenHandler;
import com.cannolicatfish.rankine.commands.BlockWallCommand;
import com.cannolicatfish.rankine.commands.CreateAlloyCommand;
import com.cannolicatfish.rankine.commands.GiveTagCommand;
import com.cannolicatfish.rankine.compatibility.Patchouli;
import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.entities.goals.EatGrassGoalModified;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.items.InformationItem;
import com.cannolicatfish.rankine.items.alloys.*;
import com.cannolicatfish.rankine.items.tools.CrowbarItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.items.tools.KnifeItem;
import com.cannolicatfish.rankine.items.tools.SpearItem;
import com.cannolicatfish.rankine.items.totems.InvigoratingTotemItem;
import com.cannolicatfish.rankine.items.totems.SofteningTotemItem;
import com.cannolicatfish.rankine.potion.RankineEffects;
import com.cannolicatfish.rankine.recipe.RockGeneratorRecipe;
import com.cannolicatfish.rankine.recipe.SluicingRecipe;
import com.cannolicatfish.rankine.recipe.StrippingRecipe;
import com.cannolicatfish.rankine.util.*;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.*;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.SaplingGrowTreeEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.IReverseTag;
import net.minecraftforge.registries.tags.ITagManager;
import vazkii.patchouli.api.PatchouliAPI;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mod.EventBusSubscriber
public class RankineEventHandler {

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<LevelChunk> event) {
        event.addCapability(new ResourceLocation("rankine:retrogen_chunk"), new ChunkRetrogenProvider());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void retrogenChunk(ChunkEvent.Load event) {
        if (!event.getWorld().isClientSide() && Config.WORLDGEN.RETRO_GEN.get()) {
            ChunkAccess chunkAccess = event.getChunk();
            if (chunkAccess instanceof ICapabilityProvider && chunkAccess.getStatus().isOrAfter(ChunkStatus.FULL)) {
                LazyOptional<IChunkRetrogenHandler> capability = ((ICapabilityProvider) chunkAccess).getCapability(ChunkRetrogenProvider.CAPABILITY, null);
                if (capability.isPresent() && capability.resolve().isPresent() && !capability.resolve().get().getValue()) {
                    ReplacementUtils.performRetrogenReplacement(chunkAccess);
                    capability.ifPresent(iChunkRetrogenHandler -> iChunkRetrogenHandler.setValue(true));
                }
            }
        }

    }

    @SubscribeEvent
    public static void onItemPickup(PlayerEvent.ItemPickupEvent event) {

        // Totem of Cobbling
        if (((ForgeRegistries.ITEMS.tags().getTag(Tags.Items.STONE).contains(event.getStack().getItem()) || event.getStack().getItem() == Items.COBBLESTONE) && (event.getPlayer().getMainHandItem().getItem() == RankineItems.TOTEM_OF_COBBLING.get() || event.getPlayer().getOffhandItem().getItem() == RankineItems.TOTEM_OF_COBBLING.get()))) {
            Player player = event.getPlayer();
            ItemStack totem = player.getMainHandItem().getItem() == RankineItems.TOTEM_OF_COBBLING.get() ? player.getMainHandItem() : player.getOffhandItem();
            if (totem.getDamageValue() != 0) {
                int x = totem.getDamageValue() - event.getStack().copy().getCount();
                player.getInventory().getItem(event.getPlayer().getInventory().findSlotMatchingItem(event.getStack())).shrink(totem.getDamageValue());
                totem.setDamageValue(Math.max(x,0));

            }
        }
    }

    @SubscribeEvent
    public static void onAlloyToolHarvest(PlayerEvent.HarvestCheck event) {
        ItemStack stack = event.getPlayer().getMainHandItem();
        if (stack.getItem() instanceof IAlloyTool) {
            List<Tiers> tiers = Arrays.asList(Tiers.WOOD,Tiers.STONE,Tiers.IRON,Tiers.DIAMOND,Tiers.NETHERITE);
            event.setCanHarvest(TierSortingRegistry.isCorrectTierForDrops(tiers.get(((IAlloyTool) stack.getItem()).getAlloyHarvestLevel(stack)),event.getTargetBlock()));
        }
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CreateAlloyCommand.register(event.getDispatcher());
        GiveTagCommand.register(event.getDispatcher());
        BlockWallCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void addWandererTrades(WandererTradesEvent event) {
        if (Config.GENERAL.VILLAGER_TRADES.get()) {
            event.getGenericTrades().add(new BasicItemListing(1,new ItemStack(RankineItems.PINEAPPLE.get(), 1),4,1,0.5f));
            event.getGenericTrades().add(new BasicItemListing(1,new ItemStack(RankineBlocks.LIMESTONE.get(), 8),8,1,0.05f));
            event.getRareTrades().add(new BasicItemListing(3,new ItemStack(RankineItems.METEORIC_IRON.get()),6,1,0.5f));
        }
    }

    @SubscribeEvent
    public static void addVillagerTrades(VillagerTradesEvent event) {
        List<VillagerTrades.ItemListing> level1 = event.getTrades().get(1);
        List<VillagerTrades.ItemListing> level2 = event.getTrades().get(2);
        List<VillagerTrades.ItemListing> level3 = event.getTrades().get(3);
        List<VillagerTrades.ItemListing> level4 = event.getTrades().get(4);
        List<VillagerTrades.ItemListing> level5 = event.getTrades().get(5);

        if (event.getType() == RankineVillagerProfessions.METALLURGIST) {
            level1.add(new BasicItemListing(1, new ItemStack(RankineItems.ALLOY_TEMPLATE.get()),12,1,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.TIN_INGOT.get(), 8), new ItemStack(Items.EMERALD),12,2,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(Items.COPPER_INGOT, 4), new ItemStack(Items.EMERALD),12,2,0.05f));
            level2.add(new BasicItemListing(1, new ItemStack(RankineItems.ZINC_INGOT.get(), 2),12,10,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.METEORIC_IRON.get(), 4), new ItemStack(Items.EMERALD),12,10,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.COIN.get(), 16), new ItemStack(Items.EMERALD),12,10,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(RankineItems.MANGANESE_INGOT.get(), 2),12,10,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(RankineItems.MOLYBDENUM_INGOT.get(), 2),12,10,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(RankineItems.VANADIUM_INGOT.get(), 2),12,10,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(RankineItems.NIOBIUM_INGOT.get(), 2),12,10,0.05f));
            level4.add(new BasicItemListing(6, new ItemStack(RankineItems.ELEMENT_INDEXER.get()),12,15,0.05f));
            level5.add(new BasicItemListing(10, new ItemStack(RankineItems.ORE_DETECTOR.get()),12,30,0.05f));
            level5.add(new RankineVillagerTrades.EnchantedAlloyItemForEmeraldsTrade(RankineItems.STEEL_PICKAXE.get(),"76Fe-15Cr-4V-4W-1C","rankine:alloying/damascus_steel_alloying","item.rankine.damascus_steel_alloying",15,3,30,0.2f));
            level5.add(new RankineVillagerTrades.EnchantedAlloyItemForEmeraldsTrade(RankineItems.STEEL_SWORD.get(),"76Fe-15Cr-4V-4W-1C","rankine:alloying/damascus_steel_alloying","item.rankine.damascus_steel_alloying",15,3,30,0.2f));
        } else if (event.getType() == RankineVillagerProfessions.MINERALOGIST) {
            level1.add(new BasicItemListing(1, new ItemStack(RankineItems.STIBNITE.get()),12,1,0.05f));
            level1.add(new BasicItemListing(1, new ItemStack(RankineItems.PROSPECTING_STICK.get()),12,1,0.05f));
            level1.add(new BasicItemListing(1, new ItemStack(RankineItems.HARDNESS_TESTER.get()),12,1,0.05f));
            level2.add(new BasicItemListing(1, new ItemStack(RankineItems.CHALCOPYRITE.get()),12,5,0.05f));
            level2.add(new BasicItemListing(1, new ItemStack(RankineItems.BORAX.get()),12,5,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.MICA.get(), 4), new ItemStack(Items.EMERALD),12,10,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.AMPHIBOLE.get(), 4), new ItemStack(Items.EMERALD),12,10,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.PLAGIOCLASE_FELDSPAR.get(), 4), new ItemStack(Items.EMERALD),12,10,0.05f));
            level3.add(new RankineVillagerTrades.EnchantedAlloyItemForEmeraldsTrade(RankineItems.INVAR_HAMMER.get(),"90Fe-10Ni","rankine:invar_alloying","item.rankine.invar_alloying",8,3,10,0.2f));
            level3.add(new BasicItemListing(1, new ItemStack(RankineItems.ZIRCON.get()),12,15,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(RankineItems.BAUXITE.get()),12,15,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(RankineItems.MAGNESITE.get()),12,15,0.05f));
            level4.add(new BasicItemListing(1, new ItemStack(RankineItems.VANADINITE.get()),12,15,0.05f));
            level4.add(new BasicItemListing(1, new ItemStack(RankineItems.PETALITE.get()),12,15,0.05f));
            level4.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.OLIVINE.get(), 4), new ItemStack(Items.EMERALD),12,20,0.05f));
            level4.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.PYROXENE.get(), 4), new ItemStack(Items.EMERALD),12,20,0.05f));
            level5.add(new BasicItemListing(1, new ItemStack(RankineItems.WOLFRAMITE.get()),12,30,0.05f));
            level5.add(new BasicItemListing(1, new ItemStack(RankineItems.COBALTITE.get()),12,30,0.05f));
            level5.add(new RankineVillagerTrades.EnchantedAlloyItemForEmeraldsTrade(RankineItems.STEEL_HAMMER.get(),"76Fe-15Cr-4V-4W-1C","rankine:alloying/damascus_steel_alloying","item.rankine.damascus_steel_alloying",15,3,30,0.2f));
        } else if (event.getType() == RankineVillagerProfessions.BOTANIST) {
            level1.addAll(RankineVillagerTrades.returnTagTrades(ItemTags.SMALL_FLOWERS,Items.DANDELION,3,1,12,10,0.05f));
            level1.addAll(RankineVillagerTrades.returnTagTrades(ItemTags.TALL_FLOWERS,Items.ROSE_BUSH,2,1,12,10,0.05f));
            level1.addAll(RankineVillagerTrades.returnTagTrades(RankineTags.Items.BERRIES,RankineItems.ELDERBERRIES.get(),2,1,12,10,0.05f));
            level2.addAll(RankineVillagerTrades.returnTagTrades(ItemTags.SAPLINGS,Items.OAK_SAPLING,2,1,12,10,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(Items.BAMBOO, 4),12,15,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(Items.VINE, 4),12,10,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(Items.LILY_PAD, 4),12,10,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(Items.RED_MUSHROOM, 4),12,15,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(Items.BROWN_MUSHROOM, 4),12,15,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(Items.SEA_PICKLE, 4),12,10,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(Items.KELP, 4),12,10,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(Items.SUGAR_CANE, 4),12,10,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(Items.CACTUS, 4),12,10,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(Items.DEAD_BUSH, 4),12,10,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(Items.NETHER_WART, 4),12,10,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(Items.POISONOUS_POTATO, 4),12,10,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(RankineItems.ALOE.get(), 4),12,30,0.05f));
            level4.add(new BasicItemListing(1, new ItemStack(Items.WARPED_NYLIUM, 1),12,15,0.05f));
            level4.add(new BasicItemListing(1, new ItemStack(Items.CRIMSON_NYLIUM, 1),12,15,0.05f));
            level4.add(new BasicItemListing(1, new ItemStack(Items.BRAIN_CORAL, 2),12,15,0.05f));
            level4.add(new BasicItemListing(1, new ItemStack(Items.BUBBLE_CORAL, 2),12,15,0.05f));
            level4.add(new BasicItemListing(1, new ItemStack(Items.FIRE_CORAL, 2),12,15,0.05f));
            level4.add(new BasicItemListing(1, new ItemStack(Items.HORN_CORAL, 2),12,15,0.05f));
            level4.add(new BasicItemListing(1, new ItemStack(Items.TUBE_CORAL, 2),12,15,0.05f));
            level5.add(new BasicItemListing(1, new ItemStack(Items.SHROOMLIGHT, 2),12,15,0.05f));
            level5.add(new BasicItemListing(1, new ItemStack(Items.MYCELIUM, 1),12,30,0.05f));
            level5.add(new BasicItemListing(5, new ItemStack(Items.CHORUS_FLOWER, 1),12,30,0.05f));
            level5.add(new BasicItemListing(10, new ItemStack(Items.WITHER_ROSE),12,30,0.05f));

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
            level4.addAll(RankineVillagerTrades.returnTagTrades(Tags.Items.GEMS,RankineItems.OPAL.get(),1,12,16,10,0.05f));
            level5.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.LONSDALEITE_DIAMOND.get(), 1), new ItemStack(Items.EMERALD, 6),12,20,0.05f));
            level5.add(new BasicItemListing(20, new ItemStack(RankineItems.LONSDALEITE_DIAMOND.get(), 1),12,50,0.05f));
            level5.add((entity,rand) -> new MerchantOffer(new ItemStack(Items.NETHER_STAR, 1), new ItemStack(Items.EMERALD, 64),12,50,0.05f));

        } else if (event.getType() == RankineVillagerProfessions.ROCK_COLLECTOR) {
            level1.addAll(RankineVillagerTrades.returnTagTrades(Tags.Items.STONE,RankineItems.ANORTHOSITE.get(),16,1,16,10,0.05f));
            List<Block> rocks = ForgeRegistries.BLOCKS.tags().getTag(Tags.Blocks.STONE).stream().toList();
            if (!rocks.isEmpty()) {
                for (Block rock : rocks) {
                    level2.add((entity, rand) -> new MerchantOffer(new ItemStack(rock.asItem(), 24), new ItemStack(Items.EMERALD, 1), 16, 10, 0.05f));
                }
            } else {
                level2.add((entity, rand) -> new MerchantOffer(new ItemStack(Items.SANDSTONE, 24), new ItemStack(Items.EMERALD, 1), 16, 10, 0.05f));
                level2.add((entity, rand) -> new MerchantOffer(new ItemStack(Items.RED_SANDSTONE, 12), new ItemStack(Items.EMERALD, 1), 16, 10, 0.05f));
            }
            level3.add(new BasicItemListing(1, new ItemStack(Items.OBSIDIAN, 1),12,10,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(Items.NETHERRACK, 8),12,10,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(Items.END_STONE, 2),12,10,0.05f));
            level3.add(new BasicItemListing(1, new ItemStack(Items.PURPUR_BLOCK, 2),12,10,0.05f));
            level4.add(new BasicItemListing(1, new ItemStack(RankineItems.PHOSPHORITE.get(), 2),12,10,0.05f));
            level4.add(new BasicItemListing(1, new ItemStack(RankineItems.IRONSTONE.get(), 2),12,10,0.05f));
            level4.add(new BasicItemListing(1, new ItemStack(RankineItems.METEORITE.get(), 2),12,10,0.05f));
            level4.add(new BasicItemListing(1, new ItemStack(RankineItems.ENSTATITE_CHONDRITE.get(), 2),12,10,0.05f));
            level5.add(new BasicItemListing(1, new ItemStack(RankineItems.ROMAN_CONCRETE.get(), 1),24,10,0.05f));
        }

        if (Config.GENERAL.VILLAGER_TRADES.get()) {
            if (event.getType() == VillagerProfession.MASON) {
                event.getTrades().get(1).add(new BasicItemListing(1,new ItemStack(RankineItems.MORTAR.get(), 16),16,1,0.05f));
                event.getTrades().get(1).add(new BasicItemListing(1,new ItemStack(RankineItems.REFRACTORY_BRICK.get(), 10),16,1,0.05f));
            } else if (event.getType() == VillagerProfession.CLERIC) {
                event.getTrades().get(1).add(new BasicItemListing(1, new ItemStack(RankineItems.SALTPETER.get(),2),12,1,0.05f));
            }
        }

    }

    @SubscribeEvent
    public static void onSaplingGrow(SaplingGrowTreeEvent event) {
        if (event.getRand().nextFloat() < 1-Config.GENERAL.SAPLING_GROW.get()) {
            event.setResult(Event.Result.DENY);
        }
        BlockPos pos = event.getPos();
        LevelAccessor worldIn = event.getWorld();
        if (worldIn.getBlockState(pos).is(Blocks.SPRUCE_SAPLING)) {

        }
    }

    @SubscribeEvent
    public static void onCropTrample(BlockEvent.FarmlandTrampleEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SofteningTotemItem || player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof SofteningTotemItem) {
                event.setCanceled(true);
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level worldIn = player.level;
        BlockPos pos = player.isShiftKeyDown() ? player.blockPosition() : player.blockPosition().above();

        // Tools
        if (worldIn.getGameTime()%5==0 && !worldIn.isClientSide()) {
            if (!Config.TOOLS.DISABLE_COMPASS.get() && (player.getOffhandItem().getItem() == Items.COMPASS || player.getMainHandItem().getItem() == Items.COMPASS)) {
                switch (player.getDirection()) {
                    case NORTH:
                        player.displayClientMessage(new TextComponent("Facing North with coordinates: X =" + new DecimalFormat("###,###").format(pos.getX()) + " Z =" + new DecimalFormat("###,###").format(pos.getZ())).withStyle(ChatFormatting.GOLD), true);
                        break;
                    case EAST:
                        player.displayClientMessage(new TextComponent("Facing East with coordinates: X =" + new DecimalFormat("###,###").format(pos.getX()) + " Z =" + new DecimalFormat("###,###").format(pos.getZ())).withStyle(ChatFormatting.GOLD), true);
                        break;
                    case SOUTH:
                        player.displayClientMessage(new TextComponent("Facing South with coordinates: X =" + new DecimalFormat("###,###").format(pos.getX()) + " Z =" + new DecimalFormat("###,###").format(pos.getZ())).withStyle(ChatFormatting.GOLD), true);
                        break;
                    case WEST:
                        player.displayClientMessage(new TextComponent("Facing West with coordinates: X =" + new DecimalFormat("###,###").format(pos.getX()) + " Z =" + new DecimalFormat("###,###").format(pos.getZ())).withStyle(ChatFormatting.GOLD), true);
                        break;
                }
            } else if (!Config.TOOLS.DISABLE_CLOCK.get() && (player.getOffhandItem().getItem() == Items.CLOCK || player.getMainHandItem().getItem() == Items.CLOCK)) {
                double hours = ((Math.floor(worldIn.getDayTime() / 1000f)) + 6) % 24;
                double minutes = ((worldIn.getDayTime() / 1000f) % 1) * 60;
                player.displayClientMessage(new TextComponent("Time = " + new DecimalFormat("00").format(hours) + ":" + new DecimalFormat("00").format(minutes) + " (" + worldIn.getDayTime() % 24000 + ")").withStyle(ChatFormatting.GOLD), true);
            } else if (!Config.TOOLS.DISABLE_THERMOMETER.get() && (player.getOffhandItem().getItem() == RankineItems.THERMOMETER.get() || player.getMainHandItem().getItem() == RankineItems.THERMOMETER.get())) {
                Biome biome = worldIn.getBiome(pos).value();
                float temp = biome.getBaseTemperature();
                if (biome.shouldSnowGolemBurn(pos)) {
                    player.displayClientMessage(new TextComponent("Temperature = " + new DecimalFormat("#.###").format(temp)).withStyle(ChatFormatting.RED, ChatFormatting.BOLD), true);
                } else if (biome.warmEnoughToRain(pos)) {
                    player.displayClientMessage(new TextComponent("Temperature = " + new DecimalFormat("#.###").format(temp)).withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD), true);
                } else if (!biome.shouldMeltFrozenOceanIcebergSlightly(pos)) {
                    player.displayClientMessage(new TextComponent("Temperature = " + new DecimalFormat("#.###").format(temp)).withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD), true);
                } else {
                    player.displayClientMessage(new TextComponent("Temperature = " + new DecimalFormat("#.###").format(temp)).withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD), true);
                }
            } else if (!Config.TOOLS.DISABLE_ALTIMETER.get() && (player.getOffhandItem().getItem() == RankineItems.ALTIMETER.get() || player.getMainHandItem().getItem() == RankineItems.ALTIMETER.get())) {
                int y = pos.getY();
                if (y < 0) {
                    player.displayClientMessage(new TextComponent("Altitude: Y = " + new DecimalFormat("###,###").format(y)).withStyle(ChatFormatting.WHITE, ChatFormatting.BOLD), true);
                } else if (y < 64) {
                    player.displayClientMessage(new TextComponent("Altitude: Y = " + new DecimalFormat("###,###").format(y)).withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD), true);
                } else if (y < 128) {
                    player.displayClientMessage(new TextComponent("Altitude: Y = " + new DecimalFormat("###,###").format(y)).withStyle(ChatFormatting.DARK_AQUA, ChatFormatting.BOLD), true);
                } else {
                    player.displayClientMessage(new TextComponent("Altitude: Y = " + new DecimalFormat("###,###").format(y)).withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD), true);
                }
            } else if (!Config.TOOLS.DISABLE_PHOTOMETER.get() && (player.getOffhandItem().getItem() == RankineItems.PHOTOMETER.get() || player.getMainHandItem().getItem() == RankineItems.PHOTOMETER.get())) {
                int SLL = worldIn.getBrightness(LightLayer.SKY,pos);
                int BLL = worldIn.getBrightness(LightLayer.BLOCK,pos);

                player.displayClientMessage(new TextComponent("Light Levels: Sky = " + new DecimalFormat("##").format(SLL) + " Block = " + new DecimalFormat("##").format(BLL)).withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD), true);
            } else if (!Config.TOOLS.DISABLE_BIOMETER.get() && (player.getOffhandItem().getItem() == RankineItems.BIOMETER.get() || player.getMainHandItem().getItem() == RankineItems.BIOMETER.get())) {
                player.displayClientMessage(new TextComponent("Biome = " + new TranslatableComponent(Util.makeDescriptionId("biome",worldIn.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getKey(worldIn.getBiome(pos).value()))).getString()).withStyle(ChatFormatting.GOLD), true);
            } else if (!Config.TOOLS.DISABLE_BIOMETER.get() && (player.getOffhandItem().getItem() == RankineItems.MAGNETOMETER.get() || player.getMainHandItem().getItem() == RankineItems.MAGNETOMETER.get())) {
                double strength = 0.05D;
                if (BlockPos.findClosestMatch(player.blockPosition(), 5, 4, (p) -> worldIn.getBlockState(p).is(RankineTags.Blocks.ELECTROMAGNETS)).isPresent()) {
                    strength = 3.00D;
                } else if (BlockPos.findClosestMatch(player.blockPosition(), 5, 4, (p) -> worldIn.getBlockState(p).is(Blocks.OBSERVER)).isPresent()) {
                    strength = 1.00D;
                } else {
                    Optional<BlockPos> b = BlockPos.findClosestMatch(player.blockPosition(), Config.TOOLS.MAGNETOMETER_RANGE.get(), Config.TOOLS.MAGNETOMETER_RANGE.get(), (p) -> worldIn.getBlockState(p).is(Tags.Blocks.ORES));
                    if (b.isPresent()) {
                        strength = 0.5D/(player.blockPosition().distSqr(b.get())-1);
                    }
                }
                player.displayClientMessage(new TranslatableComponent("item.rankine.magnetometer.message1", new DecimalFormat("#.##").format(strength)).withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD), true);

            }
        }
        if (!Config.TOOLS.DISABLE_SPEEDOMETER.get() && worldIn.isClientSide() && worldIn.getGameTime()%5==0 && (player.getOffhandItem().getItem() == RankineItems.SPEEDOMETER.get() || player.getMainHandItem().getItem() == RankineItems.SPEEDOMETER.get())) {
            Entity ent = player;
            if (player.getVehicle() != null) {
                ent = player.getVehicle();
            }

            double d0 = Math.abs(ent.getX() - player.xOld);
            double d1 = Math.abs(ent.getZ() - player.zOld);
            //double d2 = Math.abs(player.getPosY() - player.lastTickPosY);
            double speed = Math.sqrt(Math.pow(d0, 2) + Math.pow(d1, 2));
            Item mainhand = player.getMainHandItem().getItem();
            Item offhand = player.getOffhandItem().getItem();
            if ((offhand == RankineItems.SPEEDOMETER.get() && !(mainhand instanceof InformationItem || mainhand == Items.COMPASS || mainhand == Items.CLOCK)) ||
                    (mainhand == RankineItems.SPEEDOMETER.get() && !(offhand instanceof InformationItem || offhand == Items.COMPASS || offhand == Items.CLOCK)))
            player.displayClientMessage(new TextComponent("Speed = " + new DecimalFormat("#.##").format(speed * 20) + " blocks per second").withStyle(ChatFormatting.GOLD), true);

        }


        // Armor
        if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == RankineItems.DIVING_HELMET.get()) {
            int headSlot = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == RankineItems.DIVING_HELMET.get() ? 1 : 0;
            int chestSlot = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == RankineItems.DIVING_CHESTPLATE.get() ? 1 : 0;
            int legsSlot = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == RankineItems.DIVING_LEGGINGS.get() ? 1 : 0;
            int feetSlot = player.getItemBySlot(EquipmentSlot.FEET).getItem() == RankineItems.DIVING_BOOTS.get() ? 1 : 0;

            if (!player.isInWater()) {
                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200 * (headSlot+chestSlot+legsSlot+feetSlot), 0, false, false, true));
            }
        } else if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == RankineItems.CONDUIT_DIVING_HELMET.get() && !player.isEyeInFluid(FluidTags.WATER) && player.isInWater()) {
            int headSlot = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == RankineItems.CONDUIT_DIVING_HELMET.get() ? 1 : 0;
            int chestSlot = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == RankineItems.CONDUIT_DIVING_CHESTPLATE.get() ? 1 : 0;
            int legsSlot = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == RankineItems.CONDUIT_DIVING_LEGGINGS.get() ? 1 : 0;
            int feetSlot = player.getItemBySlot(EquipmentSlot.FEET).getItem() == RankineItems.CONDUIT_DIVING_BOOTS.get() ? 1 : 0;

            player.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 400 * (headSlot+chestSlot+legsSlot+feetSlot), 0, false, false, true));
        }


        if (player.getCommandSenderWorld().getGameTime() % 1200L == 0) {
            int count = 0;
            for (ItemStack s : player.getArmorSlots()) {
                if (s.isEnchanted() && EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.GUARD, s) > 0) {
                    count+=2;
                }
            }
            if (player.getAbsorptionAmount() < count) {
                player.setAbsorptionAmount(count);
            }
        }

        ItemStack ghast = ItemStack.EMPTY;
        for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack itemstack = player.getInventory().getItem(i);
            if (!itemstack.isEmpty() && itemstack.getDamageValue() != 0 && (player.getMainHandItem().getItem() instanceof InvigoratingTotemItem || player.getOffhandItem().getItem() instanceof InvigoratingTotemItem)) {
                ghast = itemstack;
                break;
            }
        }

        MobEffectInstance eff = player.getEffect(MobEffects.REGENERATION);
        if (ghast != ItemStack.EMPTY && eff != null) {
            int k = 50 >> eff.getAmplifier();
            if (eff.getDuration() % k == 0) {
                ghast.setDamageValue(Math.max(0,ghast.getDamageValue() - 1));
            }
        }

        AttributeInstance att = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (player.getOffhandItem().getItem() == RankineItems.TOTEM_OF_TIMESAVING.get() && att != null && !att.hasModifier(RankineAttributes.SWIFTNESS_TOTEM)) {
            att.addTransientModifier(RankineAttributes.SWIFTNESS_TOTEM);
        }

        AttributeInstance att2 = player.getAttribute(Attributes.MAX_HEALTH);
        if (player.getOffhandItem().getItem() == RankineItems.TOTEM_OF_ENDURING.get() && att2 != null && !att2.hasModifier(RankineAttributes.ENDURING_TOTEM)) {
            att2.addTransientModifier(RankineAttributes.ENDURING_TOTEM);
        }
    }

    @SubscribeEvent
    public static void onLightningEvent(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof LightningBolt) {
            LightningBolt entity = (LightningBolt) event.getEntity();
            Level worldIn = event.getWorld();
            BlockPos startPos = entity.blockPosition().below();
            if (!worldIn.isClientSide && Config.GENERAL.LIGHTNING_CONVERSION.get()) {
                Iterable<BlockPos> positions = BlockPos.withinManhattan(startPos,2,2,2);
                for (BlockPos pos : positions) {
                    double rand;
                    if (startPos.getX() == pos.getX() && startPos.getZ() == pos.getZ()) {
                        rand = 1/(1f + Math.abs(startPos.getY() - pos.getY()));
                    } else {
                        rand = pos.distSqr(new Vec3i(startPos.getX(),startPos.getY(),startPos.getZ()));
                    }

                    Block BLK = worldIn.getBlockState(pos).getBlock();
                    if (worldIn.getRandom().nextFloat() < 1/rand && ForgeRegistries.BLOCKS.tags().getTag(RankineTags.Blocks.LIGHTNING_VITRIFIED).contains(BLK)) {
                        worldIn.setBlock(pos,RankineBlocks.FULGURITE.get().defaultBlockState(),3);
                    } else if (worldIn.getRandom().nextFloat() < 1/rand && BLK.equals(Blocks.SAND) || BLK.equals(RankineBlocks.SILT.get()) || BLK.equals(RankineBlocks.DESERT_SAND.get())) {
                        worldIn.setBlock(pos,RankineBlocks.LIGHTNING_GLASS.get().defaultBlockState(),3);
                    } else if (worldIn.getRandom().nextFloat() < 1/rand && BLK.equals(Blocks.RED_SAND)) {
                        worldIn.setBlock(pos,RankineBlocks.RED_LIGHTNING_GLASS.get().defaultBlockState(),3);
                    } else if (worldIn.getRandom().nextFloat() < 1/rand && BLK.equals(Blocks.SOUL_SAND)) {
                        worldIn.setBlock(pos,RankineBlocks.SOUL_LIGHTNING_GLASS.get().defaultBlockState(),3);
                    } else if (worldIn.getRandom().nextFloat() < 1/rand && BLK.equals(RankineBlocks.BLACK_SAND.get())) {
                        worldIn.setBlock(pos,RankineBlocks.BLACK_LIGHTNING_GLASS.get().defaultBlockState(),3);
                    } else if (worldIn.getRandom().nextFloat() < 1/rand && BLK.equals(RankineBlocks.WHITE_SAND.get())) {
                        worldIn.setBlock(pos,RankineBlocks.WHITE_LIGHTNING_GLASS.get().defaultBlockState(),3);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamaged(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof Player) {
            Player player = (Player) event.getEntityLiving();
            Level worldIn = player.getCommandSenderWorld();
            for (int i = 0; i < player.getInventory().armor.size(); ++i) {
                ItemStack s = player.getInventory().armor.get(i);
                if (!event.getSource().isProjectile() || EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOBIOTIC,s) == 0) {
                    if (s.getItem() instanceof AlloyArmorItem) {
                        AlloyArmorItem armor = (AlloyArmorItem) s.getItem();
                        if (worldIn.getRandom().nextFloat() > armor.getHeatResist(s) && (player.isInLava() || player.getRemainingFireTicks() > 0 || worldIn.dimension() == Level.NETHER)) {
                            int finalI = i;
                            s.hurtAndBreak(1,player,(entity) -> {
                                entity.broadcastBreakEvent(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, finalI));
                            });
                        } else if ((worldIn.getRandom().nextFloat() > armor.getCorrResist(s) && player.isInWaterOrRain())) {
                            int finalI1 = i;
                            s.hurtAndBreak(1 + EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOBIOTIC,s)*3,player,(entity) -> {
                                entity.broadcastBreakEvent(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, finalI1));
                            });
                        }
                    }
                } else if (event.getSource().isProjectile() && EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOBIOTIC,s) > 0) {
                    if (!worldIn.isClientSide) {
                        double d0 = player.getX();
                        double d1 = player.getY();
                        double d2 = player.getZ();
                        if (s.getItem() instanceof AlloyArmorItem) {
                            AlloyArmorItem armor = (AlloyArmorItem) s.getItem();
                            if ((worldIn.getRandom().nextFloat() > armor.getCorrResist(s) && player.isInWaterOrRain())) {
                                int finalI1 = i;
                                s.hurtAndBreak(1 + EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOBIOTIC,s)*3,player,(entity) -> {
                                    entity.broadcastBreakEvent(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, finalI1));
                                });
                            }
                        }

                        for(int j = 0; j < 16; ++j) {
                            double d3 = player.getX() + (player.getRandom().nextDouble() - 0.5D) * 16.0D;
                            double d4 = Mth.clamp(player.getY() + (double)(player.getRandom().nextInt(16) - 8), 0.0D, (double)(worldIn.getHeight() - 1));
                            double d5 = player.getZ() + (player.getRandom().nextDouble() - 0.5D) * 16.0D;
                            if (player.isPassenger()) {
                                player.stopRiding();
                            }

                            if (player.randomTeleport(d3, d4, d5, true)) {
                                SoundEvent soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                                worldIn.playSound((Player)null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
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
                for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
                    ItemStack itemstack = player.getInventory().getItem(i);
                    if (!itemstack.isEmpty() && EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.WITHERING_CURSE, itemstack) > 0) {
                        wither = true;
                        break;
                    }
                }
                if (wither) {
                    player.addEffect(new MobEffectInstance(MobEffects.WITHER,100));
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
        if (Config.HARD_MODE.RADIOACTIVE.get()) {
            LivingEntity ent = event.getEntityLiving();
            AttributeInstance maxHealth = ent.getAttribute(Attributes.MAX_HEALTH);
            MobEffectInstance rad = ent.getEffect(RankineEffects.RADIATION_POISONING);
            if (rad != null) {
                int duration = rad.getDuration();
                if (duration >= 400 && maxHealth != null && !maxHealth.hasModifier(RankineAttributes.MINOR_RADIATION_POISONING)) {
                    maxHealth.addPermanentModifier(RankineAttributes.MINOR_RADIATION_POISONING);
                }

                if (duration >= 1600 && maxHealth != null && !maxHealth.hasModifier(RankineAttributes.RADIATION_POISONING)) {
                    maxHealth.addPermanentModifier(RankineAttributes.RADIATION_POISONING);
                }

                if (duration >= 3200 && maxHealth != null && !maxHealth.hasModifier(RankineAttributes.EXTREME_RADIATION_POISONING)) {
                    maxHealth.addPermanentModifier(RankineAttributes.EXTREME_RADIATION_POISONING);
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
    public static void fuelValues(WorldEvent.Load event) {
        VanillaIntegration.populateFuelMap();
    }

    @SubscribeEvent
    public static void fuelValues(FurnaceFuelBurnTimeEvent event) {
        Item Fuel = event.getItemStack().getItem();
        if (VanillaIntegration.fuelValueMap.containsKey(Fuel)) {
            event.setBurnTime(VanillaIntegration.fuelValueMap.get(Fuel));
        }
    }

    @SubscribeEvent
    public static void movementModifier(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level world = event.player.level;
        BlockPos pos;
        if (player.getY() % 1 < 0.5) {
            pos = player.blockPosition().below();
        } else {
            pos = player.blockPosition();
        }
        Block ground = world.getBlockState(pos).getBlock();


        // Path Creation
        if (Config.GENERAL.PATH_CREATION.get() && !player.isCreative() && player.tickCount%(Config.GENERAL.PATH_CREATION_TIME.get()*20)==0 && !world.isClientSide) {
            if (VanillaIntegration.pathBlocks_map.get(ground) != null && world.getBlockState(pos.above()).is(Blocks.AIR) && world.getBlockState(pos.above()).getBlock() instanceof BushBlock) {
                world.setBlock(pos, VanillaIntegration.pathBlocks_map.get(ground).defaultBlockState(),2);
            }

        }


        Item feetEquipment = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item headEquipment = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        if (player.isEyeInFluid(FluidTags.WATER) && headEquipment == RankineItems.GOGGLES.get()) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,400,0,false,false));
        }


        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeInstance swimSpeed = player.getAttribute(ForgeMod.SWIM_SPEED.get());

        //movementSpeed.applyNonPersistentModifier(new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fb5"), "rankine:block_ms", 0.0D, AttributeModifier.Operation.ADDITION));

        ITagManager<Block> blockITagManager = ForgeRegistries.BLOCKS.tags();
        // Movement Modifiersa
        if (Config.GENERAL.MOVEMENT_MODIFIERS.get() && blockITagManager != null) {
            List<AttributeModifier> mods = Arrays.asList(RankineAttributes.BRICKS_MS, RankineAttributes.CONCRETE_MS, RankineAttributes.GRASS_PATH_MS, RankineAttributes.ROMAN_CONCRETE_MS, RankineAttributes.DIRT_MS, RankineAttributes.MUD_MS, RankineAttributes.POLISHED_STONE_MS, RankineAttributes.SAND_MS, RankineAttributes.SNOW_MS, RankineAttributes.WOODEN_MS);
            if (player.isCreative() || player.isFallFlying()) {
                for (AttributeModifier m : mods) {
                    movementSpeed.removeModifier(m);
                }
            } else if (blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_PATHS).contains(ground) && !movementSpeed.hasModifier(RankineAttributes.GRASS_PATH_MS)) {
                if (!player.isCreative() && !player.isFallFlying()) {
                    movementSpeed.addTransientModifier(RankineAttributes.GRASS_PATH_MS);
                }
            } else if (!blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_PATHS).contains(ground) && movementSpeed.hasModifier(RankineAttributes.GRASS_PATH_MS)) {
                    movementSpeed.removeModifier(RankineAttributes.GRASS_PATH_MS);
            } else if (blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_SAND).contains(ground) && !movementSpeed.hasModifier(RankineAttributes.SAND_MS)) {
                if (!player.isCreative() && !player.isFallFlying()) {
                    movementSpeed.addTransientModifier(RankineAttributes.SAND_MS);
                }
            } else if (!blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_SAND).contains(ground) && movementSpeed.hasModifier(RankineAttributes.SAND_MS) && ground != Blocks.AIR) {
                    movementSpeed.removeModifier(RankineAttributes.SAND_MS);
            } else if (blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_MUD).contains(ground) && !movementSpeed.hasModifier(RankineAttributes.MUD_MS)) {
                if (!player.isCreative() && !player.isFallFlying()) {
                    movementSpeed.addTransientModifier(RankineAttributes.MUD_MS);
                }
            } else if (!blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_MUD).contains(ground) && movementSpeed.hasModifier(RankineAttributes.MUD_MS) && ground != Blocks.AIR) {
                    movementSpeed.removeModifier(RankineAttributes.MUD_MS);
            } else if ((blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_SNOW).contains(world.getBlockState(player.blockPosition()).getBlock()) || blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_SNOW).contains(world.getBlockState(player.blockPosition().below()).getBlock())) && !movementSpeed.hasModifier(RankineAttributes.SNOW_MS)) {
                if (!player.isCreative() && !player.isFallFlying()) {
                    movementSpeed.addTransientModifier(RankineAttributes.SNOW_MS);
                }
            } else if ((!blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_SNOW).contains(world.getBlockState(player.blockPosition()).getBlock()) || !blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_SNOW).contains(world.getBlockState(player.blockPosition().below()).getBlock())) && movementSpeed.hasModifier(RankineAttributes.SNOW_MS) && ground != Blocks.AIR) {
                    movementSpeed.removeModifier(RankineAttributes.SNOW_MS);
            } else if (blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_DIRT).contains(ground) && !movementSpeed.hasModifier(RankineAttributes.DIRT_MS)) {
                if (!player.isCreative() && !player.isFallFlying()) {
                    movementSpeed.addTransientModifier(RankineAttributes.DIRT_MS);
                }
            } else if (!blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_DIRT).contains(ground) && movementSpeed.hasModifier(RankineAttributes.DIRT_MS) && ground != Blocks.AIR) {
                    movementSpeed.removeModifier(RankineAttributes.DIRT_MS);
            } else if (blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_WOOD).contains(ground) && !movementSpeed.hasModifier(RankineAttributes.WOODEN_MS)) {
                if (!player.isCreative() && !player.isFallFlying()) {
                    movementSpeed.addTransientModifier(RankineAttributes.WOODEN_MS);
                }
            } else if (!blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_WOOD).contains(ground) && movementSpeed.hasModifier(RankineAttributes.WOODEN_MS) && ground != Blocks.AIR) {
                    movementSpeed.removeModifier(RankineAttributes.WOODEN_MS);
            } else if (blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_POLISHED).contains(ground) && !movementSpeed.hasModifier(RankineAttributes.POLISHED_STONE_MS)) {
                if (!player.isCreative() && !player.isFallFlying()) {
                    movementSpeed.addTransientModifier(RankineAttributes.POLISHED_STONE_MS);
                }
            } else if (!blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_POLISHED).contains(ground) && movementSpeed.hasModifier(RankineAttributes.POLISHED_STONE_MS) && ground != Blocks.AIR) {
                    movementSpeed.removeModifier(RankineAttributes.POLISHED_STONE_MS);
            } else if (blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_BRICKS).contains(ground) && !movementSpeed.hasModifier(RankineAttributes.BRICKS_MS)) {
                if (!player.isCreative() && !player.isFallFlying()) {
                    movementSpeed.addTransientModifier(RankineAttributes.BRICKS_MS);
                }
            } else if (!blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_BRICKS).contains(ground) && movementSpeed.hasModifier(RankineAttributes.BRICKS_MS) && ground != Blocks.AIR) {
                    movementSpeed.removeModifier(RankineAttributes.BRICKS_MS);
            } else if (blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_ROMAN).contains(ground) && !movementSpeed.hasModifier(RankineAttributes.ROMAN_CONCRETE_MS)) {
                if (!player.isCreative() && !player.isFallFlying()) {
                    movementSpeed.addTransientModifier(RankineAttributes.ROMAN_CONCRETE_MS);
                }
            } else if (!blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_ROMAN).contains(ground) && movementSpeed.hasModifier(RankineAttributes.ROMAN_CONCRETE_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(RankineAttributes.ROMAN_CONCRETE_MS);
            } else if (blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_CONCRETE).contains(ground) && !movementSpeed.hasModifier(RankineAttributes.CONCRETE_MS)) {
                if (!player.isCreative() && !player.isFallFlying()) {
                    movementSpeed.addTransientModifier(RankineAttributes.CONCRETE_MS);
                }
            } else if (!blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_CONCRETE).contains(ground) && movementSpeed.hasModifier(RankineAttributes.CONCRETE_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(RankineAttributes.CONCRETE_MS);
            }
        }
        if (ground == Blocks.ICE) {
            if (world.random.nextFloat() < Config.GENERAL.ICE_BREAK.get() && !(RankineEnchantmentHelper.hasSpeedSkater(player))) {
                for (BlockPos B : BlockPos.betweenClosed(pos.offset(-2, -1, -2), pos.offset(2, -1, 2))) {
                    if (world.getBlockState(B).getBlock() == Blocks.ICE) {
                        world.setBlockAndUpdate(B, Blocks.FROSTED_ICE.defaultBlockState().setValue(FrostedIceBlock.AGE, 2));
                    }
                }
            }
        }
        if (RankineEnchantmentHelper.hasDuneWalker(player) || feetEquipment == RankineItems.SANDALS.get()) {
            if (blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_SAND).contains(ground) && !movementSpeed.hasModifier(RankineAttributes.DUNE_WALKER)) {
                movementSpeed.addTransientModifier(RankineAttributes.DUNE_WALKER);
                player.maxUpStep = 1.0f;
            }
        } else if (!RankineEnchantmentHelper.hasDuneWalker(player) && feetEquipment != RankineItems.SANDALS.get() && movementSpeed.hasModifier(RankineAttributes.DUNE_WALKER)) {
            movementSpeed.removeModifier(RankineAttributes.DUNE_WALKER);
            player.maxUpStep = 0.5f;
        }
        if (!blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_SAND).contains(ground) && ground != Blocks.AIR && movementSpeed.hasModifier(RankineAttributes.DUNE_WALKER)) {
            movementSpeed.removeModifier(RankineAttributes.DUNE_WALKER);
            player.maxUpStep = 0.5f;
        }
        if (RankineEnchantmentHelper.hasSnowDrifter(player) || feetEquipment == RankineItems.SNOWSHOES.get()) {
            if ((blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_SNOW).contains(world.getBlockState(player.blockPosition()).getBlock()) || blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_SNOW).contains(world.getBlockState(player.blockPosition().below()).getBlock())) && !movementSpeed.hasModifier(RankineAttributes.SNOW_DRIFTER)) {
                movementSpeed.addTransientModifier(RankineAttributes.SNOW_DRIFTER);
                player.maxUpStep = 1.0f;
            }
        } else if (!RankineEnchantmentHelper.hasSnowDrifter(player) && feetEquipment != RankineItems.SNOWSHOES.get() && movementSpeed.hasModifier(RankineAttributes.SNOW_DRIFTER)) {
            movementSpeed.removeModifier(RankineAttributes.SNOW_DRIFTER);
            player.maxUpStep = 0.5f;
        }
        if ((!blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_SNOW).contains(world.getBlockState(player.blockPosition()).getBlock()) && !blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_SNOW).contains(world.getBlockState(player.blockPosition().below()).getBlock())) && ground != Blocks.AIR && movementSpeed.hasModifier(RankineAttributes.SNOW_DRIFTER)) {
            movementSpeed.removeModifier(RankineAttributes.SNOW_DRIFTER);
            player.maxUpStep = 0.5f;
        }
        if (RankineEnchantmentHelper.hasSpeedSkater(player) || feetEquipment == RankineItems.ICE_SKATES.get()) {
            if ((blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_ICE).contains(world.getBlockState(player.blockPosition()).getBlock()) || blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_ICE).contains(world.getBlockState(player.blockPosition().below()).getBlock())) && !movementSpeed.hasModifier(RankineAttributes.SPEED_SKATER)) {
                movementSpeed.addTransientModifier(RankineAttributes.SPEED_SKATER);
                player.maxUpStep = 1.0f;
            }
        } else if (!RankineEnchantmentHelper.hasSpeedSkater(player) && feetEquipment != RankineItems.ICE_SKATES.get() && movementSpeed.hasModifier(RankineAttributes.SPEED_SKATER)) {
            movementSpeed.removeModifier(RankineAttributes.SPEED_SKATER);
            player.maxUpStep = 0.5f;
        }
        if ((!blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_ICE).contains(world.getBlockState(player.blockPosition()).getBlock()) && !blockITagManager.getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_ICE).contains(world.getBlockState(player.blockPosition().below()).getBlock())) && ground != Blocks.AIR && movementSpeed.hasModifier(RankineAttributes.SPEED_SKATER)) {
            movementSpeed.removeModifier(RankineAttributes.SPEED_SKATER);
            player.maxUpStep = 0.5f;
        }
        if (RankineEnchantmentHelper.hasFlippers(player) || feetEquipment == RankineItems.FINS.get()) {
            if (player.isSwimming() && !swimSpeed.hasModifier(RankineAttributes.FLIPPERS)) {
                swimSpeed.addTransientModifier(RankineAttributes.FLIPPERS);
            }
        } else if (!RankineEnchantmentHelper.hasFlippers(player) && feetEquipment != RankineItems.FINS.get() && swimSpeed.hasModifier(RankineAttributes.FLIPPERS)) {
            swimSpeed.removeModifier(RankineAttributes.FLIPPERS);
        }
        if (!player.isSwimming() && swimSpeed.hasModifier(RankineAttributes.FLIPPERS)) {
            swimSpeed.removeModifier(RankineAttributes.FLIPPERS);
        }
        if (headEquipment == RankineItems.GOGGLES.get() && player.isEyeInFluid(FluidTags.WATER) && !swimSpeed.hasModifier(RankineAttributes.WATER_VISION)) {
            swimSpeed.addTransientModifier(RankineAttributes.WATER_VISION);
        } else if ((headEquipment != RankineItems.GOGGLES.get() || !player.isEyeInFluid(FluidTags.WATER)) && swimSpeed.hasModifier(RankineAttributes.WATER_VISION)) {
            swimSpeed.removeModifier(RankineAttributes.WATER_VISION);
        }
    }


    @SubscribeEvent
    public static void specialEnchants(AnvilUpdateEvent event) {
        ItemStack input = event.getLeft();
        if (event.getRight().getItem() == RankineItems.SANDALS.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getSlot() == EquipmentSlot.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.DUNE_WALKER,event.getOutput()) != 1) {
                event.getOutput().enchant(RankineEnchantments.DUNE_WALKER, 1);
                event.setCost(20);
            }
        } else if (event.getRight().getItem() == RankineItems.SNOWSHOES.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getSlot() == EquipmentSlot.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.SNOW_DRIFTER,event.getOutput()) != 1) {
                event.getOutput().enchant(RankineEnchantments.SNOW_DRIFTER, 1);
                event.setCost(20);
            }
        } else if (event.getRight().getItem() == RankineItems.ICE_SKATES.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getSlot() == EquipmentSlot.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.SPEED_SKATER,event.getOutput()) != 1) {
                event.getOutput().enchant(RankineEnchantments.SPEED_SKATER, 1);
                event.setCost(20);
            }
        } else if (event.getRight().getItem() == RankineItems.GAS_MASK.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getSlot() == EquipmentSlot.HEAD) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.GAS_PROTECTION,event.getOutput()) != 1) {
                event.getOutput().enchant(RankineEnchantments.GAS_PROTECTION, 1);
                event.setCost(20);
            }
        }
    }


    @SubscribeEvent
    public static void onFluidInteraction(BlockEvent.FluidPlaceBlockEvent event)
    {
        if (event.getState() == Blocks.COBBLESTONE.defaultBlockState() && Config.GENERAL.IGNEOUS_COBBLE_GEN.get())
        {
            Level worldIn = (Level) event.getWorld();
            BlockPos pos = event.getPos();
            Map<BlockPos,Block> posMap = new HashMap<>();
            for (Direction d : Direction.values()) {
                posMap.put(pos.relative(d),worldIn.getBlockState(pos.relative(d)).getBlock());
            }
            ItemStack[] items = posMap.values().stream().map(ItemStack::new).toArray(ItemStack[]::new);
            RockGeneratorRecipe recipe = worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR).stream().flatMap((r) -> {
                if (r.getGenType().equals(RockGeneratorUtils.RockGenType.INTRUSIVE_IGNEOUS)) {
                    return DataFixUtils.orElseGet(RankineRecipeTypes.ROCK_GENERATOR.tryMatch(r, worldIn, new SimpleContainer(items)).map(Stream::of),Stream::empty);
                }
                return null;
            }).findFirst().orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getResultItem();
                if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                    event.setNewState(((BlockItem) output.getItem()).getBlock().defaultBlockState());
                    if (worldIn.getRandom().nextFloat() < Config.GENERAL.ROCK_GENERATOR_REMOVAL_CHANCE.get()) {
                        BlockPos b = recipe.getRandomInput(posMap);
                        if (b != null) {
                            worldIn.removeBlock(b,false);
                        }
                    }
                }
            }

        } else if (event.getState() == Blocks.BASALT.defaultBlockState() && Config.GENERAL.IGNEOUS_COBBLE_GEN.get())
        {
            Level worldIn = (Level) event.getWorld();
            BlockPos pos = event.getPos();
            Map<BlockPos,Block> posMap = new HashMap<>();
            for (Direction d : Direction.values()) {
                posMap.put(pos.relative(d),worldIn.getBlockState(pos.relative(d)).getBlock());
            }
            ItemStack[] items = posMap.values().stream().map(ItemStack::new).toArray(ItemStack[]::new);
            RockGeneratorRecipe recipe = worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR).stream().flatMap((r) -> {
                if (r.getGenType().equals(RockGeneratorUtils.RockGenType.EXTRUSIVE_IGNEOUS)) {
                    return DataFixUtils.orElseGet(RankineRecipeTypes.ROCK_GENERATOR.tryMatch(r, worldIn, new SimpleContainer(items)).map(Stream::of),Stream::empty);
                }
                return null;
            }).findFirst().orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getResultItem();
                if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                    event.setNewState(((BlockItem) output.getItem()).getBlock().defaultBlockState());
                    if (worldIn.getRandom().nextFloat() < Config.GENERAL.ROCK_GENERATOR_REMOVAL_CHANCE.get()) {
                        BlockPos b = recipe.getRandomInput(posMap);
                        if (b != null) {
                            worldIn.removeBlock(b,false);
                        }
                    }
                }
            } else {
                event.setNewState(Blocks.BLACKSTONE.defaultBlockState());
            }
        } else if (event.getState() == Blocks.STONE.defaultBlockState()) {
            Level worldIn = (Level) event.getWorld();
            BlockPos pos = event.getPos();
            Map<BlockPos,Block> posMap = new HashMap<>();
            for (Direction d : Direction.values()) {
                posMap.put(pos.relative(d),worldIn.getBlockState(pos.relative(d)).getBlock());
            }
            ItemStack[] items = posMap.values().stream().map(ItemStack::new).toArray(ItemStack[]::new);
            RockGeneratorRecipe recipe = worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR).stream().flatMap((r) -> {
                if (r.getGenType().equals(RockGeneratorUtils.RockGenType.METAMORPHIC)) {
                    return DataFixUtils.orElseGet(RankineRecipeTypes.ROCK_GENERATOR.tryMatch(r, worldIn, new SimpleContainer(items)).map(Stream::of),Stream::empty);
                }
                return null;
            }).findFirst().orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getResultItem();
                if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                    event.setNewState(((BlockItem) output.getItem()).getBlock().defaultBlockState());
                    if (worldIn.getRandom().nextFloat() < Config.GENERAL.ROCK_GENERATOR_REMOVAL_CHANCE.get()) {
                        BlockPos b = recipe.getRandomInput(posMap);
                        if (b != null) {
                            worldIn.removeBlock(b,false);
                        }
                    }
                }
            } else {
                event.setNewState(RankineBlocks.SKARN.get().defaultBlockState());
            }
        } else if (event.getState() == Blocks.OBSIDIAN.defaultBlockState()) {
            Level worldIn = (Level) event.getWorld();
            BlockPos pos = event.getPos();
            Map<BlockPos,Block> posMap = new HashMap<>();
            for (Direction d : Direction.values()) {
                posMap.put(pos.relative(d),worldIn.getBlockState(pos.relative(d)).getBlock());
            }
            ItemStack[] items = posMap.values().stream().map(ItemStack::new).toArray(ItemStack[]::new);
            RockGeneratorRecipe recipe = worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR).stream().flatMap((r) -> {
                if (r.getGenType().equals(RockGeneratorUtils.RockGenType.VOLCANIC)) {
                    return DataFixUtils.orElseGet(RankineRecipeTypes.ROCK_GENERATOR.tryMatch(r, worldIn, new SimpleContainer(items)).map(Stream::of),Stream::empty);
                }
                return null;
            }).findFirst().orElse(null);
            if (recipe != null) {
                ItemStack output = recipe.getResultItem();
                if (!output.isEmpty() && output.getItem() instanceof BlockItem) {
                    event.setNewState(((BlockItem) output.getItem()).getBlock().defaultBlockState());
                    if (worldIn.getRandom().nextFloat() < Config.GENERAL.ROCK_GENERATOR_REMOVAL_CHANCE.get()) {
                        BlockPos b = recipe.getRandomInput(posMap);
                        if (b != null) {
                            worldIn.removeBlock(b,false);
                        }
                    }
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
            for(int i = 0; i < event.getPlayer().getInventory().getContainerSize(); ++i) {
                ItemStack itemstack = event.getPlayer().getInventory().getItem(i);
                if (!itemstack.isEmpty() && itemstack.getItem() instanceof IAlloyItem) {
                    ((AlloyItem)itemstack.getItem()).setRefresh(itemstack);
                }
            }
        }

        if (Config.GENERAL.STARTING_BOOK.get() && !event.getPlayer().getCommandSenderWorld().isClientSide && Patchouli.isInstalled()) {

            CompoundTag data = event.getPlayer().getPersistentData();
            CompoundTag persistent;
            if (!data.contains(Player.PERSISTED_NBT_TAG)) {
                data.put(Player.PERSISTED_NBT_TAG, (persistent = new CompoundTag()));
            } else {
                persistent = data.getCompound(Player.PERSISTED_NBT_TAG);
            }

            if (!persistent.contains(NBT_KEY)) {
                persistent.putBoolean(NBT_KEY, true);
                event.getPlayer().getInventory().add(PatchouliAPI.get().getBookStack(new ResourceLocation("rankine:rankine_journal")));
            }
        }
    }

    @SubscribeEvent
    public static void onParryEvent(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof Player) {
            Player player = (Player) event.getEntityLiving();
            ItemStack stack = player.getOffhandItem().getItem() instanceof KnifeItem ? player.getOffhandItem() : ItemStack.EMPTY;
            if (!stack.isEmpty()) {
                int i = stack.getItem().getUseDuration(stack) - player.getUseItemRemainingTicks();
                if (i < (10 + EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.PREPARATION,stack))) {


                    if (!event.getSource().isBypassArmor()) {
                        player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP,1.0f, 1.0f);
                        if (event.getSource().getEntity() instanceof LivingEntity && EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.RETALIATE,stack) >= 1) {
                            LivingEntity ent = (LivingEntity) event.getSource().getEntity();
                            ent.hurt(event.getSource(),event.getAmount());
                        } else if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.RETREAT,stack) >= 1) {
                            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,60));
                        }
                        if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDGAME,stack) > 0) {
                            List<LivingEntity> list = player.level.getEntitiesOfClass(LivingEntity.class, new AABB(player.blockPosition()).inflate(5, 5, 5), (e) -> (e instanceof Mob || e instanceof Player) && !e.equals(player));
                            for (LivingEntity entity : list) {
                                ItemStack offhand = player.getOffhandItem();
                                ItemStack mainhand = player.getMainHandItem();
                                player.setItemSlot(EquipmentSlot.MAINHAND,offhand);
                                player.setItemSlot(EquipmentSlot.OFFHAND,mainhand);
                                player.attack(entity);
                            }
                            double d0 = player.getX();
                            double d1 = player.getY();
                            double d2 = player.getZ();

                            for(int j = 0; j < 16; ++j) {
                                double d3 = player.getX() + (player.getRandom().nextDouble() - 0.5D) * 16.0D;
                                double d4 = Mth.clamp(player.getY() + (double)(player.getRandom().nextInt(16) - 8), 0.0D, (double)(player.level.getHeight() - 1));
                                double d5 = player.getZ() + (player.getRandom().nextDouble() - 0.5D) * 16.0D;
                                if (player.isPassenger()) {
                                    player.stopRiding();
                                }

                                if (player.randomTeleport(d3, d4, d5, true)) {
                                    SoundEvent soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                                    player.level.playSound((Player)null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                                    player.playSound(soundevent, 1.0F, 1.0F);
                                    break;
                                }
                            }
                        }
                        stack.hurtAndBreak(1, player, (p_220045_0_) -> {
                            p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                        });
                        event.setCanceled(true);
                    }
                }
            }
        }
    }





    @SubscribeEvent
    public static void onBlockHarvest(PlayerEvent.HarvestCheck event) {/*
        Material mat = event.getTargetBlock().getMaterial();
        boolean flag = mat == Material.STONE || mat == Material.METAL || mat == Material.HEAVY_METAL;
        if (flag && (event.getPlayer().getMainHandItem().getItem() instanceof AlloyPickaxeItem || event.getPlayer().getMainHandItem().getItem() instanceof AlloyPickaxeItem)) {
            ItemStack stack = event.getPlayer().getMainHandItem();
            Item item = event.getPlayer().getMainHandItem().getItem();
            boolean bool = item.canHarvestBlock(stack,event.getTargetBlock());
            event.setCanHarvest(bool);
        }*/
    }

    @SubscribeEvent
    public static void onItemAttributeModification(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof IAlloyTool && event.getSlotType() == EquipmentSlot.MAINHAND && ((IAlloyTool) stack.getItem()).isAlloyInit(stack))
        {

            IAlloyTool alloyTool = (IAlloyTool) stack.getItem();

            event.addModifier(Attributes.ATTACK_DAMAGE,new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fc1"), "Rankine Damage modifier",
                    alloyTool.getAlloyAttackDamage(stack),
                    AttributeModifier.Operation.ADDITION));
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fc2"), "Rankine Attspeed modifier",
                    alloyTool.getAlloyAttackSpeed(stack),
                    AttributeModifier.Operation.ADDITION));
        }

        if (stack.getItem() instanceof AlloyArmorItem && ((AlloyArmorItem) stack.getItem()).isAlloyInit(stack) && stack.getItem() instanceof ArmorItem && event.getSlotType() == ((ArmorItem)stack.getItem()).getSlot())
        {
            AlloyArmorItem alloyArmor = (AlloyArmorItem) stack.getItem();
            String character = "a";
            switch (event.getSlotType().getFilterFlag()) {
                case 0:
                    character = "a";
                    break;
                case 1:
                    character = "b";
                    break;
                case 2:
                    character = "c";
                    break;
                case 3:
                    character = "d";
                    break;
            }
            String slot1 = character + "0";
            String slot2 = character + "1";
            String slot3 = character + "2";
            int tough = alloyArmor.getAlloyArmorToughness(stack);
            int def = alloyArmor.getAlloyDamageResistance(stack);
            float kr = alloyArmor.getKnockbackResistance(stack);
            event.addModifier(Attributes.ARMOR_TOUGHNESS,new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe2a"+slot1), "Rankine Armor Toughness modifier",
                    tough,
                    AttributeModifier.Operation.ADDITION));
            event.addModifier(Attributes.ARMOR, new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe2a"+slot2), "Rankine Armor modifier",
                    def,
                    AttributeModifier.Operation.ADDITION));
            event.addModifier(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe2a"+slot3), "Rankine Knockback Resist modifier",
                    kr,
                    AttributeModifier.Operation.ADDITION));
        }
        if ((stack.getItem() instanceof HammerItem)) {
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.SWING,stack) > 0 && event.getSlotType() == EquipmentSlot.MAINHAND) {
                event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fc3"), "Rankine Swing modifier",
                        0.5D * EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.SWING,stack),
                        AttributeModifier.Operation.ADDITION));
            }
        }

        if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ANTIQUATED,stack) > 0 && (event.getSlotType() == EquipmentSlot.MAINHAND || event.getSlotType() == EquipmentSlot.OFFHAND)) {
            event.addModifier(Attributes.LUCK, new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fd1"), "Rankine Antiquated modifier",
                    EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ANTIQUATED,stack),
                    AttributeModifier.Operation.ADDITION));
        }
        if (stack.getItem() instanceof SpearItem && event.getSlotType() == EquipmentSlot.MAINHAND) {
            event.addModifier(RankineAttributes.REACH_DISTANCE, new AttributeModifier(RankineAttributes.SPEAR_REACH_MODIFIER,"Weapon modifier", 1, AttributeModifier.Operation.ADDITION));
        }
        if (stack.getItem() instanceof KnifeItem && event.getSlotType() == EquipmentSlot.MAINHAND) {
            event.addModifier(RankineAttributes.REACH_DISTANCE, new AttributeModifier(RankineAttributes.KNIFE_REACH_MODIFIER,"Weapon modifier", -2, AttributeModifier.Operation.ADDITION));
        }
        if (stack.getItem() == RankineItems.TOTEM_OF_PROMISING.get() && event.getSlotType() == EquipmentSlot.OFFHAND) {
            event.addModifier(Attributes.LUCK, new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fd1"), "Rankine Totem modifier",
                    2,
                    AttributeModifier.Operation.ADDITION));
        }
    }
    
    @SubscribeEvent
    public static void onToolUse(BlockEvent.BlockToolModificationEvent event) {
        if (Config.TOOLS.DISABLE_WOODEN_SWORD.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.WOODEN_SWORD) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_WOODEN_AXE.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.WOODEN_AXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_WOODEN_SHOVEL.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.WOODEN_SHOVEL) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_WOODEN_PICKAXE.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.WOODEN_PICKAXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_WOODEN_HOE.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.WOODEN_HOE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_STONE_SWORD.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.STONE_SWORD) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_STONE_AXE.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.STONE_AXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_STONE_SHOVEL.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.STONE_SHOVEL) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_STONE_PICKAXE.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.STONE_PICKAXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_STONE_HOE.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.STONE_HOE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_IRON_SWORD.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.IRON_SWORD) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_IRON_AXE.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.IRON_AXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_IRON_SHOVEL.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.IRON_SHOVEL) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_IRON_PICKAXE.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.IRON_PICKAXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_IRON_HOE.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.IRON_HOE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_GOLDEN_SWORD.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.GOLDEN_SWORD) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_GOLDEN_AXE.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.GOLDEN_AXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_GOLDEN_SHOVEL.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.GOLDEN_SHOVEL) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_GOLDEN_PICKAXE.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.GOLDEN_PICKAXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_GOLDEN_HOE.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.GOLDEN_HOE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_DIAMOND_SWORD.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.DIAMOND_SWORD) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_DIAMOND_AXE.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.DIAMOND_AXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_DIAMOND_SHOVEL.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.DIAMOND_SHOVEL) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_DIAMOND_PICKAXE.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.DIAMOND_PICKAXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_DIAMOND_HOE.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.DIAMOND_HOE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_NETHERITE_SWORD.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.NETHERITE_SWORD) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_NETHERITE_AXE.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.NETHERITE_AXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_NETHERITE_SHOVEL.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.NETHERITE_SHOVEL) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_NETHERITE_PICKAXE.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.NETHERITE_PICKAXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_NETHERITE_HOE.get() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.NETHERITE_HOE) { event.setCanceled(true); }
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        BlockState targetBS = event.getState();
        Item heldItem = event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem();

        if (!(heldItem instanceof AxeItem) && event.getState().is(BlockTags.LOGS) && Config.GENERAL.MANDATORY_AXE.get()) { event.setNewSpeed(0f); }
        if (heldItem instanceof HammerItem) { event.setNewSpeed(0f); }
        if (heldItem instanceof CrowbarItem) { event.setNewSpeed(0f); }

        if (Config.TOOLS.DISABLE_WOODEN_SWORD.get() && heldItem == Items.WOODEN_SWORD) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_WOODEN_AXE.get() && heldItem == Items.WOODEN_AXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_WOODEN_SHOVEL.get() && heldItem == Items.WOODEN_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_WOODEN_PICKAXE.get() && heldItem == Items.WOODEN_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_WOODEN_HOE.get() && heldItem == Items.WOODEN_HOE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_STONE_SWORD.get() && heldItem == Items.STONE_SWORD) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_STONE_AXE.get() && heldItem == Items.STONE_AXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_STONE_SHOVEL.get() && heldItem == Items.STONE_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_STONE_PICKAXE.get() && heldItem == Items.STONE_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_STONE_HOE.get() && heldItem == Items.STONE_HOE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_IRON_SWORD.get() && heldItem == Items.IRON_SWORD) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_IRON_AXE.get() && heldItem == Items.IRON_AXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_IRON_SHOVEL.get() && heldItem == Items.IRON_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_IRON_PICKAXE.get() && heldItem == Items.IRON_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_IRON_HOE.get() && heldItem == Items.IRON_HOE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_GOLDEN_SWORD.get() && heldItem == Items.GOLDEN_SWORD) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_GOLDEN_AXE.get() && heldItem == Items.GOLDEN_AXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_GOLDEN_SHOVEL.get() && heldItem == Items.GOLDEN_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_GOLDEN_PICKAXE.get() && heldItem == Items.GOLDEN_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_GOLDEN_HOE.get() && heldItem == Items.GOLDEN_HOE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_DIAMOND_SWORD.get() && heldItem == Items.DIAMOND_SWORD) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_DIAMOND_AXE.get() && heldItem == Items.DIAMOND_AXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_DIAMOND_SHOVEL.get() && heldItem == Items.DIAMOND_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_DIAMOND_PICKAXE.get() && heldItem == Items.DIAMOND_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_DIAMOND_HOE.get() && heldItem == Items.DIAMOND_HOE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_NETHERITE_SWORD.get() && heldItem == Items.NETHERITE_SWORD) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_NETHERITE_AXE.get() && heldItem == Items.NETHERITE_AXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_NETHERITE_SHOVEL.get() && heldItem == Items.NETHERITE_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_NETHERITE_PICKAXE.get() && heldItem == Items.NETHERITE_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.TOOLS.DISABLE_NETHERITE_HOE.get() && heldItem == Items.NETHERITE_HOE) { event.setNewSpeed(0f); }

        if (event.getPlayer().getOffhandItem().getItem() == RankineItems.TOTEM_OF_HASTENING.get()) {
            event.setNewSpeed(event.getNewSpeed() + 3);
        }

        if (targetBS.is(RankineTags.Blocks.COBBLES) || targetBS.is(RankineBlocks.STUMP.get())) {
            event.setNewSpeed(event.getNewSpeed()/2f);
        }

        if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.QUAKE,event.getPlayer().getMainHandItem()) > 0) {
            int enchant = EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.QUAKE,event.getPlayer().getMainHandItem());
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
        if (event.getSource().getEntity() instanceof Player) {
            Player player = (Player) event.getSource().getEntity();
            if (Config.TOOLS.DISABLE_WOODEN_SWORD.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.WOODEN_SWORD) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_WOODEN_AXE.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.WOODEN_AXE) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_WOODEN_SHOVEL.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.WOODEN_SHOVEL) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_WOODEN_PICKAXE.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.WOODEN_PICKAXE) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_WOODEN_HOE.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.WOODEN_HOE) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_STONE_SWORD.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.STONE_SWORD) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_STONE_AXE.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.STONE_AXE) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_STONE_SHOVEL.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.STONE_SHOVEL) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_STONE_PICKAXE.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.STONE_PICKAXE) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_STONE_HOE.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.STONE_HOE) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_IRON_SWORD.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.IRON_SWORD) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_IRON_AXE.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.IRON_AXE) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_IRON_SHOVEL.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.IRON_SHOVEL) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_IRON_PICKAXE.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.IRON_PICKAXE) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_IRON_HOE.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.IRON_HOE) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_GOLDEN_SWORD.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.GOLDEN_SWORD) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_GOLDEN_AXE.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.GOLDEN_AXE) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_GOLDEN_SHOVEL.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.GOLDEN_SHOVEL) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_GOLDEN_PICKAXE.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.GOLDEN_PICKAXE) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_GOLDEN_HOE.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.GOLDEN_HOE) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_DIAMOND_SWORD.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.DIAMOND_SWORD) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_DIAMOND_AXE.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.DIAMOND_AXE) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_DIAMOND_SHOVEL.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.DIAMOND_SHOVEL) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_DIAMOND_PICKAXE.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.DIAMOND_PICKAXE) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_DIAMOND_HOE.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.DIAMOND_HOE) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_NETHERITE_SWORD.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.NETHERITE_SWORD) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_NETHERITE_AXE.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.NETHERITE_AXE) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_NETHERITE_SHOVEL.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.NETHERITE_SHOVEL) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_NETHERITE_PICKAXE.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.NETHERITE_PICKAXE) { event.setAmount(1f); }
            if (Config.TOOLS.DISABLE_NETHERITE_HOE.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.NETHERITE_HOE) { event.setAmount(1f); }

            if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof HammerItem && !player.level.isClientSide) {
                LivingEntity receiver = event.getEntityLiving();
                if ((receiver instanceof Blaze || receiver instanceof AbstractGolem || receiver instanceof AbstractSkeleton || receiver instanceof Guardian)) {
                    int endLevel = EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDEAVOR,player.getItemInHand(InteractionHand.MAIN_HAND));
                    event.setAmount(event.getAmount() + event.getAmount()/2f + 1.5f*endLevel);
                    if (endLevel > 0 && player.level.getRandom().nextFloat() < (0.15f*endLevel) && receiver.level.getServer() != null && player.level instanceof ServerLevel) {
                        LootTable loot = receiver.level.getServer().getLootTables().get(receiver.getLootTable());
                        LootContext n = new LootContext.Builder((ServerLevel) player.level)
                                .withParameter(LootContextParams.THIS_ENTITY, receiver)
                                .withParameter(LootContextParams.ORIGIN, receiver.position())
                                .withParameter(LootContextParams.DAMAGE_SOURCE, DamageSource.playerAttack(player))
                                .withParameter(LootContextParams.LAST_DAMAGE_PLAYER,player).create(LootContextParamSets.ENTITY);
                        List<ItemStack> s = loot.getRandomItems(n);
                        if (s.size() > 1) {
                            receiver.spawnAtLocation(s.get(receiver.level.getRandom().nextInt(s.size())));
                        } else if (s.size() == 1) {
                            receiver.spawnAtLocation(s.get(0));
                        }
                    }
                }
            }

            if ((player.getMainHandItem().getItem().equals(RankineItems.TOTEM_OF_BLAZING.get()) || player.getOffhandItem().getItem().equals(RankineItems.TOTEM_OF_BLAZING.get())) && !player.level.isClientSide) {
                float damage = event.getAmount() + event.getAmount() * 0.5f;
                event.setAmount(damage);
            }
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOTOXIN,player.getItemInHand(InteractionHand.MAIN_HAND)) >= 1 && !player.level.isClientSide) {
                LivingEntity receiver = event.getEntityLiving();
                if ((receiver instanceof EnderMan || receiver instanceof Shulker || receiver instanceof Endermite || receiver.getCommandSenderWorld().dimension().equals(Level.END))) {
                    event.setAmount(event.getAmount() + 2.5f*EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOTOXIN,player.getItemInHand(InteractionHand.MAIN_HAND)));
                }
            }

            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.CLEANSE,player.getItemInHand(InteractionHand.MAIN_HAND)) >= 1 && !player.level.isClientSide) {
                LivingEntity receiver = event.getEntityLiving();
                float damage = EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.CLEANSE,player.getItemInHand(InteractionHand.MAIN_HAND)) * receiver.getActiveEffects().size();
                event.setAmount(event.getAmount() + damage);
                boolean flag = damage >= 1;
                if (flag) {
                    receiver.removeAllEffects();
                    receiver.playSound(SoundEvents.GRINDSTONE_USE,1.0f, 1.0f);
                }
            }

            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.BACKSTAB,player.getItemInHand(InteractionHand.MAIN_HAND)) >= 1 && !player.level.isClientSide) {
                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

                LivingEntity receiver = event.getEntityLiving();
                if (receiver.getDirection().equals(player.getDirection())) {
                    receiver.playSound(SoundEvents.TRIDENT_HIT,1.0f, 1.0f);
                    float damage = event.getAmount() + event.getAmount() * EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.BACKSTAB,stack);
                    event.setAmount(damage);
                }
            }

            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.LEVERAGE,player.getItemInHand(InteractionHand.MAIN_HAND)) >= 1 && !player.level.isClientSide) {
                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

                LivingEntity receiver = event.getEntityLiving();
                float size = receiver.getDimensions(receiver.getPose()).height * receiver.getDimensions(receiver.getPose()).width;
                int lvl = EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.LEVERAGE,stack);
                //System.out.println(size);
                float mod = -2 + lvl;
                float damage = event.getAmount() + Math.max(0,Math.min(size + mod,1.5f*EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.LEVERAGE,stack)));
                //System.out.println("damageOut: " + damage);
                event.setAmount(damage);
            }

            for (ItemStack armor : event.getEntityLiving().getArmorSlots()) {
                if (armor.getItem() instanceof AlloyArmorItem) {
                    EquipmentSlot slot = armor.getEquipmentSlot() != null ? armor.getEquipmentSlot() : EquipmentSlot.HEAD;
                    int i = ((AlloyArmorItem) armor.getItem()).calcDurabilityLoss(armor,event.getEntity().getCommandSenderWorld(),event.getEntityLiving(),true);
                    armor.hurtAndBreak(i,player, (p_220287_1_) -> {
                        p_220287_1_.broadcastBreakEvent(slot);
                    });
                }
            }
        }

    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onTooltipCheck(ItemTooltipEvent event) {
        if (Config.TOOLS.DISABLE_WOODEN_SWORD.get() && event.getItemStack().getItem() == Items.WOODEN_SWORD) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_WOODEN_AXE.get() && event.getItemStack().getItem() == Items.WOODEN_AXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_WOODEN_SHOVEL.get() && event.getItemStack().getItem() == Items.WOODEN_SHOVEL) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_WOODEN_PICKAXE.get() && event.getItemStack().getItem() == Items.WOODEN_PICKAXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_WOODEN_HOE.get() && event.getItemStack().getItem() == Items.WOODEN_HOE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_STONE_SWORD.get() && event.getItemStack().getItem() == Items.STONE_SWORD) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_STONE_AXE.get() && event.getItemStack().getItem() == Items.STONE_AXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_STONE_SHOVEL.get() && event.getItemStack().getItem() == Items.STONE_SHOVEL) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_STONE_PICKAXE.get() && event.getItemStack().getItem() == Items.STONE_PICKAXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_STONE_HOE.get() && event.getItemStack().getItem() == Items.STONE_HOE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_IRON_SWORD.get() && event.getItemStack().getItem() == Items.IRON_SWORD) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_IRON_AXE.get() && event.getItemStack().getItem() == Items.IRON_AXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_IRON_SHOVEL.get() && event.getItemStack().getItem() == Items.IRON_SHOVEL) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_IRON_PICKAXE.get() && event.getItemStack().getItem() == Items.IRON_PICKAXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_IRON_HOE.get() && event.getItemStack().getItem() == Items.IRON_HOE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_GOLDEN_SWORD.get() && event.getItemStack().getItem() == Items.GOLDEN_SWORD) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_GOLDEN_AXE.get() && event.getItemStack().getItem() == Items.GOLDEN_AXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_GOLDEN_SHOVEL.get() && event.getItemStack().getItem() == Items.GOLDEN_SHOVEL) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_GOLDEN_PICKAXE.get() && event.getItemStack().getItem() == Items.GOLDEN_PICKAXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_GOLDEN_HOE.get() && event.getItemStack().getItem() == Items.GOLDEN_HOE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_DIAMOND_SWORD.get() && event.getItemStack().getItem() == Items.DIAMOND_SWORD) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_DIAMOND_AXE.get() && event.getItemStack().getItem() == Items.DIAMOND_AXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_DIAMOND_SHOVEL.get() && event.getItemStack().getItem() == Items.DIAMOND_SHOVEL) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_DIAMOND_PICKAXE.get() && event.getItemStack().getItem() == Items.DIAMOND_PICKAXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_DIAMOND_HOE.get() && event.getItemStack().getItem() == Items.DIAMOND_HOE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_NETHERITE_SWORD.get() && event.getItemStack().getItem() == Items.NETHERITE_SWORD) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_NETHERITE_AXE.get() && event.getItemStack().getItem() == Items.NETHERITE_AXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_NETHERITE_SHOVEL.get() && event.getItemStack().getItem() == Items.NETHERITE_SHOVEL) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_NETHERITE_PICKAXE.get() && event.getItemStack().getItem() == Items.NETHERITE_PICKAXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_NETHERITE_HOE.get() && event.getItemStack().getItem() == Items.NETHERITE_HOE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
    }

    @SubscribeEvent
    public static void onLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getPlayer().getMainHandItem().getItem() instanceof HammerItem) {
            ItemStack stack = event.getPlayer().getMainHandItem();
            HammerItem hammer = (HammerItem) stack.getItem();
            Level worldIn = event.getWorld();
            BlockPos pos = event.getPos();
            Player player = event.getPlayer();

            if (event.getPlayer().getAttackStrengthScale(0) >= (1f)) {
                event.getPlayer().resetAttackStrengthTicker();
                if (HammerItem.getExcavateModifier(stack) != 0)
                {
                    hammer.getExcavationResult(pos,worldIn,player,stack);
                } else {
                    hammer.mineBlock(stack,worldIn,worldIn.getBlockState(pos),pos, player);
                }
            } else {
                event.getPlayer().resetAttackStrengthTicker();
            }
        } else if (event.getPlayer().getMainHandItem().getItem() instanceof CrowbarItem) {
            ItemStack stack = event.getPlayer().getMainHandItem();
            CrowbarItem crowbar = (CrowbarItem) stack.getItem();
            Level worldIn = event.getWorld();
            BlockPos pos = event.getPos();
            Player player = event.getPlayer();

            if (event.getPlayer().getAttackStrengthScale(0) >= (1f)) {
                event.getPlayer().resetAttackStrengthTicker();
                crowbar.mineBlock(stack,worldIn,worldIn.getBlockState(pos),pos, player);
            } else {
                event.getPlayer().resetAttackStrengthTicker();
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
                Random rand = rand;
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
            Random rand = rand;
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
        Stream<TagKey<Block>> targetStream = ForgeRegistries.BLOCKS.tags().getReverseTag(event.getWorld().getBlockState(event.getPos()).getBlock()).map(IReverseTag::getTagKeys).orElseGet(Stream::of);
        Stream<TagKey<Item>> handStream = ForgeRegistries.ITEMS.tags().getReverseTag(event.getItemStack().getItem()).map(IReverseTag::getTagKeys).orElseGet(Stream::of);
        Set<ResourceLocation> hand = handStream.map(TagKey::location).collect(Collectors.toSet());
        Set<ResourceLocation> target = targetStream.map(TagKey::location).collect(Collectors.toSet());
        BlockState oldBlock = event.getWorld().getBlockState(event.getPos());

        BlockState newBlock = null;
        if (Config.GENERAL.COLOR_WORLD.get() && !event.getWorld().isClientSide()) {
            if (hand.contains(new ResourceLocation("forge:dyes/red"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.RED_LED.get().defaultBlockState().setValue(LEDBlock.LIT, oldBlock.getValue(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.RED_CONCRETE.defaultBlockState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.RED_WOOL.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.RED_MINERAL_WOOL.get().defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.RED_STAINED_GLASS.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.RED_STAINED_GLASS_PANE.defaultBlockState().setValue(IronBarsBlock.NORTH, oldBlock.getValue(IronBarsBlock.NORTH)).setValue(IronBarsBlock.WEST, oldBlock.getValue(IronBarsBlock.WEST)).setValue(IronBarsBlock.EAST, oldBlock.getValue(IronBarsBlock.EAST)).setValue(IronBarsBlock.SOUTH, oldBlock.getValue(IronBarsBlock.SOUTH)).setValue(IronBarsBlock.WATERLOGGED, oldBlock.getValue(IronBarsBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.RED_CONCRETE_POWDER.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.RED_TERRACOTTA.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.RED_GLAZED_TERRACOTTA.defaultBlockState().setValue(GlazedTerracottaBlock.FACING, oldBlock.getValue(GlazedTerracottaBlock.FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/orange"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.ORANGE_LED.get().defaultBlockState().setValue(LEDBlock.LIT, oldBlock.getValue(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.ORANGE_CONCRETE.defaultBlockState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.ORANGE_WOOL.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.ORANGE_MINERAL_WOOL.get().defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.ORANGE_STAINED_GLASS.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.ORANGE_STAINED_GLASS_PANE.defaultBlockState().setValue(IronBarsBlock.NORTH, oldBlock.getValue(IronBarsBlock.NORTH)).setValue(IronBarsBlock.WEST, oldBlock.getValue(IronBarsBlock.WEST)).setValue(IronBarsBlock.EAST, oldBlock.getValue(IronBarsBlock.EAST)).setValue(IronBarsBlock.SOUTH, oldBlock.getValue(IronBarsBlock.SOUTH)).setValue(IronBarsBlock.WATERLOGGED, oldBlock.getValue(IronBarsBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.ORANGE_CONCRETE_POWDER.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.ORANGE_TERRACOTTA.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.ORANGE_GLAZED_TERRACOTTA.defaultBlockState().setValue(GlazedTerracottaBlock.FACING, oldBlock.getValue(GlazedTerracottaBlock.FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/yellow"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.YELLOW_LED.get().defaultBlockState().setValue(LEDBlock.LIT, oldBlock.getValue(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.YELLOW_CONCRETE.defaultBlockState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.YELLOW_WOOL.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.YELLOW_MINERAL_WOOL.get().defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.YELLOW_STAINED_GLASS.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.YELLOW_STAINED_GLASS_PANE.defaultBlockState().setValue(IronBarsBlock.NORTH, oldBlock.getValue(IronBarsBlock.NORTH)).setValue(IronBarsBlock.WEST, oldBlock.getValue(IronBarsBlock.WEST)).setValue(IronBarsBlock.EAST, oldBlock.getValue(IronBarsBlock.EAST)).setValue(IronBarsBlock.SOUTH, oldBlock.getValue(IronBarsBlock.SOUTH)).setValue(IronBarsBlock.WATERLOGGED, oldBlock.getValue(IronBarsBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.YELLOW_CONCRETE_POWDER.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.YELLOW_TERRACOTTA.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.YELLOW_GLAZED_TERRACOTTA.defaultBlockState().setValue(GlazedTerracottaBlock.FACING, oldBlock.getValue(GlazedTerracottaBlock.FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/lime"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.LIME_LED.get().defaultBlockState().setValue(LEDBlock.LIT, oldBlock.getValue(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.LIME_CONCRETE.defaultBlockState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.LIME_WOOL.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.LIME_MINERAL_WOOL.get().defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.LIME_STAINED_GLASS.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.LIME_STAINED_GLASS_PANE.defaultBlockState().setValue(IronBarsBlock.NORTH, oldBlock.getValue(IronBarsBlock.NORTH)).setValue(IronBarsBlock.WEST, oldBlock.getValue(IronBarsBlock.WEST)).setValue(IronBarsBlock.EAST, oldBlock.getValue(IronBarsBlock.EAST)).setValue(IronBarsBlock.SOUTH, oldBlock.getValue(IronBarsBlock.SOUTH)).setValue(IronBarsBlock.WATERLOGGED, oldBlock.getValue(IronBarsBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.LIME_CONCRETE_POWDER.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.LIME_TERRACOTTA.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.LIME_GLAZED_TERRACOTTA.defaultBlockState().setValue(GlazedTerracottaBlock.FACING, oldBlock.getValue(GlazedTerracottaBlock.FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/green"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.GREEN_LED.get().defaultBlockState().setValue(LEDBlock.LIT, oldBlock.getValue(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.GREEN_CONCRETE.defaultBlockState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.GREEN_WOOL.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.GREEN_MINERAL_WOOL.get().defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.GREEN_STAINED_GLASS.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.GREEN_STAINED_GLASS_PANE.defaultBlockState().setValue(IronBarsBlock.NORTH, oldBlock.getValue(IronBarsBlock.NORTH)).setValue(IronBarsBlock.WEST, oldBlock.getValue(IronBarsBlock.WEST)).setValue(IronBarsBlock.EAST, oldBlock.getValue(IronBarsBlock.EAST)).setValue(IronBarsBlock.SOUTH, oldBlock.getValue(IronBarsBlock.SOUTH)).setValue(IronBarsBlock.WATERLOGGED, oldBlock.getValue(IronBarsBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.GREEN_CONCRETE_POWDER.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.GREEN_TERRACOTTA.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.GREEN_GLAZED_TERRACOTTA.defaultBlockState().setValue(GlazedTerracottaBlock.FACING, oldBlock.getValue(GlazedTerracottaBlock.FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/cyan"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.CYAN_LED.get().defaultBlockState().setValue(LEDBlock.LIT, oldBlock.getValue(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.CYAN_CONCRETE.defaultBlockState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.CYAN_WOOL.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.CYAN_MINERAL_WOOL.get().defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.CYAN_STAINED_GLASS.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.CYAN_STAINED_GLASS_PANE.defaultBlockState().setValue(IronBarsBlock.NORTH, oldBlock.getValue(IronBarsBlock.NORTH)).setValue(IronBarsBlock.WEST, oldBlock.getValue(IronBarsBlock.WEST)).setValue(IronBarsBlock.EAST, oldBlock.getValue(IronBarsBlock.EAST)).setValue(IronBarsBlock.SOUTH, oldBlock.getValue(IronBarsBlock.SOUTH)).setValue(IronBarsBlock.WATERLOGGED, oldBlock.getValue(IronBarsBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.CYAN_CONCRETE_POWDER.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.CYAN_TERRACOTTA.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.CYAN_GLAZED_TERRACOTTA.defaultBlockState().setValue(GlazedTerracottaBlock.FACING, oldBlock.getValue(GlazedTerracottaBlock.FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/blue"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.BLUE_LED.get().defaultBlockState().setValue(LEDBlock.LIT, oldBlock.getValue(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.BLUE_CONCRETE.defaultBlockState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.BLUE_WOOL.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.BLUE_MINERAL_WOOL.get().defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.BLUE_STAINED_GLASS.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.BLUE_STAINED_GLASS_PANE.defaultBlockState().setValue(IronBarsBlock.NORTH, oldBlock.getValue(IronBarsBlock.NORTH)).setValue(IronBarsBlock.WEST, oldBlock.getValue(IronBarsBlock.WEST)).setValue(IronBarsBlock.EAST, oldBlock.getValue(IronBarsBlock.EAST)).setValue(IronBarsBlock.SOUTH, oldBlock.getValue(IronBarsBlock.SOUTH)).setValue(IronBarsBlock.WATERLOGGED, oldBlock.getValue(IronBarsBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.BLUE_CONCRETE_POWDER.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.BLUE_TERRACOTTA.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.BLUE_GLAZED_TERRACOTTA.defaultBlockState().setValue(GlazedTerracottaBlock.FACING, oldBlock.getValue(GlazedTerracottaBlock.FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/light_blue"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.LIGHT_BLUE_LED.get().defaultBlockState().setValue(LEDBlock.LIT, oldBlock.getValue(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.LIGHT_BLUE_CONCRETE.defaultBlockState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.LIGHT_BLUE_WOOL.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.LIGHT_BLUE_MINERAL_WOOL.get().defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.LIGHT_BLUE_STAINED_GLASS.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.LIGHT_BLUE_STAINED_GLASS_PANE.defaultBlockState().setValue(IronBarsBlock.NORTH, oldBlock.getValue(IronBarsBlock.NORTH)).setValue(IronBarsBlock.WEST, oldBlock.getValue(IronBarsBlock.WEST)).setValue(IronBarsBlock.EAST, oldBlock.getValue(IronBarsBlock.EAST)).setValue(IronBarsBlock.SOUTH, oldBlock.getValue(IronBarsBlock.SOUTH)).setValue(IronBarsBlock.WATERLOGGED, oldBlock.getValue(IronBarsBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.LIGHT_BLUE_CONCRETE_POWDER.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.LIGHT_BLUE_TERRACOTTA.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA.defaultBlockState().setValue(GlazedTerracottaBlock.FACING, oldBlock.getValue(GlazedTerracottaBlock.FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/magenta"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.MAGENTA_LED.get().defaultBlockState().setValue(LEDBlock.LIT, oldBlock.getValue(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.MAGENTA_CONCRETE.defaultBlockState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.MAGENTA_WOOL.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.MAGENTA_MINERAL_WOOL.get().defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.MAGENTA_STAINED_GLASS.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.MAGENTA_STAINED_GLASS_PANE.defaultBlockState().setValue(IronBarsBlock.NORTH, oldBlock.getValue(IronBarsBlock.NORTH)).setValue(IronBarsBlock.WEST, oldBlock.getValue(IronBarsBlock.WEST)).setValue(IronBarsBlock.EAST, oldBlock.getValue(IronBarsBlock.EAST)).setValue(IronBarsBlock.SOUTH, oldBlock.getValue(IronBarsBlock.SOUTH)).setValue(IronBarsBlock.WATERLOGGED, oldBlock.getValue(IronBarsBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.MAGENTA_CONCRETE_POWDER.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.MAGENTA_TERRACOTTA.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.MAGENTA_GLAZED_TERRACOTTA.defaultBlockState().setValue(GlazedTerracottaBlock.FACING, oldBlock.getValue(GlazedTerracottaBlock.FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/purple"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.PURPLE_LED.get().defaultBlockState().setValue(LEDBlock.LIT, oldBlock.getValue(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.PURPLE_CONCRETE.defaultBlockState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.PURPLE_WOOL.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.PURPLE_MINERAL_WOOL.get().defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.PURPLE_STAINED_GLASS.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.PURPLE_STAINED_GLASS_PANE.defaultBlockState().setValue(IronBarsBlock.NORTH, oldBlock.getValue(IronBarsBlock.NORTH)).setValue(IronBarsBlock.WEST, oldBlock.getValue(IronBarsBlock.WEST)).setValue(IronBarsBlock.EAST, oldBlock.getValue(IronBarsBlock.EAST)).setValue(IronBarsBlock.SOUTH, oldBlock.getValue(IronBarsBlock.SOUTH)).setValue(IronBarsBlock.WATERLOGGED, oldBlock.getValue(IronBarsBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.PURPLE_CONCRETE_POWDER.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.PURPLE_TERRACOTTA.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.PURPLE_GLAZED_TERRACOTTA.defaultBlockState().setValue(GlazedTerracottaBlock.FACING, oldBlock.getValue(GlazedTerracottaBlock.FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/pink"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.PINK_LED.get().defaultBlockState().setValue(LEDBlock.LIT, oldBlock.getValue(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.PINK_CONCRETE.defaultBlockState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.PINK_WOOL.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.PINK_MINERAL_WOOL.get().defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.PINK_STAINED_GLASS.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.PINK_STAINED_GLASS_PANE.defaultBlockState().setValue(IronBarsBlock.NORTH, oldBlock.getValue(IronBarsBlock.NORTH)).setValue(IronBarsBlock.WEST, oldBlock.getValue(IronBarsBlock.WEST)).setValue(IronBarsBlock.EAST, oldBlock.getValue(IronBarsBlock.EAST)).setValue(IronBarsBlock.SOUTH, oldBlock.getValue(IronBarsBlock.SOUTH)).setValue(IronBarsBlock.WATERLOGGED, oldBlock.getValue(IronBarsBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.PINK_CONCRETE_POWDER.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.PINK_TERRACOTTA.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.PINK_GLAZED_TERRACOTTA.defaultBlockState().setValue(GlazedTerracottaBlock.FACING, oldBlock.getValue(GlazedTerracottaBlock.FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/brown"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.BROWN_LED.get().defaultBlockState().setValue(LEDBlock.LIT, oldBlock.getValue(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.BROWN_CONCRETE.defaultBlockState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.BROWN_WOOL.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.BROWN_MINERAL_WOOL.get().defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.BROWN_STAINED_GLASS.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:eglass_panes"))) {
                    newBlock = Blocks.BROWN_STAINED_GLASS_PANE.defaultBlockState().setValue(IronBarsBlock.NORTH, oldBlock.getValue(IronBarsBlock.NORTH)).setValue(IronBarsBlock.WEST, oldBlock.getValue(IronBarsBlock.WEST)).setValue(IronBarsBlock.EAST, oldBlock.getValue(IronBarsBlock.EAST)).setValue(IronBarsBlock.SOUTH, oldBlock.getValue(IronBarsBlock.SOUTH)).setValue(IronBarsBlock.WATERLOGGED, oldBlock.getValue(IronBarsBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.BROWN_CONCRETE_POWDER.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.BROWN_TERRACOTTA.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.BROWN_GLAZED_TERRACOTTA.defaultBlockState().setValue(GlazedTerracottaBlock.FACING, oldBlock.getValue(GlazedTerracottaBlock.FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/black"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.BLACK_LED.get().defaultBlockState().setValue(LEDBlock.LIT, oldBlock.getValue(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.BLACK_CONCRETE.defaultBlockState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.BLACK_WOOL.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.BLACK_MINERAL_WOOL.get().defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.BLACK_STAINED_GLASS.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.BLACK_STAINED_GLASS_PANE.defaultBlockState().setValue(IronBarsBlock.NORTH, oldBlock.getValue(IronBarsBlock.NORTH)).setValue(IronBarsBlock.WEST, oldBlock.getValue(IronBarsBlock.WEST)).setValue(IronBarsBlock.EAST, oldBlock.getValue(IronBarsBlock.EAST)).setValue(IronBarsBlock.SOUTH, oldBlock.getValue(IronBarsBlock.SOUTH)).setValue(IronBarsBlock.WATERLOGGED, oldBlock.getValue(IronBarsBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.BLACK_CONCRETE_POWDER.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.BLACK_TERRACOTTA.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.BLACK_GLAZED_TERRACOTTA.defaultBlockState().setValue(GlazedTerracottaBlock.FACING, oldBlock.getValue(GlazedTerracottaBlock.FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/white"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.WHITE_LED.get().defaultBlockState().setValue(LEDBlock.LIT, oldBlock.getValue(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.WHITE_CONCRETE.defaultBlockState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.WHITE_WOOL.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.WHITE_MINERAL_WOOL.get().defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.WHITE_STAINED_GLASS.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.WHITE_STAINED_GLASS_PANE.defaultBlockState().setValue(IronBarsBlock.NORTH, oldBlock.getValue(IronBarsBlock.NORTH)).setValue(IronBarsBlock.WEST, oldBlock.getValue(IronBarsBlock.WEST)).setValue(IronBarsBlock.EAST, oldBlock.getValue(IronBarsBlock.EAST)).setValue(IronBarsBlock.SOUTH, oldBlock.getValue(IronBarsBlock.SOUTH)).setValue(IronBarsBlock.WATERLOGGED, oldBlock.getValue(IronBarsBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.WHITE_CONCRETE_POWDER.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.WHITE_TERRACOTTA.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.WHITE_GLAZED_TERRACOTTA.defaultBlockState().setValue(GlazedTerracottaBlock.FACING, oldBlock.getValue(GlazedTerracottaBlock.FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/gray"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.GRAY_LED.get().defaultBlockState().setValue(LEDBlock.LIT, oldBlock.getValue(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.GRAY_CONCRETE.defaultBlockState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.GRAY_WOOL.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.GRAY_MINERAL_WOOL.get().defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.GRAY_STAINED_GLASS.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.GRAY_STAINED_GLASS_PANE.defaultBlockState().setValue(IronBarsBlock.NORTH, oldBlock.getValue(IronBarsBlock.NORTH)).setValue(IronBarsBlock.WEST, oldBlock.getValue(IronBarsBlock.WEST)).setValue(IronBarsBlock.EAST, oldBlock.getValue(IronBarsBlock.EAST)).setValue(IronBarsBlock.SOUTH, oldBlock.getValue(IronBarsBlock.SOUTH)).setValue(IronBarsBlock.WATERLOGGED, oldBlock.getValue(IronBarsBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.GRAY_CONCRETE_POWDER.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.GRAY_TERRACOTTA.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.GRAY_GLAZED_TERRACOTTA.defaultBlockState().setValue(GlazedTerracottaBlock.FACING, oldBlock.getValue(GlazedTerracottaBlock.FACING));
                }
            } else if (hand.contains(new ResourceLocation("forge:dyes/light_gray"))) {
                if (target.contains(new ResourceLocation("rankine:leds"))) {
                    newBlock = RankineBlocks.LIGHT_GRAY_LED.get().defaultBlockState().setValue(LEDBlock.LIT, oldBlock.getValue(LEDBlock.LIT));
                } else if (target.contains(new ResourceLocation("forge:concrete"))) {
                    newBlock = Blocks.LIGHT_GRAY_CONCRETE.defaultBlockState();
                } else if (target.contains(new ResourceLocation("minecraft:wool"))) {
                    newBlock = Blocks.LIGHT_GRAY_WOOL.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:mineral_wool"))) {
                    newBlock = RankineBlocks.LIGHT_GRAY_MINERAL_WOOL.get().defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass"))) {
                    newBlock = Blocks.LIGHT_GRAY_STAINED_GLASS.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glass_panes"))) {
                    newBlock = Blocks.LIGHT_GRAY_STAINED_GLASS_PANE.defaultBlockState().setValue(IronBarsBlock.NORTH, oldBlock.getValue(IronBarsBlock.NORTH)).setValue(IronBarsBlock.WEST, oldBlock.getValue(IronBarsBlock.WEST)).setValue(IronBarsBlock.EAST, oldBlock.getValue(IronBarsBlock.EAST)).setValue(IronBarsBlock.SOUTH, oldBlock.getValue(IronBarsBlock.SOUTH)).setValue(IronBarsBlock.WATERLOGGED, oldBlock.getValue(IronBarsBlock.WATERLOGGED));
                } else if (target.contains(new ResourceLocation("forge:concrete_powder"))) {
                    newBlock = Blocks.LIGHT_GRAY_CONCRETE_POWDER.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:terracotta"))) {
                    newBlock = Blocks.LIGHT_GRAY_TERRACOTTA.defaultBlockState();
                } else if (target.contains(new ResourceLocation("forge:glazed_terracotta"))) {
                    newBlock = Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA.defaultBlockState().setValue(GlazedTerracottaBlock.FACING, oldBlock.getValue(GlazedTerracottaBlock.FACING));
                }
            }
            if (newBlock != null) {
                event.getWorld().setBlock(event.getPos(), newBlock, 3);
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
            Level world = event.getWorld();
            Random rand = world.random;
            Player player = event.getPlayer();
            BlockPos blockpos1 = event.getPos().relative(event.getFace());
            if (player.getMainHandItem().getItem() == Items.FLINT && player.getOffhandItem().getItem() == Items.FLINT) {
                if (world.getBlockState(pos) == RankineBlocks.CHARCOAL_PIT.get().defaultBlockState().setValue(CharcoalPitBlock.LIT, false) && !world.isClientSide) {
                    for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(0, -Config.MACHINES.CHARCOAL_PIT_HEIGHT.get(), 0), pos.offset(0, Config.MACHINES.CHARCOAL_PIT_HEIGHT.get(), 0))) {
                        if (world.getBlockState(blockpos).getBlock() == RankineBlocks.CHARCOAL_PIT.get()) {
                            world.setBlock(blockpos, world.getBlockState(blockpos).setValue(BlockStateProperties.LIT, Boolean.TRUE), 3);
                        }
                    }
                    player.swing(InteractionHand.MAIN_HAND);
                    if (rand.nextFloat() < Config.GENERAL.FLINT_FIRE_CHANCE.get()) {
                        player.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
                        player.getItemInHand(InteractionHand.OFF_HAND).shrink(1);
                    }
                    world.playSound(player, blockpos1, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
                } else if (world.getBlockState(pos) == RankineBlocks.BEEHIVE_OVEN_PIT.get().defaultBlockState().setValue(BlockStateProperties.LIT, false)) {
                    if (!world.isClientSide()) {
                        world.setBlock(pos, world.getBlockState(pos).setValue(BlockStateProperties.LIT, Boolean.TRUE), 3);
                        player.swing(InteractionHand.MAIN_HAND);
                        if (rand.nextFloat() < Config.GENERAL.FLINT_FIRE_CHANCE.get()) {
                            player.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
                            player.getItemInHand(InteractionHand.OFF_HAND).shrink(1);
                        }
                    }
                    world.playSound(player, blockpos1, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
                } else if (BaseFireBlock.canBePlacedAt(world, blockpos1, event.getFace()) && !world.isClientSide && !(world.getBlockState(pos).getBlock() instanceof BeehiveOvenPitBlock) &&
                    world.getBlockState(pos) != RankineBlocks.CHARCOAL_PIT.get().defaultBlockState().setValue(CharcoalPitBlock.LIT, true)) {
                    world.setBlock(blockpos1, BaseFireBlock.getState(world, blockpos1), 11);
                    player.swing(InteractionHand.MAIN_HAND);
                    if (rand.nextFloat() < Config.GENERAL.FLINT_FIRE_CHANCE.get()) {
                        player.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
                        player.getItemInHand(InteractionHand.OFF_HAND).shrink(1);
                    }
                }
                world.playSound(player, blockpos1, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        Level world = event.getWorld();
        Direction direction = event.getFace();
        InteractionHand hand = event.getHand();
        BlockPos pos = event.getPos();
        BlockState state = world.getBlockState(pos);
        Player player = event.getPlayer();

        if (ForgeRegistries.ITEMS.tags().getTag(RankineTags.Items.KNIVES).contains(stack.getItem()) && direction != null && hand == InteractionHand.MAIN_HAND) {
            Block target = state.getBlock();
            if ((ForgeRegistries.BLOCKS.tags().getTag(RankineTags.Blocks.GRASS_BLOCKS).contains(target)) && direction.equals(Direction.UP)) {
                world.playSound(player, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                if (RankineLists.GRASS_BLOCKS.contains(target)) {
                    world.setBlock(pos, RankineLists.SOIL_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(target)).defaultBlockState(), 3);
                } else {
                    world.setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);
                }
                player.swing(hand);
                if (!world.isClientSide && world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !world.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                    Block.popResource(world, pos.above(), new ItemStack(Items.GRASS, 1));
                    if (world.getRandom().nextFloat() < 0.2) Block.popResource(world, pos.above(), new ItemStack(RankineItems.GRASS_SEEDS.get(), 1));
                }
                if (!world.isClientSide) {
                    player.getItemInHand(hand).hurtAndBreak(1, player, (p_220038_0_) -> {
                        p_220038_0_.broadcastBreakEvent(hand);
                    });
                }
            } else if (target == RankineBlocks.AGED_CHEESE.get()) {
                player.swing(hand);
                if (state.getValue(CakeBlock.BITES) < 6) {
                    world.setBlockAndUpdate(pos, state.setValue(CakeBlock.BITES, state.getValue(CakeBlock.BITES) + 1));
                    player.addItem(new ItemStack(RankineItems.CHEESE.get(), 1));
                } else {
                    player.addItem(new ItemStack(RankineItems.CHEESE.get(), 1));
                    world.removeBlock(pos, false);
                }
                player.getItemInHand(hand).hurtAndBreak(1, player, (p_220040_1_) -> {
                    p_220040_1_.broadcastBreakEvent(hand);
                });
                world.playSound(player, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 0.7F, world.getRandom().nextFloat() * 0.4F + 0.5F);

            } else if (state.getBlock() == Blocks.CAKE) {
                player.swing(hand);
                if (state.getValue(CakeBlock.BITES) < 6) {
                    world.setBlockAndUpdate(pos, state.setValue(CakeBlock.BITES, state.getValue(CakeBlock.BITES) + 1));
                    player.addItem(new ItemStack(RankineItems.CAKE_SLICE.get(), 1));
                } else {
                    player.addItem(new ItemStack(RankineItems.CAKE_SLICE.get(), 1));
                    world.removeBlock(pos, false);
                }
                player.getMainHandItem().hurtAndBreak(1, player, (p_220040_1_) -> {
                    p_220040_1_.broadcastBreakEvent(player.swingingArm);
                });
                world.playSound(player, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 0.7F, world.getRandom().nextFloat() * 0.4F + 0.5F);

            }
        } else if (ForgeRegistries.ITEMS.tags().getTag(RankineTags.Items.SLUICING_TOOLS).contains(stack.getItem()) && direction != null && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            SluicingRecipe recipe = world.getRecipeManager().getRecipeFor(RankineRecipeTypes.SLUICING, new SimpleContainer(new ItemStack(world.getBlockState(pos).getBlock()), stack), world).orElse(null);
            if (recipe != null) {
                float r = world.getRandom().nextFloat();
                world.playSound(player, pos, SoundEvents.SAND_FALL, SoundSource.BLOCKS, 1.0F, r * 0.4F + 0.8F);
                world.playSound(player, pos, SoundEvents.SAND_FALL, SoundSource.BLOCKS, 1.0F, r * 0.6F + 0.8F);
                world.playSound(player, pos, SoundEvents.SAND_FALL, SoundSource.BLOCKS, 1.0F, r * 0.2F + 0.8F);
                ItemStack out = recipe.getSluicingResult(world);
                world.removeBlock(pos, false);
                player.swing(hand,true);
                if (!world.isClientSide) {
                    Block.popResource(world,pos,out);
                    if (Config.GENERAL.SLUICING_COOLDOWN.get()) {
                        player.getCooldowns().addCooldown(stack.getItem(), recipe.getCooldownTicks());
                    }

                    if (stack.getItem().canBeDepleted()) {
                        player.getMainHandItem().hurtAndBreak(1, player, (p_220038_0_) -> {
                            p_220038_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
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
        Level worldIn = event.getWorld();
        BlockPos pos = event.getPos();
        Player player = event.getPlayer();
        BlockState targetBS = worldIn.getBlockState(pos);
        Block b = targetBS.getBlock();

        if(item instanceof AxeItem) {
            //Extra items from stripping recipe
            StrippingRecipe irecipe = worldIn.getRecipeManager().getRecipeFor(RankineRecipeTypes.STRIPPING, new SimpleContainer(new ItemStack(b)), worldIn).orElse(null);
            if (irecipe != null) {
                if (worldIn.getRandom().nextFloat() < irecipe.getChance()) {
                    Block.popResource(event.getWorld(), event.getPos(), irecipe.getResult());
                }
            }
            if (ForgeRegistries.BLOCKS.tags().getTag(BlockTags.LOGS).contains(b) && !b.getRegistryName().toString().contains("stripped") && Config.GENERAL.STRIPPABLES_STICKS.get() && worldIn.getRandom().nextFloat() < 0.3) {
                Block.popResource(event.getWorld(), event.getPos(), new ItemStack(Items.STICK, 1));
            }

            if(VanillaIntegration.stripping_map.get(b) != null) {
                if(b instanceof RotatedPillarBlock) {
                    Direction.Axis axis = targetBS.getValue(RotatedPillarBlock.AXIS);
                    worldIn.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    worldIn.setBlock(pos, VanillaIntegration.stripping_map.get(b).defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis), 2);
                    stack.hurtAndBreak(1, player, (entity) -> {
                        entity.broadcastBreakEvent(event.getHand());
                    });
                    player.swing(event.getHand());
                    event.setResult(Event.Result.ALLOW);
                }
            }
        } else if (item instanceof ShovelItem) {
            if (VanillaIntegration.pathBlocks_map.get(b) != null) {
                worldIn.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                worldIn.setBlock(pos, VanillaIntegration.pathBlocks_map.get(b).defaultBlockState(), 2);
                stack.hurtAndBreak(1, player, (entity) -> {
                    entity.broadcastBreakEvent(event.getHand());
                });
                player.swing(event.getHand());
                event.setResult(Event.Result.ALLOW);
            }
        } else if (item instanceof HoeItem) {
            if (VanillaIntegration.hoeables_map.get(b) != null) {
                worldIn.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                worldIn.setBlock(pos, RankineBlocks.TILLED_SOIL.get().defaultBlockState().setValue(TilledSoilBlock.MOISTURE, 0).setValue(TilledSoilBlock.SOIL_TYPE, VanillaIntegration.hoeables_map.get(b)), 3);
                stack.hurtAndBreak(1, player, (entity) -> {
                    entity.broadcastBreakEvent(event.getHand());
                });
                player.swing(event.getHand());
                event.setResult(Event.Result.ALLOW);
            } else if (b instanceof DoubleCropsBlock && item instanceof AlloyHoeItem) {
                if (targetBS.hasProperty(CropBlock.AGE) && targetBS.getValue(DoubleCropsBlock.AGE) == 7) {
                    if (targetBS.getValue(DoubleCropsBlock.SECTION) == DoubleBlockHalf.LOWER) {
                        worldIn.destroyBlock(pos,true);
                        if (targetBS.getBlock().canSurvive(b.defaultBlockState().setValue(CropBlock.AGE, 0),worldIn,pos)) {
                            worldIn.setBlockAndUpdate(pos, b.defaultBlockState().setValue(CropBlock.AGE, 0));
                        }
                    } else if (targetBS.getValue(DoubleCropsBlock.SECTION) == DoubleBlockHalf.UPPER) {
                        worldIn.destroyBlock(pos.below(),true);
                        if (targetBS.getBlock().canSurvive(b.defaultBlockState().setValue(CropBlock.AGE, 0),worldIn,pos.below())) {
                            worldIn.setBlockAndUpdate(pos.below(), b.defaultBlockState().setValue(CropBlock.AGE, 0));
                        }
                    }
                    if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOSPORE,stack) > 0 && worldIn.getRandom().nextFloat() < (0.2f + Math.min(player.getLuck()/20f,0.3))) {
                        Optional<BlockPos> bp = BlockPos.findClosestMatch(pos,3,3,blockPos ->!blockPos.equals(pos) && worldIn.isEmptyBlock(blockPos) && targetBS.getBlock().canSurvive(b.defaultBlockState().setValue(CropBlock.AGE, 0),worldIn,blockPos));
                        bp.ifPresent(blockPos -> worldIn.setBlockAndUpdate(blockPos, b.defaultBlockState().setValue(CropBlock.AGE, CropBlock.AGE.getPossibleValues().stream().max(Integer::compareTo).orElse(0))));
                    }
                }
            } else if (b instanceof TripleCropsBlock && item instanceof AlloyHoeItem) {
                if (targetBS.hasProperty(CropBlock.AGE) && targetBS.getValue(DoubleCropsBlock.AGE) == 7) {
                    if (targetBS.getValue(TripleCropsBlock.SECTION) == TripleBlockSection.BOTTOM) {
                        worldIn.destroyBlock(pos,true);
                        if (targetBS.getBlock().canSurvive(b.defaultBlockState().setValue(CropBlock.AGE, 0),worldIn,pos)) {
                            worldIn.setBlockAndUpdate(pos, b.defaultBlockState().setValue(CropBlock.AGE, 0));
                        }
                    } else if (targetBS.getValue(TripleCropsBlock.SECTION) == TripleBlockSection.MIDDLE) {
                        worldIn.destroyBlock(pos.below(),true);
                        if (targetBS.getBlock().canSurvive(b.defaultBlockState().setValue(CropBlock.AGE, 0),worldIn,pos.below())) {
                            worldIn.setBlockAndUpdate(pos.below(), b.defaultBlockState().setValue(CropBlock.AGE, 0));
                        }
                    } else if (targetBS.getValue(TripleCropsBlock.SECTION) == TripleBlockSection.TOP) {
                        worldIn.destroyBlock(pos.below(2),true);
                        if (targetBS.getBlock().canSurvive(b.defaultBlockState().setValue(CropBlock.AGE, 0),worldIn,pos.below(2))) {
                            worldIn.setBlockAndUpdate(pos.below(2), b.defaultBlockState().setValue(CropBlock.AGE, 0));
                        }
                    }
                    if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOSPORE,stack) > 0 && worldIn.getRandom().nextFloat() < (0.2f + Math.min(player.getLuck()/20f,0.3))) {
                        Optional<BlockPos> bp = BlockPos.findClosestMatch(pos,3,3,blockPos -> !blockPos.equals(pos) && worldIn.isEmptyBlock(blockPos) && targetBS.getBlock().canSurvive(b.defaultBlockState().setValue(CropBlock.AGE, 0),worldIn,blockPos));
                        bp.ifPresent(blockPos -> worldIn.setBlockAndUpdate(blockPos, b.defaultBlockState().setValue(CropBlock.AGE, CropBlock.AGE.getPossibleValues().stream().max(Integer::compareTo).orElse(0))));
                    }
                }
            } else if (b instanceof CropBlock && item instanceof AlloyHoeItem) {

                if (targetBS.hasProperty(CropBlock.AGE) && targetBS.getValue(CropBlock.AGE) == 7) {
                    worldIn.destroyBlock(pos,true);
                    if (targetBS.getBlock().canSurvive(b.defaultBlockState().setValue(CropBlock.AGE, 0),worldIn,pos)) {
                        worldIn.setBlockAndUpdate(pos,b.defaultBlockState().setValue(CropBlock.AGE, 0));
                    }
                    if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOSPORE,stack) > 0 && worldIn.getRandom().nextFloat() < (0.2f + Math.min(player.getLuck()/20f,0.3))) {
                        Optional<BlockPos> bp = BlockPos.findClosestMatch(pos,3,3,blockPos -> !blockPos.equals(pos) && worldIn.isEmptyBlock(blockPos) && targetBS.getBlock().canSurvive(b.defaultBlockState().setValue(CropBlock.AGE, 0),worldIn,blockPos));
                        bp.ifPresent(blockPos -> worldIn.setBlockAndUpdate(blockPos, b.defaultBlockState().setValue(CropBlock.AGE, CropBlock.AGE.getPossibleValues().stream().max(Integer::compareTo).orElse(0))));
                    }
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
        if (Config.GENERAL.DISABLE_WATER.get() && event.getPos().getY() > WorldgenUtils.waterTableHeight((Level) event.getWorld(), event.getPos())) {
            event.setResult(Event.Result.DENY);
        }

    }

    @SubscribeEvent
    public static void treeChop(PlayerEvent.BreakSpeed event) {
        BlockPos pos = event.getPos();
        Player player = event.getPlayer();
        Level worldIn = player.level;
        BlockState state = event.getState();

        if (Config.GENERAL.TREE_CHOPPING.get() && !player.isCreative() && !player.isShiftKeyDown() && player.getMainHandItem().is(RankineTags.Items.TREE_CHOPPERS) && state.is(RankineTags.Blocks.TREE_LOGS)) {
            Set<BlockPos> checkedBlocks = new HashSet<>();
            Stack<BlockPos> toCheck = new Stack<>();
            boolean alive = false;

            toCheck.add(pos);
            while (!toCheck.isEmpty()) {
                BlockPos cp = toCheck.pop();
                if (!checkedBlocks.contains(cp)) {
                    checkedBlocks.add(cp);
                    for (BlockPos b : BlockPos.betweenClosed(cp.offset(-1,-1,-1), cp.offset(1,1,1))) {
                        BlockState target = worldIn.getBlockState(b.immutable());
                        if (worldIn.getBlockState(cp).is(RankineTags.Blocks.TREE_LOGS) && target.is(RankineTags.Blocks.TREE_LOGS)) {
                            toCheck.add(b.immutable());
                        } else if (target.is(RankineTags.Blocks.TREE_LEAVES)) {
                            if (target.getBlock() instanceof LeavesBlock) {
                                if (!target.getValue(LeavesBlock.PERSISTENT)) { /*&& target.get(LeavesBlock.DISTANCE) <= 5*/
                                    alive = true;
                                }
                            } else {
                                alive = true;
                            }
                        }
                    }
                }
            }
            if (alive) event.setNewSpeed((event.getNewSpeed() / checkedBlocks.size()) * Config.GENERAL.TREE_CHOP_SPEED.get().floatValue());
        }

    }

    @SubscribeEvent
    public static void blockBreakingEvents(BlockEvent.BreakEvent event) {
        ServerLevel worldIn = (ServerLevel) event.getWorld();
        Random rand = worldIn.random;
        Player player = event.getPlayer();
        BlockPos pos = event.getPos();
        Block target = worldIn.getBlockState(pos).getBlock();
        ItemStack mainHandItem = player.getMainHandItem();
        Item offHandItem = player.getOffhandItem().getItem();
        float CHANCE = worldIn.getRandom().nextFloat();


        if (!player.getAbilities().instabuild) {
            if (Config.GENERAL.TREE_CHOPPING.get() && !player.isShiftKeyDown() && !worldIn.isClientSide && player.getMainHandItem().is(RankineTags.Items.TREE_CHOPPERS) && worldIn.getBlockState(pos).is(RankineTags.Blocks.TREE_LOGS)) {
                Set<BlockPos> checkedBlocks = new HashSet<>();
                Set<BlockPos> logs = new HashSet<>();
                Set<BlockPos> leaves = new HashSet<>();
                Stack<BlockPos> toCheck = new Stack<>();
                boolean alive = false;
                int forceBreak = Config.GENERAL.FORCE_BREAK.get();

                toCheck.add(pos);
                while (!toCheck.isEmpty()) {
                    BlockPos cp = toCheck.pop();
                    if (!checkedBlocks.contains(cp)) {
                        checkedBlocks.add(cp);
                        for (BlockPos b : BlockPos.betweenClosed(cp.offset(-1,-1,-1), cp.offset(1,1,1))) {
                            BlockState logBS = worldIn.getBlockState(b.immutable());
                            if (logBS.is(RankineTags.Blocks.TREE_LOGS)) {
                                toCheck.add(b.immutable());
                                logs.add(b.immutable());
                            } else if (!alive && logBS.is(RankineTags.Blocks.TREE_LEAVES)) {
                                if (logBS.getBlock() instanceof LeavesBlock) {
                                    if (!logBS.getValue(LeavesBlock.PERSISTENT)) {
                                        alive = true;
                                    }
                                } else {
                                    alive = true;
                                }
                            }
                        }
                        for (BlockPos leaf : BlockPos.betweenClosed(cp.offset(-forceBreak,-forceBreak,-forceBreak), cp.offset(forceBreak,forceBreak+1,forceBreak))) {
                            if (!leaves.contains(leaf)) {
                                BlockState logBS = worldIn.getBlockState(leaf.immutable());
                                if (logBS.is(RankineTags.Blocks.TREE_LEAVES)) {
                                    if (logBS.getBlock() instanceof LeavesBlock) {
                                        if (!logBS.getValue(LeavesBlock.PERSISTENT) /*&& logBS.get(LeavesBlock.DISTANCE) <= 5*/) {
                                            leaves.add(leaf.immutable());
                                        }
                                    } else {
                                        leaves.add(leaf.immutable());
                                    }
                                }
                            }
                        }
                        if (logs.size() > Config.GENERAL.MAX_TREE.get() || logs.size() > player.getMainHandItem().getMaxDamage() - player.getMainHandItem().getDamageValue()) {
                            break;
                        }
                    }
                }

                if (alive) {
                    for (BlockPos b : logs) {
                        if (Config.GENERAL.STUMP_CREATION.get() && (worldIn.getBlockState(b.below()).getMaterial().equals(Material.DIRT) || worldIn.getBlockState(b.below()).getMaterial().equals(Material.SAND))) {
                            worldIn.setBlock(b, RankineBlocks.STUMP.get().defaultBlockState(),3);
                        } else {
                            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOTHERMIC,player.getMainHandItem()) > 0) {
                                Block.popResource(worldIn,b,new ItemStack(Items.CHARCOAL, CharcoalPitTile.logLayerCount(worldIn,worldIn.getBlockState(b))));
                                worldIn.destroyBlock(b,false);
                            } else {
                                worldIn.destroyBlock(b, true);
                            }
                        }

                    }

                    for (BlockPos b : leaves) {
                        BlockState LEAF = worldIn.getBlockState(b);
                        LeavesBlock.dropResources(LEAF,worldIn,pos);
                        worldIn.removeBlock(b,false);
                        if (worldIn.getRandom().nextFloat() < Config.GENERAL.LEAF_LITTER_GEN_TREES.get() && ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(LEAF.getBlock().getRegistryName().toString().replace("leaves", "leaf_litter"))) != null) {
                            worldIn.setBlockAndUpdate(b, ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse("rankine:"+LEAF.getBlock().getRegistryName().getPath().replace("leaves", "leaf_litter"))).defaultBlockState());
                        }
                    }
                    worldIn.playSound(null,pos, SoundEvents.GRASS_BREAK, SoundSource.BLOCKS,1.0f,0.8f);

                    if (worldIn.getBlockState(pos).getDestroySpeed(worldIn, pos) != 0.0F) {
                        player.getMainHandItem().hurtAndBreak(logs.size()-1, player, (p_220038_0_) -> {
                            p_220038_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                        });
                    }

                    event.setCanceled(true);
                    return;

                }


            }

            if (ForgeRegistries.BLOCKS.tags().getTag(BlockTags.LOGS_THAT_BURN).contains(target) && EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOTHERMIC,player.getMainHandItem()) > 0 && !worldIn.isClientSide) {
                worldIn.removeBlock(pos,false);
                Block.popResource(worldIn,pos,new ItemStack(Items.CHARCOAL, CharcoalPitTile.logLayerCount(worldIn,worldIn.getBlockState(pos))));
                player.getMainHandItem().hurtAndBreak(1, player, (p_220038_0_) -> {
                    p_220038_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                });

                event.setCanceled(true);
                //return;
            }



            if (target.equals(Blocks.GLOWSTONE) && !worldIn.isClientSide) {
                Block gas = Arrays.asList(RankineBlocks.ARGON_GAS_BLOCK.get(),RankineBlocks.NEON_GAS_BLOCK.get(),RankineBlocks.KRYPTON_GAS_BLOCK.get()).get(rand.nextInt(3));
                if (ForgeRegistries.BIOMES.tags().getTag(BiomeTags.IS_NETHER).contains(worldIn.getBiome(pos).value()) && rand.nextFloat() < Config.GENERAL.GLOWSTONE_GAS_CHANCE.get()) {
                    worldIn.setBlock(pos, gas.defaultBlockState(),3);
                }  else if (rand.nextFloat() < Config.GENERAL.GLOWSTONE_GAS_CHANCE.get()/5f) {
                    worldIn.setBlock(pos, gas.defaultBlockState(),3);
                }
                /*
                else if ( == Biome.BiomeCategory.THEEND && rand.nextFloat() < Config.GENERAL.GLOWSTONE_GAS_CHANCE.get()*5) {
                    worldIn.setBlock(pos, gas.defaultBlockState(),3);
                }
                 */
            }


            //Luck Pendant
            if (offHandItem == RankineItems.TOTEM_OF_PROMISING.get()) {
                if (event.getState().is(RankineTags.Blocks.PROMISING_TOTEM_BLOCKS)) {
                    if (rand.nextFloat() < Config.GENERAL.TOTEM_PROMISING_CHANCE.get()) {
                        Block.dropResources(worldIn.getBlockState(pos),worldIn,pos);
                    }
                }
            } else if (offHandItem == RankineItems.TOTEM_OF_SOFTENING.get()) {
                if ((player.getMainHandItem().isEmpty() && worldIn.getBlockState(pos).canHarvestBlock(worldIn,pos,player) && worldIn.getBlockEntity(pos) == null && worldIn.getFluidState(pos).isEmpty()) && !worldIn.isClientSide) {
                    worldIn.removeBlock(pos, false);
                    Block.popResource(worldIn,pos,new ItemStack(worldIn.getBlockState(pos).getBlock().asItem(), 1));
                    SoundType soundtype = worldIn.getBlockState(pos).getSoundType(worldIn, pos, null);
                    worldIn.playLocalSound(pos.getX(),pos.getY(),pos.getZ(), soundtype.getBreakSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
                }
            }

            //Alloy shovel perk
            /*
            if (target instanceof FallingBlock && mainHandItem instanceof AlloyShovelItem) {
                for (int i = 1; i <=5; ++i) {
                    if (mainHandItem.canHarvestBlock(worldIn.getBlockState(pos.down(i)))) {
                        worldIn.destroyBlock(pos.up(i), true);
                    }
                }
            }

             */


            //Nugget Drops
            if (ForgeRegistries.BLOCKS.tags().getTag(Tags.Blocks.STONE).contains(target)) {
                if (mainHandItem.getItem() instanceof PickaxeItem) {
                    BlockPos foundPos = null;
                    for (int x = 1; x < Config.GENERAL.NUGGET_DISTANCE.get(); x++) {
                        if (worldIn.getBlockState(pos.below(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.below(x);
                        } else if (worldIn.getBlockState(pos.above(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.above(x);
                        } else if (worldIn.getBlockState(pos.south(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.south(x);
                        } else if (worldIn.getBlockState(pos.north(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.north(x);
                        } else if (worldIn.getBlockState(pos.east(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.east(x);
                        } else if (worldIn.getBlockState(pos.west(x)).getBlock() instanceof RankineOreBlock) {
                            foundPos = pos.west(x);
                        }
                        if (foundPos != null && rand.nextFloat() < Config.GENERAL.NUGGET_CHANCE.get() && !worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !worldIn.restoringBlockSnapshots) {
                            Block b = worldIn.getBlockState(foundPos).getBlock();
                            ItemStack nug = ItemStack.EMPTY;
                            if (VanillaIntegration.oreNuggetMap.containsKey(b)) {
                                nug = new ItemStack(VanillaIntegration.oreNuggetMap.get(b));
                            }
                            if (!nug.isEmpty()) {
                                Block.popResource(worldIn, pos, nug);
                                break;
                            }
                        }
                    }

                    //Geodes
                    if (worldIn.getRandom().nextFloat() <= Config.GENERAL.GEODE_CHANCE.get() && !worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !worldIn.restoringBlockSnapshots) {
                        Block.popResource(worldIn, pos, new ItemStack(RankineItems.GEODE.get(), 1));
                    }

                }//end pick check

                //Flint drop
                if (player.getItemInHand(InteractionHand.MAIN_HAND).is(RankineTags.Items.CRUDE_TOOLS)) {
                    if (CHANCE < Config.GENERAL.FLINT_DROP_CHANCE.get()) {
                        Block.popResource(worldIn,pos,new ItemStack(Items.FLINT,1));
                    }
                }

            } //end stone check

            //knife stuff
            if (mainHandItem.is(RankineTags.Items.KNIVES)) {
                if (worldIn.getBlockState(pos).is(RankineTags.Blocks.KNIFE_SHEARABLE)) {
                    worldIn.destroyBlock(pos,false);
                    Block.popResource(worldIn, pos, new ItemStack(target.asItem()));
                    player.getItemInHand(event.getPlayer().swingingArm).hurtAndBreak(1, player, (p_220040_1_) -> {
                        p_220040_1_.broadcastBreakEvent(event.getPlayer().swingingArm);
                    });
                }
            }

            //Foraging Enchantment
            if (ForgeRegistries.BLOCKS.tags().getTag(BlockTags.DIRT).contains(target)) {
                ItemStack heldItemStack = player.getItemInHand(InteractionHand.MAIN_HAND);

                if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.FORAGING, heldItemStack) > 0) {
                    ItemStack FOOD;
                    Biome.BiomeCategory cat = Biome.getBiomeCategory(worldIn.getBiome(event.getPos()));
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
                        case MOUNTAIN:
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
                    Block.popResource(worldIn,pos,FOOD);
                }
            }


        } //end creative check


    }

    @SubscribeEvent
    public static void onSheepJoinWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Sheep) {
            Sheep ent = (Sheep) entity;
            ent.goalSelector.removeGoal(new EatBlockGoal(ent));
            ent.goalSelector.addGoal(5,new EatGrassGoalModified(ent));
            ent.goalSelector.removeGoal(new TemptGoal(ent, 1.1D, Ingredient.of(Items.WHEAT), false));
            ent.goalSelector.addGoal(3,new TemptGoal(ent, 1.1D, Ingredient.of(RankineTags.Items.BREEDABLES_SHEEP), false));
        } else if (entity instanceof Cow) {
            Cow ent = (Cow) entity;
            //ent.goalSelector.removeGoal(new TemptGoal(ent, 1.1D, Ingredient.fromItems(Items.WHEAT), false));
            ent.goalSelector.addGoal(3,new TemptGoal(ent, 1.25D, Ingredient.of(RankineTags.Items.BREEDABLES_COW), false));
        } else if (entity instanceof Pig) {
            Pig ent = (Pig) entity;
            //ent.goalSelector.removeGoal(new TemptGoal(ent, 1.1D, Ingredient.fromItems(Items.WHEAT), false));
            ent.goalSelector.addGoal(4,new TemptGoal(ent, 1.2D, Ingredient.of(RankineTags.Items.BREEDABLES_PIG), false));
        } else if (entity instanceof Chicken) {
            Chicken ent = (Chicken) entity;
            //ent.goalSelector.removeGoal(new TemptGoal(ent, 1.1D, Ingredient.fromItems(Items.WHEAT), false));
            ent.goalSelector.addGoal(3,new TemptGoal(ent, 1.0D, Ingredient.of(RankineTags.Items.BREEDABLES_CHICKEN), false));
        } else if (entity instanceof Rabbit) {
            Rabbit ent = (Rabbit) entity;
            //ent.goalSelector.removeGoal(new TemptGoal(ent, 1.1D, Ingredient.fromItems(Items.WHEAT), false));
            ent.goalSelector.addGoal(3,new TemptGoal(ent, 1.0D, Ingredient.of(RankineTags.Items.BREEDABLES_RABBIT), false));
        }
    }

    @SubscribeEvent
    public static void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
        if (event.getEntityLiving() instanceof Monster && event.getTarget() != null) {
            if (event.getTarget().getOffhandItem().getItem() == RankineItems.TOTEM_OF_REPULSING.get() || event.getEntityLiving().getEffect(RankineEffects.MERCURY_POISONING) != null) {
                ((Mob) event.getEntityLiving()).setTarget(null);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof Monster && event.getEntityLiving().getLastHurtByMob() != null) {
            if (event.getEntityLiving().getLastHurtByMob().getOffhandItem().getItem() == RankineItems.TOTEM_OF_REPULSING.get() || event.getEntityLiving().getEffect(RankineEffects.MERCURY_POISONING) != null) {
                event.getEntityLiving().setLastHurtByMob(null);
            }
        }
    }


    @SubscribeEvent
    public static void onBreedEvent(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getPlayer();
        Entity ent = event.getTarget();
        ItemStack itemStack = event.getItemStack();

        if (ent instanceof Animal) {
            Animal entA = (Animal) ent;
            EntityType<?> type = ent.getType();
            boolean flag = false;
            if (type.equals(EntityType.PIG) && itemStack.is(RankineTags.Items.BREEDABLES_PIG)) {
                flag = true;
            } else if ((type.equals(EntityType.COW) || type.equals(EntityType.MOOSHROOM)) && itemStack.is(RankineTags.Items.BREEDABLES_COW)) {
                flag = true;
            } else if (type.equals(EntityType.SHEEP) && itemStack.is(RankineTags.Items.BREEDABLES_SHEEP)) {
                flag = true;
            } else if (type.equals(EntityType.CHICKEN) && itemStack.is(RankineTags.Items.BREEDABLES_CHICKEN)) {
                flag = true;
            } else if (type.equals(EntityType.FOX) && itemStack.is(RankineTags.Items.BREEDABLES_FOX)) {
                flag = true;
            } else if (type.equals(EntityType.RABBIT) && itemStack.is(RankineTags.Items.BREEDABLES_RABBIT)) {
                flag = true;
            } else if (type.equals(EntityType.CAT) && itemStack.is(RankineTags.Items.BREEDABLES_CAT)) {
                flag = true;
            } else if ((type.equals(EntityType.HORSE) || type.equals(EntityType.DONKEY)) && itemStack.is(RankineTags.Items.BREEDABLES_HORSE)) {
                flag = true;
            }

            if (flag) {
                int i = entA.getAge();
                if (!entA.level.isClientSide && i == 0 && entA.canFallInLove()) {
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    entA.setInLove(player);
                    event.setResult(Event.Result.ALLOW);
                }

                if (entA.isBaby()) {
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    entA.ageUp((int) ((float) (-i / 20) * 0.1F), true);
                    event.setResult(Event.Result.ALLOW);
                }

                if (entA.level.isClientSide) {
                    event.setResult(Event.Result.ALLOW);
                }
            } else {
                event.setResult(Event.Result.DENY);
            }

        }


    }

}
