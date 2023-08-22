package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.element.AlloyBonusStats;
import com.cannolicatfish.rankine.element.Element;
import com.cannolicatfish.rankine.init.RankineElements;
import com.cannolicatfish.rankine.recipe.helper.AlloyEnchantmentData;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientData;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.registries.RegistryManager;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractAlloyingRecipe implements Recipe<Container> {
    protected final RecipeType<?> recipeType;
    private final ResourceLocation id;
    private final String name;
    private final int color;
    private final boolean forceNBT;
    private final boolean requiresTemplate;
    private final AlloyBonusStats bonusAlloyStats;
    private final int materialToIngot;
    private final ItemStack recipeOutput;

    private final Map<ResourceLocation, Float> defaultComposition;
    private final Map<ResourceLocation, AlloyIngredientData> requiredElements;
    private final Map<ResourceLocation, AlloyIngredientData> optionalElements;
    private final AlloyEnchantmentData enchantmentRequirements;

    public AbstractAlloyingRecipe(RecipeType<?> recipeTypeIn, ResourceLocation idIn, String nameIn, int colorIn, boolean forceNBTIn, boolean requiresTemplateIn, int materialToIngotIn, Map<ResourceLocation,Float> defaultCompositionIn,
                                  Map<ResourceLocation,AlloyIngredientData> requiredElementsIn, Map<ResourceLocation,AlloyIngredientData> optionalElementsIn, AlloyBonusStats alloyBonusStatsIn, AlloyEnchantmentData enchantmentRequirementsIn,
                                  ItemStack recipeOutputIn) {
        this.recipeType = recipeTypeIn;
        this.id = idIn;
        this.name = nameIn;
        this.forceNBT = forceNBTIn;
        this.materialToIngot = materialToIngotIn;
        this.requiresTemplate = requiresTemplateIn;
        this.defaultComposition = defaultCompositionIn;
        this.requiredElements = requiredElementsIn;
        this.optionalElements = optionalElementsIn;
        this.enchantmentRequirements = enchantmentRequirementsIn;
        this.bonusAlloyStats = alloyBonusStatsIn;
        this.recipeOutput = recipeOutputIn;
        this.color = colorIn;
    }
    @Override
    public boolean matches(Container inv, Level level) {
        Registry<Element> elementRegistry = level.registryAccess().registry(RankineElements.ELEMENT_REGISTRY_KEY).orElse(null);
        if (elementRegistry != null) {
            Set<Element> requiredElementsCopy = requiredElements.keySet().stream().map(elementRegistry::get).collect(Collectors.toSet());
            List<Integer> missingIndex = new ArrayList<>();
            HashMap<Element,Integer> currentElements = new HashMap<>();
            int materialTotal = 0;
            for (int i = 0; i < inv.getContainerSize(); i++) {
                boolean foundElement = false;
                for (Element element : requiredElementsCopy) {
                    if (this.testElement(element,inv.getItem(i))) {
                        foundElement = true;
                        requiredElementsCopy.remove(element);
                        int matValue = element.getMaterialCount(inv.getItem(i));
                        if (currentElements.containsKey(element)) {
                            currentElements.put(element,currentElements.get(element) + matValue);
                        } else {
                            currentElements.put(element,matValue);
                        }
                        materialTotal += matValue;
                        break;
                    }
                }
                if (!foundElement) {
                    missingIndex.add(i);
                }
            }
            if (!requiredElementsCopy.isEmpty()) return false;

            Set<Element> optionalElementsCopy = optionalElements.keySet().stream().map(elementRegistry::get).collect(Collectors.toSet());
            for (int i : missingIndex) {
                boolean foundElement = false;
                for (Element element : optionalElementsCopy) {
                    if (this.testElement(element,inv.getItem(i))) {
                        foundElement = true;
                        int matValue = element.getMaterialCount(inv.getItem(i));
                        if (currentElements.containsKey(element)) {
                            currentElements.put(element,currentElements.get(element) + matValue);
                        } else {
                            currentElements.put(element,matValue);
                        }
                        materialTotal += matValue;
                        break;
                    }
                }
                if (!foundElement) {
                    return false;
                }
            }

            int percentTotal = 0;
            for (Element e : currentElements.keySet())
            {
                float min = 0;
                float max = 0;
                if (requiredElements.containsKey(e)) {
                    min = Math.round(requiredElements.get(e).min() * 100);
                    max = Math.round(requiredElements.get(e).max() * 100);
                } else if (optionalElements.containsKey(e)) {
                    min = Math.round(optionalElements.get(e).min()* 100);
                    max = Math.round(optionalElements.get(e).max()* 100);
                }
                float calc = Math.round(currentElements.get(e)*100f/materialTotal);
                if (calc < min || calc > max || calc == 0) {
                    return false;
                }
                percentTotal += calc;
            }
            return percentTotal == 100;
        }
        return false;
    }

    @Override
    public ItemStack assemble(Container inv, RegistryAccess registryAccess) {
        ItemStack output = this.recipeOutput.copy();
        return output;
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

    public ItemStack getRecipeOutput() {
        return recipeOutput;
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

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public int getMaterialToIngot() {
        return materialToIngot;
    }

    public boolean forceNBT() {
        return forceNBT;
    }

    public boolean requiresTemplate() {
        return requiresTemplate;
    }


    public Map<ResourceLocation, Float> getDefaultComposition() {
        return defaultComposition;
    }

    public Map<ResourceLocation, AlloyIngredientData> getRequiredElements() {
        return requiredElements;
    }

    public Map<ResourceLocation, AlloyIngredientData> getOptionalElements() {
        return optionalElements;
    }

    public AlloyEnchantmentData getEnchantmentRequirements() {
        return enchantmentRequirements;
    }

    public AlloyBonusStats getBonusAlloyStats() {
        return bonusAlloyStats;
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

    public static class Serializer<T extends AbstractAlloyingRecipe> implements RecipeSerializer<T> {
        private final Serializer.CookieBaker<T> factory;
        public Serializer(Serializer.CookieBaker<T> p_44330_) {
            this.factory = p_44330_;
        }
        @Override
        public T fromJson(ResourceLocation resourceLocation, JsonObject json) {
            String name = json.has("name") ? json.get("name").getAsString() : "alloy.unknown.name";
            int color = json.has("color") ? Math.max(0,json.get("color").getAsInt()) : 1677215;
            boolean forceNBT = json.has("forceNBT") && json.get("forceNBT").getAsBoolean();
            boolean requiresTemplate = json.has("requiresTemplate") && json.get("requiresTemplate").getAsBoolean();
            int materialToIngot = json.has("materialToIngot") ? Math.max(1,json.get("materialToIngot").getAsInt()) : 9;

            ItemStack result = new ItemStack(BuiltInRegistries.ITEM.getOptional(new ResourceLocation(GsonHelper.getAsString(json, "result"))).orElseThrow(() -> new IllegalStateException("Item: " + new ResourceLocation(GsonHelper.getAsString(json, "result")) + " does not exist")));

            HashMap<ResourceLocation,Float> defaultComposition = new HashMap<>();
            JsonArray defaultCompositionArray = new JsonArray();
            if (json.has("defaultComposition")) {
                defaultCompositionArray = GsonHelper.getAsJsonArray(json, "defaultComposition");
            }

            for (JsonElement element : defaultCompositionArray) {
                if (element.isJsonObject()) {
                    JsonObject object = element.getAsJsonObject();
                    defaultComposition.put(new ResourceLocation(object.get("element").getAsString()),object.get("percent").getAsFloat());
                }
            }

            HashMap<ResourceLocation,AlloyIngredientData> requiredElements = new HashMap<>();
            JsonArray requiredArray = new JsonArray();
            if (json.has("requiredElements")) {
                requiredArray = GsonHelper.getAsJsonArray(json, "requiredElements");
            }
            for (JsonElement element : requiredArray) {
                if (element.isJsonObject()) {
                    JsonObject object = element.getAsJsonObject();
                    requiredElements.put(new ResourceLocation(object.get("element").getAsString()),new AlloyIngredientData(object.get("min").getAsFloat(),object.get("max").getAsFloat()));
                }
            }

            HashMap<ResourceLocation,AlloyIngredientData> optionalElements = new HashMap<>();
            JsonArray optionalArray = new JsonArray();
            if (json.has("optionalElements")) {
                optionalArray = GsonHelper.getAsJsonArray(json, "optionalElements");
            }
            for (JsonElement element : optionalArray) {
                if (element.isJsonObject()) {
                    JsonObject object = element.getAsJsonObject();
                    optionalElements.put(new ResourceLocation(object.get("element").getAsString()),new AlloyIngredientData(object.get("min").getAsFloat(),object.get("max").getAsFloat()));
                }

            }

            JsonElement bonusStatsObject = new JsonObject();
            AlloyBonusStats bonusStats = null;
            if (json.has("bonusStats")) {
                bonusStatsObject = GsonHelper.getAsJsonObject(json, "bonusStats");
            }
            if (bonusStatsObject.isJsonObject()) {
                JsonObject object = bonusStatsObject.getAsJsonObject();
                Tier currentTier = Tiers.WOOD;
                if (object.has("tier")) {
                    Tier curTier = TierSortingRegistry.byName(new ResourceLocation(object.get("tier").getAsString()));
                    if (curTier != null) {
                        currentTier = curTier;
                    }
                }

                bonusStats = new AlloyBonusStats(object.has("durability") ? object.get("durability").getAsInt() : 0,
                        object.has("miningSpeed") ? object.get("miningSpeed").getAsFloat() : 0,
                        object.has("density") ? object.get("density").getAsFloat() : 0,
                        object.has("attackDamage") ? object.get("attackDamage").getAsInt() : 0,
                        object.has("enchantability") ? object.get("enchantability").getAsInt() : 0,
                        currentTier);
            }

            JsonArray enchantmentArray = new JsonArray();
            List<ResourceLocation> enchantments = new ArrayList<>();
            int enchantOffset = 5;
            if (json.has("enchantments")) {
                enchantmentArray = GsonHelper.getAsJsonArray(json, "enchantments");
                enchantOffset = json.get("enchantments").getAsJsonObject().has("enchantabilityOffset") ? json.get("enchantments").getAsJsonObject().get("enchantabilityOffset").getAsInt() : 5;
            }

            for (JsonElement element : enchantmentArray) {
                if (element.isJsonObject()) {
                    JsonObject object = element.getAsJsonObject();
                    if (object.has("enchantment")) {
                        enchantments.add(new ResourceLocation(object.get("enchantment").getAsString()));
                    }
                }
            }

            AlloyEnchantmentData enchantmentData = new AlloyEnchantmentData(enchantments,enchantOffset);
            return this.factory.create(resourceLocation,name,color,forceNBT,requiresTemplate,materialToIngot,defaultComposition,requiredElements,optionalElements,bonusStats,enchantmentData,result);
        }

        @Override
        public @Nullable T fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf buffer) {
            String name = buffer.readUtf();
            int color = buffer.readInt();
            boolean forceNBT = buffer.readBoolean();
            boolean requiresTemplate = buffer.readBoolean();
            short materialToIngot = buffer.readShort();

            Map<ResourceLocation,Float> defaultComposition = buffer.readMap(FriendlyByteBuf::readResourceLocation,FriendlyByteBuf::readFloat);

            Map<ResourceLocation,AlloyIngredientData> requiredElements = buffer.readMap(FriendlyByteBuf::readResourceLocation, friendlyByteBuf -> new AlloyIngredientData(friendlyByteBuf.readFloat(), friendlyByteBuf.readFloat()));
            Map<ResourceLocation,AlloyIngredientData> optionalElements = buffer.readMap(FriendlyByteBuf::readResourceLocation, friendlyByteBuf -> new AlloyIngredientData(friendlyByteBuf.readFloat(), friendlyByteBuf.readFloat()));

            AlloyBonusStats bonusStats = buffer.readNullable(friendlyByteBuf -> new AlloyBonusStats(buffer.readShort(),buffer.readFloat(),buffer.readFloat(),buffer.readShort(),buffer.readShort(),TierSortingRegistry.byName(buffer.readResourceLocation())));
            AlloyEnchantmentData enchantmentData = buffer.readNullable(friendlyByteBuf -> new AlloyEnchantmentData(friendlyByteBuf.readList(FriendlyByteBuf::readResourceLocation),friendlyByteBuf.readShort()));

            ItemStack result = buffer.readItem();

            return this.factory.create(resourceLocation,name,color,forceNBT,requiresTemplate,materialToIngot,defaultComposition,requiredElements,optionalElements,bonusStats,enchantmentData,result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, T recipe) {
            buffer.writeUtf(recipe.getName());
            buffer.writeInt(recipe.getColor());
            buffer.writeBoolean(recipe.forceNBT());
            buffer.writeBoolean(recipe.requiresTemplate());
            buffer.writeShort(recipe.getMaterialToIngot());

            buffer.writeMap(recipe.getDefaultComposition(),FriendlyByteBuf::writeResourceLocation,FriendlyByteBuf::writeFloat);
            buffer.writeMap(recipe.getRequiredElements(),FriendlyByteBuf::writeResourceLocation,(friendlyByteBuf, alloyIngredientData) -> {
                buffer.writeFloat(alloyIngredientData.min());
                buffer.writeFloat(alloyIngredientData.max());
            });
            buffer.writeMap(recipe.getOptionalElements(),FriendlyByteBuf::writeResourceLocation,(friendlyByteBuf, alloyIngredientData) -> {
                buffer.writeFloat(alloyIngredientData.min());
                buffer.writeFloat(alloyIngredientData.max());
            });

            buffer.writeNullable(recipe.getBonusAlloyStats(),(friendlyByteBuf, alloyBonusStats) -> {
                buffer.writeShort(alloyBonusStats.durability());
                buffer.writeFloat(alloyBonusStats.miningSpeed());
                buffer.writeFloat(alloyBonusStats.density());
                buffer.writeShort(alloyBonusStats.attackDamage());
                buffer.writeShort(alloyBonusStats.enchantability());
                buffer.writeResourceLocation(alloyBonusStats.getTierResourceLocation());
            });

            buffer.writeNullable(recipe.getEnchantmentRequirements(),(friendlyByteBuf, alloyEnchantmentData) -> {
                    buffer.writeCollection(alloyEnchantmentData.enchantments(),FriendlyByteBuf::writeResourceLocation);
                    buffer.writeShort(alloyEnchantmentData.enchantabilityOffset());
            });

            buffer.writeItem(recipe.getRecipeOutput().copy());

        }

        public interface CookieBaker<T extends AbstractAlloyingRecipe> {
            T create(ResourceLocation idIn, String nameIn, int colorIn, boolean forceNBTIn, boolean requiresTemplateIn, int materialToIngotIn, Map<ResourceLocation,Float> defaultCompositionIn,
                     Map<ResourceLocation,AlloyIngredientData> requiredElementsIn, Map<ResourceLocation,AlloyIngredientData> optionalElementsIn, AlloyBonusStats alloyBonusStatsIn, AlloyEnchantmentData enchantmentRequirementsIn, ItemStack recipeOutputIn);

        }
    }

}
