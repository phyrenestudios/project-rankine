package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.entities.CannonballEntity;
import com.cannolicatfish.rankine.entities.CarcassEntity;
import com.cannolicatfish.rankine.items.GasBottleItem;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyCustomHelper;
import com.cannolicatfish.rankine.recipe.helper.AlloyRecipeHelper;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

import java.util.*;

@EventBusSubscriber(modid = ProjectRankine.MODID)
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

            BlockPos blockpos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
            Level world = source.level();
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
            BlockPos blockpos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
            Level world = source.level();
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
            Direction direction = source.state().getValue(DispenserBlock.FACING);
            Position iposition = DispenserBlock.getDispensePosition(source);
            double d0 = iposition.x() + (double) ((float) direction.getStepX() * 0.3F);
            double d1 = iposition.y() + (double) ((float) direction.getStepY() * 0.3F);
            double d2 = iposition.z() + (double) ((float) direction.getStepZ() * 0.3F);
            Level world = source.level();
            RandomSource random = world.random;
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
            Direction direction = source.state().getValue(DispenserBlock.FACING);
            Position iposition = DispenserBlock.getDispensePosition(source);
            double d0 = iposition.x() + (double) ((float) direction.getStepX() * 0.3F);
            double d1 = iposition.y() + (double) ((float) direction.getStepY() * 0.3F);
            double d2 = iposition.z() + (double) ((float) direction.getStepZ() * 0.3F);
            Level world = source.level();
            RandomSource random = world.random;
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
            source.level().levelEvent(1018, source.pos(), 0);
        }


    /**
     * Brewing recipes moved from BrewingRecipeRegistry (GONE in NeoForge 1.21.1) to
     * RegisterBrewingRecipesEvent (game bus). The old recipes used Ingredient.of(potion stack),
     * which matched by item only; addMix(basePotion, ingredientItem, resultPotion) preserves the
     * intended base-potion + reagent -> result behavior.
     */
    @SubscribeEvent
    public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        var builder = event.getBuilder();
        builder.addMix(Potions.AWKWARD, RankineItems.MERCURY.get(), RankinePotions.MERCURY_POISONING);
        builder.addMix(Potions.AWKWARD, RankineItems.SODIUM_CHLORIDE.get(), RankinePotions.CONDUCTIVITY);
        builder.addMix(Potions.AWKWARD, RankineItems.PINK_SALT.get(), RankinePotions.CONDUCTIVITY);
        builder.addMix(Potions.AWKWARD, RankineItems.AMBER.get(), Potions.REGENERATION);
        builder.addMix(Potions.AWKWARD, RankineItems.HELIUM_INGOT.get(), Potions.LEAPING);
        builder.addMix(Potions.AWKWARD, RankineItems.OXYGEN_INGOT.get(), Potions.WATER_BREATHING);
        builder.addMix(Potions.AWKWARD, RankineItems.ARSENIC.get(), Potions.POISON);
        builder.addMix(Potions.AWKWARD, RankineItems.LEAD_INGOT.get(), Potions.WEAKNESS);
        builder.addMix(Potions.AWKWARD, RankineItems.SULFUR_SHELF_MUSHROOM.get(), Potions.HARMING);
        builder.addMix(Potions.AWKWARD, RankineItems.FOUR_LEAF_CLOVER.get(), Potions.LUCK);
        builder.addMix(Potions.AWKWARD, RankineItems.LIONS_MANE_MUSHROOM.get(), Potions.STRENGTH);
        builder.addMix(Potions.NIGHT_VISION, RankineItems.CALCITE.get(), Potions.INVISIBILITY);
        builder.addMix(Potions.AWKWARD, RankineItems.STINGING_NETTLE.get(), Potions.HEALING);
        builder.addMix(Potions.AWKWARD, RankineItems.WILLOW_BRANCHLET.get(), Potions.HEALING);
    }

    /**
     * Kept because ProjectRankine#commonSetup still calls it; brewing registration now happens in
     * {@link #registerBrewingRecipes(RegisterBrewingRecipesEvent)}.
     */
    public static void registerPotionRecipes() {
        // no-op — see registerBrewingRecipes above
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

    public static String generateAlloyString(Container inv, Level level) {
        Map<ElementRecipe, Integer> currentElements = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (AlloyCustomHelper.hasElement(stack.getItem())) {
                    Tuple<ElementRecipe, Integer> entry = AlloyCustomHelper.getEntryForElementItem(stack.getItem());
                    if (!currentElements.containsKey(entry.getA())) {
                        currentElements.put(entry.getA(), entry.getB() * stack.getCount());
                    } else {
                        currentElements.put(entry.getA(), currentElements.get(entry.getA()) + entry.getB() * stack.getCount());
                    }
                } else {
                    ElementRecipe recipe = level.getRecipeManager().getRecipeFor(RankineRecipeTypes.ELEMENT.get(), new SingleRecipeInput(stack), level).map(RecipeHolder::value).orElse(null);
                    if (recipe != null) {
                        if (!currentElements.containsKey(recipe)) {
                            currentElements.put(recipe, recipe.getMaterialCount(stack.getItem()) * stack.getCount());
                        } else {
                            currentElements.put(recipe, currentElements.get(recipe) + recipe.getMaterialCount(stack.getItem()) * stack.getCount());
                        }
                    }

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
