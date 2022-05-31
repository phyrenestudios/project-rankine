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
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.Position;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

import java.util.*;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RankineRecipes {

    private static final DispenseItemBehavior gasDispenseBehavior = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultBehaviour = new DefaultDispenseItemBehavior();

        /**
         * Dispense the specified stack, play the dispense sound and spawn particles.
         */
        public ItemStack execute(BlockSource source, ItemStack stack) {
            Block dispensing = Blocks.AIR;
            if (stack.getItem() instanceof GasBottleItem) {
                GasBottleItem bucketitem = (GasBottleItem) stack.getItem();
                dispensing = bucketitem.getGas();
            }

            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            Level world = source.getLevel();
            if (world.getBlockState(blockpos).isAir()) {
                world.setBlock(blockpos, dispensing.defaultBlockState(),3);
                return new ItemStack(Items.GLASS_BOTTLE);
            } else {
                return this.defaultBehaviour.dispense(source, stack);
            }
        }
    };

    private static final DispenseItemBehavior bucketItemBehavior = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultBehaviour = new DefaultDispenseItemBehavior();

        /**
         * Dispense the specified stack, play the dispense sound and spawn particles.
         */
        public ItemStack execute(BlockSource source, ItemStack stack) {
            BucketItem bucketitem = (BucketItem)stack.getItem();
            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            Level world = source.getLevel();
            if (bucketitem.emptyContents((Player)null, world, blockpos, (BlockHitResult)null)) {
                bucketitem.checkExtraContent(null, world, stack, blockpos);
                return new ItemStack(Items.BUCKET);
            } else {
                return this.defaultBehaviour.dispense(source, stack);
            }
        }
    };

    private static final DispenseItemBehavior cannonballItemBehavior = new DefaultDispenseItemBehavior() {
        /**
         * Dispense the specified stack, play the dispense sound and spawn particles.
         */
        public ItemStack execute(BlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            Position iposition = DispenserBlock.getDispensePosition(source);
            double d0 = iposition.x() + (double) ((float) direction.getStepX() * 0.3F);
            double d1 = iposition.y() + (double) ((float) direction.getStepY() * 0.3F);
            double d2 = iposition.z() + (double) ((float) direction.getStepZ() * 0.3F);
            Level world = source.getLevel();
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


    private static final DispenseItemBehavior carcassItemBehavior = new DefaultDispenseItemBehavior() {
        /**
         * Dispense the specified stack, play the dispense sound and spawn particles.
         */
        public ItemStack execute(BlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            Position iposition = DispenserBlock.getDispensePosition(source);
            double d0 = iposition.x() + (double) ((float) direction.getStepX() * 0.3F);
            double d1 = iposition.y() + (double) ((float) direction.getStepY() * 0.3F);
            double d2 = iposition.z() + (double) ((float) direction.getStepZ() * 0.3F);
            Level world = source.getLevel();
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
        protected void playDispenseSound(BlockSource source) {
            source.getLevel().levelEvent(1018, source.getPos(), 0);
        }


        public static void registerPotionRecipes() {
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.of(RankineItems.MERCURY::get), PotionUtils.setPotion(new ItemStack(Items.POTION), RankinePotions.MERCURY_POISON));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.of(RankineItems.SODIUM_CHLORIDE::get,RankineItems.PINK_SALT::get), PotionUtils.setPotion(new ItemStack(Items.POTION), RankinePotions.CONDUCTIVE_POTION));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.of(RankineItems.AMBER::get), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.REGENERATION));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.of(RankineItems.HELIUM_INGOT::get), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.LEAPING));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.of(RankineItems.OXYGEN_INGOT::get), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER_BREATHING));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.of(RankineItems.ARSENIC::get), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.POISON));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.of(RankineItems.LEAD_INGOT::get), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WEAKNESS));
        //BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion()(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.of()(RankineItems.LEAD_INGOT::get), PotionUtils.setPotion()(new ItemStack(Items.POTION), Potions.NIGHT_VISION));
        //BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion()(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.of()(RankineItems.LEAD_INGOT::get), PotionUtils.setPotion()(new ItemStack(Items.POTION), Potions.FIRE_RESISTANCE));
        //BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion()(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.of()(RankineItems.LEAD_INGOT::get), PotionUtils.setPotion()(new ItemStack(Items.POTION), Potions.SWIFTNESS));
        //BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion()(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.of()(RankineItems.LEAD_INGOT::get), PotionUtils.setPotion()(new ItemStack(Items.POTION), Potions.SLOWNESS));
        //BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion()(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.of()(RankineItems.LEAD_INGOT::get), PotionUtils.setPotion()(new ItemStack(Items.POTION), Potions.SLOW_FALLING));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.of(RankineItems.SULFUR_SHELF_MUSHROOM::get), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HARMING));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.of(RankineItems.FOUR_LEAF_CLOVER::get), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.LUCK));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.of(RankineItems.LIONS_MANE_MUSHROOM::get), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRENGTH));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.NIGHT_VISION)),Ingredient.of(RankineItems.CALCITE::get), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.INVISIBILITY));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.of(RankineItems.STINGING_NETTLE::get, RankineItems.WILLOW_BRANCHLET::get), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HEALING));
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

    public static String generateAlloyString(Container inv) {
        Map<ElementRecipe, Integer> currentElements = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && AlloyCustomHelper.hasElement(stack.getItem())) {
                Tuple<ElementRecipe, Integer> entry = AlloyCustomHelper.getEntryForElementItem(stack.getItem());
                if (!currentElements.containsKey(entry.getA())) {
                    currentElements.put(entry.getA(), entry.getB() * stack.getCount());
                } else {
                    currentElements.put(entry.getA(), currentElements.get(entry.getA()) + entry.getB() * stack.getCount());
                }
            }
        }
        int sum = currentElements.values().stream().mapToInt(Integer::intValue).sum();

        List<Integer> percents = new ArrayList<>();
        List<String> symbols = new ArrayList<>();
        for (Map.Entry<ElementRecipe, Integer> ent : currentElements.entrySet()) {
            ElementRecipe curEl = ent.getKey();
            int curPer = Math.round(ent.getValue() * 100f / sum);
            symbols.add(curEl.getSymbol());
            percents.add(curPer);
        }
        return AlloyRecipeHelper.getDirectComposition(percents, symbols);
    }

}
