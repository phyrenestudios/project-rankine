package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.items.InformationItem;
import com.cannolicatfish.rankine.items.totems.InvigoratingTotemItem;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PlayerTickHandler {
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
}
