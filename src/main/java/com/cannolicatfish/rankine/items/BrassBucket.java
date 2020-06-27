package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class BrassBucket extends BucketItem {
    private final Fluid containedBlock;
    public BrassBucket(Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder);
        this.containedBlock = supplier.get();
        this.getContainerItem(new ItemStack(ModItems.BRASS_BUCKET));

    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(ModItems.BRASS_BUCKET);
    }

    @Override
    public boolean hasContainerItem(ItemStack itemStack) {
        return true;
    }

    @Override
    protected ItemStack emptyBucket(ItemStack p_203790_1_, PlayerEntity p_203790_2_) {
        return !p_203790_2_.abilities.isCreativeMode ? new ItemStack(ModItems.BRASS_BUCKET) : p_203790_1_;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, this.containedBlock == Fluids.EMPTY ? RayTraceContext.FluidMode.SOURCE_ONLY : RayTraceContext.FluidMode.NONE);
        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(playerIn, worldIn, itemstack, raytraceresult);
        if (ret != null) return ret;
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return new ActionResult<>(ActionResultType.PASS, itemstack);
        } else if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
            return new ActionResult<>(ActionResultType.PASS, itemstack);
        } else {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
            BlockPos blockpos = blockraytraceresult.getPos();
            if (worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos, blockraytraceresult.getFace(), itemstack)) {
                if (this.containedBlock == Fluids.EMPTY) {
                    BlockState blockstate1 = worldIn.getBlockState(blockpos);
                    if (blockstate1.getBlock() instanceof IBucketPickupHandler) {
                        Fluid fluid = ((IBucketPickupHandler)blockstate1.getBlock()).pickupFluid(worldIn, blockpos, blockstate1);
                        if (fluid != Fluids.EMPTY) {
                            playerIn.addStat(Stats.ITEM_USED.get(this));

                            SoundEvent soundevent = this.containedBlock.getAttributes().getEmptySound();
                            if(soundevent == null) soundevent = fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_FILL_LAVA : SoundEvents.ITEM_BUCKET_FILL;
                            playerIn.playSound(soundevent, 1.0F, 1.0F);
                            ItemStack itemstack1 = this.fillBucket(itemstack, playerIn, fluid.getFilledBucket());
                            if (!worldIn.isRemote) {
                                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity)playerIn, new ItemStack(fluid.getFilledBucket()));
                            }

                            return new ActionResult<>(ActionResultType.SUCCESS, itemstack1);
                        }
                    }

                    return new ActionResult<>(ActionResultType.FAIL, itemstack);
                } else {
                    BlockState blockstate = worldIn.getBlockState(blockpos);
                    BlockPos blockpos1 = blockstate.getBlock() instanceof ILiquidContainer && this.containedBlock == Fluids.WATER ? blockpos : blockraytraceresult.getPos().offset(blockraytraceresult.getFace());
                    if (this.tryPlaceContainedLiquid(playerIn, worldIn, blockpos1, blockraytraceresult)) {
                        this.onLiquidPlaced(worldIn, itemstack, blockpos1);
                        if (playerIn instanceof ServerPlayerEntity) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerIn, blockpos1, itemstack);
                        }

                        playerIn.addStat(Stats.ITEM_USED.get(this));
                        return new ActionResult<>(ActionResultType.SUCCESS, this.emptyBucket(itemstack, playerIn));
                    } else {
                        return new ActionResult<>(ActionResultType.FAIL, itemstack);
                    }
                }
            } else {
                return new ActionResult<>(ActionResultType.FAIL, itemstack);
            }
        }
    }

    private ItemStack fillBucket(ItemStack emptyBuckets, PlayerEntity player, Item fullBucket) {
        System.out.println(fullBucket);
        if (fullBucket.getItem() == Items.WATER_BUCKET)
        {
            fullBucket = ModItems.WATER_BRASS_BUCKET;
        }
        if (fullBucket.getItem() == Items.LAVA_BUCKET)
        {
            //player.playSound();
            fullBucket = ModItems.LAVA_BRASS_BUCKET;
        }
        if (fullBucket.getItem() == Items.MILK_BUCKET)
        {
            //player.playSound();
            fullBucket = ModItems.MILK_BRASS_BUCKET;
        }
        if (player.abilities.isCreativeMode) {
            return emptyBuckets;
        } else {
            emptyBuckets.shrink(1);
            if (emptyBuckets.isEmpty()) {
                return new ItemStack(fullBucket);
            } else {
                if (!player.inventory.addItemStackToInventory(new ItemStack(fullBucket))) {
                    player.dropItem(new ItemStack(fullBucket), false);
                }

                return emptyBuckets;
            }
        }
    }

    private Item getBrassBucket(Fluid fluid)
    {
        if (fluid == Fluids.WATER)
        {
            return ModItems.WATER_BRASS_BUCKET;
        }
        return ModItems.BRASS_BUCKET;
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (stack.getItem() == ModItems.LAVA_BRASS_BUCKET)
        {
            ((LivingEntity) entityIn).setFire(2);
        }


    }
}
