package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.recipe.PistonCrusherRecipes;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AlloyHammerItem extends HammerItem implements IAlloyTool{
    private final AlloyUtils alloy;
    public AlloyHammerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, AlloyUtils alloy, Properties properties) {
        super(attackDamageIn, attackSpeedIn, tier, properties);
        this.alloy = alloy;
    }

    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getEntityWorld().isRainingAt(target.getPosition()) && getLightningModifier(stack) == 1)
        {
            LightningBoltEntity ent = new LightningBoltEntity(EntityType.LIGHTNING_BOLT,attacker.world);
            //ent.func_233576_c_(Vector3d.func_237492_c_(new BlockPos(target.getPosX(),target.getPosY(),target.getPosZ())));
            ent.setPosition(target.getPosX(),target.getPosY(),target.getPosZ());
            ((ServerWorld)target.getEntityWorld()).addEntity(ent);
        }
        if (getDazeModifier(stack) != 0)
        {
            if (attacker instanceof PlayerEntity)
            {
                PlayerEntity player = (PlayerEntity) attacker;
                if (player.getCooledAttackStrength(0) >= (1f))
                {
                    target.addPotionEffect(new EffectInstance(Effects.SLOWNESS,getDazeModifier(stack)*20, 2));
                } else {
                    target.addPotionEffect(new EffectInstance(Effects.SLOWNESS,getDazeModifier(stack)*20, 1));
                }
            } else {
                target.addPotionEffect(new EffectInstance(Effects.SLOWNESS,getDazeModifier(stack)*20, 1));
            }

        }
        stack.damageItem(calcDurabilityLoss(stack,this.alloy,attacker.getEntityWorld(),attacker,true), attacker, (entity) -> {
            entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if(PistonCrusherRecipes.getInstance().getPrimaryResult(new ItemStack(state.getBlock())).getValue()[0] > 0f && this.getTier().getHarvestLevel() >= state.getBlock().getHarvestLevel(state))
        {
            if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
                stack.damageItem(calcDurabilityLoss(stack,this.alloy,worldIn,entityLiving,true), entityLiving, (p_220038_0_) -> {
                    p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });
            }
            if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
                float f = 0.5F;
                double d0 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                Item item;
                int val;

                if (getBlastModifier(stack) > 0)
                {
                    BlockRayTraceResult raytraceresult = rayTrace(worldIn, (PlayerEntity) entityLiving, RayTraceContext.FluidMode.ANY);

                    BlockPos exppos;
                    switch (raytraceresult.getFace())
                    {
                        case EAST:
                            exppos = new BlockPos(pos.getX() - (getBlastModifier(stack) - 1),pos.getY(),pos.getZ());
                            break;
                        case WEST:
                            exppos = new BlockPos(pos.getX() + (getBlastModifier(stack) - 1),pos.getY(),pos.getZ());
                            break;
                        case DOWN:
                            exppos = new BlockPos(pos.getX(),pos.getY() + (getBlastModifier(stack) - 1),pos.getZ());
                            break;
                        case UP:
                            exppos = new BlockPos(pos.getX(),pos.getY() - (getBlastModifier(stack) - 1),pos.getZ());
                            break;
                        case NORTH:
                            exppos = new BlockPos(pos.getX(),pos.getY(),pos.getZ() + (getBlastModifier(stack) - 1));
                            break;
                        case SOUTH:
                            exppos = new BlockPos(pos.getX(),pos.getY(),pos.getZ() - (getBlastModifier(stack) - 1));
                            break;
                        default:
                            exppos = pos;
                    }
                    System.out.println(raytraceresult.getFace());
                    System.out.println(exppos);
                    worldIn.removeBlock(exppos,false);
                    worldIn.createExplosion(null, exppos.getX(), exppos.getY() + 16 * .0625D, exppos.getZ(), 0.5F + getBlastModifier(stack), Explosion.Mode.DESTROY);
                    if (state.getBlockHardness(worldIn, pos) != 0.0F) {
                        stack.damageItem(getBlastModifier(stack), entityLiving, (p_220038_0_) -> {
                            p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                        });
                    }
                }
                if (PistonCrusherRecipes.getInstance().getPrimaryResult(new ItemStack(state.getBlock())).getValue()[0] > 0f)
                {
                    if (PistonCrusherRecipes.getInstance().getPrimaryResult(new ItemStack(state.getBlock())).getValue()[0] <= 1f)
                    {
                        item = PistonCrusherRecipes.getInstance().getPrimaryResult(new ItemStack(state.getBlock())).getKey().getItem();
                        val = 1;
                    } else
                    {
                        item = PistonCrusherRecipes.getInstance().getPrimaryResult(new ItemStack(state.getBlock())).getKey().getItem();
                        val = Math.round(PistonCrusherRecipes.getInstance().getPrimaryResult(new ItemStack(state.getBlock())).getValue()[0]) - 2 + Math.round(this.getTier().getHarvestLevel());
                        if (val <= 1)
                        {
                            val = 1;
                        }
                    }
                    ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, new ItemStack(item,val));
                    itementity.setDefaultPickupDelay();
                    worldIn.addEntity(itementity);
                    if (getAtomizeModifier(stack) == 0)
                    {
                        worldIn.removeBlock(pos,false);
                    }
                }
                if (PistonCrusherRecipes.getInstance().getSecondaryResult(new ItemStack(state.getBlock())).getValue() > 0f && getAtomizeModifier(stack) == 1)
                {
                    item = PistonCrusherRecipes.getInstance().getSecondaryResult((new ItemStack(state.getBlock()))).getKey().getItem();
                    if (random.nextFloat() <= PistonCrusherRecipes.getInstance().getSecondaryResult((new ItemStack(state.getBlock()))).getValue()) {
                        ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(item, 1));
                        itementity.setDefaultPickupDelay();
                        worldIn.addEntity(itementity);

                    }
                    worldIn.removeBlock(pos, false);
                }

            }
            SoundType soundtype = worldIn.getBlockState(pos).getSoundType(worldIn, pos, null);
            worldIn.playSound(pos.getX(),pos.getY(),pos.getZ(), soundtype.getBreakSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
        }

        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return getDamage(stack) * 1f / this.getAlloyDurability(returnCompositionString(stack,this.alloy),this.alloy);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.getAlloyDurability(returnCompositionString(stack,this.alloy),this.alloy);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        DecimalFormat df = Util.make(new DecimalFormat("##.#"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        if (returnCompositionString(stack,this.alloy) != null)
        {
            if (!Screen.hasShiftDown())
            {
                tooltip.add((new StringTextComponent("Hold shift for details...")).mergeStyle(TextFormatting.GRAY));
            }
            if (Screen.hasShiftDown())
            {
                float eff = getAlloyEfficiency(returnCompositionString(stack,this.alloy),this.alloy);
                float wear = getWearAsPercent(eff,getAlloyWear(getWearModifierMining(eff),getDamage(stack),getMaxDamage(stack)));
                tooltip.add((new StringTextComponent("Composition: " + returnCompositionString(stack,this.alloy))).mergeStyle(alloy.getAlloyGroupColor()));
                tooltip.add((new StringTextComponent("Tool Efficiency: " + Math.round(wear) + "%")).mergeStyle(getWearColor(wear)));
                tooltip.add((new StringTextComponent("Durability: " + (getAlloyDurability(returnCompositionString(stack,this.alloy),this.alloy) - getDamage(stack)) + "/" + getAlloyDurability(returnCompositionString(stack,this.alloy),this.alloy))).mergeStyle(TextFormatting.DARK_GREEN));
                tooltip.add((new StringTextComponent("Harvest Level: " + getAlloyMiningLevel(returnCompositionString(stack,this.alloy),this.alloy))).mergeStyle(TextFormatting.GRAY));
                tooltip.add((new StringTextComponent("Mining Speed: " + df.format(eff))).mergeStyle(TextFormatting.GRAY));
                tooltip.add((new StringTextComponent("Enchantability: " + getAlloyEnchantability(returnCompositionString(stack,this.alloy),this.alloy))).mergeStyle(TextFormatting.GRAY));
                if (Config.ALLOY_CORROSION.get())
                {
                    tooltip.add((new StringTextComponent("Corrosion Resistance: " + (df.format(getCorrResist(stack,this.alloy) * 100)) + "%")).mergeStyle(TextFormatting.GRAY));
                }
                if (Config.ALLOY_HEAT.get())
                {
                    tooltip.add((new StringTextComponent("Heat Resistance: " + (df.format(getHeatResist(stack,this.alloy) * 100)) + "%")).mergeStyle(TextFormatting.GRAY));
                }
                if (Config.ALLOY_TOUGHNESS.get())
                {
                    tooltip.add((new StringTextComponent("Toughness: " + (df.format(getToughness(stack,this.alloy) * 100)) + "%")).mergeStyle(TextFormatting.GRAY));
                }
            }
        }
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        for (Enchantment e: getEnchantments(returnCompositionString(stack,this.alloy),stack.getItem(),this.alloy))
        {
            stack.addEnchantment(e,alloy.getEnchantmentLevel(e,getAlloyEnchantability(returnCompositionString(stack,this.alloy),this.alloy)));
        }
        super.onCreated(stack, worldIn, playerIn);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (group == ItemGroup.SEARCH || group == ProjectRankine.setup.rankineTools) {
            ItemStack stack = getAlloyItemStack(new AlloyData(alloy.getDefComposition()),this.getItem());
            for (Enchantment e: getEnchantments(returnCompositionString(stack,this.alloy),stack.getItem(),this.alloy))
            {
                stack.addEnchantment(e,alloy.getEnchantmentLevel(e,getAlloyEnchantability(returnCompositionString(stack,this.alloy),this.alloy)));
            }
            items.add(stack);
        }
    }

    @Override
    public AlloyUtils returnAlloyUtils() {
        return this.alloy;
    }
}
