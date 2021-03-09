package com.cannolicatfish.rankine.recipe.helper;

import com.cannolicatfish.rankine.init.WGConfig;
import net.minecraft.block.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigHelper {

    private static final List<ForgeConfigSpec.IntValue> oreConfig = Arrays.asList(WGConfig.ORES.ACANTHITE_ORE_HL,WGConfig.ORES.ANTHRACITE_ORE_HL,WGConfig.ORES.ANTITAENITE_ORE_HL,WGConfig.ORES.AQUAMARINE_ORE_HL,
            WGConfig.ORES.BAUXITE_ORE_HL,WGConfig.ORES.BITUMINOUS_ORE_HL,WGConfig.ORES.BISMUTHINITE_ORE_HL,WGConfig.ORES.CASSITERITE_ORE_HL,WGConfig.ORES.CELESTINE_ORE_HL,
            WGConfig.ORES.CHROMITE_ORE_HL,WGConfig.ORES.CINNABAR_ORE_HL,WGConfig.ORES.COBALTITE_ORE_HL,WGConfig.ORES.COLUMBITE_ORE_HL,WGConfig.ORES.CRYOLITE_ORE_HL,WGConfig.ORES.DIAMOND_ORE_HL,
            WGConfig.ORES.EMERALD_ORE_HL,WGConfig.ORES.FLUORITE_ORE_HL,WGConfig.ORES.GALENA_ORE_HL,WGConfig.ORES.GREENOCKITE_ORE_HL,WGConfig.ORES.HALITE_ORE_HL,WGConfig.ORES.ILMENITE_ORE_HL,
            WGConfig.ORES.INTERSPINIFEX_ORE_HL,WGConfig.ORES.KAMACITE_ORE_HL,WGConfig.ORES.LAZURITE_ORE_HL,WGConfig.ORES.LIGNITE_ORE_HL,WGConfig.ORES.LONSDALEITE_ORE_HL,
            WGConfig.ORES.MAGNESITE_ORE_HL,WGConfig.ORES.MAGNETITE_ORE_HL,WGConfig.ORES.MAJORITE_ORE_HL,WGConfig.ORES.MALACHITE_ORE_HL,WGConfig.ORES.MOISSANITE_ORE_HL,WGConfig.ORES.MOLYBDENITE_ORE_HL,
            WGConfig.ORES.MONAZITE_ORE_HL,WGConfig.ORES.NATIVE_ALUMINUM_ORE_HL,WGConfig.ORES.NATIVE_ARSENIC_ORE_HL,WGConfig.ORES.NATIVE_BISMUTH_ORE_HL,WGConfig.ORES.NATIVE_COPPER_ORE_HL,
            WGConfig.ORES.NATIVE_GALLIUM_ORE_HL,WGConfig.ORES.NATIVE_GOLD_ORE_HL,WGConfig.ORES.NATIVE_INDIUM_ORE_HL,WGConfig.ORES.NATIVE_LEAD_ORE_HL,WGConfig.ORES.NATIVE_SELENIUM_ORE_HL,
            WGConfig.ORES.NATIVE_SILVER_ORE_HL,WGConfig.ORES.NATIVE_SULFUR_ORE_HL,WGConfig.ORES.NATIVE_TELLURIUM_ORE_HL,WGConfig.ORES.NATIVE_TIN_ORE_HL,WGConfig.ORES.OPAL_ORE_HL,
            WGConfig.ORES.PENTLANDITE_ORE_HL,WGConfig.ORES.PETALITE_ORE_HL,WGConfig.ORES.PINK_HALITE_ORE_HL,WGConfig.ORES.PLUMBAGO_ORE_HL,WGConfig.ORES.PYRITE_ORE_HL,WGConfig.ORES.PYROLUSITE_ORE_HL,
            WGConfig.ORES.QUARTZ_ORE_HL,WGConfig.ORES.SPERRYLITE_ORE_HL,WGConfig.ORES.SPHALERITE_ORE_HL,WGConfig.ORES.STIBNITE_ORE_HL,WGConfig.ORES.SUBBITUMINOUS_ORE_HL,
            WGConfig.ORES.TAENITE_ORE_HL,WGConfig.ORES.TANTALITE_ORE_HL,WGConfig.ORES.TETRATAENITE_ORE_HL,WGConfig.ORES.URANINITE_ORE_HL,WGConfig.ORES.VANADINITE_ORE_HL,WGConfig.ORES.WOLFRAMITE_ORE_HL,
            WGConfig.ORES.XENOTIME_ORE_HL);

    public static int getOreHarvestLevel(List<String> path) {
        List<ForgeConfigSpec.IntValue> in = oreConfig.stream().filter((config) -> config.getPath().equals(path)).collect(Collectors.<ForgeConfigSpec.IntValue>toList());
        //System.out.println(in);
        if (in.size() >= 1) {
            return in.get(0).get();
        } else {
            return -1;
        }
    }
}
