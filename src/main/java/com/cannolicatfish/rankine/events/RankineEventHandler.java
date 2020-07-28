package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.init.ModTags;
import com.cannolicatfish.rankine.items.TransformationStaffItem;
import com.cannolicatfish.rankine.items.tools.ItemHammer;
import net.minecraft.block.*;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static net.minecraft.block.Block.nudgeEntitiesWithNewState;
import static net.minecraft.block.Block.spawnAsEntity;

@Mod.EventBusSubscriber
public class RankineEventHandler {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        event.getPlayer().addExhaustion(Config.GLOBAL_BREAK_EXHAUSTION.get().floatValue());
    }

    @SubscribeEvent
    public static void luckyBreak(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        if (!player.abilities.isCreativeMode && player.getHeldItemOffhand().getItem() == ModItems.LUCK_PENDANT) {
            if (event.getState().getBlock().isIn(ModTags.LUCK_PENDANT)) {
                if (new Random().nextFloat() < 0.3f) {
                    for (ItemStack i : Block.getDrops(event.getState(), (ServerWorld) event.getWorld().getWorld(), event.getPos(), null)) {
                        spawnAsEntity((World) event.getWorld(), event.getPos(), new ItemStack(i.getItem(), 1));
                    }
                }
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
    public static void onBlockBreak(PlayerEvent.BreakSpeed event)
    {
        if (!(event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() instanceof AxeItem) && event.getState().getBlock().getTags().contains(new ResourceLocation("minecraft/logs")) && Config.MANDATORY_AXE.get())
        {
            event.setNewSpeed(0f);
        }
        if (event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() instanceof ItemHammer)
        {
            event.setNewSpeed(0f);
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
    public static void axeStrip(PlayerInteractEvent.RightClickBlock event)
    {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        World world = event.getWorld();
        Direction direction = event.getFace();
        BlockPos pos = event.getPos();
        BlockPos posWithOffset = pos.offset(direction);
        PlayerEntity player = event.getPlayer();

        if(item instanceof AxeItem) {
        BlockState activatedBlock = world.getBlockState(pos);
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

    /*
    @SubscribeEvent
    public static void transformBlocks(PlayerInteractEvent.RightClickBlock event)
    {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        PlayerEntity player = event.getPlayer();
        ItemStack newBlock = player.getHeldItemOffhand();

        if(item instanceof TransformationStaffItem) {
            BlockState activatedBlock = world.getBlockState(pos);
            Block block = activatedBlock.getBlock();
            if (newBlock.getItem() instanceof BlockItem) {
                if (((BlockItem) newBlock.getItem()).getBlock().isIn(ModTags.TRANSFORMABLE)) {
                    for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-2, -2, -2), pos.add(2, 2, 2))) {
                        if (world.getBlockState(blockpos).getBlock() == block) {
                            world.setBlockState(blockpos, ((BlockItem) newBlock.getItem()).getBlock().getDefaultState());
                            stack.damageItem(1, player, (entity) -> {
                                entity.sendBreakAnimation(event.getHand());
                            });
                            if (!player.abilities.isCreativeMode) {
                                newBlock.shrink(1);
                            }
                        }

                    }
                    world.playSound(player, pos, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    player.swingArm(event.getHand());
                    //event.setResult(Event.Result.ALLOW);
                }
            }
        }
    }
*/


}
