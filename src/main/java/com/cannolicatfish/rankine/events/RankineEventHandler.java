package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.blocks.CharcoalPit;
import com.cannolicatfish.rankine.blocks.LEDBlock;
import com.cannolicatfish.rankine.commands.CreateAlloyCommand;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.tools.ItemHammer;
import com.cannolicatfish.rankine.potion.ModEffects;
import com.cannolicatfish.rankine.util.RankineMathHelper;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.*;

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
        if (event.getType() == ModVillagerProfessions.METALLURGIST) {
            event.getTrades().get(1).add(new BasicTrade(1, new ItemStack(ModItems.ALLOY_TEMPLATE),12,1,0.05f));
            event.getTrades().get(1).add((entity,rand) -> new MerchantOffer(new ItemStack(ModItems.TIN_INGOT, 8), new ItemStack(Items.EMERALD),12,2,0.05f));
            event.getTrades().get(1).add((entity,rand) -> new MerchantOffer(new ItemStack(ModItems.COPPER_INGOT, 4), new ItemStack(Items.EMERALD),12,2,0.05f));
            event.getTrades().get(2).add((entity,rand) -> new MerchantOffer(new ItemStack(ModItems.METEORIC_IRON, 4), new ItemStack(Items.EMERALD),12,10,0.05f));
            event.getTrades().get(2).add(new BasicTrade(1, new ItemStack(ModItems.TRIPLE_ALLOY_TEMPLATE),12,5,0.05f));
            event.getTrades().get(3).add(new BasicTrade(1, new ItemStack(ModItems.MANGANESE_INGOT, 2),12,10,0.05f));
            event.getTrades().get(3).add(new BasicTrade(1, new ItemStack(ModItems.MOLYBDENUM_INGOT, 2),12,10,0.05f));
            event.getTrades().get(3).add(new BasicTrade(1, new ItemStack(ModItems.VANADIUM_INGOT, 2),12,10,0.05f));
            event.getTrades().get(3).add(new BasicTrade(1, new ItemStack(ModItems.NIOBIUM_INGOT, 2),12,10,0.05f));
            event.getTrades().get(4).add(new BasicTrade(6, new ItemStack(ModItems.ELEMENT_INDEXER),12,15,0.05f));
            event.getTrades().get(5).add(new BasicTrade(10, new ItemStack(ModItems.ORE_DETECTOR),12,30,0.05f));
        }
        if (Config.VILLAGER_TRADES.get())
        {
            if (event.getType() == VillagerProfession.MASON)
            {
                event.getTrades().get(1).add(new BasicTrade(1,new ItemStack(ModItems.MORTAR, 16),16,1,0.05f));
                event.getTrades().get(1).add(new BasicTrade(1,new ItemStack(ModItems.REFRACTORY_BRICK, 10),16,1,0.05f));

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
    public static void fuelValues(FurnaceFuelBurnTimeEvent event) {
        if (Config.FUEL_VALUES.get()) {
            Item Fuel = event.getItemStack().getItem();
            String path = Fuel.getRegistryName().getPath();
            if (Fuel.getTags().contains(new ResourceLocation("minecraft:logs_that_burn"))) {
                if (path.contains("douglas_fir")) {
                    event.setBurnTime(460);
                } else if (path.contains("pinyon_pine")) {
                    event.setBurnTime(520);
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
                    event.setBurnTime(470);
                } else if (path.contains("acacia")) {
                    event.setBurnTime(500);
                } else if (path.contains("magnolia")) {
                    event.setBurnTime(450);
                } else if (path.contains("hemlock")) {
                    event.setBurnTime(440);
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
        String path = ground.getRegistryName().getPath();
        ModifiableAttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (Config.MOVEMENT_MODIFIERS.get()) {
            if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/grass_path")) && !movementSpeed.hasModifier(ModAttributes.GRASS_PATH_MS)) {
                movementSpeed.applyNonPersistentModifier(ModAttributes.GRASS_PATH_MS);
            } else if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/grass_path")) && movementSpeed.hasModifier(ModAttributes.GRASS_PATH_MS)) {
                movementSpeed.removeModifier(ModAttributes.GRASS_PATH_MS);
            } else if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/sand")) && !movementSpeed.hasModifier(ModAttributes.SAND_MS)) {
                movementSpeed.applyNonPersistentModifier(ModAttributes.SAND_MS);
            } else if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/sand")) && movementSpeed.hasModifier(ModAttributes.SAND_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(ModAttributes.SAND_MS);
            } else if ((world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow")) || world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow"))) && !movementSpeed.hasModifier(ModAttributes.SNOW_MS)) {
                movementSpeed.applyNonPersistentModifier(ModAttributes.SNOW_MS);
            } else if ((!world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow")) || !world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow"))) && movementSpeed.hasModifier(ModAttributes.SNOW_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(ModAttributes.SNOW_MS);
            } else if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/dirt")) && !movementSpeed.hasModifier(ModAttributes.DIRT_MS)) {
                movementSpeed.applyNonPersistentModifier(ModAttributes.DIRT_MS);
            } else if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/dirt")) && movementSpeed.hasModifier(ModAttributes.DIRT_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(ModAttributes.DIRT_MS);
            } else if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/wooden")) && !movementSpeed.hasModifier(ModAttributes.WOODEN_MS)) {
                movementSpeed.applyNonPersistentModifier(ModAttributes.WOODEN_MS);
            } else if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/wooden")) && movementSpeed.hasModifier(ModAttributes.WOODEN_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(ModAttributes.WOODEN_MS);
            } else if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/polished_stone")) && !movementSpeed.hasModifier(ModAttributes.POLISHED_STONE_MS)) {
                movementSpeed.applyNonPersistentModifier(ModAttributes.POLISHED_STONE_MS);
            } else if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/polished_stone")) && movementSpeed.hasModifier(ModAttributes.POLISHED_STONE_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(ModAttributes.POLISHED_STONE_MS);
            } else if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/bricks")) && !movementSpeed.hasModifier(ModAttributes.BRICKS_MS)) {
                movementSpeed.applyNonPersistentModifier(ModAttributes.BRICKS_MS);
            } else if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/bricks")) && movementSpeed.hasModifier(ModAttributes.BRICKS_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(ModAttributes.BRICKS_MS);
            } else if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/concrete")) && !movementSpeed.hasModifier(ModAttributes.CONCRETE_MS)) {
                movementSpeed.applyNonPersistentModifier(ModAttributes.CONCRETE_MS);
            } else if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/concrete")) && movementSpeed.hasModifier(ModAttributes.CONCRETE_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(ModAttributes.CONCRETE_MS);
            } else if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/roman_concrete")) && !movementSpeed.hasModifier(ModAttributes.ROMAN_CONCRETE_MS)) {
                movementSpeed.applyNonPersistentModifier(ModAttributes.ROMAN_CONCRETE_MS);
            } else if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/roman_concrete")) && movementSpeed.hasModifier(ModAttributes.ROMAN_CONCRETE_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(ModAttributes.ROMAN_CONCRETE_MS);
            }
        }
        if (ground == Blocks.ICE) {
            if (new Random().nextFloat() < Config.ICE_BREAK.get()) {
                for (BlockPos B : BlockPos.getAllInBoxMutable(pos.add(-2, -1, -2), pos.add(2, -1, 2))) {
                    if (world.getBlockState(B).getBlock() == Blocks.ICE) {
                        world.setBlockState(B, Blocks.FROSTED_ICE.getDefaultState().with(FrostedIceBlock.AGE, 2));
                    }
                }
            }
        }
        if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.DUNE_WALKER, player) > 0) {
            if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/sand")) && !movementSpeed.hasModifier(ModAttributes.DUNE_WALKER)) {
                movementSpeed.applyNonPersistentModifier(ModAttributes.DUNE_WALKER);
            }
        } else if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.DUNE_WALKER, player) <= 0 && movementSpeed.hasModifier(ModAttributes.DUNE_WALKER)) {
            movementSpeed.removeModifier(ModAttributes.DUNE_WALKER);
        }
        if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/sand")) && ground != Blocks.AIR && movementSpeed.hasModifier(ModAttributes.DUNE_WALKER)) {
            movementSpeed.removeModifier(ModAttributes.DUNE_WALKER);
        }
        if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.SNOW_DRIFTER, player) > 0) {
            if ((world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow")) || world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow"))) && !movementSpeed.hasModifier(ModAttributes.SNOW_DRIFTER)) {
                movementSpeed.applyNonPersistentModifier(ModAttributes.SNOW_DRIFTER);
            }
        } else  if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.SNOW_DRIFTER, player) <= 0 && movementSpeed.hasModifier(ModAttributes.SNOW_DRIFTER)) {
            movementSpeed.removeModifier(ModAttributes.SNOW_DRIFTER);
        }
        if ((!world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow")) && !world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow"))) && ground != Blocks.AIR && movementSpeed.hasModifier(ModAttributes.SNOW_DRIFTER)) {
            movementSpeed.removeModifier(ModAttributes.SNOW_DRIFTER);
        }

    }


    @SubscribeEvent
    public static void specialEnchants(AnvilUpdateEvent event) {
        ItemStack input = event.getLeft();
        if (event.getRight().getItem() == ModItems.SANDALS && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getEquipmentSlot() == EquipmentSlotType.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.DUNE_WALKER,event.getOutput()) != 1) {
                event.getOutput().addEnchantment(ModEnchantments.DUNE_WALKER, 1);
                event.setCost(20);
            }
        } else if (event.getRight().getItem() == ModItems.SNOWSHOES && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getEquipmentSlot() == EquipmentSlotType.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.SNOW_DRIFTER,event.getOutput()) != 1) {
                event.getOutput().addEnchantment(ModEnchantments.SNOW_DRIFTER, 1);
                event.setCost(20);
            }
        }

        if (event.getPlayer() != null && event.getPlayer().getHeldItemOffhand().getItem() instanceof ItemHammer)
        {
            ItemStack hammer = event.getPlayer().getHeldItemOffhand();
            int enchLvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.SMITHING,hammer);
            int durability = hammer.getMaxDamage() - hammer.getDamage();
            if (enchLvl > 0 && durability >= Math.round(hammer.getMaxDamage() * 1f/enchLvl))
            {
                if (event.getRight().getItem() instanceof EnchantedBookItem && !event.getLeft().isEnchanted())
                {
                    event.setOutput(input.copy());
                    int i = 0;
                    ItemStack itemstack = input.copy();
                    ItemStack itemstack1 = input.copy();
                    ItemStack itemstack2 = event.getRight().copy();
                    boolean flag = event.getRight().getItem() == Items.ENCHANTED_BOOK && !EnchantedBookItem.getEnchantments(event.getRight()).isEmpty();
                    boolean flag2 = false;
                    boolean flag3 = false;
                    Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemstack1);
                    Map<Enchantment, Integer> map1 = EnchantmentHelper.getEnchantments(itemstack2);
                    for(Enchantment enchantment1 : map1.keySet()) {
                        if (enchantment1 != null) {
                            int i2 = map.getOrDefault(enchantment1, 0);
                            int j2 = map1.get(enchantment1);
                            j2 = i2 == j2 ? j2 + 1 : Math.max(j2, i2);
                            boolean flag1 = enchantment1.canApply((itemstack));
                            if (event.getPlayer().abilities.isCreativeMode || (itemstack.getItem() == Items.ENCHANTED_BOOK)) {
                                flag1 = true;
                            }

                            for(Enchantment enchantment : map.keySet()) {
                                if (enchantment != enchantment1 && !enchantment1.isCompatibleWith(enchantment)) {
                                    flag1 = false;
                                }
                            }

                            if (!flag1) {
                                flag3 = true;
                            } else {
                                flag2 = true;
                                if (j2 > enchantment1.getMaxLevel()) {
                                    j2 = enchantment1.getMaxLevel();
                                }

                                map.put(enchantment1, j2);
                                int k3 = 0;
                                switch(enchantment1.getRarity()) {
                                    case COMMON:
                                        k3 = 1;
                                        break;
                                    case UNCOMMON:
                                        k3 = 2;
                                        break;
                                    case RARE:
                                        k3 = 4;
                                        break;
                                    case VERY_RARE:
                                        k3 = 8;
                                }

                                if (flag) {
                                    k3 = Math.max(1, k3 / 2);
                                }

                                i += k3 * j2;
                                if (itemstack.getCount() > 1) {
                                    i = 40;
                                }
                            }
                        }
                    }
                    if (flag3 && !flag2) {
                        return;
                    }

                    if (!itemstack1.isEmpty()) {
                        EnchantmentHelper.setEnchantments(map, itemstack1);
                    }
                    event.setOutput(itemstack1.copy());
                    event.setCost(1);
                }

            }
        }
    }

    @SubscribeEvent
    public static void onAnvilRepair(AnvilRepairEvent event)
    {
        if (event.getPlayer() != null && event.getPlayer().getHeldItemOffhand().getItem() instanceof ItemHammer && event.getPlayer().getHeldItemOffhand().isEnchanted())
        {
            ItemStack hammer = event.getPlayer().getHeldItemOffhand();
            int enchLvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.SMITHING,hammer);
            int durability = hammer.getMaxDamage() - hammer.getDamage();
            if (enchLvl > 0 && event.getIngredientInput().getItem() instanceof EnchantedBookItem && !event.getItemInput().isEnchanted() && durability >= Math.round(hammer.getMaxDamage() * 1f/enchLvl))
            {

                float newBreakChance = Math.max(event.getBreakChance() - enchLvl * 0.02f,0);
                event.setBreakChance(newBreakChance);
                hammer.damageItem(Math.round(hammer.getMaxDamage() * 1f/enchLvl), event.getPlayer(), (p_220038_0_) -> {
                    p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onFluidInteraction(BlockEvent.FluidPlaceBlockEvent event)
    {
        if (event.getState() == Blocks.COBBLESTONE.getDefaultState() && Config.IGNEOUS_COBBLE_GEN.get() && event.getWorld() instanceof World)
        {
            World worldIn = (World) event.getWorld();
            BlockPos pos = event.getPos();
            List<Block> adjPos = Arrays.asList(worldIn.getBlockState(pos.up()).getBlock(),worldIn.getBlockState(pos.south()).getBlock(),worldIn.getBlockState(pos.north()).getBlock(),
                    worldIn.getBlockState(pos.west()).getBlock(),worldIn.getBlockState(pos.east()).getBlock(),worldIn.getBlockState(pos.down()).getBlock());
            if (adjPos.contains(ModBlocks.FELDSPAR_BLOCK) && adjPos.contains(Blocks.QUARTZ_BLOCK))
            {
                event.setNewState(Blocks.GRANITE.getDefaultState());
                return;
            } else if (adjPos.contains(ModBlocks.PLAGIOCLASE_FELDSPAR_BLOCK) && adjPos.contains(Blocks.QUARTZ_BLOCK))
            {
                event.setNewState(ModBlocks.RED_GRANITE.getDefaultState());
                return;
            } else if (adjPos.contains(ModBlocks.FELDSPAR_BLOCK) && adjPos.contains(ModBlocks.MICA_BLOCK))
            {
                event.setNewState(ModBlocks.GRANODIORITE.getDefaultState());
                return;
            } else if (adjPos.contains(ModBlocks.PLAGIOCLASE_FELDSPAR_BLOCK) && adjPos.contains(ModBlocks.MICA_BLOCK))
            {
                event.setNewState(Blocks.DIORITE.getDefaultState());
                return;
            }  else if (adjPos.contains(ModBlocks.PLAGIOCLASE_FELDSPAR_BLOCK) && adjPos.contains(ModBlocks.PYROXENE_BLOCK))
            {
                event.setNewState(ModBlocks.GABBRO.getDefaultState());
                return;
            } else if (adjPos.contains(ModBlocks.PLAGIOCLASE_FELDSPAR_BLOCK) && adjPos.contains(ModBlocks.OLIVINE_BLOCK))
            {
                event.setNewState(ModBlocks.ANORTHOSITE.getDefaultState());
                return;
            } else if (adjPos.contains(ModBlocks.OLIVINE_BLOCK) && adjPos.contains(ModBlocks.PYROXENE_BLOCK))
            {
                event.setNewState(ModBlocks.PERIDOTITE.getDefaultState());
                return;
            } else if (adjPos.contains(ModBlocks.MAGNESIA_BLOCK) && adjPos.contains(ModBlocks.PYROXENE_BLOCK))
            {
                event.setNewState(ModBlocks.KOMATIITE.getDefaultState());
                return;
            } else if (adjPos.contains(ModBlocks.MAGNESIA_BLOCK) && adjPos.contains(ModBlocks.OLIVINE_BLOCK))
            {
                event.setNewState(ModBlocks.KIMBERLITE.getDefaultState());
                return;
            }

            switch (event.getWorld().getRandom().nextInt(5))
            {
                case 0:
                    event.setNewState(Blocks.GRANITE.getDefaultState());
                    break;
                case 1:
                    event.setNewState(Blocks.DIORITE.getDefaultState());
                    break;
                case 2:
                    event.setNewState(ModBlocks.RED_GRANITE.getDefaultState());
                    break;
                case 3:
                    event.setNewState(ModBlocks.GRANODIORITE.getDefaultState());
                    break;
                case 4:
                    event.setNewState(ModBlocks.ANORTHOSITE.getDefaultState());
                    break;
            }
        } else if (event.getState() == Blocks.BASALT.getDefaultState() && Config.IGNEOUS_COBBLE_GEN.get() && event.getWorld() instanceof World)
        {
            World worldIn = (World) event.getWorld();
            BlockPos pos = event.getPos();
            List<Block> adjPos = Arrays.asList(worldIn.getBlockState(pos.up()).getBlock(),worldIn.getBlockState(pos.south()).getBlock(),worldIn.getBlockState(pos.north()).getBlock(),
                    worldIn.getBlockState(pos.west()).getBlock(),worldIn.getBlockState(pos.east()).getBlock());

            if (adjPos.contains(ModBlocks.FELDSPAR_BLOCK))
            {
                event.setNewState(ModBlocks.RHYOLITE.getDefaultState());
                return;
            } else if (adjPos.contains(Blocks.QUARTZ_BLOCK))
            {
                event.setNewState(ModBlocks.BLACK_DACITE.getDefaultState());
                return;
            } else if (adjPos.contains(ModBlocks.PLAGIOCLASE_FELDSPAR_BLOCK))
            {
                event.setNewState(Blocks.ANDESITE.getDefaultState());
                return;
            } else if (adjPos.contains(ModBlocks.MICA_BLOCK))
            {
                event.setNewState(ModBlocks.RED_DACITE.getDefaultState());
                return;
            } else if (adjPos.contains(ModBlocks.AMPHIBOLE_BLOCK))
            {
                event.setNewState(ModBlocks.HORNBLENDE_ANDESITE.getDefaultState());
                return;
            } else if (adjPos.contains(ModBlocks.PYROXENE_BLOCK))
            {
                event.setNewState(Blocks.BASALT.getDefaultState());
                return;
            } else if (adjPos.contains(ModBlocks.OLIVINE_BLOCK))
            {
                event.setNewState(ModBlocks.THOLEIITIC_BASALT.getDefaultState());
                return;
            }
            switch (event.getWorld().getRandom().nextInt(6))
            {
                case 0:
                    event.setNewState(Blocks.BASALT.getDefaultState());
                    break;
                case 1:
                    event.setNewState(ModBlocks.THOLEIITIC_BASALT.getDefaultState());
                    break;
                case 2:
                    event.setNewState(ModBlocks.GABBRO.getDefaultState());
                    break;
                case 3:
                    event.setNewState(ModBlocks.RHYOLITE.getDefaultState());
                    break;
                case 4:
                    event.setNewState(ModBlocks.HORNBLENDE_ANDESITE.getDefaultState());
                    break;
                case 5:
                    event.setNewState(Blocks.ANDESITE.getDefaultState());
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
    public static void flintFire(PlayerInteractEvent.RightClickBlock event) {
        BlockPos pos = event.getPos();
        World world = event.getWorld();
        PlayerEntity player = event.getPlayer();
        BlockPos blockpos1 = event.getPos().offset(event.getFace());
        if (player.getHeldItemMainhand().getItem() == Items.FLINT && player.getHeldItemOffhand().getItem() == Items.FLINT) {
            if (world.getBlockState(pos) == ModBlocks.CHARCOAL_PIT.getDefaultState().with(CharcoalPit.LIT, false)) {
                for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(0,-Config.CHARCOAL_PIT_HEIGHT.get(),0), pos.add(0, Config.CHARCOAL_PIT_HEIGHT.get(),0))) {
                    if (world.getBlockState(blockpos).getBlock() == ModBlocks.CHARCOAL_PIT && !world.isRemote) {
                        world.setBlockState(blockpos, world.getBlockState(blockpos).with(BlockStateProperties.LIT, Boolean.TRUE), 2);
                    }
                }
                player.swingArm(Hand.MAIN_HAND);
                player.getHeldItem(Hand.MAIN_HAND).shrink(1);
                player.getHeldItem(Hand.OFF_HAND).shrink(1);
                world.playSound(player, blockpos1, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, new Random().nextFloat() * 0.4F + 0.8F);
            } else if (AbstractFireBlock.canLightBlock(world, blockpos1, event.getFace()) && !world.isRemote && world.getBlockState(pos) != ModBlocks.CHARCOAL_PIT.getDefaultState().with(CharcoalPit.LIT, true)) {
                world.setBlockState(blockpos1, AbstractFireBlock.getFireForPlacement(world, blockpos1), 11);
                player.swingArm(Hand.MAIN_HAND);
                player.getHeldItem(Hand.MAIN_HAND).shrink(1);
                player.getHeldItem(Hand.OFF_HAND).shrink(1);
                world.playSound(player, blockpos1, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, new Random().nextFloat() * 0.4F + 0.8F);
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
        if (!player.abilities.isCreativeMode) {
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
        if (!player.abilities.isCreativeMode) {
            if (event.getState().getBlock().getTags().contains(new ResourceLocation("forge:dirt"))) {
                if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.FORAGING,player.getHeldItem(Hand.MAIN_HAND)) > 0)
                {
                    ItemStack FOOD;
                    World worldIn = (World) event.getWorld();
                    Biome.Category cat = worldIn.getBiome(event.getPos()).getCategory();
                    List<Item> possibleItems;
                    switch (cat) {
                        case JUNGLE:
                            possibleItems = Arrays.asList(ModItems.SALTPETER,Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT,ModItems.PINEAPPLE, Items.COCOA_BEANS, Items.MELON_SEEDS);
                            break;
                        case SWAMP:
                        case MUSHROOM:
                            possibleItems = Arrays.asList(ModItems.SALTPETER,Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT,Items.BROWN_MUSHROOM,Items.RED_MUSHROOM);
                            break;
                        case EXTREME_HILLS:
                            possibleItems = Arrays.asList(ModItems.SALTPETER,Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT,ModItems.FIRE_CLAY_BALL, ModItems.SNOWBERRIES);
                            break;
                        case RIVER:
                            possibleItems = Arrays.asList(ModItems.SALTPETER,Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT,Items.CLAY_BALL,Items.KELP,Items.SUGAR_CANE);
                            break;
                        case PLAINS:
                            possibleItems = Arrays.asList(ModItems.SALTPETER,Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT,Items.PUMPKIN_SEEDS,Items.FEATHER,Items.LEATHER);
                            break;
                        case DESERT:
                            possibleItems = Arrays.asList(ModItems.SALTPETER,ModItems.SALTPETER,Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT,Items.RABBIT_HIDE,Items.BONE);
                            break;
                        default:
                            possibleItems = Arrays.asList(ModItems.SALTPETER,Items.STRING,Items.POTATO,Items.CARROT,Items.BEETROOT);
                            break;
                    }

                    if (CHANCE < Config.FORAGING_CHANCE.get())
                    {
                        FOOD = new ItemStack(possibleItems.get(event.getWorld().getRandom().nextInt(possibleItems.size())));
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
                else if (player.getHeldItem(Hand.MAIN_HAND).isEmpty() || player.getHeldItem(Hand.MAIN_HAND).getItem().getTags().contains(new ResourceLocation("rankine:bronze_tools")) || player.getHeldItem(Hand.MAIN_HAND).getItem().getTags().contains(new ResourceLocation("rankine:flint_tools")) || player.getHeldItem(Hand.MAIN_HAND).getItem().getTags().contains(new ResourceLocation("rankine:pewter_tools")) || player.getHeldItem(Hand.MAIN_HAND).getItem().getTags().contains(new ResourceLocation("rankine:colored_gold_tools"))) {
                    ItemStack FOOD;
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

        if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.QUAKE,event.getPlayer().getHeldItemMainhand()) > 0) {
            int enchant = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.QUAKE,event.getPlayer().getHeldItemMainhand());
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
