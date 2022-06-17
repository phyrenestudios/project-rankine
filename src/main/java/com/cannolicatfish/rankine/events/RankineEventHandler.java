package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.events.handlers.common.AnvilUpdateHandler;
import com.cannolicatfish.rankine.events.handlers.common.BlockBreakHandler;
import com.cannolicatfish.rankine.events.handlers.common.BlockToolInteractHandler;
import com.cannolicatfish.rankine.events.handlers.common.BreakSpeedHandler;
import com.cannolicatfish.rankine.events.handlers.common.CreateFluidSourceHandler;
import com.cannolicatfish.rankine.events.handlers.common.CropTrampleHandler;
import com.cannolicatfish.rankine.events.handlers.common.EntityInteractHandler;
import com.cannolicatfish.rankine.events.handlers.common.EntityJoinWorldHandler;
import com.cannolicatfish.rankine.events.handlers.common.FluidPlaceBlockHandler;
import com.cannolicatfish.rankine.events.handlers.common.FurnaceFuelBurnTimeHandler;
import com.cannolicatfish.rankine.events.handlers.common.HarvestCheckHandler;
import com.cannolicatfish.rankine.events.handlers.common.ItemAttributeModifierHandler;
import com.cannolicatfish.rankine.events.handlers.common.ItemPickupHandler;
import com.cannolicatfish.rankine.events.handlers.common.LeftClickBlockHandler;
import com.cannolicatfish.rankine.events.handlers.common.LivingDamagedHandler;
import com.cannolicatfish.rankine.events.handlers.common.LivingSetAttackTargetHandler;
import com.cannolicatfish.rankine.events.handlers.common.LivingUpdateHandler;
import com.cannolicatfish.rankine.events.handlers.common.PlayerLoginHandler;
import com.cannolicatfish.rankine.events.handlers.common.PlayerTickHandler;
import com.cannolicatfish.rankine.events.handlers.common.RegisterCommandHandler;
import com.cannolicatfish.rankine.events.handlers.common.RightClickBlockHandler;
import com.cannolicatfish.rankine.events.handlers.common.SaplingGrowHandler;
import com.cannolicatfish.rankine.events.handlers.common.VillagerTradeHandler;
import com.cannolicatfish.rankine.events.handlers.common.WanderingTradeHandler;
import com.cannolicatfish.rankine.events.handlers.common.WorldLoadHandler;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.SaplingGrowTreeEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RankineEventHandler {

    @SubscribeEvent
    public static void onItemPickup(PlayerEvent.ItemPickupEvent event) {
        ItemPickupHandler.onItemPickup(event);
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        RegisterCommandHandler.registerCommands(event);
    }

    @SubscribeEvent
    public static void addWandererTrades(WandererTradesEvent event) {
        WanderingTradeHandler.addWandererTrades(event);
    }

    @SubscribeEvent
    public static void addVillagerTrades(VillagerTradesEvent event) {
        VillagerTradeHandler.addVillagerTrades(event);
    }

    @SubscribeEvent
    public static void onSaplingGrow(SaplingGrowTreeEvent event) {
        SaplingGrowHandler.onSaplingGrow(event);
    }

    @SubscribeEvent
    public static void onCropTrample(BlockEvent.FarmlandTrampleEvent event) {
        CropTrampleHandler.onCropTrample(event);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerTickHandler.onPlayerTick(event);
        PlayerTickHandler.movementModifier(event);
    }

    @SubscribeEvent
    public static void onLightningEvent(EntityJoinWorldEvent event) {
        EntityJoinWorldHandler.onLightningEvent(event);
        EntityJoinWorldHandler.onSheepJoinWorld(event);
    }

    @SubscribeEvent
    public static void onLivingDamaged(LivingDamageEvent event) {
        LivingDamagedHandler.onLivingDamaged(event);
        LivingDamagedHandler.onParryEvent(event);
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
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingUpdateHandler.onEnvironmentEffect(event);
        LivingUpdateHandler.onLivingUpdate(event);
    }


    @SubscribeEvent
    public static void fuelValues(WorldEvent.Load event) {
        WorldLoadHandler.fuelValues(event);
    }


    @SubscribeEvent
    public static void fuelValues(FurnaceFuelBurnTimeEvent event) {
        FurnaceFuelBurnTimeHandler.fuelValues(event);
    }


    @SubscribeEvent
    public static void onAnvilUpdate( AnvilUpdateEvent event) {
        AnvilUpdateHandler.specialEnchants(event);
    }


    @SubscribeEvent
    public static void onFluidInteraction(BlockEvent.FluidPlaceBlockEvent event) {
        FluidPlaceBlockHandler.onFluidInteraction(event);
    }
/*
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (event.getBlockSnapshot().getReplacedBlock().getBlock() instanceof GasBlock && event.getPlacedBlock().getBlock() instanceof AbstractFireBlock) {
            event.setCanceled(true);
        }
    }*/

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerLoginHandler.onPlayerJoin(event);
    }


    @SubscribeEvent
    public static void onBlockHarvest(PlayerEvent.HarvestCheck event) {
        HarvestCheckHandler.onBlockHarvest(event);
    }

    @SubscribeEvent
    public static void onItemAttributeModification(ItemAttributeModifierEvent event) {
        ItemAttributeModifierHandler.onItemAttributeModification(event);
    }
    
    @SubscribeEvent
    public static void onToolUse(BlockEvent.BlockToolInteractEvent event) {
        BlockToolInteractHandler.onToolUse(event);
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        BreakSpeedHandler.onBreakSpeed(event);
        BreakSpeedHandler.treeChop(event);
    }

    @SubscribeEvent
    public static void onDamageEntity(LivingDamageEvent event) {
        LivingDamagedHandler.onDamageEntity(event);
    }

    @SubscribeEvent
    public static void onLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        LeftClickBlockHandler.onLeftClick(event);
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
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        RightClickBlockHandler.worldDye(event);
        RightClickBlockHandler.onRightClickBlock(event);
        RightClickBlockHandler.flintFire(event);
        RightClickBlockHandler.axeStrip(event);
    }

    @SubscribeEvent
    public static void noWater(BlockEvent.CreateFluidSourceEvent event) {
        CreateFluidSourceHandler.noWater(event);
    }

    @SubscribeEvent
    public static void blockBreakingEvents(BlockEvent.BreakEvent event) {
        BlockBreakHandler.blockBreakingEvents(event);
    }

    @SubscribeEvent
    public static void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
        LivingSetAttackTargetHandler.onLivingSetAttackTarget(event);
    }

    @SubscribeEvent
    public static void onBreedEvent(PlayerInteractEvent.EntityInteract event) {
        EntityInteractHandler.onBreedEvent(event);
    }

}
