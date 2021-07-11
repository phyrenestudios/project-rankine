package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyRecipeHelper;
import com.cannolicatfish.rankine.util.alloys.AlloyEnchantmentUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class AlloyHammerItem extends HammerItem implements IAlloyTool{
    private final String defaultComposition;
    private final ResourceLocation defaultAlloyRecipe;
    public AlloyHammerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, String defaultCompositionIn, @Nullable ResourceLocation defaultAlloyRecipeIn,  Properties properties) {
        super(attackDamageIn, attackSpeedIn, tier, properties);
        this.defaultComposition = defaultCompositionIn;
        this.defaultAlloyRecipe = defaultAlloyRecipeIn;
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
        stack.damageItem(calcDurabilityLoss(stack,attacker.getEntityWorld(),attacker,true), attacker, (entity) -> {
            entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        boolean creativeFlag = false;
        if (entityLiving instanceof PlayerEntity)
        {
            creativeFlag = ((PlayerEntity) entityLiving).isCreative();
        }
        if (!worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots && !worldIn.isAirBlock(pos)) {
            for (CrushingRecipe recipe : worldIn.getRecipeManager().getRecipesForType(RankineRecipeTypes.CRUSHING)) {
                for (ItemStack s : recipe.getIngredientAsStackList()) {
                    if (s.getItem() == worldIn.getBlockState(pos).getBlock().asItem() && getAlloyHarvestLevel(stack) >= state.getBlock().getHarvestLevel(state)) {
                        if (state.getBlockHardness(worldIn, pos) != 0.0F) {
                            stack.damageItem(calcDurabilityLoss(stack,worldIn,entityLiving,true), entityLiving, (p_220038_0_) -> {
                                p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                            });
                        }
                        double d0 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                        double d1 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
                        double d2 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;

                        if (!creativeFlag) {
                            List<ItemStack> results = recipe.getResults(getAlloyHarvestLevel(stack),worldIn);
                            if (getAtomizeModifier(stack) >= 1) {
                                for (int i = 0; i < results.size(); i++) {
                                    if (results.get(i).isEmpty()) {
                                        ItemStack resu = recipe.getSpecificResult(getTier().getHarvestLevel(),i,worldIn);
                                        results.set(i,resu);
                                    }
                                }
                            }
                            for (ItemStack t : results)
                            {
                                ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, t.copy());
                                itementity.setDefaultPickupDelay();
                                worldIn.addEntity(itementity);
                            }
                        }

                        worldIn.destroyBlock(pos,false);
                        return true;

                    }

                }
            }
            SoundType soundtype = worldIn.getBlockState(pos).getSoundType(worldIn, pos, null);
            worldIn.playSound(pos.getX(),pos.getY(),pos.getZ(), soundtype.getHitSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
        }
        return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return getDamage(stack) * 1f / this.getAlloyDurability(stack);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.getAlloyDurability(stack);
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return this.getAlloyEnchantability(stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        DecimalFormat df = Util.make(new DecimalFormat("##.#"), (p_234699_0_) -> {
            p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
        });
        if (this.isAlloyInit(stack))
        {
            if (!Screen.hasShiftDown())
            {
                tooltip.add((new StringTextComponent("Hold shift for details...")).mergeStyle(TextFormatting.GRAY));
            }
            if (Screen.hasShiftDown())
            {
                float eff = getAlloyEfficiency(stack);
                float wear = getWearAsPercent(eff,getAlloyWear(getWearModifierMining(eff),getDamage(stack),getMaxDamage(stack)));
                tooltip.add((new StringTextComponent("Composition: " + getAlloyComposition(stack)).mergeStyle(TextFormatting.GOLD)));
                tooltip.add((new StringTextComponent("Tool Efficiency: " + Math.round(wear) + "%")).mergeStyle(getWearColor(wear)));
                tooltip.add((new StringTextComponent("Durability: " + (getAlloyDurability(stack) - getDamage(stack)) + "/" + getAlloyDurability(stack))).mergeStyle(TextFormatting.DARK_GREEN));
                tooltip.add((new StringTextComponent("Harvest Level: " + (getAlloyHarvestLevel(stack)))).mergeStyle(TextFormatting.GRAY));
                tooltip.add((new StringTextComponent("Mining Speed: " + df.format(eff))).mergeStyle(TextFormatting.GRAY));
                tooltip.add((new StringTextComponent("Enchantability: " + getAlloyEnchantability(stack))).mergeStyle(TextFormatting.GRAY));
                if (Config.ALLOYS.ALLOY_CORROSION.get())
                {
                    tooltip.add((new StringTextComponent("Corrosion Resistance: " + (df.format(getCorrResist(stack) * 100)) + "%")).mergeStyle(TextFormatting.GRAY));
                }
                if (Config.ALLOYS.ALLOY_HEAT.get())
                {
                    tooltip.add((new StringTextComponent("Heat Resistance: " + (df.format(getHeatResist(stack) * 100)) + "%")).mergeStyle(TextFormatting.GRAY));
                }
                if (Config.ALLOYS.ALLOY_TOUGHNESS.get())
                {
                    tooltip.add((new StringTextComponent("Toughness: " + (df.format(getToughness(stack) * 100)) + "%")).mergeStyle(TextFormatting.GRAY));
                }
            }
        }
    }

    /*
        @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        if (getComposition(stack).size() > 0 && alloy.getDefComposition().equals("80Hg-20Au") && nbt != null && !nbt.getString("nameAdd").isEmpty() && !nbt.getString("nameAdd").equals("false")) {
            String name = new TranslationTextComponent(this.getTranslationKey(stack)).getString();
            String[] sp = name.split(" ");
            if (sp.length > 0) {
                name = sp[sp.length - 1];
            }
            return new StringTextComponent(stack.getTag().getString("nameAdd") + " " + name);
        }
        return super.getDisplayName(stack);
    }


     */

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        this.applyAlloyEnchantments(stack,worldIn);
        super.onCreated(stack, worldIn, playerIn);
    }


    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!this.isAlloyInit(stack)) {
            this.createAlloyNBT(stack,worldIn,this.defaultComposition,this.defaultAlloyRecipe,null);
            this.applyAlloyEnchantments(stack,worldIn);
        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
/*
    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (group == ItemGroup.SEARCH || group == ProjectRankine.setup.rankineTools) {
            ItemStack stack = getAlloyItemStack(new AlloyData(alloy.getDefComposition()),this.getItem());
            for (Enchantment e: getEnchantments(returnCompositionString(stack,this.alloy),stack.getItem(),this.alloy))
            {
                int enchLvl = alloy.getEnchantmentLevel(e,getAlloyEnchantability(stack));
                if (enchLvl > 0) {
                    stack.addEnchantment(e,enchLvl);
                }
            }
            items.add(stack);
        }
    }
*/

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getPlayer() != null && context.getPlayer().isCrouching() && context.getWorld().getBlockState(context.getPos()).getBlock() instanceof AnvilBlock) {
            World worldIn = context.getWorld();
            BlockPos pos = context.getPos();
            BlockState anvil = worldIn.getBlockState(pos);
            if (anvil.getBlock() == Blocks.CHIPPED_ANVIL && (getAlloyDurability(context.getItem()) - context.getItem().getDamage()) >= 100) {
                worldIn.setBlockState(pos,Blocks.ANVIL.getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING,anvil.get(HorizontalBlock.HORIZONTAL_FACING)),2);
                worldIn.playSound(context.getPlayer(),pos, SoundEvents.ENTITY_IRON_GOLEM_REPAIR,SoundCategory.BLOCKS,1.0f,worldIn.getRandom().nextFloat() * 0.4F + 0.8F);
                context.getItem().damageItem(100, context.getPlayer(), (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
                return ActionResultType.SUCCESS;
            } else if (anvil.getBlock() == Blocks.DAMAGED_ANVIL && (getAlloyDurability(context.getItem()) - context.getItem().getDamage()) >= 100) {
                worldIn.setBlockState(pos,Blocks.CHIPPED_ANVIL.getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING,anvil.get(HorizontalBlock.HORIZONTAL_FACING)),2);
                worldIn.playSound(context.getPlayer(),pos, SoundEvents.ENTITY_IRON_GOLEM_REPAIR,SoundCategory.BLOCKS,1.0f,worldIn.getRandom().nextFloat() * 0.4F + 0.8F);
                context.getItem().damageItem(100, context.getPlayer(), (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
                return ActionResultType.SUCCESS;
            }
        }
        return super.onItemUse(context);
    }
}
