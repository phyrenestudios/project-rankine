package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.loot.SurfRodModifier;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraft.loot.FishingPredicate;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.*;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class RankineLootModifierProvider extends GlobalLootModifierProvider {
    public RankineLootModifierProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn, ProjectRankine.MODID);
    }

    public String getName() {
        return "Project Rankine - Global Loot Modifiers";
    }

    @Override
    protected void start() {
        add("surf_rod_modifier",SurfRodModifier.SERIALIZER,new SurfRodModifier(new ILootCondition[]{MatchTool.toolMatches(ItemPredicate.Builder.item().of(RankineItems.ALLOY_SURF_ROD.get())).build(), EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().fishingHook(FishingPredicate.inOpenWater(true))).build()}, Items.COD, RankineItems.TUNA.get()));

    }
}
