package com.cannolicatfish.rankine.loot;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SurfRodModifier extends LootModifier {
    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected SurfRodModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {

        return null;
    }


    private static class Serializer extends GlobalLootModifierSerializer<SurfRodModifier> {
        @Override
        public SurfRodModifier read(ResourceLocation name, JsonObject json, ILootCondition[] conditionsIn) {
            return new SurfRodModifier(conditionsIn);
        }

        @Override
        public JsonObject write(SurfRodModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
