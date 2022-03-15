package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyCustomHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
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

import net.minecraft.item.Item.Properties;

public class AlloyHammerItem extends HammerItem implements IAlloyTool {
    private final String defaultComposition;
    private final ResourceLocation defaultAlloyRecipe;
    public AlloyHammerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, String defaultCompositionIn, @Nullable ResourceLocation defaultAlloyRecipeIn,  Properties properties) {
        super(attackDamageIn, attackSpeedIn, tier, properties);
        this.defaultComposition = defaultCompositionIn;
        this.defaultAlloyRecipe = defaultAlloyRecipeIn;
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        if (!IAlloyItem.getNameOverride(stack).isEmpty()) {
            return new TranslationTextComponent(this.getDescriptionId(stack),new TranslationTextComponent(IAlloyItem.getNameOverride(stack)));
        }
        return new TranslationTextComponent(this.getDescriptionId(stack),new TranslationTextComponent(generateLangFromRecipe(this.defaultAlloyRecipe)));
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getCommandSenderWorld().isRainingAt(target.blockPosition()) && getLightningModifier(stack) == 1)
        {
            LightningBoltEntity ent = new LightningBoltEntity(EntityType.LIGHTNING_BOLT,attacker.level);
            //ent.moveTo(Vector3d.atBottomCenterOf(new BlockPos(target.getPosX(),target.getPosY(),target.getPosZ())));
            ent.setPos(target.getX(),target.getY(),target.getZ());
            ((ServerWorld)target.getCommandSenderWorld()).addFreshEntity(ent);
        }
        if (getDazeModifier(stack) != 0)
        {
            if (attacker instanceof PlayerEntity)
            {
                PlayerEntity player = (PlayerEntity) attacker;
                if (player.getAttackStrengthScale(0) >= (1f))
                {
                    target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN,getDazeModifier(stack)*20, 2));
                } else {
                    target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN,getDazeModifier(stack)*20, 1));
                }
            } else {
                target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN,getDazeModifier(stack)*20, 1));
            }

        }
        stack.hurtAndBreak(calcDurabilityLoss(stack,attacker.getCommandSenderWorld(),attacker,true), attacker, (entity) -> {
            entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        boolean creativeFlag = false;
        if (entityLiving instanceof PlayerEntity)
        {
            creativeFlag = ((PlayerEntity) entityLiving).isCreative();
        }
        if (!worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !worldIn.restoringBlockSnapshots && !worldIn.isEmptyBlock(pos)) {
            for (CrushingRecipe recipe : worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.CRUSHING)) {
                for (ItemStack s : recipe.getIngredientAsStackList()) {
                    if (s.getItem() == worldIn.getBlockState(pos).getBlock().asItem() && getAlloyHarvestLevel(stack) >= state.getBlock().getHarvestLevel(state)) {
                        if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
                            stack.hurtAndBreak(calcDurabilityLoss(stack,worldIn,entityLiving,true), entityLiving, (p_220038_0_) -> {
                                p_220038_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
                            });
                        }
                        double d0 = (double)(worldIn.random.nextFloat() * 0.5F) + 0.25D;
                        double d1 = (double)(worldIn.random.nextFloat() * 0.5F) + 0.25D;
                        double d2 = (double)(worldIn.random.nextFloat() * 0.5F) + 0.25D;

                        if (!creativeFlag) {
                            List<ItemStack> results = recipe.getResults(getAlloyHarvestLevel(stack),worldIn);
                            if (getAtomizeModifier(stack) >= 1) {
                                for (int i = 0; i < results.size(); i++) {
                                    if (results.get(i).isEmpty()) {
                                        ItemStack resu = recipe.getSpecificResult(getTier().getLevel(),i,worldIn);
                                        results.set(i,resu);
                                    }
                                }
                            }
                            for (ItemStack t : results)
                            {
                                ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, t.copy());
                                itementity.setDefaultPickUpDelay();
                                worldIn.addFreshEntity(itementity);
                            }
                        }

                        worldIn.destroyBlock(pos,false);
                        return true;

                    }

                }
            }
            SoundType soundtype = worldIn.getBlockState(pos).getSoundType(worldIn, pos, null);
            worldIn.playLocalSound(pos.getX(),pos.getY(),pos.getZ(), soundtype.getHitSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
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
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        addAlloyInformation(stack,worldIn,tooltip,flagIn);
        if (flagIn.isAdvanced()) {
            addAdvancedAlloyInformation(stack,worldIn,tooltip,flagIn);
        }
    }

    @Override
    public void onCraftedBy(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        this.applyAlloyEnchantments(stack,worldIn);
        super.onCraftedBy(stack, worldIn, playerIn);
    }

    @Override
    public boolean canHarvestBlock(ItemStack stack, BlockState blockIn) {
        return getAlloyHarvestLevel(stack) >= blockIn.getHarvestLevel();
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

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group) && this.defaultAlloyRecipe == null) {
            items.addAll(AlloyCustomHelper.getItemsFromAlloying(this));
            items.addAll(AlloyCustomHelper.getItemsFromAlloyCrafting(this));
        } else if (this.allowdedIn(group)) {
            super.fillItemCategory(group,items);
        }
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        if (context.getPlayer() != null && context.getPlayer().isCrouching() && context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof AnvilBlock) {
            World worldIn = context.getLevel();
            BlockPos pos = context.getClickedPos();
            BlockState anvil = worldIn.getBlockState(pos);
            if (anvil.getBlock() == Blocks.CHIPPED_ANVIL && (getAlloyDurability(context.getItemInHand()) - context.getItemInHand().getDamageValue()) >= 100) {
                worldIn.setBlock(pos,Blocks.ANVIL.defaultBlockState().setValue(HorizontalBlock.FACING,anvil.getValue(HorizontalBlock.FACING)),2);
                worldIn.playSound(context.getPlayer(),pos, SoundEvents.IRON_GOLEM_REPAIR,SoundCategory.BLOCKS,1.0f,worldIn.getRandom().nextFloat() * 0.4F + 0.8F);
                context.getItemInHand().hurtAndBreak(100, context.getPlayer(), (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
                return ActionResultType.SUCCESS;
            } else if (anvil.getBlock() == Blocks.DAMAGED_ANVIL && (getAlloyDurability(context.getItemInHand()) - context.getItemInHand().getDamageValue()) >= 100) {
                worldIn.setBlock(pos,Blocks.CHIPPED_ANVIL.defaultBlockState().setValue(HorizontalBlock.FACING,anvil.getValue(HorizontalBlock.FACING)),2);
                worldIn.playSound(context.getPlayer(),pos, SoundEvents.IRON_GOLEM_REPAIR,SoundCategory.BLOCKS,1.0f,worldIn.getRandom().nextFloat() * 0.4F + 0.8F);
                context.getItemInHand().hurtAndBreak(100, context.getPlayer(), (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
                return ActionResultType.SUCCESS;
            }
        }
        return super.useOn(context);
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
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
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
