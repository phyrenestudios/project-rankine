package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.blocks.LEDBlock;
import com.cannolicatfish.rankine.blocks.CompositionBlock;
import com.cannolicatfish.rankine.commands.CreateAlloyCommand;
import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModEnchantments;
import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.init.ModRecipes;
import com.cannolicatfish.rankine.items.AlloyTemplate;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.tools.ItemHammer;
import com.cannolicatfish.rankine.potion.ModEffects;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityEvent;
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
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.items.IItemHandler;

import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

import static net.minecraft.block.Block.spawnAsEntity;

@Mod.EventBusSubscriber
public class RankineEventHandler {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CreateAlloyCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void addVillagerTrades(VillagerTradesEvent event)
    {
        if (Config.VILLAGER_TRADES.get())
        {
            if (event.getType() == VillagerProfession.MASON)
            {
                event.getTrades().get(1).add(new BasicTrade(1,new ItemStack(ModItems.MORTAR, 16),16,1,0.05f));
                event.getTrades().get(1).add(new BasicTrade(1,new ItemStack(ModItems.REFRACTORY_BRICK, 10),16,1,0.05f));

            } else if (event.getType() == VillagerProfession.LIBRARIAN)
            {
                event.getTrades().get(1).add(new BasicTrade(1, new ItemStack(ModItems.ALLOY_TEMPLATE),12,1,0.05f));
                event.getTrades().get(2).add(new BasicTrade(1, new ItemStack(ModItems.TRIPLE_ALLOY_TEMPLATE),12,5,0.05f));
            } else if (event.getType() == VillagerProfession.CLERIC)
            {
                event.getTrades().get(1).add(new BasicTrade(1, new ItemStack(ModItems.SALTPETER,2),12,1,0.05f));
            }
        }

    }

    @SubscribeEvent
    public static void addWandererTrades(WandererTradesEvent event)
    {
        if (Config.VILLAGER_TRADES.get())
        {
            event.getGenericTrades().add(new BasicTrade(1,new ItemStack(ModItems.PINEAPPLE, 1),4,1,0.5f));

            event.getGenericTrades().add(new BasicTrade(1,new ItemStack(ModBlocks.LIMESTONE, 8),8,1,0.05f));
            event.getRareTrades().add(new BasicTrade(1,new ItemStack(ModItems.ELEMENT_TRANSMUTER, 2),8,1,0.05f));
            ItemStack met = new ItemStack(ModItems.METEORIC_IRON);
            AlloyItem.addAlloy(met,new AlloyData("50Fe-50Ni"));
            event.getRareTrades().add(new BasicTrade(3,met,6,1,0.5f));
        }
        event.getRareTrades().add(new BasicTrade(3,new ItemStack(ModItems.PACKAGED_TOOL),6,1,0.05f));
    }

    @SubscribeEvent
    public static void onToolUse(BlockEvent.BlockToolInteractEvent event) {
        if (Config.DISABLE_WOODEN_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_SWORD) { event.setCanceled(true); }
        if (Config.DISABLE_WOODEN_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_AXE) { event.setCanceled(true); }
        if (Config.DISABLE_WOODEN_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_SHOVEL) { event.setCanceled(true); }
        if (Config.DISABLE_WOODEN_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_PICKAXE) { event.setCanceled(true); }
        if (Config.DISABLE_WOODEN_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_HOE) { event.setCanceled(true); }
        if (Config.DISABLE_STONE_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_SWORD) { event.setCanceled(true); }
        if (Config.DISABLE_STONE_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_AXE) { event.setCanceled(true); }
        if (Config.DISABLE_STONE_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_SHOVEL) { event.setCanceled(true); }
        if (Config.DISABLE_STONE_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_PICKAXE) { event.setCanceled(true); }
        if (Config.DISABLE_STONE_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_HOE) { event.setCanceled(true); }
        if (Config.DISABLE_IRON_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_SWORD) { event.setCanceled(true); }
        if (Config.DISABLE_IRON_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_AXE) { event.setCanceled(true); }
        if (Config.DISABLE_IRON_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_SHOVEL) { event.setCanceled(true); }
        if (Config.DISABLE_IRON_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_PICKAXE) { event.setCanceled(true); }
        if (Config.DISABLE_IRON_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_HOE) { event.setCanceled(true); }
        if (Config.DISABLE_GOLDEN_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_SWORD) { event.setCanceled(true); }
        if (Config.DISABLE_GOLDEN_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_AXE) { event.setCanceled(true); }
        if (Config.DISABLE_GOLDEN_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_SHOVEL) { event.setCanceled(true); }
        if (Config.DISABLE_GOLDEN_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_PICKAXE) { event.setCanceled(true); }
        if (Config.DISABLE_GOLDEN_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_HOE) { event.setCanceled(true); }
        if (Config.DISABLE_DIAMOND_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_SWORD) { event.setCanceled(true); }
        if (Config.DISABLE_DIAMOND_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_AXE) { event.setCanceled(true); }
        if (Config.DISABLE_DIAMOND_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_SHOVEL) { event.setCanceled(true); }
        if (Config.DISABLE_DIAMOND_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_PICKAXE) { event.setCanceled(true); }
        if (Config.DISABLE_DIAMOND_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_HOE) { event.setCanceled(true); }
        if (Config.DISABLE_NETHERITE_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_SWORD) { event.setCanceled(true); }
        if (Config.DISABLE_NETHERITE_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_AXE) { event.setCanceled(true); }
        if (Config.DISABLE_NETHERITE_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_SHOVEL) { event.setCanceled(true); }
        if (Config.DISABLE_NETHERITE_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_PICKAXE) { event.setCanceled(true); }
        if (Config.DISABLE_NETHERITE_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_HOE) { event.setCanceled(true); }
    }

    @SubscribeEvent
    public static void onFluidInteraction(BlockEvent.FluidPlaceBlockEvent event) {
        if (event.getState() == Blocks.COBBLESTONE.getDefaultState() && Config.IGNEOUS_COBBLE_GEN.get()) {
            switch (event.getWorld().getRandom().nextInt(10)) {
                case 0:
                    event.setNewState(Blocks.GRANITE.getDefaultState());
                    break;
                case 1:
                    event.setNewState(Blocks.ANDESITE.getDefaultState());
                    break;
                case 2:
                    event.setNewState(Blocks.DIORITE.getDefaultState());
                    break;
                case 3:
                    event.setNewState(ModBlocks.RED_GRANITE.getDefaultState());
                    break;
                case 4:
                    event.setNewState(ModBlocks.HORNBLENDE_ANDESITE.getDefaultState());
                    break;
                case 5:
                    event.setNewState(ModBlocks.GRANODIORITE.getDefaultState());
                    break;
                case 6:
                    event.setNewState(ModBlocks.ANORTHOSITE.getDefaultState());
                    break;
                case 7:
                    event.setNewState(ModBlocks.THOLEIITIC_BASALT.getDefaultState());
                    break;
                case 8:
                    event.setNewState(ModBlocks.GABBRO.getDefaultState());
                    break;
                case 9:
                    event.setNewState(ModBlocks.RHYOLITE.getDefaultState());
                    break;
            }
        }
    }

    /*
        private static final String NBT_KEY = "rankine.firstjoin";
        @SubscribeEvent
        public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
            if (!Config.STARTING_BOOK.get()) {
                return;
            }

            CompoundNBT data = event.getPlayer().getPersistentData();
            CompoundNBT persistent;
            if (!data.hasUniqueId(PlayerEntity.PERSISTED_NBT_TAG)) {
                data.put(PlayerEntity.PERSISTED_NBT_TAG, (persistent = new CompoundNBT()));
            } else {
                persistent = data.getCompound(PlayerEntity.PERSISTED_NBT_TAG);
            }

            if (!persistent.hasUniqueId(NBT_KEY)) {
                persistent.putBoolean(NBT_KEY, true);
                event.getPlayer().inventory.addItemStackToInventory(PatchouliAPI.instance.getBookStack(new ResourceLocation("rankine:rankine_journal")));
            }
        }
     */







    @SubscribeEvent
    public static void onCraft(PlayerEvent.ItemCraftedEvent event) {
        if (event.getCrafting().getItem() == ModItems.GLASS_CUTTER) {
            event.getCrafting().addEnchantment(Enchantments.SILK_TOUCH,1);
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(PlayerEvent.BreakSpeed event)
    {
        if (!(event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() instanceof AxeItem) && event.getState().getBlock().getTags().contains(new ResourceLocation("minecraft/logs")) && Config.MANDATORY_AXE.get()) { event.setNewSpeed(0f); }
        if (event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() instanceof ItemHammer) { event.setNewSpeed(0f); }
        
        if (Config.DISABLE_WOODEN_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_SWORD) { event.setNewSpeed(0f); }
        if (Config.DISABLE_WOODEN_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_AXE) { event.setNewSpeed(0f); }
        if (Config.DISABLE_WOODEN_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.DISABLE_WOODEN_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.DISABLE_WOODEN_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_HOE) { event.setNewSpeed(0f); }
        if (Config.DISABLE_STONE_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_SWORD) { event.setNewSpeed(0f); }
        if (Config.DISABLE_STONE_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_AXE) { event.setNewSpeed(0f); }
        if (Config.DISABLE_STONE_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.DISABLE_STONE_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.DISABLE_STONE_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_HOE) { event.setNewSpeed(0f); }
        if (Config.DISABLE_IRON_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_SWORD) { event.setNewSpeed(0f); }
        if (Config.DISABLE_IRON_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_AXE) { event.setNewSpeed(0f); }
        if (Config.DISABLE_IRON_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.DISABLE_IRON_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.DISABLE_IRON_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_HOE) { event.setNewSpeed(0f); }
        if (Config.DISABLE_GOLDEN_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_SWORD) { event.setNewSpeed(0f); }
        if (Config.DISABLE_GOLDEN_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_AXE) { event.setNewSpeed(0f); }
        if (Config.DISABLE_GOLDEN_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.DISABLE_GOLDEN_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.DISABLE_GOLDEN_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_HOE) { event.setNewSpeed(0f); }
        if (Config.DISABLE_DIAMOND_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_SWORD) { event.setNewSpeed(0f); }
        if (Config.DISABLE_DIAMOND_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_AXE) { event.setNewSpeed(0f); }
        if (Config.DISABLE_DIAMOND_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.DISABLE_DIAMOND_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.DISABLE_DIAMOND_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_HOE) { event.setNewSpeed(0f); }
        if (Config.DISABLE_NETHERITE_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_SWORD) { event.setNewSpeed(0f); }
        if (Config.DISABLE_NETHERITE_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_AXE) { event.setNewSpeed(0f); }
        if (Config.DISABLE_NETHERITE_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.DISABLE_NETHERITE_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.DISABLE_NETHERITE_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_HOE) { event.setNewSpeed(0f); }
    }

    @SubscribeEvent
    public static void onDamageEntity(LivingDamageEvent event) {
        if (event.getSource().getTrueSource() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
            if (Config.DISABLE_WOODEN_SWORD.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_SWORD) { event.setAmount(1f); }
            if (Config.DISABLE_WOODEN_AXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_AXE) { event.setAmount(1f); }
            if (Config.DISABLE_WOODEN_SHOVEL.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_SHOVEL) { event.setAmount(1f); }
            if (Config.DISABLE_WOODEN_PICKAXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_PICKAXE) { event.setAmount(1f); }
            if (Config.DISABLE_WOODEN_HOE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_HOE) { event.setAmount(1f); }
            if (Config.DISABLE_STONE_SWORD.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_SWORD) { event.setAmount(1f); }
            if (Config.DISABLE_STONE_AXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_AXE) { event.setAmount(1f); }
            if (Config.DISABLE_STONE_SHOVEL.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_SHOVEL) { event.setAmount(1f); }
            if (Config.DISABLE_STONE_PICKAXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_PICKAXE) { event.setAmount(1f); }
            if (Config.DISABLE_STONE_HOE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_HOE) { event.setAmount(1f); }
            if (Config.DISABLE_IRON_SWORD.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_SWORD) { event.setAmount(1f); }
            if (Config.DISABLE_IRON_AXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_AXE) { event.setAmount(1f); }
            if (Config.DISABLE_IRON_SHOVEL.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_SHOVEL) { event.setAmount(1f); }
            if (Config.DISABLE_IRON_PICKAXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_PICKAXE) { event.setAmount(1f); }
            if (Config.DISABLE_IRON_HOE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_HOE) { event.setAmount(1f); }
            if (Config.DISABLE_GOLDEN_SWORD.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_SWORD) { event.setAmount(1f); }
            if (Config.DISABLE_GOLDEN_AXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_AXE) { event.setAmount(1f); }
            if (Config.DISABLE_GOLDEN_SHOVEL.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_SHOVEL) { event.setAmount(1f); }
            if (Config.DISABLE_GOLDEN_PICKAXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_PICKAXE) { event.setAmount(1f); }
            if (Config.DISABLE_GOLDEN_HOE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_HOE) { event.setAmount(1f); }
            if (Config.DISABLE_DIAMOND_SWORD.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_SWORD) { event.setAmount(1f); }
            if (Config.DISABLE_DIAMOND_AXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_AXE) { event.setAmount(1f); }
            if (Config.DISABLE_DIAMOND_SHOVEL.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_SHOVEL) { event.setAmount(1f); }
            if (Config.DISABLE_DIAMOND_PICKAXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_PICKAXE) { event.setAmount(1f); }
            if (Config.DISABLE_DIAMOND_HOE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_HOE) { event.setAmount(1f); }
            if (Config.DISABLE_NETHERITE_SWORD.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_SWORD) { event.setAmount(1f); }
            if (Config.DISABLE_NETHERITE_AXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_AXE) { event.setAmount(1f); }
            if (Config.DISABLE_NETHERITE_SHOVEL.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_SHOVEL) { event.setAmount(1f); }
            if (Config.DISABLE_NETHERITE_PICKAXE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_PICKAXE) { event.setAmount(1f); }
            if (Config.DISABLE_NETHERITE_HOE.get() && player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_HOE) { event.setAmount(1f); }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onTooltipCheck(ItemTooltipEvent event) {
        if (Config.DISABLE_WOODEN_SWORD.get() && event.getItemStack().getItem() == Items.WOODEN_SWORD) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_WOODEN_AXE.get() && event.getItemStack().getItem() == Items.WOODEN_AXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_WOODEN_SHOVEL.get() && event.getItemStack().getItem() == Items.WOODEN_SHOVEL) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_WOODEN_PICKAXE.get() && event.getItemStack().getItem() == Items.WOODEN_PICKAXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_WOODEN_HOE.get() && event.getItemStack().getItem() == Items.WOODEN_HOE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_STONE_SWORD.get() && event.getItemStack().getItem() == Items.STONE_SWORD) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_STONE_AXE.get() && event.getItemStack().getItem() == Items.STONE_AXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_STONE_SHOVEL.get() && event.getItemStack().getItem() == Items.STONE_SHOVEL) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_STONE_PICKAXE.get() && event.getItemStack().getItem() == Items.STONE_PICKAXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_STONE_HOE.get() && event.getItemStack().getItem() == Items.STONE_HOE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_IRON_SWORD.get() && event.getItemStack().getItem() == Items.IRON_SWORD) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_IRON_AXE.get() && event.getItemStack().getItem() == Items.IRON_AXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_IRON_SHOVEL.get() && event.getItemStack().getItem() == Items.IRON_SHOVEL) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_IRON_PICKAXE.get() && event.getItemStack().getItem() == Items.IRON_PICKAXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_IRON_HOE.get() && event.getItemStack().getItem() == Items.IRON_HOE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_GOLDEN_SWORD.get() && event.getItemStack().getItem() == Items.GOLDEN_SWORD) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_GOLDEN_AXE.get() && event.getItemStack().getItem() == Items.GOLDEN_AXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_GOLDEN_SHOVEL.get() && event.getItemStack().getItem() == Items.GOLDEN_SHOVEL) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_GOLDEN_PICKAXE.get() && event.getItemStack().getItem() == Items.GOLDEN_PICKAXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_GOLDEN_HOE.get() && event.getItemStack().getItem() == Items.GOLDEN_HOE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_DIAMOND_SWORD.get() && event.getItemStack().getItem() == Items.DIAMOND_SWORD) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_DIAMOND_AXE.get() && event.getItemStack().getItem() == Items.DIAMOND_AXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_DIAMOND_SHOVEL.get() && event.getItemStack().getItem() == Items.DIAMOND_SHOVEL) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_DIAMOND_PICKAXE.get() && event.getItemStack().getItem() == Items.DIAMOND_PICKAXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_DIAMOND_HOE.get() && event.getItemStack().getItem() == Items.DIAMOND_HOE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_NETHERITE_SWORD.get() && event.getItemStack().getItem() == Items.NETHERITE_SWORD) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_NETHERITE_AXE.get() && event.getItemStack().getItem() == Items.NETHERITE_AXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_NETHERITE_SHOVEL.get() && event.getItemStack().getItem() == Items.NETHERITE_SHOVEL) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_NETHERITE_PICKAXE.get() && event.getItemStack().getItem() == Items.NETHERITE_PICKAXE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
        if (Config.DISABLE_NETHERITE_HOE.get() && event.getItemStack().getItem() == Items.NETHERITE_HOE) { event.getToolTip().add(new StringTextComponent("This tool is disabled in the config.").mergeStyle(TextFormatting.RED)); }
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
    public static void brickify(PlayerInteractEvent.RightClickBlock event) {
        PlayerEntity player = event.getPlayer();
        ItemStack items = event.getItemStack();
        BlockState blockstate = event.getWorld().getBlockState(event.getPos());




    }


    @SubscribeEvent
    public static void deBrick(PlayerInteractEvent.RightClickBlock event) {
        PlayerEntity player = event.getPlayer();
        ItemStack items = event.getItemStack();
        BlockState blockstate = event.getWorld().getBlockState(event.getPos());




    }


    @SubscribeEvent
    public static void dyeLED(PlayerInteractEvent.RightClickBlock event) {
        if (event.getWorld().getBlockState(event.getPos()).getBlock() instanceof LEDBlock) {
            if (event.getItemStack().getItem().getTags().contains(new ResourceLocation("forge:dyes/red"))) {
                event.getWorld().setBlockState(event.getPos(), ModBlocks.RED_LED.getDefaultState(),3);
                if (!event.getPlayer().isCreative()) {
                    event.getItemStack().shrink(1);
                }
            } else if (event.getItemStack().getItem().getTags().contains(new ResourceLocation("forge:dyes/green"))) {
                event.getWorld().setBlockState(event.getPos(), ModBlocks.GREEN_LED.getDefaultState(),3);
                if (!event.getPlayer().isCreative()) {
                    event.getItemStack().shrink(1);
                }
            } else if (event.getItemStack().getItem().getTags().contains(new ResourceLocation("forge:dyes/blue"))) {
                event.getWorld().setBlockState(event.getPos(), ModBlocks.BLUE_LED.getDefaultState(),3);
                if (!event.getPlayer().isCreative()) {
                    event.getItemStack().shrink(1);
                }
            }
        }
    }


    @SubscribeEvent
    public static void checkCowInteraction(PlayerInteractEvent.EntityInteract event)
    {
        if (event.getTarget() instanceof CowEntity  && !event.getPlayer().abilities.isCreativeMode)
        {
            CowEntity cow = ((CowEntity)event.getTarget());
            Hand hand = event.getHand();
            if (event.getPlayer().getHeldItem(hand).getItem() == ModItems.BRASS_BUCKET && !cow.isChild())
            {
                event.getPlayer().playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                event.getPlayer().getHeldItem(hand).shrink(1);
                if (event.getPlayer().getHeldItem(hand).isEmpty())
                {
                    event.getPlayer().setHeldItem(hand, new ItemStack(ModItems.MILK_BRASS_BUCKET,1));
                } else
                {
                    event.getPlayer().addItemStackToInventory(new ItemStack(ModItems.MILK_BRASS_BUCKET,1));
                }

            }
            if (event.getPlayer().getHeldItem(hand).getItem() == ModItems.WOOD_BUCKET && !cow.isChild())
            {
                event.getPlayer().playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                event.getPlayer().getHeldItem(hand).shrink(1);
                if (event.getPlayer().getHeldItem(hand).isEmpty())
                {
                    event.getPlayer().setHeldItem(hand, new ItemStack(ModItems.MILK_WOOD_BUCKET,1));
                } else
                {
                    event.getPlayer().addItemStackToInventory(new ItemStack(ModItems.MILK_WOOD_BUCKET,1));
                }
            }
        }
    }


    public static Map<Block, Block> stripping_map = new HashMap<Block, Block>();

    @SubscribeEvent
    public static void axeStrip(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        World world = event.getWorld();
        Direction direction = event.getFace();
        BlockPos pos = event.getPos();
        BlockPos posWithOffset = pos.offset(direction);
        PlayerEntity player = event.getPlayer();

        if(item instanceof AxeItem) {
        BlockState activatedBlock = world.getBlockState(pos);
            if (activatedBlock.getBlock() == Blocks.BIRCH_LOG){
                if (new Random().nextFloat() < 0.2f) {
                spawnAsEntity(event.getWorld(), event.getPos(), new ItemStack(Items.PAPER, 1));
                }
            }
            if(stripping_map.get(activatedBlock.getBlock()) != null) {
                Block block = activatedBlock.getBlock();
                if(block instanceof RotatedPillarBlock) {
                    Direction.Axis axis = activatedBlock.get(RotatedPillarBlock.AXIS);
                    world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    world.setBlockState(pos, stripping_map.get(activatedBlock.getBlock()).getDefaultState().with(RotatedPillarBlock.AXIS, axis), 2);
                    stack.damageItem(1, player, (entity) -> {
                        entity.sendBreakAnimation(event.getHand());
                    });
                    player.swingArm(event.getHand());
                    event.setResult(Event.Result.ALLOW);
                }
            }
        }
    }



    @SubscribeEvent
    public static void flintDrop(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        float CHANCE = new Random().nextFloat();
        if (Config.FLINT_DROP.get() && !player.abilities.isCreativeMode) {
            if (event.getState().getBlock().getTags().contains(new ResourceLocation("forge:stone"))) {
                if ( player.getHeldItem(Hand.MAIN_HAND).getItem().getTags().contains(new ResourceLocation("rankine:bronze_tools")) || player.getHeldItem(Hand.MAIN_HAND).getItem().getTags().contains(new ResourceLocation("rankine:flint_tools")) || player.getHeldItem(Hand.MAIN_HAND).getItem().getTags().contains(new ResourceLocation("rankine:pewter_tools")) || player.getHeldItem(Hand.MAIN_HAND).getItem().getTags().contains(new ResourceLocation("rankine:colored_gold_tools"))) {
                    if (CHANCE < Config.FLINT_DROP_CHANCE.get()) {
                        double d0 = (double) (new Random().nextFloat() * 0.5F) + 0.25D;
                        double d1 = (double) (new Random().nextFloat() * 0.5F) + 0.25D;
                        double d2 = (double) (new Random().nextFloat() * 0.5F) + 0.25D;
                        ItemEntity itementity = new ItemEntity((ServerWorld) event.getWorld(), (double) event.getPos().getX() + d0, (double) event.getPos().getY() + d1, (double) event.getPos().getZ() + d2, new ItemStack(Items.FLINT, 1));
                        itementity.setDefaultPickupDelay();
                        event.getWorld().addEntity(itementity);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void forage(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        float CHANCE = new Random().nextFloat();
        if (Config.FORAGING.get() && !player.abilities.isCreativeMode) {
            if (event.getState().getBlock().getTags().contains(new ResourceLocation("forge:dirt"))) {
                if (player.getHeldItem(Hand.MAIN_HAND).isEmpty() || player.getHeldItem(Hand.MAIN_HAND).getItem().getTags().contains(new ResourceLocation("rankine:flint_tools"))) {
                    ItemStack FOOD = null;
                    if (CHANCE < Config.FORAGING_CHANCE.get() * 0.3) {
                        FOOD = new ItemStack(Items.POTATO,1);
                    } else if (CHANCE < Config.FORAGING_CHANCE.get() * 0.6) {
                        FOOD = new ItemStack(Items.CARROT,1);
                    } else if (CHANCE < Config.FORAGING_CHANCE.get()) {
                        FOOD = new ItemStack(Items.BEETROOT,1);
                    } else {
                        return;
                    }
                    double d0 = (double) (new Random().nextFloat() * 0.5F) + 0.25D;
                    double d1 = (double) (new Random().nextFloat() * 0.5F) + 0.25D;
                    double d2 = (double) (new Random().nextFloat()  * 0.5F) + 0.25D;
                    ItemEntity itementity = new ItemEntity((ServerWorld) event.getWorld(), (double) event.getPos().getX() + d0, (double) event.getPos().getY() + d1, (double) event.getPos().getZ() + d2, FOOD);
                    itementity.setDefaultPickupDelay();
                    event.getWorld().addEntity(itementity);
                }
            }
        }
    }



    //PENDANTS

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (event.getPlayer().getHeldItemOffhand().getItem() == ModItems.HASTE_PENDANT) {
            event.setNewSpeed(event.getNewSpeed() + 3);
        }
    }

    @SubscribeEvent
    public static void luckyBreak(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        if (!player.abilities.isCreativeMode && player.getHeldItemOffhand().getItem() == ModItems.LUCK_PENDANT) {
            if (event.getState().getBlock().getTags().contains(new ResourceLocation("rankine:luck_pendant"))) {
                if (new Random().nextFloat() < 0.2f) {
                    for (ItemStack i : Block.getDrops(event.getState(), (ServerWorld) event.getWorld(), event.getPos(), null)) {
                        spawnAsEntity((World) event.getWorld(), event.getPos(), new ItemStack(i.getItem(), 1));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
        if (event.getEntityLiving() instanceof MonsterEntity && event.getTarget() != null) {
            if (event.getTarget().getHeldItemOffhand().getItem() == ModItems.REPULSION_PENDANT || event.getEntityLiving().getActivePotionEffect(ModEffects.MERCURY_POISONING) != null) {
                ((MobEntity) event.getEntityLiving()).setAttackTarget(null);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof MonsterEntity && event.getEntityLiving().getRevengeTarget() != null) {
            if (event.getEntityLiving().getRevengeTarget().getHeldItemOffhand().getItem() == ModItems.REPULSION_PENDANT || event.getEntityLiving().getActivePotionEffect(ModEffects.MERCURY_POISONING) != null) {
                event.getEntityLiving().setRevengeTarget(null);
            }
        }
    }

}
