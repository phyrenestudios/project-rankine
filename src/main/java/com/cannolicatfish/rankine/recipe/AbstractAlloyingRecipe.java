package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.element.Element;
import com.cannolicatfish.rankine.init.RankineElements;
import com.cannolicatfish.rankine.recipe.helper.AlloyEnchantmentData;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientData;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryManager;

import javax.annotation.Nullable;
import java.util.*;

public abstract class AbstractAlloyingRecipe implements Recipe<Container> {
    protected final RecipeType<?> recipeType;
    private final ResourceLocation id;
    private final String name;
    private final boolean forceNBT;
    private final boolean requiresTemplate;

    private final int materialToIngot;
    private final ItemStack recipeOutput;
    private final HashMap<Element, AlloyIngredientData> requiredElements;
    private final HashMap<Element, AlloyIngredientData> optionalElements;
    private final AlloyEnchantmentData enchantmentRequirements;

    public AbstractAlloyingRecipe(RecipeType<?> recipeTypeIn, ResourceLocation idIn, String nameIn, boolean forceNBTIn, boolean requiresTemplateIn, int materialToIngotIn, HashMap<Element,AlloyIngredientData> requiredElementsIn,
                                  HashMap<Element,AlloyIngredientData> optionalElementsIn, AlloyEnchantmentData enchantmentRequirementsIn, ItemStack recipeOutputIn) {
        this.recipeType = recipeTypeIn;
        this.id = idIn;
        this.name = nameIn;
        this.forceNBT = forceNBTIn;
        this.materialToIngot = materialToIngotIn;
        this.requiresTemplate = requiresTemplateIn;
        this.requiredElements = requiredElementsIn;
        this.optionalElements = optionalElementsIn;
        this.enchantmentRequirements = enchantmentRequirementsIn;
        this.recipeOutput = recipeOutputIn;
    }
    @Override
    public boolean matches(Container inv, Level level) {
        Set<Element> requiredElementsCopy = new HashSet<>(requiredElements.keySet());
        List<Integer> missingIndex = new ArrayList<>();
        for (int i = 0; i < inv.getContainerSize(); i++) {
            boolean foundElement = false;
            for (Element element : requiredElementsCopy) {
                if (this.testElement(element,inv.getItem(i))) {
                    foundElement = true;
                    requiredElementsCopy.remove(element);
                    break;
                }
            }
            if (!foundElement) {
                missingIndex.add(i);
            }
        }
        if (!requiredElementsCopy.isEmpty()) return false;

        for (int i : missingIndex) {
            boolean foundElement = false;
            for (Element element : optionalElements.keySet()) {
                if (this.testElement(element,inv.getItem(i))) {
                    foundElement = true;
                    break;
                }
            }
            if (!foundElement) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(Container inv, RegistryAccess registryAccess) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        ItemStack out = this.recipeOutput.copy();
        return out;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public RecipeType<?> getType() {
        return this.recipeType;
    }

    public static Element deserializeElement(JsonObject object) {
        String s = GsonHelper.getAsString(object, "element");
        Element element = RegistryManager.ACTIVE.getRegistry(RankineElements.ELEMENT_REGISTRY_KEY).getValue(new ResourceLocation(s));
        if (element == null) {
            throw new JsonSyntaxException("Unknown element '" + s + "'");
        }
        return element;
    }

    public boolean testElement(Element element, @Nullable ItemStack stack) {
        if (stack == null) {
            return false;
        } else {
            for(Ingredient i : element.getIngredients()) {
                if (i.test(stack)) {
                    return true;
                }
            }

            return false;
        }
    }
}
