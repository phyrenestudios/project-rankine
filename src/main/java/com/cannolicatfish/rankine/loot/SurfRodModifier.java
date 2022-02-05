package com.cannolicatfish.rankine.loot;

import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SurfRodModifier extends LootModifier {
    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    private final Item itemToReplace;
    private final Item itemReplacement;
    public static final Serializer SERIALIZER = new Serializer();
    public SurfRodModifier(ILootCondition[] conditionsIn, Item toReplace, Item replacement) {
        super(conditionsIn);
        itemToReplace = toReplace;
        itemReplacement = replacement;
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.removeIf(x -> x.getItem() == itemToReplace);
        generatedLoot.add(new ItemStack(itemReplacement));
        return generatedLoot;
    }


    public static class Serializer extends GlobalLootModifierSerializer<SurfRodModifier> {
        @Override
        public SurfRodModifier read(ResourceLocation name, JsonObject json, ILootCondition[] conditionsIn) {
            Item toReplace = ForgeRegistries.ITEMS.getValue(new ResourceLocation((JSONUtils.getString(json, "originalItem"))));
            Item replacement = ForgeRegistries.ITEMS.getValue(new ResourceLocation((JSONUtils.getString(json, "replacement"))));
            return new SurfRodModifier(conditionsIn,toReplace,replacement);
        }

        @Override
        public JsonObject write(SurfRodModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            json.addProperty("originalItem",ForgeRegistries.ITEMS.getKey(instance.itemToReplace).toString());
            json.addProperty("replacement",ForgeRegistries.ITEMS.getKey(instance.itemReplacement).toString());
            return json;
        }
    }
}
