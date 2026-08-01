package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.items.alloys.IAlloySpecialItem;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;

/**
 * PORT NOTE: {@code UpgradeRecipe} was removed; smithing recipes now implement {@link SmithingRecipe}
 * with a template/base/addition layout (input slots: 0 = template, 1 = base, 2 = addition).
 * The 1.18.2 JSON layout ({@code base}/{@code addition}/{@code result}) is preserved; an optional
 * {@code template} field is accepted and defaults to an empty ingredient (no template required).
 */
public class AlloySmithingRecipe implements SmithingRecipe {
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final ItemStack result;

    public AlloySmithingRecipe(Ingredient template, Ingredient base, Ingredient addition, ItemStack result) {
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    public AlloySmithingRecipe(Ingredient base, Ingredient addition, ItemStack result) {
        this(Ingredient.EMPTY, base, addition, result);
    }

    @Override
    public boolean matches(SmithingRecipeInput input, Level level) {
        return this.template.test(input.template()) && this.base.test(input.base()) && this.addition.test(input.addition());
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input, HolderLookup.Provider provider) {
        ItemStack itemstack = this.result.copy();
        // 1.18.2 behavior: the crafted item keeps the base item's stack data (now data components).
        itemstack.applyComponents(input.base().getComponentsPatch());
        if (itemstack.getItem() instanceof IAlloySpecialItem item) {
            // add alloyModifier data to alloy
            item.setRefresh(itemstack);
        }
        return itemstack;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.result;
    }

    /** Convenience overload matching the pre-1.21 API. */
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public boolean isTemplateIngredient(ItemStack stack) {
        return this.template.test(stack);
    }

    @Override
    public boolean isBaseIngredient(ItemStack stack) {
        return this.base.test(stack);
    }

    @Override
    public boolean isAdditionIngredient(ItemStack stack) {
        return this.addition.test(stack);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Serializer implements RecipeSerializer<AlloySmithingRecipe> {

        /** PORT NOTE: the 1.18.2 code never registered this serializer; expose a stable instance in case the init agent wants to. */
        public static final Serializer INSTANCE = new Serializer();

        public static final MapCodec<AlloySmithingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.optionalFieldOf("template", Ingredient.EMPTY).forGetter(recipe -> recipe.template),
                Ingredient.CODEC.fieldOf("base").forGetter(recipe -> recipe.base),
                Ingredient.CODEC.fieldOf("addition").forGetter(recipe -> recipe.addition),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
        ).apply(instance, AlloySmithingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, AlloySmithingRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<AlloySmithingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AlloySmithingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static AlloySmithingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient template = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient base = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient addition = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
            return new AlloySmithingRecipe(template, base, addition, result);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, AlloySmithingRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.template);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.base);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.addition);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
        }
    }
}
