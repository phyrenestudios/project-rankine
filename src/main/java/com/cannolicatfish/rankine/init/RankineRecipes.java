package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.advancements.ExactCompositionPredicate;
import com.cannolicatfish.rankine.advancements.HarvestLevelPredicate;
import com.cannolicatfish.rankine.advancements.IncludesCompositionPredicate;
import com.cannolicatfish.rankine.advancements.AlloyEnchantabilityPredicate;
import com.cannolicatfish.rankine.entities.CannonballEntity;
import com.cannolicatfish.rankine.entities.CarcassEntity;
import com.cannolicatfish.rankine.items.GasBottleItem;
import com.cannolicatfish.rankine.potion.RankinePotions;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyCustomHelper;
import com.cannolicatfish.rankine.recipe.helper.AlloyRecipeHelper;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

import java.util.*;

public class RankineRecipes {

    private static final IDispenseItemBehavior gasDispenseBehavior = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultBehaviour = new DefaultDispenseItemBehavior();

        /**
         * Dispense the specified stack, play the dispense sound and spawn particles.
         */
        public ItemStack execute(IBlockSource source, ItemStack stack) {
            Block dispensing = Blocks.AIR;
            if (stack.getItem() instanceof GasBottleItem) {
                GasBottleItem bucketitem = (GasBottleItem) stack.getItem();
                dispensing = bucketitem.getGas();
            }

            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            World world = source.getLevel();
            if (world.getBlockState(blockpos).isAir()) {
                world.setBlock(blockpos, dispensing.defaultBlockState(),3);
                return new ItemStack(Items.GLASS_BOTTLE);
            } else {
                return this.defaultBehaviour.dispense(source, stack);
            }
        }
    };

    private static final IDispenseItemBehavior bucketItemBehavior = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultBehaviour = new DefaultDispenseItemBehavior();

        /**
         * Dispense the specified stack, play the dispense sound and spawn particles.
         */
        public ItemStack execute(IBlockSource source, ItemStack stack) {
            BucketItem bucketitem = (BucketItem)stack.getItem();
            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            World world = source.getLevel();
            if (bucketitem.emptyBucket((PlayerEntity)null, world, blockpos, (BlockRayTraceResult)null)) {
                bucketitem.checkExtraContent(world, stack, blockpos);
                return new ItemStack(Items.BUCKET);
            } else {
                return this.defaultBehaviour.dispense(source, stack);
            }
        }
    };

    private static final IDispenseItemBehavior cannonballItemBehavior = new DefaultDispenseItemBehavior() {
        /**
         * Dispense the specified stack, play the dispense sound and spawn particles.
         */
        public ItemStack execute(IBlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            IPosition iposition = DispenserBlock.getDispensePosition(source);
            double d0 = iposition.x() + (double) ((float) direction.getStepX() * 0.3F);
            double d1 = iposition.y() + (double) ((float) direction.getStepY() * 0.3F);
            double d2 = iposition.z() + (double) ((float) direction.getStepZ() * 0.3F);
            World world = source.getLevel();
            Random random = world.random;
            double d3 = random.nextGaussian() * 0.05D + (double) direction.getStepX();
            double d4 = random.nextGaussian() * 0.05D + (double) direction.getStepY();
            double d5 = random.nextGaussian() * 0.05D + (double) direction.getStepZ();
            world.addFreshEntity(Util.make(new CannonballEntity(world, d0, d1, d2, d3, d4, d5), (fireball) -> {
                fireball.setStack(stack);
            }));
            stack.shrink(1);
            return stack;
        }
    };


    private static final IDispenseItemBehavior carcassItemBehavior = new DefaultDispenseItemBehavior() {
        /**
         * Dispense the specified stack, play the dispense sound and spawn particles.
         */
        public ItemStack execute(IBlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            IPosition iposition = DispenserBlock.getDispensePosition(source);
            double d0 = iposition.x() + (double) ((float) direction.getStepX() * 0.3F);
            double d1 = iposition.y() + (double) ((float) direction.getStepY() * 0.3F);
            double d2 = iposition.z() + (double) ((float) direction.getStepZ() * 0.3F);
            World world = source.getLevel();
            Random random = world.random;
            double d3 = random.nextGaussian() * 0.05D + (double) direction.getStepX();
            double d4 = random.nextGaussian() * 0.05D + (double) direction.getStepY();
            double d5 = random.nextGaussian() * 0.05D + (double) direction.getStepZ();
            world.addFreshEntity(Util.make(new CarcassEntity(world, d0, d1, d2, d3, d4, d5), (fireball) -> {
                fireball.setItem(stack);
            }));
            stack.shrink(1);
            return stack;
        }
    };

        /**
         * Play the dispense sound from the specified block.
         */
        protected void playDispenseSound(IBlockSource source) {
            source.getLevel().levelEvent(1018, source.getPos(), 0);
        }


        public static void registerPotionRecipes() {
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.of(RankineItems.MERCURY::get), PotionUtils.setPotion(new ItemStack(Items.POTION), RankinePotions.MERCURY_POISON));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.of(RankineItems.SODIUM_CHLORIDE::get,RankineItems.PINK_SALT::get), PotionUtils.setPotion(new ItemStack(Items.POTION), RankinePotions.CONDUCTIVE_POTION));

    }

    public static void registerDispenserBehaviors() {
        for (Item i : RankineLists.GAS_BOTTLES) {
            DispenserBlock.registerBehavior(i,gasDispenseBehavior);
        }
        DispenserBlock.registerBehavior(RankineItems.JUGLONE_BUCKET.get(),bucketItemBehavior);
        DispenserBlock.registerBehavior(RankineItems.LIQUID_MERCURY_BUCKET.get(),bucketItemBehavior);
        DispenserBlock.registerBehavior(RankineItems.AQUA_REGIA_BUCKET.get(),bucketItemBehavior);
        DispenserBlock.registerBehavior(RankineItems.HYDROBROMIC_ACID_BUCKET.get(),bucketItemBehavior);
        DispenserBlock.registerBehavior(RankineItems.GRAY_MUD_BUCKET.get(),bucketItemBehavior);
        DispenserBlock.registerBehavior(RankineItems.RED_MUD_BUCKET.get(),bucketItemBehavior);
        DispenserBlock.registerBehavior(RankineItems.SULFURIC_ACID_BUCKET.get(),bucketItemBehavior);
        DispenserBlock.registerBehavior(RankineItems.LATEX_BUCKET.get(),bucketItemBehavior);
        DispenserBlock.registerBehavior(RankineItems.RESIN_BUCKET.get(),bucketItemBehavior);
        DispenserBlock.registerBehavior(RankineItems.SAP_BUCKET.get(),bucketItemBehavior);
        DispenserBlock.registerBehavior(RankineItems.MAPLE_SAP_BUCKET.get(),bucketItemBehavior);
        DispenserBlock.registerBehavior(RankineItems.BLACK_LIQUOR_BUCKET.get(),bucketItemBehavior);
        DispenserBlock.registerBehavior(RankineItems.GREEN_LIQUOR_BUCKET.get(),bucketItemBehavior);
        DispenserBlock.registerBehavior(RankineItems.WHITE_LIQUOR_BUCKET.get(),bucketItemBehavior);

        DispenserBlock.registerBehavior(RankineItems.CANNONBALL.get(),cannonballItemBehavior);
        DispenserBlock.registerBehavior(RankineItems.CARCASS.get(),carcassItemBehavior);
    }

    public static void registerPredicates() {
        ItemPredicate.register(new ResourceLocation("rankine","harvest_level_check"), HarvestLevelPredicate::new);
        ItemPredicate.register(new ResourceLocation("rankine","enchant_check"), AlloyEnchantabilityPredicate::new);
        ItemPredicate.register(new ResourceLocation("rankine","exact_composition"), ExactCompositionPredicate::new);
        ItemPredicate.register(new ResourceLocation("rankine","includes_composition"), IncludesCompositionPredicate::new);
    }

    public static String generateAlloyString(IInventory inv) {
        List<ElementRecipe> currentElements = new ArrayList<>();
        List<Integer> currentMaterial = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && AlloyCustomHelper.hasElement(stack.getItem())) {
                Tuple<ElementRecipe,Integer> entry = AlloyCustomHelper.getEntryForElementItem(stack.getItem());
                if (!currentElements.contains(entry.getA())) {
                    currentElements.add(entry.getA());
                    currentMaterial.add(entry.getB());
                }
            }
        }
        int sum = currentMaterial.stream().mapToInt(Integer::intValue).sum();

        List<Integer> percents = new ArrayList<>();
        List<String> symbols = new ArrayList<>();
        for (int j = 0; j < currentElements.size(); j++) {
            ElementRecipe curEl = currentElements.get(j);
            int curPer = Math.round(currentMaterial.get(j) * 100f/sum);
            symbols.add(curEl.getSymbol());
            percents.add(curPer);
        }
        return AlloyRecipeHelper.getDirectComposition(percents,symbols);
    }

}
