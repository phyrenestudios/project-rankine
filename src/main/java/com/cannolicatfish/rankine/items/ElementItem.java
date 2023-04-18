package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.blocks.FumaroleBlock;
import com.cannolicatfish.rankine.blocks.gases.GasBlock;
import com.cannolicatfish.rankine.entities.ReactiveItemEntity;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class ElementItem extends Item {
    float waterReactive;
    boolean canBreakBlocks;
    int radioactive;
    public ElementItem(float waterReactive, boolean canBreakBlocks, int radioactive, Properties properties) {
        super(properties);
        this.waterReactive = waterReactive;
        this.canBreakBlocks = canBreakBlocks;
        this.radioactive = radioactive;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (Config.HARD_MODE.WATER_REACTIVE.get() && waterReactive > 0.0f) {
            if (waterReactive < 2.0f) {
                tooltip.add(Component.literal("Warning! Reactive with water!").withStyle(ChatFormatting.GRAY));
            } else if (waterReactive >= 2.0f) {
                tooltip.add(Component.literal("Warning! Highly reactive with water!").withStyle(ChatFormatting.GRAY));
            }
        }
        if (Config.HARD_MODE.RADIOACTIVE.get() && radioactive > 0) {
            tooltip.add(Component.literal("Warning: Radioactive! Prolonged exposure is harmful.").withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
            if (Config.HARD_MODE.WATER_REACTIVE.get() && waterReactive > 0.0f) {
                if (entityIn.isInWater() && isSelected) {
                    if (entityIn instanceof Player) {
                        Player player = (Player) entityIn;
                        if (!player.getAbilities().instabuild) {
                            stack.shrink(1);
                            BlockPos pos = entityIn.blockPosition();
                            if (canBreakBlocks) {
                                entityIn.getCommandSenderWorld().explode(null, pos.getX(), pos.getY() + 16 * .0625D, pos.getZ(), this.waterReactive, Level.ExplosionInteraction.BLOCK);
                            } else {
                                entityIn.getCommandSenderWorld().explode(null, pos.getX(), pos.getY() + 16 * .0625D, pos.getZ(), this.waterReactive, Level.ExplosionInteraction.NONE);
                            }
                            for (int i = 0; i < Math.round(this.waterReactive); i++) {
                                BlockPos close = BlockPos.findClosestMatch(pos,3,3,B -> worldIn.isEmptyBlock(B) && !(worldIn.getBlockState(B).getBlock() instanceof GasBlock)
                                        && BlockPos.findClosestMatch(B,1,1,P -> worldIn.getBlockState(P).getBlock() instanceof GasBlock || worldIn.getBlockState(P).getBlock() instanceof FumaroleBlock).orElse(null) != null).orElse(null);
                                if (close == null) {
                                    break;
                                } else {
                                    worldIn.setBlock(close, RankineBlocks.HYDROGEN_GAS_BLOCK.get().defaultBlockState(),3);
                                }
                            }
                        }
                    }
                }
            }
            if (Config.HARD_MODE.RADIOACTIVE.get()) {
                if (entityIn instanceof Player) {
                    Player player = (Player) entityIn;
                    if (!player.isCreative()) {
                        MobEffectInstance rad = player.getEffect(RankineMobEffects.RADIATION_POISONING.get());
                        int dur = rad == null ? Math.max(0,this.radioactive * stack.getCount()) : Math.max(0,rad.getDuration() + this.radioactive * stack.getCount());
                        if (dur > 0) {
                            player.addEffect(new MobEffectInstance(RankineMobEffects.RADIATION_POISONING.get(),dur,0, false, false, false));
                        }
                    }
                }
            }
        }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return Config.HARD_MODE.WATER_REACTIVE.get();
    }

    @Override
    public Entity createEntity(Level world, Entity location, ItemStack itemstack) {
        if (waterReactive > 0.0f) {
            ReactiveItemEntity result = getResult(location,itemstack);
            result.setPickUpDelay(40);
            result.setDeltaMovement(location.getDeltaMovement());
            return result;
        } else {
            return null;
        }

    }

    public ReactiveItemEntity getResult(Entity location, ItemStack itemstack) {
        switch (ForgeRegistries.ITEMS.getKey(this).toString()) {
            case "rankine:lithium_ingot":
                return new ReactiveItemEntity(location.level,location.getX(),location.getY(),location.getZ(), this.waterReactive,
                        this.canBreakBlocks, itemstack, RankineItems.LITHIUM_HYDROXIDE.get(),RankineBlocks.HYDROGEN_GAS_BLOCK.get());
            case "rankine:sodium_ingot":
                return new ReactiveItemEntity(location.level,location.getX(),location.getY(),location.getZ(), this.waterReactive,
                        this.canBreakBlocks, itemstack, RankineItems.SODIUM_HYDROXIDE.get(),RankineBlocks.HYDROGEN_GAS_BLOCK.get());
            case "rankine:potassium_ingot":
                return new ReactiveItemEntity(location.level,location.getX(),location.getY(),location.getZ(), this.waterReactive,
                        this.canBreakBlocks, itemstack, RankineItems.POTASSIUM_HYDROXIDE.get(),RankineBlocks.HYDROGEN_GAS_BLOCK.get());
            case "rankine:rubidium_ingot":
                return new ReactiveItemEntity(location.level,location.getX(),location.getY(),location.getZ(), this.waterReactive,
                        this.canBreakBlocks, itemstack, RankineItems.RUBIDIUM_HYDROXIDE.get(),RankineBlocks.HYDROGEN_GAS_BLOCK.get());
            case "rankine:cesium_ingot":
                return new ReactiveItemEntity(location.level,location.getX(),location.getY(),location.getZ(), this.waterReactive,
                        this.canBreakBlocks, itemstack, RankineItems.CESIUM_HYDROXIDE.get(),RankineBlocks.HYDROGEN_GAS_BLOCK.get());
            case "rankine:francium_ingot":
                return new ReactiveItemEntity(location.level,location.getX(),location.getY(),location.getZ(), this.waterReactive,
                        this.canBreakBlocks, itemstack, RankineItems.FRANCIUM_HYDROXIDE.get(),RankineBlocks.HYDROGEN_GAS_BLOCK.get());
            default:
                return new ReactiveItemEntity(location.level,location.getX(),location.getY(),location.getZ(), itemstack);
        }
    }

}
