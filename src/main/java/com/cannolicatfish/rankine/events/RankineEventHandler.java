package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.RankinePlantBlock;
import com.cannolicatfish.rankine.compatibility.Patchouli;
import com.cannolicatfish.rankine.entities.*;
import com.cannolicatfish.rankine.fluids.RankineFluids;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.blocks.CharcoalPitBlock;
import com.cannolicatfish.rankine.blocks.LEDBlock;
import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.blocks.beehiveoven.BeehiveOvenPitBlock;
import com.cannolicatfish.rankine.commands.CreateAlloyCommand;
import com.cannolicatfish.rankine.commands.GiveTagCommand;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.items.alloys.*;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.potion.RankineEffects;
import com.cannolicatfish.rankine.recipe.helper.FluidHelper;
import com.cannolicatfish.rankine.util.RankineVillagerTrades;
import com.cannolicatfish.rankine.util.RankineMathHelper;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
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
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.*;
import java.util.stream.Stream;

import static net.minecraft.block.Block.spawnAsEntity;

@Mod.EventBusSubscriber
public class RankineEventHandler {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CreateAlloyCommand.register(event.getDispatcher());
        GiveTagCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void addWandererTrades(WandererTradesEvent event) {
        if (Config.GENERAL.VILLAGER_TRADES.get()) {
            event.getGenericTrades().add(new BasicTrade(1,new ItemStack(RankineItems.PINEAPPLE.get(), 1),4,1,0.5f));
            event.getGenericTrades().add(new BasicTrade(1,new ItemStack(RankineBlocks.TUFA_LIMESTONE.get(), 8),8,1,0.05f));
            event.getRareTrades().add(new BasicTrade(1,new ItemStack(RankineItems.ELEMENT_TRANSMUTER.get(), 2),8,1,0.05f));
            ItemStack met = new ItemStack(RankineItems.METEORIC_IRON.get());
            AlloyItem.addAlloy(met,new AlloyData("50Fe-50Ni"));
            event.getRareTrades().add(new BasicTrade(3,met,6,1,0.5f));
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
            level1.add(new BasicTrade(1, new ItemStack(RankineItems.STONE_HAMMER.get()),12,1,0.2f));
            level1.add(new BasicTrade(1, new ItemStack(RankineItems.PROSPECTING_STICK.get()),12,1,0.05f));
            level1.add(new BasicTrade(1, new ItemStack(RankineItems.HARDNESS_TESTER.get()),12,1,0.05f));
            level2.add(new BasicTrade(1, new ItemStack(RankineItems.CHALCOPYRITE.get()),12,5,0.05f));
            level2.add(new BasicTrade(1, new ItemStack(RankineItems.BORAX.get()),12,5,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.MICA.get(), 4), new ItemStack(Items.EMERALD),12,10,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.AMPHIBOLE.get(), 4), new ItemStack(Items.EMERALD),12,10,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.PLAGIOCLASE_FELDSPAR.get(), 4), new ItemStack(Items.EMERALD),12,10,0.05f));
            level3.add(new RankineVillagerTrades.EnchantedAlloyItemForEmeraldsTrade(RankineItems.INVAR_HAMMER.get(),"90Fe-10Ni",8,3,10,0.2f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.ZIRCON.get()),12,15,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.ALUMINA.get()),12,15,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.MAGNESITE.get()),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(RankineItems.VANADINITE.get()),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(RankineItems.PETALITE.get()),12,15,0.05f));
            level4.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.OLIVINE.get(), 4), new ItemStack(Items.EMERALD),12,20,0.05f));
            level4.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.PYROXENE.get(), 4), new ItemStack(Items.EMERALD),12,20,0.05f));
            level5.add(new BasicTrade(1, new ItemStack(RankineItems.WOLFRAMITE.get()),12,30,0.05f));
            level5.add(new BasicTrade(1, new ItemStack(RankineItems.COBALTITE.get()),12,30,0.05f));
            level5.add(new RankineVillagerTrades.EnchantedAlloyItemForEmeraldsTrade(RankineItems.STEEL_HAMMER.get(),"99Fe-1C",15,3,30,0.2f));
        } else if (event.getType() == RankineVillagerProfessions.BOTANIST) {
            level1.addAll(RankineVillagerTrades.returnTagTrades(new ResourceLocation("rankine","flowers"),Items.DANDELION,2,1,12,10,0.05f));
            level1.addAll(RankineVillagerTrades.returnTagTrades(new ResourceLocation("forge", "berries"),RankineItems.ELDERBERRIES.get(),2,1,12,10,0.05f));
            level2.addAll(RankineVillagerTrades.returnTagTrades(new ResourceLocation("minecraft","saplings"),Items.OAK_SAPLING,2,1,12,10,0.05f));
            //level2.addAll(RankineVillagerTrades.returnTagTrades(new ResourceLocation("minecraft","logs_that_burn"),Items.OAK_LOG,4,1,12,10,0.05f));
            level3.addAll(RankineVillagerTrades.returnTagTrades(new ResourceLocation("minecraft","tall_flowers"),Items.ROSE_BUSH,3,1,12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(RankineItems.FLOWER_SEEDS.get(), 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.VINE, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.LILY_PAD, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.RED_MUSHROOM, 4),12,15,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.BROWN_MUSHROOM, 4),12,15,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.SEA_PICKLE, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.KELP, 4),12,10,0.05f));
            level3.add(new BasicTrade(1, new ItemStack(Items.NETHER_WART, 4),12,10,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(Items.BAMBOO, 4),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(RankineItems.CAMPHOR_BASIL_SEEDS.get()),12,30,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(RankineItems.ALOE.get()),12,30,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(Items.SHROOMLIGHT, 4),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(Items.WARPED_FUNGUS, 4),12,15,0.05f));
            level4.add(new BasicTrade(1, new ItemStack(Items.CRIMSON_FUNGUS, 4),12,15,0.05f));
            level5.add(new BasicTrade(1, new ItemStack(Items.MYCELIUM, 4),12,30,0.05f));
            level5.add(new BasicTrade(5, new ItemStack(Items.CHORUS_FLOWER, 1),12,30,0.05f));
            level5.add(new BasicTrade(10, new ItemStack(Items.WITHER_ROSE),12,30,0.05f));

        } else if (event.getType() == RankineVillagerProfessions.GEM_CUTTER) {
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.AQUAMARINE.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.OPAL.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level1.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.GARNET.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.RUBY.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.SAPPHIRE.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.PERIDOT.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level2.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.TOPAZ.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level3.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.PEARL.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level3.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.TOURMALINE.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level3.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.TIGER_IRON.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level3.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.LABRADORITE.get(), 1), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level4.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.RHODONITE.get(), 1), new ItemStack(Items.EMERALD, 3),12,20,0.05f));
            level4.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.RHODOCHROSITE.get(), 1), new ItemStack(Items.EMERALD, 3),12,20,0.05f));
            level4.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.CHROME_ENSTATITE.get(), 1), new ItemStack(Items.EMERALD, 3),12,20,0.05f));
            level4.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.FLUORITE.get(), 3), new ItemStack(Items.EMERALD, 2),12,20,0.05f));
            level5.add((entity,rand) -> new MerchantOffer(new ItemStack(RankineItems.LONSDALEITE_DIAMOND.get(), 1), new ItemStack(Items.EMERALD, 5),12,20,0.05f));
            level5.add(new BasicTrade(20, new ItemStack(Items.DIAMOND, 1),12,20,0.05f));
            level5.add(new BasicTrade(30, new ItemStack(RankineItems.LONSDALEITE_DIAMOND.get(), 1),12,50,0.05f));
            level5.add((entity,rand) -> new MerchantOffer(new ItemStack(Items.NETHER_STAR, 1), new ItemStack(Items.EMERALD, 64),12,50,0.05f));
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
        } else {
            if (movementSpeed != null && movementSpeed.hasModifier(RankineAttributes.MERCURY_MS)) {
                movementSpeed.removeModifier(RankineAttributes.MERCURY_MS);
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
        String path = ground.getRegistryName().getPath();
        ModifiableAttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

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
        if (EnchantmentHelper.getMaxEnchantmentLevel(RankineEnchantments.DUNE_WALKER, player) > 0) {
            if (ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/sand")) && !movementSpeed.hasModifier(RankineAttributes.DUNE_WALKER)) {
                movementSpeed.applyNonPersistentModifier(RankineAttributes.DUNE_WALKER);
                player.stepHeight = 1.0f;
            }
        } else if (EnchantmentHelper.getMaxEnchantmentLevel(RankineEnchantments.DUNE_WALKER, player) <= 0 && movementSpeed.hasModifier(RankineAttributes.DUNE_WALKER)) {
            movementSpeed.removeModifier(RankineAttributes.DUNE_WALKER);
            player.stepHeight = 0.5f;
        }
        if (!ground.getTags().contains(new ResourceLocation("rankine:movement_modifiers/sand")) && ground != Blocks.AIR && movementSpeed.hasModifier(RankineAttributes.DUNE_WALKER)) {
            movementSpeed.removeModifier(RankineAttributes.DUNE_WALKER);
            player.stepHeight = 0.5f;
        }
        if (EnchantmentHelper.getMaxEnchantmentLevel(RankineEnchantments.SNOW_DRIFTER, player) > 0) {
            if ((world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow")) || world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow"))) && !movementSpeed.hasModifier(RankineAttributes.SNOW_DRIFTER)) {
                movementSpeed.applyNonPersistentModifier(RankineAttributes.SNOW_DRIFTER);
                player.stepHeight = 1.0f;
            }
        } else  if (EnchantmentHelper.getMaxEnchantmentLevel(RankineEnchantments.SNOW_DRIFTER, player) <= 0 && movementSpeed.hasModifier(RankineAttributes.SNOW_DRIFTER)) {
            movementSpeed.removeModifier(RankineAttributes.SNOW_DRIFTER);
            player.stepHeight = 0.5f;
        }
        if ((!world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow")) && !world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/snow"))) && ground != Blocks.AIR && movementSpeed.hasModifier(RankineAttributes.SNOW_DRIFTER)) {
            movementSpeed.removeModifier(RankineAttributes.SNOW_DRIFTER);
            player.stepHeight = 0.5f;
        }
        if (EnchantmentHelper.getMaxEnchantmentLevel(RankineEnchantments.SPEED_SKATER, player) > 0) {
            if ((world.getBlockState(player.getPosition()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/ice")) || world.getBlockState(player.getPosition().down()).getBlock().getTags().contains(new ResourceLocation("rankine:movement_modifiers/ice"))) && !movementSpeed.hasModifier(RankineAttributes.SPEED_SKATER)) {
                movementSpeed.applyNonPersistentModifier(RankineAttributes.SPEED_SKATER);
                player.stepHeight = 1.0f;
            }
        } else  if (EnchantmentHelper.getMaxEnchantmentLevel(RankineEnchantments.SPEED_SKATER, player) <= 0 && movementSpeed.hasModifier(RankineAttributes.SPEED_SKATER)) {
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
                event.setCost(30);
            }
        } else if (event.getRight().getItem() == RankineItems.SNOWSHOES.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getEquipmentSlot() == EquipmentSlotType.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.SNOW_DRIFTER,event.getOutput()) != 1) {
                event.getOutput().addEnchantment(RankineEnchantments.SNOW_DRIFTER, 1);
                event.setCost(30);
            }
        } else if (event.getRight().getItem() == RankineItems.ICE_SKATES.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getEquipmentSlot() == EquipmentSlotType.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.SPEED_SKATER,event.getOutput()) != 1) {
                event.getOutput().addEnchantment(RankineEnchantments.SPEED_SKATER, 1);
                event.setCost(30);
            }
        }
    }

    /*
    @SubscribeEvent
    public void onFOVUpdate(FOVUpdateEvent event) {
        PlayerEntity player = event.getEntity();

        ModifiableAttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

        float modifier = 1.0f;
        if (movementSpeed.hasModifier(RankineAttributes.GRASS_PATH_MS)) {
            modifier = (float) RankineAttributes.GRASS_PATH_MS.getAmount();
        } else if (movementSpeed.hasModifier(RankineAttributes.ROMAN_CONCRETE_MS)) {
            modifier = 5.0f;
        }

       // event.setNewfov(event.getNewfov() * (float) movementSpeed.getValue() - modifier);
        event.setNewfov(event.getFov() + modifier);
    }

     */





    @SubscribeEvent
    public static void onFluidInteraction(BlockEvent.FluidPlaceBlockEvent event)
    {
        if (event.getState() == Blocks.COBBLESTONE.getDefaultState() && Config.GENERAL.IGNEOUS_COBBLE_GEN.get() && event.getWorld() instanceof World)
        {
            World worldIn = (World) event.getWorld();
            BlockPos pos = event.getPos();
            List<Block> adjPos = Arrays.asList(worldIn.getBlockState(pos.up()).getBlock(),worldIn.getBlockState(pos.south()).getBlock(),worldIn.getBlockState(pos.north()).getBlock(),
                    worldIn.getBlockState(pos.west()).getBlock(),worldIn.getBlockState(pos.east()).getBlock(),worldIn.getBlockState(pos.down()).getBlock());
            if (adjPos.contains(RankineBlocks.FELDSPAR_BLOCK.get()) && adjPos.contains(Blocks.QUARTZ_BLOCK))
            {
                event.setNewState(Blocks.GRANITE.getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.PLAGIOCLASE_FELDSPAR_BLOCK.get()) && adjPos.contains(Blocks.QUARTZ_BLOCK))
            {
                event.setNewState(RankineBlocks.GRAY_GRANITE.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.FELDSPAR_BLOCK.get()) && adjPos.contains(RankineBlocks.MICA_BLOCK.get()))
            {
                event.setNewState(RankineBlocks.GRANODIORITE.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.PLAGIOCLASE_FELDSPAR_BLOCK.get()) && adjPos.contains(RankineBlocks.MICA_BLOCK.get()))
            {
                event.setNewState(Blocks.DIORITE.getDefaultState());
                return;
            }  else if (adjPos.contains(RankineBlocks.PLAGIOCLASE_FELDSPAR_BLOCK.get()) && adjPos.contains(RankineBlocks.PYROXENE_BLOCK.get()))
            {
                event.setNewState(RankineBlocks.PYROXENE_GABBRO.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.PLAGIOCLASE_FELDSPAR_BLOCK.get()) && adjPos.contains(RankineBlocks.OLIVINE_BLOCK.get()))
            {
                event.setNewState(RankineBlocks.ANORTHOSITE.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.FELDSPAR_BLOCK.get()) && adjPos.contains(RankineBlocks.CALCIUM_SILICATE_BLOCK.get()))
            {
                event.setNewState(RankineBlocks.RED_PORPHYRY.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.PLAGIOCLASE_FELDSPAR_BLOCK.get()) && adjPos.contains(RankineBlocks.CALCIUM_SILICATE_BLOCK.get()))
            {
                event.setNewState(RankineBlocks.PURPLE_PORPHYRY.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.OLIVINE_BLOCK.get()) && adjPos.contains(RankineBlocks.PYROXENE_BLOCK.get()))
            {
                event.setNewState(RankineBlocks.PERIDOTITE.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.OLIVINE_BLOCK.get()) && adjPos.contains(RankineBlocks.CALCIUM_SILICATE_BLOCK.get()))
            {
                event.setNewState(RankineBlocks.TROCTOLITE.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.MAGNESIA_BLOCK.get()) && adjPos.contains(RankineBlocks.PYROXENE_BLOCK.get()))
            {
                event.setNewState(RankineBlocks.KOMATIITE.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.MAGNESIA_BLOCK.get()) && adjPos.contains(RankineBlocks.OLIVINE_BLOCK.get()))
            {
                event.setNewState(RankineBlocks.KIMBERLITE.get().getDefaultState());
                return;
            }

            switch (event.getWorld().getRandom().nextInt(8))
            {
                case 0:
                    event.setNewState(Blocks.GRANITE.getDefaultState());
                    break;
                case 1:
                    event.setNewState(Blocks.DIORITE.getDefaultState());
                    break;
                case 2:
                    event.setNewState(RankineBlocks.GRAY_GRANITE.get().getDefaultState());
                    break;
                case 3:
                    event.setNewState(RankineBlocks.PYROXENE_GABBRO.get().getDefaultState());
                    break;
                case 4:
                    event.setNewState(RankineBlocks.GRANODIORITE.get().getDefaultState());
                    break;
                case 5:
                    event.setNewState(RankineBlocks.ANORTHOSITE.get().getDefaultState());
                    break;
                case 6:
                    event.setNewState(RankineBlocks.RED_PORPHYRY.get().getDefaultState());
                    break;
                case 7:
                    event.setNewState(RankineBlocks.PURPLE_PORPHYRY.get().getDefaultState());
                    break;
            }
        } else if (event.getState() == Blocks.BASALT.getDefaultState() && Config.GENERAL.IGNEOUS_COBBLE_GEN.get() && event.getWorld() instanceof World)
        {
            World worldIn = (World) event.getWorld();
            BlockPos pos = event.getPos();
            List<Block> adjPos = Arrays.asList(worldIn.getBlockState(pos.up()).getBlock(),worldIn.getBlockState(pos.south()).getBlock(),worldIn.getBlockState(pos.north()).getBlock(),
                    worldIn.getBlockState(pos.west()).getBlock(),worldIn.getBlockState(pos.east()).getBlock());

            if (adjPos.contains(RankineBlocks.FELDSPAR_BLOCK.get()))
            {
                event.setNewState(RankineBlocks.RHYOLITE.get().getDefaultState());
                return;
            } else if (adjPos.contains(Blocks.QUARTZ_BLOCK))
            {
                event.setNewState(RankineBlocks.BLACK_DACITE.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.MICA_BLOCK.get()))
            {
                event.setNewState(RankineBlocks.RED_DACITE.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.PLAGIOCLASE_FELDSPAR_BLOCK.get()))
            {
                event.setNewState(Blocks.ANDESITE.getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.AMPHIBOLE_BLOCK.get()))
            {
                event.setNewState(RankineBlocks.HORNBLENDE_ANDESITE.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.PYROXENE_BLOCK.get()))
            {
                event.setNewState(Blocks.BASALT.getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.OLIVINE_BLOCK.get()))
            {
                event.setNewState(RankineBlocks.THOLEIITIC_BASALT.get().getDefaultState());
                return;
            } else if (adjPos.contains(Blocks.CRYING_OBSIDIAN))
            {
                event.setNewState(RankineBlocks.COMENDITE.get().getDefaultState());
                return;
            }
            switch (event.getWorld().getRandom().nextInt(7))
            {
                case 0:
                    event.setNewState(Blocks.BASALT.getDefaultState());
                    break;
                case 1:
                    event.setNewState(RankineBlocks.THOLEIITIC_BASALT.get().getDefaultState());
                    break;
                case 2:
                    event.setNewState(RankineBlocks.RED_DACITE.get().getDefaultState());
                    break;
                case 3:
                    event.setNewState(RankineBlocks.BLACK_DACITE.get().getDefaultState());
                    break;
                case 4:
                    event.setNewState(RankineBlocks.RHYOLITE.get().getDefaultState());
                    break;
                case 5:
                    event.setNewState(RankineBlocks.HORNBLENDE_ANDESITE.get().getDefaultState());
                    break;
                case 6:
                    event.setNewState(Blocks.ANDESITE.getDefaultState());
                    break;
            }
        } else if (event.getState() == Blocks.STONE.getDefaultState() && Config.GENERAL.METAMORPHIC_STONE_GEN.get()) { // Change to metamorphic gen
            World worldIn = (World) event.getWorld();
            BlockPos pos = event.getPos();
            List<Block> adjPos = Arrays.asList(worldIn.getBlockState(pos.south()).getBlock(),worldIn.getBlockState(pos.north()).getBlock(),
                    worldIn.getBlockState(pos.west()).getBlock(),worldIn.getBlockState(pos.east()).getBlock(),worldIn.getBlockState(pos.down()).getBlock());

            if (adjPos.contains(RankineBlocks.QUARTZ_SANDSTONE.get()) || adjPos.contains(Blocks.SANDSTONE)) {
                event.setNewState(RankineBlocks.QUARTZITE.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.CARBONACEOUS_SHALE.get())) {
                event.setNewState(RankineBlocks.SLATE.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.SLATE.get())) {
                event.setNewState(RankineBlocks.PHYLLITE.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.DOLOSTONE.get())) {
                event.setNewState(RankineBlocks.WHITE_MARBLE.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.TUFA_LIMESTONE.get())) {
                event.setNewState(RankineBlocks.BLACK_MARBLE.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.GRAY_GRANITE.get()) || adjPos.contains(Blocks.GRANITE)) {
                event.setNewState(RankineBlocks.GNEISS.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.GRANODIORITE.get()) || adjPos.contains(Blocks.DIORITE)) {
                event.setNewState(RankineBlocks.MICA_SCHIST.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.HORNBLENDE_ANDESITE.get()) || adjPos.contains(Blocks.ANDESITE)) {
                event.setNewState(RankineBlocks.MARIPOSITE.get().getDefaultState());
                return;
            } else if (adjPos.contains(RankineBlocks.BRECCIA.get()) || adjPos.contains(Blocks.ANDESITE)) {
                event.setNewState(RankineBlocks.SKARN.get().getDefaultState());
                return;
            }
            switch (event.getWorld().getRandom().nextInt(8))
            {
                case 0:
                    event.setNewState(RankineBlocks.QUARTZITE.get().getDefaultState());
                    break;
                case 1:
                    event.setNewState(RankineBlocks.SLATE.get().getDefaultState());
                    break;
                case 2:
                    event.setNewState(RankineBlocks.PHYLLITE.get().getDefaultState());
                    break;
                case 3:
                    event.setNewState(RankineBlocks.WHITE_MARBLE.get().getDefaultState());
                    break;
                case 4:
                    event.setNewState(RankineBlocks.BLACK_MARBLE.get().getDefaultState());
                    break;
                case 5:
                    event.setNewState(RankineBlocks.GNEISS.get().getDefaultState());
                    break;
                case 6:
                    event.setNewState(RankineBlocks.MICA_SCHIST.get().getDefaultState());
                    break;
                case 7:
                    event.setNewState(RankineBlocks.MARIPOSITE.get().getDefaultState());
                    break;
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
                event.getPlayer().inventory.addItemStackToInventory(PatchouliAPI.instance.getBookStack(new ResourceLocation("rankine:rankine_journal")));
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

            double damage = alloyTool.getAlloyAttackDamage(alloyTool.returnCompositionString(stack),alloyTool.returnAlloyUtils());
            event.addModifier(Attributes.ATTACK_DAMAGE,new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fc1"), "Rankine Damage modifier",
                    Math.max(damage - alloyTool.getAlloyWear(alloyTool.getWearModifierDmg((float) damage),stack.getItem().getDamage(stack),stack.getItem().getMaxDamage(stack)),0),
                    AttributeModifier.Operation.ADDITION));
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fc2"), "Rankine Attspeed modifier",
                    alloyTool.getAlloyAttackSpeed(alloyTool.returnCompositionString(stack),alloyTool.returnAlloyUtils()),
                    AttributeModifier.Operation.ADDITION));
        } else if (stack.getItem() instanceof IAlloyArmor && stack.getItem() instanceof ArmorItem && event.getSlotType() == ((ArmorItem)stack.getItem()).getEquipmentSlot())
        {
            IAlloyArmor alloyArmor = (IAlloyArmor) stack.getItem();

            int tough = alloyArmor.getAlloyArmorToughness(alloyArmor.returnCompositionString(stack),alloyArmor.returnAlloyUtils());
            int def = alloyArmor.getAlloyDamageReduceAmount(alloyArmor.returnCompositionString(stack),alloyArmor.returnAlloyUtils(),((ArmorItem)stack.getItem()).getEquipmentSlot());
            event.addModifier(Attributes.ARMOR_TOUGHNESS,new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe1fa1"), "Rankine Armor Toughness modifier",
                    tough,
                    AttributeModifier.Operation.ADDITION));
            event.addModifier(Attributes.ARMOR, new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe1fa2"), "Rankine Armor modifier",
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
    public static void onBlockBreak(PlayerEvent.BreakSpeed event)
    {
        if (!(event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() instanceof AxeItem) && event.getState().getBlock().getTags().contains(new ResourceLocation("minecraft/logs")) && Config.GENERAL.MANDATORY_AXE.get()) { event.setNewSpeed(0f); }
        if (event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() instanceof HammerItem) { event.setNewSpeed(0f); }
        
        if (Config.GENERAL.DISABLE_WOODEN_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_SWORD) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_WOODEN_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_AXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_WOODEN_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_WOODEN_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_WOODEN_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_HOE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_STONE_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_SWORD) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_STONE_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_AXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_STONE_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_STONE_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_STONE_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_HOE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_IRON_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_SWORD) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_IRON_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_AXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_IRON_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_IRON_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_IRON_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_HOE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_GOLDEN_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_SWORD) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_GOLDEN_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_AXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_GOLDEN_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_GOLDEN_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_GOLDEN_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_HOE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_DIAMOND_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_SWORD) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_DIAMOND_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_AXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_DIAMOND_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_DIAMOND_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_DIAMOND_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_HOE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_NETHERITE_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_SWORD) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_NETHERITE_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_AXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_NETHERITE_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_SHOVEL) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_NETHERITE_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_PICKAXE) { event.setNewSpeed(0f); }
        if (Config.GENERAL.DISABLE_NETHERITE_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_HOE) { event.setNewSpeed(0f); }
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
    public static void knifeClick(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        World world = event.getWorld();
        Direction direction = event.getFace();
        BlockPos pos = event.getPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = event.getPlayer();

        if (stack.getItem().getTags().contains(new ResourceLocation("rankine:knives"))) {
            Block target = state.getBlock();
            if (target == Blocks.GRASS_BLOCK && direction.equals(Direction.UP)) {
                world.playSound(player, pos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                world.setBlockState(pos, Blocks.DIRT.getDefaultState(), 3);
                if (!world.isRemote && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !world.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                    double d0 = (double) (world.rand.nextFloat() * 0.5F) + 0.25D;
                    double d1 = (double) (world.rand.nextFloat() * 0.5F) + 0.25D;
                    double d2 = (double) (world.rand.nextFloat() * 0.5F) + 0.25D;
                    ItemEntity itementity = new ItemEntity(world, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(Items.GRASS, 1));
                    itementity.setDefaultPickupDelay();
                    world.addEntity(itementity);
                }
                if (!world.isRemote) {
                    player.getHeldItemMainhand().damageItem(1, player, (p_220038_0_) -> {
                        p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                    });
                }
            } else if (target == RankineBlocks.AGED_CHEESE.get()) {
                if (state.get(CakeBlock.BITES) < 6) {
                    world.setBlockState(pos, state.with(CakeBlock.BITES, state.get(CakeBlock.BITES) + 1));
                    player.addItemStackToInventory(new ItemStack(RankineItems.CHEESE.get(), 1));
                } else {
                    player.addItemStackToInventory(new ItemStack(RankineItems.CHEESE.get(), 1));
                    world.removeBlock(pos, false);
                }
                player.getHeldItemMainhand().damageItem(1, player, (p_220040_1_) -> {
                    p_220040_1_.sendBreakAnimation(player.swingingHand);
                });
                world.playSound(player, pos, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS, 0.7F, world.getRandom().nextFloat() * 0.4F + 0.5F);

            } else if (state.getBlock() == Blocks.CAKE) {
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
        }

    }


    @SubscribeEvent
    public static void knifeBreak(BlockEvent.BreakEvent event) {
        ServerWorld worldIn = (ServerWorld) event.getWorld();
        PlayerEntity player = event.getPlayer();
        BlockPos pos = event.getPos();
        Block target = worldIn.getBlockState(pos).getBlock();

        if (player.getHeldItemMainhand().getTag() != null && player.getHeldItemMainhand().getItem().getTags().contains(new ResourceLocation("rankine:knives"))) {
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
            } else if (target == Blocks.DEAD_BUSH || target instanceof RankinePlantBlock || target instanceof SweetBerryBushBlock) {
                drops = new ItemStack(Items.STICK, 2 + worldIn.getRandom().nextInt(4));
            }
            if (drops != null && !worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                double d0 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, drops);
                itementity.setDefaultPickupDelay();
                worldIn.addEntity(itementity);
                worldIn.destroyBlock(pos, false);
            }
            if (drops != null && !worldIn.isRemote) {
                player.getHeldItemMainhand().damageItem(1, player, (p_220038_0_) -> {
                    p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });
            }
        }
    }

    @SubscribeEvent
    public static void nuggetDrop(BlockEvent.BreakEvent event) {
        ServerWorld worldIn = (ServerWorld) event.getWorld();
        PlayerEntity player = event.getPlayer();
        BlockPos pos = event.getPos();
        Block target = worldIn.getBlockState(pos).getBlock();
        if (target.getTags().contains(new ResourceLocation("rankine:nugget_stones"))) {
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
                if (foundPos != null && new Random().nextFloat() < Config.GENERAL.NUGGET_CHANCE.get() && !worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots && !player.abilities.isCreativeMode) {
                    Block b = worldIn.getBlockState(foundPos).getBlock();
                    ItemStack nug = ItemStack.EMPTY;
                    if (b == RankineBlocks.MAGNETITE_ORE.get()) {
                        nug = new ItemStack(Items.IRON_NUGGET);
                    } else if (b == RankineBlocks.MALACHITE_ORE.get()) {
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
                    } else if (b == RankineBlocks.COLUMBITE_ORE.get()) {
                        nug = new ItemStack(RankineItems.NIOBIUM_NUGGET.get());
                    } else if (b == RankineBlocks.TANTALITE_ORE.get()) {
                        nug = new ItemStack(RankineItems.TANTALUM_NUGGET.get());
                    } else if (b == RankineBlocks.WOLFRAMITE_ORE.get()) {
                        nug = new ItemStack(RankineItems.TUNGSTEN_NUGGET.get());
                    } else if (b == RankineBlocks.GREENOCKITE_ORE.get()) {
                        nug = new ItemStack(RankineItems.CADMIUM_NUGGET.get());
                    } else if (b == RankineBlocks.VANADINITE_ORE.get()) {
                        nug = new ItemStack(RankineItems.VANADIUM_NUGGET.get());
                    } else if (b == RankineBlocks.XENOTIME_ORE.get()) {
                        nug = new ItemStack(RankineItems.CERIUM_NUGGET.get());
                    } else if (b == RankineBlocks.URANINITE_ORE.get()) {
                        nug = new ItemStack(RankineItems.URANIUM_NUGGET.get());
                    }

                    if (!nug.isEmpty()) {
                        double d0 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                        double d1 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                        double d2 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                        ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, nug);
                        itementity.setDefaultPickupDelay();
                        worldIn.addEntity(itementity);
                        break;
                    }
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
        PlayerEntity player = event.getPlayer();

        if(item instanceof AxeItem) {
            BlockState activatedBlock = world.getBlockState(pos);
            Block b = activatedBlock.getBlock();

            if (Config.GENERAL.EXTRA_STRIPPABLES.get()) {
                ItemStack strip = null;
                if (b == Blocks.BIRCH_LOG || b == RankineBlocks.YELLOW_BIRCH_LOG.get() || b == RankineBlocks.BLACK_BIRCH_LOG.get()) {
                    if (world.getRandom().nextFloat() < 0.3) {
                        strip = new ItemStack(Items.PAPER, 1);
                    }
                } else if (b == RankineBlocks.CORK_OAK_LOG.get()) {
                    strip = new ItemStack(RankineItems.CORK.get(), 1);
                } else if (b.getTags().contains(new ResourceLocation("minecraft:logs"))) {
                    if (world.getRandom().nextFloat() < 0.3) {
                        strip = new ItemStack(Items.STICK, 1);
                    }
                }
                if (!world.isRemote && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !world.restoringBlockSnapshots && strip != null) {
                    spawnAsEntity(event.getWorld(), event.getPos(), strip);
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
                if (player.getHeldItem(Hand.MAIN_HAND).getItem().getTags().contains(new ResourceLocation("rankine:bronze_tools")) || player.getHeldItem(Hand.MAIN_HAND).getItem().getTags().contains(new ResourceLocation("rankine:flint_tools")) || player.getHeldItem(Hand.MAIN_HAND).getItem().getTags().contains(new ResourceLocation("rankine:pewter_tools")) || player.getHeldItem(Hand.MAIN_HAND).getItem().getTags().contains(new ResourceLocation("rankine:colored_gold_tools"))) {
                    if (CHANCE < Config.GENERAL.FLINT_DROP_CHANCE.get()) {
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
                if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.FORAGING,player.getHeldItem(Hand.MAIN_HAND)) > 0)
                {
                    ItemStack FOOD;
                    World worldIn = (World) event.getWorld();
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

                    if (CHANCE < Config.GENERAL.FORAGING_CHANCE.get())
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
                    if (CHANCE < Config.GENERAL.FORAGING_CHANCE.get() * 0.3) {
                        FOOD = new ItemStack(Items.POTATO,1);
                    } else if (CHANCE < Config.GENERAL.FORAGING_CHANCE.get() * 0.6) {
                        FOOD = new ItemStack(Items.CARROT,1);
                    } else if (CHANCE < Config.GENERAL.FORAGING_CHANCE.get()) {
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
    public static void luckyBreak(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        if (!player.abilities.isCreativeMode && player.getHeldItemOffhand().getItem() == RankineItems.LUCK_PENDANT.get()) {
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
