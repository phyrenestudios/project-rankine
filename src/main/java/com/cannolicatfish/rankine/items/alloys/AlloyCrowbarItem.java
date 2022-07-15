package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.items.tools.CrowbarItem;
import com.cannolicatfish.rankine.recipe.helper.AlloyCustomHelper;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class AlloyCrowbarItem extends CrowbarItem implements IAlloyTool {
    private final String defaultComposition;
    private final ResourceLocation defaultAlloyRecipe;
    public AlloyCrowbarItem(Tier tier, float attackDamageIn, float attackSpeedIn, String defaultCompositionIn, @Nullable ResourceLocation defaultAlloyRecipeIn, Item.Properties builderIn) {
        super(tier, (int) attackDamageIn, attackSpeedIn, builderIn);
        this.defaultComposition = defaultCompositionIn;
        this.defaultAlloyRecipe = defaultAlloyRecipeIn;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        List<Tiers> tiers = Arrays.asList(Tiers.WOOD,Tiers.STONE,Tiers.IRON,Tiers.DIAMOND,Tiers.NETHERITE);
        return state.is(RankineTags.Blocks.MINEABLE_WITH_CROWBAR) && !state.is(RankineTags.Blocks.CROWBAR_RESISTANT) && TierSortingRegistry.isCorrectTierForDrops(tiers.get(getAlloyHarvestLevel(stack)),state);
    }

    @Override
    public Component getName(ItemStack stack) {
        if (!IAlloyItem.getNameOverride(stack).isEmpty()) {
            return new TranslatableComponent(this.getDescriptionId(stack),new TranslatableComponent(IAlloyItem.getNameOverride(stack)));
        }
        return new TranslatableComponent(this.getDescriptionId(stack),new TranslatableComponent(generateLangFromRecipe(this.defaultAlloyRecipe)));
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(calcDurabilityLoss(stack,attacker.getCommandSenderWorld(),attacker,true), attacker, (entity) -> {
            entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isClientSide && state.getDestroySpeed(worldIn, pos) != 0.0F) {
            stack.hurtAndBreak(calcDurabilityLoss(stack,worldIn,entityLiving,true), entityLiving, (entity) -> {
                entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        if ((isCorrectToolForDrops(stack,state) || (canHarvestWithRetrieval(stack,state) && worldIn.getBlockEntity(pos) == null && worldIn.getFluidState(pos).isEmpty())) && !worldIn.isClientSide && !stack.isEmpty() && worldIn.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !worldIn.restoringBlockSnapshots)
        {
            double d0 = (double)(worldIn.random.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double)(worldIn.random.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double)(worldIn.random.nextFloat() * 0.5F) + 0.25D;
            ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(state.getBlock().asItem(), 1));
            itementity.setDefaultPickUpDelay();
            worldIn.addFreshEntity(itementity);
            worldIn.removeBlock(pos, false);
            SoundType soundtype = worldIn.getBlockState(pos).getSoundType(worldIn, pos, null);
            worldIn.playLocalSound(pos.getX(),pos.getY(),pos.getZ(), soundtype.getBreakSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
        }

        return true;
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
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        addAlloyInformation(stack,worldIn,tooltip,flagIn);
        if (flagIn.isAdvanced()) {
            addAdvancedAlloyInformation(stack,worldIn,tooltip,flagIn);
        }
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        this.applyAlloyEnchantments(stack,worldIn);
        super.onCraftedBy(stack, worldIn, playerIn);
    }


    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
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
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group) && this.defaultAlloyRecipe == null) {
            items.addAll(AlloyCustomHelper.getItemsFromAlloying(this));
            items.addAll(AlloyCustomHelper.getItemsFromAlloyCrafting(this));
        } else if (this.allowdedIn(group)) {
            super.fillItemCategory(group,items);
        }
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
        if (isAlloyInit(repair) && isAlloyInit(toRepair) && (repair.is(Tags.Items.INGOTS) || repair.getItem() == this)) {
            String s = IAlloyItem.getAlloyComposition(repair);
            String r = IAlloyItem.getAlloyComposition(toRepair);

            String s2 = IAlloyItem.getAlloyRecipe(repair).toString();
            String r2 = IAlloyItem.getAlloyRecipe(toRepair).toString();
            return !s.isEmpty() && s.equals(r) && s2.equals(r2);
        }
        return false;
    }
}
