package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
    private int ticks= 0;

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (Config.HARD_MODE.WATER_REACTIVE.get() && waterReactive > 0.0f) {
            if (waterReactive < 2.0f) {
                tooltip.add(new StringTextComponent("Warning! Reactive with water!").mergeStyle(TextFormatting.GRAY));
            } else if (waterReactive >= 2.0f) {
                tooltip.add(new StringTextComponent("Warning! Highly reactive with water!").mergeStyle(TextFormatting.GRAY));
            }
        }
        if (Config.HARD_MODE.RADIOACTIVE.get() && radioactive > 0) {
            tooltip.add(new StringTextComponent("Warning: Radioactive! Prolonged exposure is harmful.").mergeStyle(TextFormatting.GRAY));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

        if (Config.HARD_MODE.WATER_REACTIVE.get() && waterReactive > 0.0f) {
            if (entityIn.isInWater() && isSelected) {
                if (entityIn instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entityIn;
                    if (!player.abilities.isCreativeMode) {
                        stack.shrink(1);
                        BlockPos pos = entityIn.getPosition();
                        if (canBreakBlocks) {
                            entityIn.getEntityWorld().createExplosion(null, pos.getX(), pos.getY() + 16 * .0625D, pos.getZ(), this.waterReactive, Explosion.Mode.BREAK);
                        } else {
                            entityIn.getEntityWorld().createExplosion(null, pos.getX(), pos.getY() + 16 * .0625D, pos.getZ(), this.waterReactive, Explosion.Mode.NONE);
                        }
                    }
                }
            }
        }
        if (Config.HARD_MODE.RADIOACTIVE.get() && radioactive > 0) {
            if (entityIn instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entityIn;
                ++ticks;
                if (ticks % radioactive == 0) {
                    player.attackEntityFrom(DamageSource.MAGIC, 1);
                }
            }
        }
    }



}
