package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.recipe.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RankineRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES.getRegistryName(), ProjectRankine.MODID);

    public static RegistryObject<RecipeType<OldAlloyingRecipe>> ALLOYING = RECIPE_TYPES.register("alloying",() -> new RecipeType<>() {
        public String toString() {
            return "alloying";
        }
    });

    public static RegistryObject<RecipeType<CrushingRecipe>> CRUSHING = RECIPE_TYPES.register("crushing",() -> new RecipeType<>() {
        public String toString() {
            return "crushing";
        }
    });

    public static RegistryObject<RecipeType<BeehiveOvenRecipe>> BEEHIVE = RECIPE_TYPES.register("beehive_oven",() -> new RecipeType<>() {
        public String toString() {
            return "beehive_oven";
        }
    });

    public static RegistryObject<RecipeType<SluicingRecipe>> SLUICING = RECIPE_TYPES.register("sluicing",() -> new RecipeType<>() {
        public String toString() {
            return "sluicing";
        }
    });

    public static RegistryObject<RecipeType<ElementRecipe>> ELEMENT = RECIPE_TYPES.register("element",() -> new RecipeType<>() {
        public String toString() {
            return "element";
        }
    });

    public static RegistryObject<RecipeType<EvaporationRecipe>> EVAPORATION = RECIPE_TYPES.register("evaporation",() -> new RecipeType<>() {
        public String toString() {
            return "evaporation";
        }
    });

    public static RegistryObject<RecipeType<CrucibleRecipe>> CRUCIBLE = RECIPE_TYPES.register("crucible",() -> new RecipeType<>() {
        public String toString() {
            return "crucible";
        }
    });

    public static RegistryObject<RecipeType<RockGeneratorRecipe>> ROCK_GENERATOR = RECIPE_TYPES.register("rock_generator",() -> new RecipeType<>() {
        public String toString() {
            return "rock_generator";
        }
    });

    public static RegistryObject<RecipeType<FusionFurnaceRecipe>> FUSION_FURNACE = RECIPE_TYPES.register("fusion_furnace",() -> new RecipeType<>() {
        public String toString() {
            return "fusion_furnace";
        }
    });

    public static RegistryObject<RecipeType<TreetappingRecipe>> TREETAPPING = RECIPE_TYPES.register("treetapping",() -> new RecipeType<>() {
        public String toString() {
            return "treetapping";
        }
    });

    public static RegistryObject<RecipeType<StrippingRecipe>> AXE_STRIPPING = RECIPE_TYPES.register("axe_stripping",() -> new RecipeType<>() {
        public String toString() {
            return "axe_stripping";
        }
    });

    public static RegistryObject<RecipeType<ForagingRecipe>> FORAGING = RECIPE_TYPES.register("foraging",() -> new RecipeType<>() {
        public String toString() {
            return "foraging";
        }
    });

    public static RegistryObject<RecipeType<AirDistillationRecipe>> AIR_DISTILLATION = RECIPE_TYPES.register("air_distillation",() -> new RecipeType<>() {
        public String toString() {
            return "air_distillation";
        }
    });

    public static RegistryObject<RecipeType<MixingRecipe>> MIXING = RECIPE_TYPES.register("mixing",() -> new RecipeType<>() {
        public String toString() {
            return "mixing";
        }
    });

    public static RegistryObject<RecipeType<AlloyModifierRecipe>> ALLOY_MODIFIER = RECIPE_TYPES.register("alloy_modifier",() -> new RecipeType<>() {
        public String toString() {
            return "alloy_modifier";
        }
    });
}
