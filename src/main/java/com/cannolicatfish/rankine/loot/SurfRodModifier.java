package com.cannolicatfish.rankine.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

/**
 * Global loot modifier replacing one item with another (surf rod fishing loot).
 * 1.21.1: GlobalLootModifierSerializer is gone — serialization via MapCodec, JSON field
 * names ("originalItem", "replacement") unchanged from 1.18.2.
 * init/RankineLootModifiers.java registers {@link #CODEC} (other agent).
 */
public class SurfRodModifier extends LootModifier {

    public static final MapCodec<SurfRodModifier> CODEC = RecordCodecBuilder.mapCodec(instance ->
            codecStart(instance).and(instance.group(
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("originalItem").forGetter(m -> m.itemToReplace),
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("replacement").forGetter(m -> m.itemReplacement)
            )).apply(instance, SurfRodModifier::new));

    private final Item itemToReplace;
    private final Item itemReplacement;

    public SurfRodModifier(LootItemCondition[] conditionsIn, Item toReplace, Item replacement) {
        super(conditionsIn);
        this.itemToReplace = toReplace;
        this.itemReplacement = replacement;
    }

    @NotNull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.removeIf(x -> x.getItem() == this.itemToReplace);
        generatedLoot.add(new ItemStack(this.itemReplacement));
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
