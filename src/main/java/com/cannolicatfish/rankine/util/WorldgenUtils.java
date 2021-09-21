package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.init.WGConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WorldgenUtils {

    public static List<ResourceLocation> GEN_BIOMES = new ArrayList<>();
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
    public static List<Block> ORE_STONES = new ArrayList<>();
    public static List<String> ORE_TEXTURES = new ArrayList<>();




    public static void initOreTextures() {
        for (String ORE : WGConfig.MISC.ORE_STONES.get()) {
            String[] ores = ORE.split("\\|");
            if (ores.length > 1) {
                ORE_TEXTURES.add(ores[1]);
            } else {
                ORE_TEXTURES.add(ores[0]);
            }

        }

    }
    public static void initConfigs() {

        for (String ORE : WGConfig.MISC.ORE_STONES.get()) {
            ORE_STONES.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(ORE.split("\\|")[0])));
        }

        for (List<Object> L : WGConfig.BIOME_GEN.BIOME_SETTINGS.get()) {
            String biomeToAdd = (String) L.get(0);
            List<String> biomeName = Arrays.asList(biomeToAdd.split(":"));
            if (biomeName.size() > 1) {
                populateLists(ResourceLocation.tryCreate(biomeToAdd),(List<String>) L.get(1),(List<String>) L.get(2),(List<String>) L.get(3));
            } else {
                for (ResourceLocation RS : getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.byName(biomeToAdd)), true)) {
                    populateLists(RS,(List<String>) L.get(1),(List<String>) L.get(2),(List<String>) L.get(3));
                }
            }

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

    private static void populateLists(ResourceLocation BIOME, List<String> SOILS, List<String> INTRUSIONS, List<String> STONES) {
        GEN_BIOMES.add(BIOME);
        if (SOILS.isEmpty()) {
            GEN_SOILS.add(Blocks.AIR);
            GEN_SOILS2.add(Blocks.AIR);
            GEN_GRASSES.add(Blocks.AIR);
            GEN_GRASSES2.add(Blocks.AIR);
        } else {
            GEN_SOILS.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(SOILS.get(0))));
            GEN_SOILS2.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(SOILS.get(2))));
            GEN_GRASSES.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(SOILS.get(1))));
            GEN_GRASSES2.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(SOILS.get(3))));
        }
        INTRUSION_LISTS.add(INTRUSIONS);
        LAYER_LISTS.add(STONES);

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

    public static int waterTableHeight(World worldIn, BlockPos pos) {
        return (int) Math.max(worldIn.getSeaLevel(), (worldIn.getSeaLevel() + worldIn.getBiome(pos).getDepth()*30));
    }

}
