package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.capabilities.ChunkRetrogenProvider;
import com.cannolicatfish.rankine.commands.BlockWallCommand;
import com.cannolicatfish.rankine.commands.CreateAlloyCommand;
import com.cannolicatfish.rankine.commands.GiveTagCommand;
import com.cannolicatfish.rankine.events.handlers.common.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.event.world.*;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RankineEventHandler {

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<LevelChunk> event) {
        event.addCapability(new ResourceLocation("rankine:retrogen_chunk"), new ChunkRetrogenProvider());
    }
    @SubscribeEvent
    public static void playSoundAtEntityEvent(PlaySoundAtEntityEvent event) {
        PlaySoundAtEntityHandler.onPlaySound(event);
    }
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CreateAlloyCommand.register(event.getDispatcher());
        GiveTagCommand.register(event.getDispatcher());
        BlockWallCommand.register(event.getDispatcher());
    }
    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        PlayerTickHandler.onPlayerTick(event);
        PlayerTickHandler.movementModifier(event);
    }
    @SubscribeEvent
    public static void worldLoadEvent(WorldEvent.Load event) {
        WorldLoadHandler.updateFuelValues(event);
    }
    @SubscribeEvent
    public static void furnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {
        FurnaceFuelBurnTimeHandler.updateFuelValues(event);
    }
    @SubscribeEvent
    public static void entityInteractEvent(PlayerInteractEvent.EntityInteract event) {
        EntityInteractHandler.breedables(event);
    }
    @SubscribeEvent
    public static void itemExpireEvent(ItemExpireEvent event) {
        ItemExpireHandler.onItemPickup(event);
    }
    @SubscribeEvent
    public static void noWater(BlockEvent.CreateFluidSourceEvent event) {
        CreateFluidSourceHandler.noWater(event);
    }
    @SubscribeEvent
    public static void entityJoinWorldEvent(EntityJoinWorldEvent event) {
        EntityJoinWorldHandler.onLightningEvent(event);
        EntityJoinWorldHandler.onSheepJoinWorld(event);
    }
    @SubscribeEvent
    public static void onPistonExtend(PistonEvent.Pre event) {
        PistonHandler.onPistonExtendPre(event);
    }
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void chunkLoadEvent(ChunkEvent.Load event) {
        ChunkLoadHandler.retrogenChunk(event);
    }
    @SubscribeEvent
    public static void itemPickupEvent(PlayerEvent.ItemPickupEvent event) {
        ItemPickupHandler.onItemPickup(event);
    }
    @SubscribeEvent
    public static void wandererTradesEvent(WandererTradesEvent event) {
        WandererTradesHandler.addWandererTrades(event);
    }
    @SubscribeEvent
    public static void villagerTradesEvent(VillagerTradesEvent event) {
        VillagerTradesHandler.addVillagerTrades(event);
    }
    @SubscribeEvent
    public static void saplingGrowTreeEvent(SaplingGrowTreeEvent event) {
        SaplingGrowTreeHandler.onSaplingGrow(event);
    }
    @SubscribeEvent
    public static void onCropTrample(BlockEvent.FarmlandTrampleEvent event) {
        FarmlandTrampleHandler.onCropTrample(event);
    }
    @SubscribeEvent
    public static void anvilUpdateEvent(AnvilUpdateEvent event) {
        AnvilUpdateHandler.specialEnchants(event);
    }
    @SubscribeEvent
    public static void livingSetAttackTargetEvent(LivingSetAttackTargetEvent event) {
        LivingSetAttackTargetHandler.onLivingSetAttackTarget(event);
    }
    @SubscribeEvent
    public static void livingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        LivingUpdateHandler.onLivingUpdate(event);
        LivingUpdateHandler.onEnvironmentEffect(event);
    }
    @SubscribeEvent
    public static void onToolUse(BlockEvent.BlockToolModificationEvent event) {
        BlockToolModificationHandler.onToolUse(event);
    }
    @SubscribeEvent
    public static void livingDamageEntity(LivingDamageEvent event) {
        LivingDamageHandler.onDamageEntity(event);
        LivingDamageHandler.onLivingDamaged(event);
        LivingDamageHandler.onParryEvent(event);
    }
    @SubscribeEvent
    public static void onLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        LeftClickBlockHandler.leftClickTools(event);
    }
    @SubscribeEvent
    public static void onFluidInteraction(BlockEvent.FluidPlaceBlockEvent event) {
        FluidPlaceBlockHandler.onFluidInteraction(event);
    }
    @SubscribeEvent
    public static void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerLoggedInHandler.onPlayerJoin(event);
    }
    @SubscribeEvent
    public static void onItemAttributeModification(ItemAttributeModifierEvent event) {
        ItemAttributeModifierHandler.onItemAttributeModification(event);
    }

    @SubscribeEvent
    public static void breakSpeedEvent(PlayerEvent.BreakSpeed event) {
        BreakSpeedHandler.treeChop(event);
        BreakSpeedHandler.onBreakSpeed(event);
    }
    @SubscribeEvent
    public static void breakSpeedEvent(BlockEvent.BreakEvent event) {
        BlockBreakHandler.blockBreakingEvents(event);
    }

    @SubscribeEvent
    public static void rightClickBlockEvent(PlayerInteractEvent.RightClickBlock event) {
        RightClickBlockHandler.onRightClickBlock(event);
        RightClickBlockHandler.rightClickBlockEvent(event);
    }



}
