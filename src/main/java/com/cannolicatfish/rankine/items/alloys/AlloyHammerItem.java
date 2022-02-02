package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class AlloyHammerItem extends HammerItem implements IAlloyTool {
    private final String defaultComposition;
    private final ResourceLocation defaultAlloyRecipe;
    public AlloyHammerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, String defaultCompositionIn, @Nullable ResourceLocation defaultAlloyRecipeIn,  Properties properties) {
        super(attackDamageIn, attackSpeedIn, tier, properties);
        this.defaultComposition = defaultCompositionIn;
        this.defaultAlloyRecipe = defaultAlloyRecipeIn;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        if (!IAlloyItem.getNameOverride(stack).isEmpty()) {
            return new TranslationTextComponent(this.getTranslationKey(stack),new TranslationTextComponent(IAlloyItem.getNameOverride(stack)));
        }
        return new TranslationTextComponent(this.getTranslationKey(stack),new TranslationTextComponent(generateLangFromRecipe(this.defaultAlloyRecipe)));
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
        addAlloyInformation(stack,worldIn,tooltip,flagIn);
        if (flagIn.isAdvanced()) {
            addAdvancedAlloyInformation(stack,worldIn,tooltip,flagIn);
        }
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        this.applyAlloyEnchantments(stack,worldIn);
        super.onCreated(stack, worldIn, playerIn);
    }


    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!this.isAlloyInit(stack)) {
            this.createAlloyNBT(stack,worldIn,this.defaultComposition,this.defaultAlloyRecipe,null);
            this.initStats(stack,getElementMap(this.defaultComposition,worldIn),getAlloyingRecipe(this.defaultAlloyRecipe,worldIn),null);
            this.applyAlloyEnchantments(stack,worldIn);
        } else if (this.needsRefresh(stack)) {
            this.createAlloyNBT(stack,worldIn,IAlloyItem.getAlloyComposition(stack),IAlloyItem.getAlloyRecipe(stack),null);
            this.initStats(stack,getElementMap(IAlloyItem.getAlloyComposition(stack),worldIn),getAlloyingRecipe(IAlloyItem.getAlloyRecipe(stack),worldIn),null);
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

    @Override
    public String getDefaultComposition() {
        return this.defaultComposition;
    }

    @Override
    public ResourceLocation getDefaultRecipe() {
        return this.defaultAlloyRecipe;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        if (isAlloyInit(repair) && isAlloyInit(toRepair) && (repair.getItem().getTags().contains(new ResourceLocation("forge:ingots")) || repair.getItem() == this)) {
            String s = IAlloyItem.getAlloyComposition(repair);
            String r = IAlloyItem.getAlloyComposition(toRepair);

            String s2 = IAlloyItem.getAlloyRecipe(repair).toString();
            String r2 = IAlloyItem.getAlloyRecipe(toRepair).toString();
            return !s.isEmpty() && s.equals(r) && s2.equals(r2);
        }
        return false;
    }
}
