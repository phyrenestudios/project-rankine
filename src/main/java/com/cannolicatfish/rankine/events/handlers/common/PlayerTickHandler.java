package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineAttributes;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.items.InformationItem;
import com.cannolicatfish.rankine.items.totems.InvigoratingTotemItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.FrostedIceBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class PlayerTickHandler {
    private static final String DIRECTION_FORMAT = "Facing %s with coordinates: X=%2.0f Z=%2.0f";
    private static final String CLOCK_FORMAT = "Time = %02.0f:%02.0f (%d)";
    private static final String TEMP_FORMAT = "Temperature = %1.3f";
    private static final String ALTIMETER_FORMAT = "Altitude: Y = %d";
    private static final String PHOTOMETER_FORMAT = "Light Levels: Sky = %2d Block = %2d";

    private static final Map<Item, Consumer<TickEvent.PlayerTickEvent>> ITEM_HOLD_MAP = new HashMap<>();
    static {
        ITEM_HOLD_MAP.put(Items.COMPASS, PlayerTickHandler::onCompassHold);
        ITEM_HOLD_MAP.put(Items.CLOCK, PlayerTickHandler::onClockHold);
        ITEM_HOLD_MAP.put(RankineItems.THERMOMETER.get(), PlayerTickHandler::onThermometerHold);
        ITEM_HOLD_MAP.put(RankineItems.ALTIMETER.get(), PlayerTickHandler::onAltimeterHold);
        ITEM_HOLD_MAP.put(RankineItems.PHOTOMETER.get(), PlayerTickHandler::onPhotometerHold);
        ITEM_HOLD_MAP.put(RankineItems.BIOMETER.get(), PlayerTickHandler::onBiometerHold);
        ITEM_HOLD_MAP.put(RankineItems.MAGNETOMETER.get(), PlayerTickHandler::onMagnetometerHold);
    }

    private static void onCompassHold(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        switch (player.getHorizontalFacing()) {
            case NORTH:
                player.sendStatusMessage(
                        new StringTextComponent(String.format(DIRECTION_FORMAT, "North", player.getPosX(), player.getPosZ()))
                                .mergeStyle(TextFormatting.GOLD),true);
                break;
            case EAST:
                player.sendStatusMessage(
                        new StringTextComponent(String.format(DIRECTION_FORMAT, "East", player.getPosX(), player.getPosZ()))
                                .mergeStyle(TextFormatting.GOLD),true);
                break;
            case SOUTH:
                player.sendStatusMessage(
                        new StringTextComponent(String.format(DIRECTION_FORMAT, "South", player.getPosX(), player.getPosZ()))
                                .mergeStyle(TextFormatting.GOLD),true);
                break;
            case WEST:
                player.sendStatusMessage(
                        new StringTextComponent(String.format(DIRECTION_FORMAT, "West", player.getPosX(), player.getPosZ()))
                                .mergeStyle(TextFormatting.GOLD),true);
                break;
        }
    }
    private static void onClockHold(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World worldIn = event.player.world;
        double hours = ((Math.floor(worldIn.getDayTime() / 1000f)) + 6) % 24;
        double minutes = ((worldIn.getDayTime() / 1000f) % 1) * 60;
        player.sendStatusMessage(new StringTextComponent(String.format(CLOCK_FORMAT, hours, minutes, worldIn.getDayTime() % 24000))
                                         .mergeStyle(TextFormatting.GOLD),
                                 true);
    }
    private static void onThermometerHold(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World worldIn = event.player.world;
        BlockPos pos = player.isSneaking() ? player.getPosition() : player.getPosition().up();
        float temp = worldIn.getBiome(pos).getTemperature(pos);
        StringTextComponent comp = new StringTextComponent(String.format(TEMP_FORMAT, temp));
        if (temp < 0.0) {
            player.sendStatusMessage(comp.mergeStyle(TextFormatting.LIGHT_PURPLE, TextFormatting.BOLD), true);
        } else if (temp < 0.15) {
            player.sendStatusMessage(comp.mergeStyle(TextFormatting.AQUA, TextFormatting.BOLD), true);
        } else if (temp < 0.8) {
            player.sendStatusMessage(comp.mergeStyle(TextFormatting.GREEN, TextFormatting.BOLD), true);
        } else if (temp < 0.95) {
            player.sendStatusMessage(comp.mergeStyle(TextFormatting.YELLOW, TextFormatting.BOLD), true);
        } else {
            player.sendStatusMessage(comp.mergeStyle(TextFormatting.RED, TextFormatting.BOLD), true);
        }
    }
    private static void onAltimeterHold(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        BlockPos pos = player.isSneaking() ? player.getPosition() : player.getPosition().up();
        int y = pos.getY();
        StringTextComponent comp = new StringTextComponent(String.format(ALTIMETER_FORMAT, y));
        if (y < 0) {
            player.sendStatusMessage(comp.mergeStyle(TextFormatting.WHITE, TextFormatting.BOLD), true);
        } else if (y < 64) {
            player.sendStatusMessage(comp.mergeStyle(TextFormatting.DARK_PURPLE, TextFormatting.BOLD), true);
        } else if (y < 128) {
            player.sendStatusMessage(comp.mergeStyle(TextFormatting.DARK_AQUA, TextFormatting.BOLD), true);
        } else {
            player.sendStatusMessage(comp.mergeStyle(TextFormatting.AQUA, TextFormatting.BOLD), true);
        }
    }
    private static void onPhotometerHold(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World worldIn = event.player.world;
        BlockPos pos = player.isSneaking() ? player.getPosition() : player.getPosition().up();
        int SLL = worldIn.getLightFor(LightType.SKY, pos);
        int BLL = worldIn.getLightFor(LightType.BLOCK,pos);
        StringTextComponent comp = new StringTextComponent(String.format(PHOTOMETER_FORMAT, SLL, BLL));

        if (worldIn.getBlockState(player.getPosition())
                   .getBlock()
                   .canCreatureSpawn(
                           worldIn.getBlockState(player.getPosition()),
                           worldIn,
                           player.getPosition(),
                           EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
                           EntityType.ZOMBIE)
        ) {
            player.sendStatusMessage(comp.mergeStyle(TextFormatting.RED, TextFormatting.BOLD), true);
        } else {
            player.sendStatusMessage(comp.mergeStyle(TextFormatting.GREEN, TextFormatting.BOLD), true);
        }
    }
    private static void onBiometerHold(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World worldIn = event.player.world;
        BlockPos pos = player.isSneaking() ? player.getPosition() : player.getPosition().up();
        player.sendStatusMessage(new StringTextComponent("Biome = " + new TranslationTextComponent(Util.makeTranslationKey("biome", worldIn.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(worldIn.getBiome(pos)))).getString()).mergeStyle(TextFormatting.GOLD), true);
    }
    private static void onMagnetometerHold( TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World worldIn = event.player.world;
        double strength = 0.05D;
        if (BlockPos.getClosestMatchingPosition(player.getPosition(), 5, 4, (p) -> worldIn.getBlockState(p).isIn(RankineTags.Blocks.ELECTROMAGNETS)).isPresent()) {
            strength = 3.00D;
        } else if (BlockPos.getClosestMatchingPosition(player.getPosition(), 5, 4, (p) -> worldIn.getBlockState(p).matchesBlock(Blocks.OBSERVER)).isPresent()) {
            strength = 1.00D;
        } else {
            Optional<BlockPos> b = BlockPos.getClosestMatchingPosition(player.getPosition(), Config.TOOLS.MAGNETOMETER_RANGE.get(), Config.TOOLS.MAGNETOMETER_RANGE.get(), ( p) -> worldIn.getBlockState(p).isIn(Tags.Blocks.ORES));
            if (b.isPresent()) {
                strength = 0.5D/(player.getPosition().distanceSq(b.get())-1);
            }
        }
        player.sendStatusMessage(new TranslationTextComponent("item.rankine.magnetometer.message1", new DecimalFormat("#.##").format(strength)).mergeStyle(TextFormatting.GOLD, TextFormatting.BOLD), true);
    }

    public static void onPlayerTick( TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World worldIn = player.world;
        Item mainHand = player.getHeldItemMainhand().getItem();
        Item offHand = player.getHeldItemOffhand().getItem();
        if(worldIn.getGameTime() % 5 == 0){
            // Tools
            if(!worldIn.isRemote()){
                ForgeConfigSpec.BooleanValue enabledMainHandItem = VanillaIntegration.DISABLED_ITEMS.get(mainHand);
                Consumer<TickEvent.PlayerTickEvent> handler;
                if(enabledMainHandItem != null && !enabledMainHandItem.get()){
                    handler = ITEM_HOLD_MAP.get(mainHand);
                    if(handler != null){
                        handler.accept(event);
                    }
                }
                ForgeConfigSpec.BooleanValue enabledOffHandItem = VanillaIntegration.DISABLED_ITEMS.get(offHand);
                if(enabledOffHandItem != null && !enabledOffHandItem.get()) {
                    handler = ITEM_HOLD_MAP.get(offHand);
                    if (handler != null) {
                        handler.accept(event);
                    }
                }
            } else {
                if (!Config.TOOLS.DISABLE_SPEEDOMETER.get() && (mainHand == RankineItems.SPEEDOMETER.get() || offHand == RankineItems.SPEEDOMETER.get())) {
                    Entity ent = player;
                    if (player.getRidingEntity() != null) {
                        ent = player.getRidingEntity();
                    }

                    double d0 = Math.abs(ent.getPosX() - player.lastTickPosX);
                    double d1 = Math.abs(ent.getPosZ() - player.lastTickPosZ);
                    //double d2 = Math.abs(player.getPosY() - player.lastTickPosY);
                    double speed = Math.sqrt(Math.pow(d0, 2) + Math.pow(d1, 2));
                    if ((offHand == RankineItems.SPEEDOMETER.get() && !(mainHand instanceof InformationItem || mainHand == Items.COMPASS || mainHand == Items.CLOCK)) ||
                            (mainHand == RankineItems.SPEEDOMETER.get() && !(offHand instanceof InformationItem || offHand.getItem() == Items.COMPASS || offHand.getItem() == Items.CLOCK)))
                        player.sendStatusMessage(new StringTextComponent("Speed = " + new DecimalFormat("#.##").format(speed * 20) + " blocks per second").mergeStyle(TextFormatting.GOLD), true);
                }
            }



        }

        // Moved the armor checks to every 10 ticks (half second) to prevent lag
        // Also made it so that it doesn't happen the same tick as the tool checks
        if(worldIn.getGameTime() % 10 == 1) {
            // Armor
            if(player.isInWater()){
                if(player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == RankineItems.CONDUIT_DIVING_HELMET.get() && player.areEyesInFluid(FluidTags.WATER)){
                    int chestSlot = player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == RankineItems.CONDUIT_DIVING_CHESTPLATE.get() ? 1 : 0;
                    int legsSlot = player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == RankineItems.CONDUIT_DIVING_LEGGINGS.get() ? 1 : 0;
                    int feetSlot = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == RankineItems.CONDUIT_DIVING_BOOTS.get() ? 1 : 0;

                    player.addPotionEffect(new EffectInstance(Effects.CONDUIT_POWER, 400 * (1+chestSlot+legsSlot+feetSlot), 0, false, false, true));
                }
            }else{
                if(player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == RankineItems.DIVING_HELMET.get()){
                    int chestSlot = player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == RankineItems.DIVING_CHESTPLATE.get() ? 1 : 0;
                    int legsSlot = player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == RankineItems.DIVING_LEGGINGS.get() ? 1 : 0;
                    int feetSlot = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == RankineItems.DIVING_BOOTS.get() ? 1 : 0;

                    player.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 200 * ( 1 + chestSlot + legsSlot + feetSlot ), 0, false, false, true));
                }
            }
        }
        if (worldIn.getGameTime() % 1200 == 0) {
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


        EffectInstance eff = player.getActivePotionEffect(Effects.REGENERATION);
        if(eff != null) {
            if(mainHand instanceof InvigoratingTotemItem || offHand instanceof InvigoratingTotemItem) {
                for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                    ItemStack itemstack = player.inventory.getStackInSlot(i);
                    if (!itemstack.isEmpty() && itemstack.getDamage() != 0) {
                        int k = 50 >> eff.getAmplifier();
                        if (eff.getDuration() % k == 0) {
                            itemstack.setDamage(Math.max(0, itemstack.getDamage() - 1));
                        }
                        break;
                    }
                }
            }
        }


        ModifiableAttributeInstance att = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if(att != null) {
            if(!att.hasModifier(RankineAttributes.SWIFTNESS_TOTEM)) {
                if(mainHand == RankineItems.TOTEM_OF_TIMESAVING.get()){
                    att.applyNonPersistentModifier(RankineAttributes.SWIFTNESS_TOTEM);
                }
            }
        }
        att = player.getAttribute(Attributes.MAX_HEALTH);
        if(att != null) {
            if(!att.hasModifier(RankineAttributes.ENDURING_TOTEM)) {
                if(mainHand == RankineItems.TOTEM_OF_ENDURING.get()){
                    att.applyNonPersistentModifier(RankineAttributes.ENDURING_TOTEM);
                }
            }
        }
    }



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
            if (VanillaIntegration.pathBlocks_map.get(ground.getBlock()) != null && world.getBlockState(pos.up()).matchesBlock(Blocks.AIR) && world.getBlockState(pos.up()).getBlock() instanceof BushBlock) {
                world.setBlockState(pos, VanillaIntegration.pathBlocks_map.get(ground).getDefaultState(),2);
            }

        }


        Item feetEquipment = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem();
        Item headEquipment = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem();
        if (player.areEyesInFluid(FluidTags.WATER) && headEquipment == RankineItems.GOGGLES.get()) {
            player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION,400,0,false,false));
        }


        ModifiableAttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        ModifiableAttributeInstance swimSpeed = player.getAttribute(ForgeMod.SWIM_SPEED.get());

        //movementSpeed.applyNonPersistentModifier(new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fb5"), "rankine:block_ms", 0.0D, AttributeModifier.Operation.ADDITION));


        // Movement Modifiersa
        if (Config.GENERAL.MOVEMENT_MODIFIERS.get()) {
            List<AttributeModifier> mods = Arrays.asList(RankineAttributes.BRICKS_MS, RankineAttributes.CONCRETE_MS, RankineAttributes.GRASS_PATH_MS, RankineAttributes.ROMAN_CONCRETE_MS, RankineAttributes.DIRT_MS, RankineAttributes.MUD_MS, RankineAttributes.POLISHED_STONE_MS, RankineAttributes.SAND_MS, RankineAttributes.SNOW_MS, RankineAttributes.WOODEN_MS);
            if (player.isCreative() || player.isElytraFlying()) {
                for (AttributeModifier m : mods) {
                    movementSpeed.removeModifier(m);
                }
            } else if (RankineTags.Blocks.MOVEMENT_MODIFIERS_PATHS.contains(ground) && !movementSpeed.hasModifier(RankineAttributes.GRASS_PATH_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.GRASS_PATH_MS);
                }
            } else if (!RankineTags.Blocks.MOVEMENT_MODIFIERS_PATHS.contains(ground) && movementSpeed.hasModifier(RankineAttributes.GRASS_PATH_MS)) {
                movementSpeed.removeModifier(RankineAttributes.GRASS_PATH_MS);
            } else if (RankineTags.Blocks.MOVEMENT_MODIFIERS_SAND.contains(ground) && !movementSpeed.hasModifier(RankineAttributes.SAND_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.SAND_MS);
                }
            } else if (!RankineTags.Blocks.MOVEMENT_MODIFIERS_SAND.contains(ground) && movementSpeed.hasModifier(RankineAttributes.SAND_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(RankineAttributes.SAND_MS);
            } else if (RankineTags.Blocks.MOVEMENT_MODIFIERS_MUD.contains(ground) && !movementSpeed.hasModifier(RankineAttributes.MUD_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.MUD_MS);
                }
            } else if (!RankineTags.Blocks.MOVEMENT_MODIFIERS_MUD.contains(ground) && movementSpeed.hasModifier(RankineAttributes.MUD_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(RankineAttributes.MUD_MS);
            } else if ((world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow")) || world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow"))) && !movementSpeed.hasModifier(RankineAttributes.SNOW_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.SNOW_MS);
                }
            } else if ((!world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow")) || !world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow"))) && movementSpeed.hasModifier(RankineAttributes.SNOW_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(RankineAttributes.SNOW_MS);
            } else if (RankineTags.Blocks.MOVEMENT_MODIFIERS_DIRT.contains(ground) && !movementSpeed.hasModifier(RankineAttributes.DIRT_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.DIRT_MS);
                }
            } else if (!RankineTags.Blocks.MOVEMENT_MODIFIERS_DIRT.contains(ground) && movementSpeed.hasModifier(RankineAttributes.DIRT_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(RankineAttributes.DIRT_MS);
            } else if (RankineTags.Blocks.MOVEMENT_MODIFIERS_WOOD.contains(ground) && !movementSpeed.hasModifier(RankineAttributes.WOODEN_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.WOODEN_MS);
                }
            } else if (!RankineTags.Blocks.MOVEMENT_MODIFIERS_WOOD.contains(ground) && movementSpeed.hasModifier(RankineAttributes.WOODEN_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(RankineAttributes.WOODEN_MS);
            } else if (RankineTags.Blocks.MOVEMENT_MODIFIERS_POLISHED.contains(ground) && !movementSpeed.hasModifier(RankineAttributes.POLISHED_STONE_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.POLISHED_STONE_MS);
                }
            } else if (!RankineTags.Blocks.MOVEMENT_MODIFIERS_POLISHED.contains(ground) && movementSpeed.hasModifier(RankineAttributes.POLISHED_STONE_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(RankineAttributes.POLISHED_STONE_MS);
            } else if (RankineTags.Blocks.MOVEMENT_MODIFIERS_BRICKS.contains(ground) && !movementSpeed.hasModifier(RankineAttributes.BRICKS_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.BRICKS_MS);
                }
            } else if (!RankineTags.Blocks.MOVEMENT_MODIFIERS_BRICKS.contains(ground) && movementSpeed.hasModifier(RankineAttributes.BRICKS_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(RankineAttributes.BRICKS_MS);
            } else if (RankineTags.Blocks.MOVEMENT_MODIFIERS_ROMAN.contains(ground) && !movementSpeed.hasModifier(RankineAttributes.ROMAN_CONCRETE_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.ROMAN_CONCRETE_MS);
                }
            } else if (!RankineTags.Blocks.MOVEMENT_MODIFIERS_ROMAN.contains(ground) && movementSpeed.hasModifier(RankineAttributes.ROMAN_CONCRETE_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(RankineAttributes.ROMAN_CONCRETE_MS);
            } else if (RankineTags.Blocks.MOVEMENT_MODIFIERS_CONCRETE.contains(ground) && !movementSpeed.hasModifier(RankineAttributes.CONCRETE_MS)) {
                if (!player.isCreative() && !player.isElytraFlying()) {
                    movementSpeed.applyNonPersistentModifier(RankineAttributes.CONCRETE_MS);
                }
            } else if (!RankineTags.Blocks.MOVEMENT_MODIFIERS_CONCRETE.contains(ground) && movementSpeed.hasModifier(RankineAttributes.CONCRETE_MS) && ground != Blocks.AIR) {
                movementSpeed.removeModifier(RankineAttributes.CONCRETE_MS);
            }
        }
        if (ground == Blocks.ICE) {
            if (world.rand.nextFloat() < Config.GENERAL.ICE_BREAK.get() && !(RankineEnchantmentHelper.hasSpeedSkater(player))) {
                for (BlockPos B : BlockPos.getAllInBoxMutable(pos.add(-2, -1, -2), pos.add(2, -1, 2))) {
                    if (world.getBlockState(B).getBlock() == Blocks.ICE) {
                        world.setBlockState(B, Blocks.FROSTED_ICE.getDefaultState().with(FrostedIceBlock.AGE, 2));
                    }
                }
            }
        }
        if (RankineEnchantmentHelper.hasDuneWalker(player) || feetEquipment == RankineItems.SANDALS.get()) {
            if (RankineTags.Blocks.MOVEMENT_MODIFIERS_SAND.contains(ground) && !movementSpeed.hasModifier(RankineAttributes.DUNE_WALKER)) {
                movementSpeed.applyNonPersistentModifier(RankineAttributes.DUNE_WALKER);
                player.stepHeight = 1.0f;
            }
        } else if (!RankineEnchantmentHelper.hasDuneWalker(player) && feetEquipment != RankineItems.SANDALS.get() && movementSpeed.hasModifier(RankineAttributes.DUNE_WALKER)) {
            movementSpeed.removeModifier(RankineAttributes.DUNE_WALKER);
            player.stepHeight = 0.5f;
        }
        if (!RankineTags.Blocks.MOVEMENT_MODIFIERS_SAND.contains(ground) && ground != Blocks.AIR && movementSpeed.hasModifier(RankineAttributes.DUNE_WALKER)) {
            movementSpeed.removeModifier(RankineAttributes.DUNE_WALKER);
            player.stepHeight = 0.5f;
        }
        if (RankineEnchantmentHelper.hasSnowDrifter(player) || feetEquipment == RankineItems.SNOWSHOES.get()) {
            if ((world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow")) || world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow"))) && !movementSpeed.hasModifier(RankineAttributes.SNOW_DRIFTER)) {
                movementSpeed.applyNonPersistentModifier(RankineAttributes.SNOW_DRIFTER);
                player.stepHeight = 1.0f;
            }
        } else if (!RankineEnchantmentHelper.hasSnowDrifter(player) && feetEquipment != RankineItems.SNOWSHOES.get() && movementSpeed.hasModifier(RankineAttributes.SNOW_DRIFTER)) {
            movementSpeed.removeModifier(RankineAttributes.SNOW_DRIFTER);
            player.stepHeight = 0.5f;
        }
        if ((!world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow")) && !world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow"))) && ground != Blocks.AIR && movementSpeed.hasModifier(RankineAttributes.SNOW_DRIFTER)) {
            movementSpeed.removeModifier(RankineAttributes.SNOW_DRIFTER);
            player.stepHeight = 0.5f;
        }
        if (RankineEnchantmentHelper.hasSpeedSkater(player) || feetEquipment == RankineItems.ICE_SKATES.get()) {
            if ((world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/ice")) || world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/ice"))) && !movementSpeed.hasModifier(RankineAttributes.SPEED_SKATER)) {
                movementSpeed.applyNonPersistentModifier(RankineAttributes.SPEED_SKATER);
                player.stepHeight = 1.0f;
            }
        } else if (!RankineEnchantmentHelper.hasSpeedSkater(player) && feetEquipment != RankineItems.ICE_SKATES.get() && movementSpeed.hasModifier(RankineAttributes.SPEED_SKATER)) {
            movementSpeed.removeModifier(RankineAttributes.SPEED_SKATER);
            player.stepHeight = 0.5f;
        }
        if ((!world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/ice")) && !world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/ice"))) && ground != Blocks.AIR && movementSpeed.hasModifier(RankineAttributes.SPEED_SKATER)) {
            movementSpeed.removeModifier(RankineAttributes.SPEED_SKATER);
            player.stepHeight = 0.5f;
        }
        if (RankineEnchantmentHelper.hasFlippers(player) || feetEquipment == RankineItems.FINS.get()) {
            if (player.isSwimming() && !swimSpeed.hasModifier(RankineAttributes.FLIPPERS)) {
                swimSpeed.applyNonPersistentModifier(RankineAttributes.FLIPPERS);
            }
        } else if (!RankineEnchantmentHelper.hasFlippers(player) && feetEquipment != RankineItems.FINS.get() && swimSpeed.hasModifier(RankineAttributes.FLIPPERS)) {
            swimSpeed.removeModifier(RankineAttributes.FLIPPERS);
        }
        if (!player.isSwimming() && swimSpeed.hasModifier(RankineAttributes.FLIPPERS)) {
            swimSpeed.removeModifier(RankineAttributes.FLIPPERS);
        }
        if (headEquipment == RankineItems.GOGGLES.get() && player.areEyesInFluid(FluidTags.WATER) && !swimSpeed.hasModifier(RankineAttributes.WATER_VISION)) {
            swimSpeed.applyNonPersistentModifier(RankineAttributes.WATER_VISION);
        } else if ((headEquipment != RankineItems.GOGGLES.get() || !player.areEyesInFluid(FluidTags.WATER)) && swimSpeed.hasModifier(RankineAttributes.WATER_VISION)) {
            swimSpeed.removeModifier(RankineAttributes.WATER_VISION);
        }
    }
}
