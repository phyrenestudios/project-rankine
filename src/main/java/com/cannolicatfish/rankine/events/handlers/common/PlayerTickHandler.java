package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.items.InformationItem;
import com.cannolicatfish.rankine.items.totems.InvigoratingTotemItem;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
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
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

import java.awt.*;
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
                if (s.isEnchanted() && EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.GUARD.get(), s) > 0) {
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
        Player playerIn = event.player;
        AttributeInstance movementSpeed = playerIn.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeInstance swimSpeed = playerIn.getAttribute(ForgeMod.SWIM_SPEED.get());
        AttributeInstance stepHeight = playerIn.getAttribute(RankineAttributes.STEP_HEIGHT);

        if (playerIn.isCreative() || playerIn.isFallFlying()) {
            List<AttributeModifier> mods = Arrays.asList(RankineAttributes.SNOW_DRIFTER, RankineAttributes.SWIFT_SWIMMER, RankineAttributes.AQUA_LENSE, RankineAttributes.SPEED_SKATER, RankineAttributes.SNOW_DRIFTER, RankineAttributes.DUNE_WALKER, RankineAttributes.CONCRETE_MS, RankineAttributes.GRASS_PATH_MS, RankineAttributes.ROMAN_CONCRETE_MS, RankineAttributes.DIRT_MS, RankineAttributes.MUD_MS, RankineAttributes.POLISHED_STONE_MS, RankineAttributes.SAND_MS, RankineAttributes.SNOW_MS, RankineAttributes.WOODEN_MS);
            for (AttributeModifier m : mods) {
                movementSpeed.removeModifier(m);
            }
            return;
        }

        Level levelIn = event.player.level;
        Item feetEquipment = playerIn.getItemBySlot(EquipmentSlot.FEET).getItem();
        Item headEquipment = playerIn.getItemBySlot(EquipmentSlot.HEAD).getItem();
        BlockPos groundPos = playerIn.getY() % 1 < 0.5 ? playerIn.blockPosition().below() : playerIn.blockPosition();
        Block groundBlock = levelIn.getBlockState(groundPos).getBlock();

        if (playerIn.isEyeInFluid(FluidTags.WATER) && (headEquipment == RankineItems.GOGGLES.get() || RankineEnchantmentHelper.hasAquaLense(playerIn))) {
            playerIn.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,400,0,false,false));
        }

        if (ForgeRegistries.BLOCKS.tags().getTag(RankineTags.Blocks.MOVEMENT_MODIFIERS_ICE).contains(groundBlock)) {
            if (levelIn.random.nextFloat() < Config.GENERAL.ICE_BREAK.get() && !RankineEnchantmentHelper.hasSpeedSkater(playerIn) && feetEquipment != RankineItems.ICE_SKATES.get()) {
                for (BlockPos B : BlockPos.betweenClosed(groundPos.offset(-2, -1, -2), groundPos.offset(2, -1, 2))) {
                    if (levelIn.getBlockState(B).getBlock() == Blocks.ICE) {
                        levelIn.setBlockAndUpdate(B, Blocks.FROSTED_ICE.defaultBlockState().setValue(FrostedIceBlock.AGE, 2));
                    }
                }
            }
        }

        handleEquipmentModifier(stepHeight, movementSpeed, RankineAttributes.DUNE_WALKER, groundBlock, RankineTags.Blocks.MOVEMENT_MODIFIERS_SAND, RankineEnchantmentHelper.hasDuneWalker(playerIn) || feetEquipment == RankineItems.SANDALS.get(), true);
        handleEquipmentModifier(stepHeight, movementSpeed, RankineAttributes.SNOW_DRIFTER, levelIn.getBlockState(playerIn.blockPosition()).getBlock(),levelIn.getBlockState(playerIn.blockPosition().below()).getBlock(), RankineTags.Blocks.MOVEMENT_MODIFIERS_SNOW, RankineEnchantmentHelper.hasSnowDrifter(playerIn) || feetEquipment == RankineItems.SNOWSHOES.get(), true);
        handleEquipmentModifier(stepHeight, movementSpeed, RankineAttributes.SPEED_SKATER, groundBlock, RankineTags.Blocks.MOVEMENT_MODIFIERS_ICE, RankineEnchantmentHelper.hasSpeedSkater(playerIn) || feetEquipment == RankineItems.ICE_SKATES.get(), true);
        handleEquipmentModifier(stepHeight, swimSpeed, RankineAttributes.SWIFT_SWIMMER,(RankineEnchantmentHelper.hasSwiftSwimmer(playerIn) || feetEquipment == RankineItems.FINS.get()) && playerIn.isSwimming(), false);
        handleEquipmentModifier(stepHeight, swimSpeed, RankineAttributes.AQUA_LENSE,(RankineEnchantmentHelper.hasAquaLense(playerIn) || headEquipment == RankineItems.GOGGLES.get()) && playerIn.isSwimming(), false);

        if (Config.GENERAL.MOVEMENT_MODIFIERS.get()) {
            if (handleMovementModifier(movementSpeed,RankineAttributes.GRASS_PATH_MS,groundBlock,RankineTags.Blocks.MOVEMENT_MODIFIERS_PATHS)) return;
            if (handleMovementModifier(movementSpeed,RankineAttributes.SAND_MS,groundBlock,RankineTags.Blocks.MOVEMENT_MODIFIERS_SAND)) return;
            if (handleMovementModifier(movementSpeed,RankineAttributes.MUD_MS,groundBlock,RankineTags.Blocks.MOVEMENT_MODIFIERS_MUD)) return;
            if (handleMovementModifierDouble(movementSpeed,RankineAttributes.SNOW_MS, levelIn.getBlockState(playerIn.blockPosition()).getBlock(),levelIn.getBlockState(playerIn.blockPosition().below()).getBlock(), RankineTags.Blocks.MOVEMENT_MODIFIERS_SNOW)) return;
            if (handleMovementModifier(movementSpeed,RankineAttributes.DIRT_MS,groundBlock,RankineTags.Blocks.MOVEMENT_MODIFIERS_DIRT)) return;
            if (handleMovementModifier(movementSpeed,RankineAttributes.WOODEN_MS,groundBlock,RankineTags.Blocks.MOVEMENT_MODIFIERS_WOOD)) return;
            if (handleMovementModifier(movementSpeed,RankineAttributes.POLISHED_STONE_MS,groundBlock,RankineTags.Blocks.MOVEMENT_MODIFIERS_POLISHED)) return;
            if (handleMovementModifier(movementSpeed,RankineAttributes.BRICKS_MS,groundBlock,RankineTags.Blocks.MOVEMENT_MODIFIERS_BRICKS)) return;
            if (handleMovementModifier(movementSpeed,RankineAttributes.ROMAN_CONCRETE_MS,groundBlock,RankineTags.Blocks.MOVEMENT_MODIFIERS_ROMAN)) return;
            if (handleMovementModifier(movementSpeed,RankineAttributes.CONCRETE_MS,groundBlock,RankineTags.Blocks.MOVEMENT_MODIFIERS_CONCRETE)) return;
        }
    }

    private static void handleEquipmentModifier(AttributeInstance stepHeight, AttributeInstance attribute, AttributeModifier modifier, boolean isEquiped, boolean autoStep) {
        if (!isEquiped) {
            if (attribute.hasModifier(modifier)) attribute.removeModifier(modifier);
            if (stepHeight.hasModifier(RankineAttributes.STEP)) stepHeight.removeModifier(RankineAttributes.STEP);
            return;
        }
        if (!attribute.hasModifier(modifier)) attribute.addTransientModifier(modifier);
        if (autoStep && !stepHeight.hasModifier(RankineAttributes.STEP)) stepHeight.addTransientModifier(RankineAttributes.STEP);
    }
    private static void handleEquipmentModifier(AttributeInstance stepHeight, AttributeInstance attribute, AttributeModifier modifier, Block groundBlock, TagKey<Block> tagIn, boolean isEquiped, boolean autoStep) {
        if (groundBlock == Blocks.AIR) return;
        ITagManager<Block> blockITagManager = ForgeRegistries.BLOCKS.tags();
        if (!isEquiped || !blockITagManager.getTag(tagIn).contains(groundBlock)) {
            if (attribute.hasModifier(modifier)) attribute.removeModifier(modifier);
            if (stepHeight.hasModifier(RankineAttributes.STEP)) stepHeight.removeModifier(RankineAttributes.STEP);
            return;
        }
        if (!attribute.hasModifier(modifier)) attribute.addTransientModifier(modifier);
        if (autoStep && !stepHeight.hasModifier(RankineAttributes.STEP)) stepHeight.addTransientModifier(RankineAttributes.STEP);
    }
    private static void handleEquipmentModifier(AttributeInstance stepHeight, AttributeInstance attribute, AttributeModifier modifier, Block groundBlock, Block groundBlock2, TagKey<Block> tagIn, boolean isEquiped, boolean autoStep) {
        if (groundBlock == Blocks.AIR && groundBlock2 == Blocks.AIR) return;
        ITagManager<Block> blockITagManager = ForgeRegistries.BLOCKS.tags();
        if (!isEquiped || (!blockITagManager.getTag(tagIn).contains(groundBlock) && !blockITagManager.getTag(tagIn).contains(groundBlock2))) {
            if (attribute.hasModifier(modifier)) attribute.removeModifier(modifier);
            if (stepHeight.hasModifier(RankineAttributes.STEP)) stepHeight.removeModifier(RankineAttributes.STEP);
            return;
        }
        if (!attribute.hasModifier(modifier)) attribute.addTransientModifier(modifier);
        if (autoStep && !stepHeight.hasModifier(RankineAttributes.STEP)) stepHeight.addTransientModifier(RankineAttributes.STEP);
    }
    private static boolean handleMovementModifier(AttributeInstance attribute, AttributeModifier modifier, Block groundBlock, TagKey<Block> tagIn) {
        if (groundBlock == Blocks.AIR) return true;
        ITagManager<Block> blockITagManager = ForgeRegistries.BLOCKS.tags();
        if (!blockITagManager.getTag(tagIn).contains(groundBlock)) {
            if (attribute.hasModifier(modifier)) attribute.removeModifier(modifier);
            return false;
        }
        if (!attribute.hasModifier(modifier)) attribute.addTransientModifier(modifier);
        return true;
    }
    private static boolean handleMovementModifierDouble(AttributeInstance attribute, AttributeModifier modifier, Block groundBlock, Block feetblock, TagKey<Block> tagIn) {
        if (groundBlock == Blocks.AIR && feetblock == Blocks.AIR) return true;
        ITagManager<Block> blockITagManager = ForgeRegistries.BLOCKS.tags();
        if (!blockITagManager.getTag(tagIn).contains(groundBlock) && !blockITagManager.getTag(tagIn).contains(feetblock)) {
            if (attribute.hasModifier(modifier)) attribute.removeModifier(modifier);
            return false;
        }
        if (!attribute.hasModifier(modifier)) attribute.addTransientModifier(modifier);
        return true;
    }
}
