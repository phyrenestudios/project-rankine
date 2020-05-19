package com.cannolicatfish.rankine.events;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.blocks.ModBlocks;
import com.cannolicatfish.rankine.items.ModItems;
import com.cannolicatfish.rankine.items.tools.ItemHammer;
import com.cannolicatfish.rankine.recipe.PistonCrusherRecipes;
import net.minecraft.block.Blocks;
import net.minecraft.block.LogBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.PistonEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class RankineEventHandler {

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
        if (!(event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() instanceof AxeItem) && event.getState().getBlock() instanceof LogBlock && Config.MANDATORY_AXE.get())
        {
            event.setNewSpeed(0f);
        }
        if (event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() instanceof ItemHammer)
        {
            event.setNewSpeed(0f);
        }
    }


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
                float f = 0.5F;
                Random rand = new Random();
                double d0 = (double)(rand.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double)(rand.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double)(rand.nextFloat() * 0.5F) + 0.25D;
                ItemEntity itementity = new ItemEntity(worldIn, (double)end.getX() + d0, (double)end.getY() + d1, (double)end.getZ() + d2, new ItemStack(ModItems.FELDSPAR,2));
                itementity.setDefaultPickupDelay();
                worldIn.addEntity(itementity);
            }
        }
    }
/*
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

}
