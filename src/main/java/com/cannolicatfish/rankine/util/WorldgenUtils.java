package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.init.WGConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class WorldgenUtils {

    public static List<Biome.Category> GEN_BIOMES = new ArrayList<>();
    public static List<Block> GEN_SOILS = new ArrayList<>();
    public static List<Block> GEN_SOILS2 = new ArrayList<>();
    public static List<Block> GEN_GRASSES = new ArrayList<>();
    public static List<Block> GEN_GRASSES2 = new ArrayList<>();
    public static List<List<String>> INTRUSION_LISTS = new ArrayList<>();
    public static List<List<Block>> INTRUSION_BLOCKS = new ArrayList<>();
    public static List<List<Float>> INTRUSION_WEIGHTS = new ArrayList<>();
    public static List<List<Block>> INTRUSION_ORES = new ArrayList<>();
    public static List<List<Float>> INTRUSION_ORE_CHANCES = new ArrayList<>();
    public static List<WeightedCollection<BlockState>> INTRUSION_COLLECTIONS = new ArrayList<>();
    public static List<List<String>> LAYER_LISTS = new ArrayList<>();

    
    
    public static void initConfigs() {
        for (List<Object> L : WGConfig.BIOME_GEN.BIOME_SETTINGS.get()) {
            GEN_BIOMES.add(Biome.Category.byName((String) L.get(0)));
            List<String> DIRTS = (List<String>) L.get(1);
            GEN_SOILS.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(DIRTS.get(0))));
            GEN_SOILS2.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(DIRTS.get(2))));
            GEN_GRASSES.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(DIRTS.get(1))));
            GEN_GRASSES2.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(DIRTS.get(3))));
            INTRUSION_LISTS.add((List<String>) L.get(2));
            LAYER_LISTS.add((List<String>) L.get(3));
        }
        for (List<String> I : INTRUSION_LISTS) {
            int ind = 0;
            WeightedCollection<BlockState> col = new WeightedCollection<>();
            List<Block> tempIB = new ArrayList<>();
            List<Float> tempIW = new ArrayList<>();
            List<Block> tempIO = new ArrayList<>();
            List<Float> tempIC = new ArrayList<>();
            for (String entry : I) {
                tempIB.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(entry.split("\\|")[0])));
                tempIW.add(Float.parseFloat(entry.split("\\|")[1]));
                tempIO.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(entry.split("\\|")[2])));
                tempIC.add(Float.parseFloat(entry.split("\\|")[3]));
                col.add(tempIW.get(ind),tempIB.get(ind).getDefaultState());
                ind += 1;
            }
            INTRUSION_BLOCKS.add(tempIB);
            INTRUSION_WEIGHTS.add(tempIW);
            INTRUSION_ORES.add(tempIO);
            INTRUSION_ORE_CHANCES.add(tempIC);
            INTRUSION_COLLECTIONS.add(col);
        }
    }

    public static List<ResourceLocation> getBiomeNamesFromCategory(List<Biome.Category> biomeCats, boolean include) {
        List<ResourceLocation> b = new ArrayList<>();
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (!biomeCats.isEmpty()) {
                for (Biome.Category cat : biomeCats) {
                    if (biome.getCategory() == cat && include){
                        b.add(biome.getRegistryName());
                    }
                    if (!include && biome.getCategory() != cat && biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
                        b.add(biome.getRegistryName());
                    }
                }
            }
            else if (!include && biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
                b.add(biome.getRegistryName());
            }
        }
        return b;
    }

    public static boolean isWet(ISeedReader reader, BlockPos pos) {
        for(BlockPos POS : BlockPos.getAllInBoxMutable(pos.add(-2,0,-2),pos.add(2,2,2))) {
            FluidState fluidstate = reader.getFluidState(POS);
            if (fluidstate.isTagged(FluidTags.WATER)) {
                return true;
            }
        }
        return false;
    }

}
