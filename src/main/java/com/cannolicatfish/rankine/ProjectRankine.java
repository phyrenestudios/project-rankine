package com.cannolicatfish.rankine;

import com.cannolicatfish.rankine.blocks.block_groups.RankineWood;
import com.cannolicatfish.rankine.init.*;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ProjectRankine.MODID)
public class ProjectRankine {
    public static final String MODID = "rankine";
    public static final Logger LOGGER = LogManager.getLogger();

    public ProjectRankine(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG, "rankine-common.toml");
        container.registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT_CONFIG, "rankine-client.toml");

        RankineBlocks.BLOCKS.register(modBus);
        RankineItems.ITEMS.register(modBus);
        RankineBlocks.ITEMS.register(modBus);
        RankineDataComponents.DATA_COMPONENTS.register(modBus);
        RankineCreativeTabs.CREATIVE_MODE_TABS.register(modBus);
        RankineEntityTypes.ENTITY_TYPES.register(modBus);
        RankineMobEffects.MOB_EFFECTS.register(modBus);
        RankinePotions.POTIONS.register(modBus);
        RankinePOIs.POI_TYPES.register(modBus);
        RankineVillagerProfessions.VILLAGER_PROFESSIONS.register(modBus);
        RankineBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modBus);
        RankineRecipeTypes.RECIPE_TYPES.register(modBus);
        RankineRecipeSerializers.RECIPE_SERIALIZERS.register(modBus);
        RankineContainers.CONTAINERS.register(modBus);
        RankineFeatures.FEATURES.register(modBus);
        RankineSoundEvents.SOUNDS.register(modBus);
        RankineLootModifiers.LOOT_MODIFIERS.register(modBus);
        RankineFluids.FLUIDS.register(modBus);
        RankineFluids.FLUID_TYPES.register(modBus);
        RankineAttributes.ATTRIBUTES.register(modBus);
        RankineArmorMaterials.ARMOR_MATERIALS.register(modBus);
        RankineAttachments.ATTACHMENT_TYPES.register(modBus);
        RankineCriteriaTriggers.ITEM_SUB_PREDICATE_TYPES.register(modBus);
        RankineBiomeModifierTypes.BIOME_MODIFIER_SERIALIZERS.register(modBus);
        RankineWorldgenTypes.PLACEMENT_MODIFIER_TYPES.register(modBus);

        modBus.addListener(this::commonSetup);
        modBus.addListener(this::loadComplete);

        WorldgenUtils.initOreTextures();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.debug("Rankine: \"CommonSetup\" Starting...");

        WorldgenUtils.initConfigs();

        event.enqueueWork(() -> {
            RankineRecipes.registerPredicates();
            RankineRecipes.registerPotionRecipes();
            RankineRecipes.registerDispenserBehaviors();
            for (RankineWood wood : RankineLists.RANKINE_WOODS) {
                WoodType.register(wood.getWoodType());
            }
        });

        LOGGER.info("Rankine: \"CommonSetup\" Event Complete!");
    }

    private void loadComplete(FMLLoadCompleteEvent event) {
        VanillaIntegration.init();
    }
}
