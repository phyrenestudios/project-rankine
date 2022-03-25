package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.loot.SurfRodModifier;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.advancements.critereon.FishingHookPredicate;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

public class RankineLootModifierProvider extends GlobalLootModifierProvider {
    public RankineLootModifierProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn, ProjectRankine.MODID);
    }

    public String getName() {
        return "Project Rankine - Global Loot Modifiers";
    }

    @Override
    protected void start() {
        add("surf_rod_modifier",SurfRodModifier.SERIALIZER,new SurfRodModifier(new LootItemCondition[]{MatchTool.toolMatches(ItemPredicate.Builder.item().of(RankineItems.ALLOY_SURF_ROD.get())).build(), LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().fishingHook(FishingHookPredicate.inOpenWater(true))).build()}, Items.COD, RankineItems.TUNA.get()));

    }
}
