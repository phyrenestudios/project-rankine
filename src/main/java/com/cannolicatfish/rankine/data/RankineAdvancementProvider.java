package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


public class RankineAdvancementProvider extends AdvancementProvider {

    public RankineAdvancementProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    public String getName() {
        return "Project Rankine - Advancements";
    }

    @Override
    protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper) {

        /*TODO: re-add challenge advancements
            alloy_collector
            dirt_collector
            element_collector
            geode_collector
            rock_collector
            totem_collector
            find_meteoric_materials
         */


        Advancement.Builder.advancement().requirements(RequirementsStrategy.AND).rewards(AdvancementRewards.EMPTY).display(new DisplayInfo(new ItemStack(RankineItems.OGANESSON_INGOT.get()),new TranslatableComponent("rankine.advancements.challenges.title"),
                new TranslatableComponent("rankine.advancements.challenges.description"),new ResourceLocation("rankine:textures/gui/advancements/backgrounds/basalt.png"), FrameType.CHALLENGE,false,false,false)).save(consumer,"rankine:challenges/challenges");

        acquireAll(consumer,new ResourceLocation("rankine:challenges/challenges"),RankineLists.ELEMENT_INGOTS,"rankine:element_collector","rankine.advancements.challenges.element_collector",
                new ItemStack(RankineItems.ELEMENT.get()),FrameType.CHALLENGE,true,true,false);

        super.registerAdvancements(consumer, fileHelper);
    }

    private void acquireAll(Consumer<Advancement> consumer, ResourceLocation root, List<Item> list, String resourceId, String langId, ItemStack icon, FrameType frameType, boolean showToast, boolean announceChat, boolean hidden) {
        Advancement.Builder builder =  Advancement.Builder.advancement();
        for (Item item : list) {
            builder.addCriterion(item.getDescriptionId(),InventoryChangeTrigger.TriggerInstance.hasItems(item));
        }
       builder.parent(root).requirements(RequirementsStrategy.AND).rewards(AdvancementRewards.EMPTY).display(new DisplayInfo(icon,new TranslatableComponent(langId+".title"),
               new TranslatableComponent(langId+".description"),null, frameType,showToast,announceChat,hidden)).save(consumer,resourceId);
    }
}
