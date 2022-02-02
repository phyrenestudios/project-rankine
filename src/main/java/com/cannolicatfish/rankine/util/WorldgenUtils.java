package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class WorldgenUtils {

    public static List<ResourceLocation> GEN_BIOMES = new ArrayList<>();
    public static List<Block> O1 = new ArrayList<>();
    public static List<Block> A1 = new ArrayList<>();
    public static List<Block> B1 = new ArrayList<>();
    public static List<Block> O2 = new ArrayList<>();
    public static List<Block> A2 = new ArrayList<>();
    public static List<Block> B2 = new ArrayList<>();
    public static List<List<String>> INTRUSION_LISTS = new ArrayList<>();
    public static List<List<Block>> INTRUSION_BLOCKS = new ArrayList<>();
    public static List<List<Float>> INTRUSION_WEIGHTS = new ArrayList<>();
    public static List<List<Block>> INTRUSION_ORES = new ArrayList<>();
    public static List<List<Float>> INTRUSION_ORE_CHANCES = new ArrayList<>();
    public static List<WeightedCollection<BlockState>> INTRUSION_COLLECTIONS = new ArrayList<>();
    public static List<List<String>> LAYER_LISTS = new ArrayList<>();
    public static List<List<String>> VEGETATION_LISTS = new ArrayList<>();
    public static List<WeightedCollection<BlockState>> VEGETATION_COLLECTIONS = new ArrayList<>();
    public static List<Block> GRAVELS = new ArrayList<>();
    public static List<Block> SANDS = new ArrayList<>();
    public static List<Block> SANDSTONES = new ArrayList<>();

    public static List<Block> ORE_STONES = new ArrayList<>();
    public static List<String> ORE_TEXTURES = new ArrayList<>();

    public static List<String> ORES = Arrays.asList("minecraft:stone", "minecraft:granite", "minecraft:diorite", "minecraft:andesite", "minecraft:sandstone|minecraft:sandstone_bottom", "minecraft:red_sandstone|minecraft:red_sandstone_bottom", "minecraft:netherrack", "minecraft:blackstone", "minecraft:basalt|minecraft:basalt_top", "minecraft:end_stone", "minecraft:obsidian", "rankine:pegmatite", "rankine:gray_granite", "rankine:rhyolite", "rankine:comendite", "rankine:granodiorite", "rankine:red_porphyry", "rankine:purple_porphyry", "rankine:hornblende_andesite", "rankine:black_dacite", "rankine:red_dacite", "rankine:tholeiitic_basalt", "rankine:diabase","rankine:gabbro", "rankine:anorthosite", "rankine:peridotite", "rankine:troctolite", "rankine:kimberlite", "rankine:komatiite","rankine:shonkinite", "rankine:norite", "rankine:pyroxenite", "rankine:rose_marble", "rankine:white_marble", "rankine:gray_marble", "rankine:black_marble", "rankine:gneiss", "rankine:mica_schist", "rankine:phyllite", "rankine:slate", "rankine:quartzite","rankine:mariposite","rankine:eclogite","rankine:limestone", "rankine:dolostone", "rankine:chalk", "rankine:shale", "rankine:siltstone", "rankine:itacolumite", "rankine:arkose", "rankine:mudstone","rankine:serpentinite","rankine:eclogite", "rankine:marlstone", "rankine:blueschist", "rankine:greenschist","rankine:sommanite","rankine:post_perovskite","rankine:bridgmanham","rankine:ringwoodine","rankine:wadsleyone","rankine:honeystone", "rankine:meteorite", "rankine:frozen_meteorite", "rankine:enstatite_chondrite", "rankine:soul_sandstone|rankine:soul_sandstone_bottom", "rankine:white_sandstone|rankine:white_sandstone_bottom", "rankine:black_sandstone|rankine:black_sandstone_bottom", "rankine:desert_sandstone|rankine:desert_sandstone_bottom");



    public static void initOreTextures() {

        for (String ORE : ORES) {
            String[] ores = ORE.split("\\|");
            if (ores.length > 1) {
                ORE_TEXTURES.add(ores[1]);
            } else {
                ORE_TEXTURES.add(ores[0]);
            }

        }

    }
    public static void initConfigs() {

        for (String ORE : ORES) {
            ORE_STONES.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(ORE.split("\\|")[0])));
        }

        for (List<Object> L : Config.BIOME_GEN.BIOME_SETTINGS.get()) {
            String biomeToAdd = (String) L.get(0);
            List<String> biomeName = Arrays.asList(biomeToAdd.split(":"));
            if (biomeName.size() > 1) {
                Block gravel = ResourceLocation.tryCreate((String) L.get(5)) == null ? Blocks.AIR : ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate((String) L.get(5)));
                Block sand = ResourceLocation.tryCreate((String) L.get(6)) == null ? Blocks.AIR : ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate((String) L.get(6)));
                Block sandstone = ResourceLocation.tryCreate((String) L.get(7)) == null ? Blocks.AIR : ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate((String) L.get(7)));
                populateLists(ResourceLocation.tryCreate(biomeToAdd),(List<String>) L.get(1),(List<String>) L.get(2),(List<String>) L.get(3),(List<String>) L.get(4), gravel, sand, sandstone);
            } else {
                for (ResourceLocation RS : getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.byName(biomeToAdd)), true)) {
                    Block gravel = ResourceLocation.tryCreate((String) L.get(5)) == null ? Blocks.AIR : ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate((String) L.get(5)));
                    Block sand = ResourceLocation.tryCreate((String) L.get(6)) == null ? Blocks.AIR : ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate((String) L.get(6)));
                    Block sandstone = ResourceLocation.tryCreate((String) L.get(7)) == null ? Blocks.AIR : ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate((String) L.get(7)));
                    populateLists(RS,(List<String>) L.get(1),(List<String>) L.get(2),(List<String>) L.get(3),(List<String>) L.get(4), gravel, sand, sandstone);
                }
            }

        }

        for (List<String> I : INTRUSION_LISTS) {
/*            if (I.isEmpty()) {
                WeightedCollection<BlockState> col = new WeightedCollection<>();

                INTRUSION_BLOCKS.add(Collections.singletonList(Blocks.AIR));
                INTRUSION_WEIGHTS.add(Collections.singletonList(1.0f));
                INTRUSION_ORES.add(Collections.singletonList(Blocks.AIR));
                INTRUSION_ORE_CHANCES.add(Collections.singletonList(1.0f));
                INTRUSION_COLLECTIONS.add(col.add(1,Blocks.AIR.getDefaultState()));
            }

 */
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

        for (List<String> V : VEGETATION_LISTS) {
            int ind = 0;
            WeightedCollection<BlockState> col = new WeightedCollection<>();
            List<Block> tempVB = new ArrayList<>();
            List<Float> tempVW = new ArrayList<>();
            for (String entry : V) {
                tempVB.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(entry.split("\\|")[0])));
                tempVW.add(Float.parseFloat(entry.split("\\|")[1]));
                col.add(tempVW.get(ind),tempVB.get(ind).getDefaultState());
                ind += 1;
            }
            VEGETATION_COLLECTIONS.add(col);
        }

    }

    private static void populateLists(ResourceLocation BIOME, List<String> SOILS, List<String> INTRUSIONS, List<String> STONES, List<String> VEGETATION, Block GRAVEL, Block SAND, Block SANDSTONE) {
        GEN_BIOMES.add(BIOME);
        if (SOILS.isEmpty()) {
            O1.add(Blocks.AIR);
            A1.add(Blocks.AIR);
            B1.add(Blocks.AIR);
            O2.add(Blocks.AIR);
            A2.add(Blocks.AIR);
            B2.add(Blocks.AIR);
        } else {
            O1.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(SOILS.get(0))));
            A1.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(SOILS.get(1))));
            B1.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(SOILS.get(2))));
            O2.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(SOILS.get(3))));
            A2.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(SOILS.get(4))));
            B2.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryCreate(SOILS.get(5))));
        }
        INTRUSION_LISTS.add(INTRUSIONS);
        LAYER_LISTS.add(STONES);
        VEGETATION_LISTS.add(VEGETATION);
        GRAVELS.add(GRAVEL);
        SANDS.add(SAND);
        SANDSTONES.add(SANDSTONE);

    }


    public static List<ResourceLocation> getBiomeNamesFromCategory(List<Biome.Category> biomeCats, boolean include) {
        List<ResourceLocation> b = new ArrayList<>();
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (!biomeCats.isEmpty()) {
                for (Biome.Category cat : biomeCats) {
                    if (biome.getCategory() == cat && include){
                        b.add(biome.getRegistryName());
                    }
                    if (!include && biome.getCategory() != cat) {
                        b.add(biome.getRegistryName());
                    }
                }
            } else if (!include) {
                b.add(biome.getRegistryName());
            }
        }
        return b;
    }

    public static boolean isWet(ISeedReader reader, BlockPos pos) {
        for(BlockPos POS : BlockPos.getAllInBoxMutable(pos.add(-2,0,-2),pos.add(2,2,2))) {
            if (reader.getFluidState(POS).isTagged(FluidTags.WATER)) {
                return true;
            }
        }
        return false;
    }

    public static Direction randomHorizontalDirection(Random rand) {
        List<Direction> dirs = Arrays.asList(Direction.NORTH,Direction.EAST,Direction.WEST,Direction.SOUTH);
        return dirs.get(rand.nextInt(dirs.size()));
    }

    public static BlockPos eightBlockDirection(BlockPos pos, int type, int mult) {
        switch (type % 8) {
            case 0:
                return pos.add(-1*mult,0,-1*mult);
            case 1:
                return pos.add(-1*mult,0,0);
            case 2:
                return pos.add(-1*mult,0, mult);
            case 7:
                return pos.add(0,0,-1*mult);
            case 3:
                return pos.add(0,0, mult);
            case 6:
                return pos.add(mult,0,-1*mult);
            case 5:
                return pos.add(mult,0,0);
            case 4:
                return pos.add(mult,0, mult);
            default:
                return pos;
        }
    }

    public static int waterTableHeight(World worldIn, BlockPos pos) {
        Biome biome = worldIn.getBiome(pos);
        int surface = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,pos.getX(),pos.getZ());

        return biome.getCategory() == Biome.Category.OCEAN || biome.getCategory() == Biome.Category.BEACH || biome.getCategory() == Biome.Category.SWAMP || biome.getCategory() == Biome.Category.RIVER ? worldIn.getSeaLevel() + 1 : (int) (worldIn.getSeaLevel()- surface*0.3 + biome.getDepth()*30 + biome.getDownfall()*10);
    }

    public static boolean inArea(BlockPos b, double radius, BlockPos... targets) {
        for (BlockPos target : targets) {
            if (b.distanceSq(target) < Math.pow(radius,2)+0.5) {
                return true;
            }
        }
        return false;
    }

    public static BlockPos averagePos(BlockPos... targets) {
        int x = 0;
        int y = 0;
        int z = 0;
        for (BlockPos target : targets) {
            x += target.getX();
            y += target.getY();
            z += target.getZ();
        }
        return new BlockPos(x/targets.length,y/targets.length,z/targets.length);
    }


    public static Block getCeillingBlock(World worldIn, BlockPos pos, Direction dir, int height) {
        int i = 1;
        while (i<=height) {
            if (!worldIn.isAirBlock(pos.offset(dir,i)) && !(worldIn.getBlockState(pos.up(i)).isReplaceable(Fluids.WATER))) {
                return worldIn.getBlockState(pos.up(i)).getBlock();
            }
            ++i;
        }
        return Blocks.AIR;
    }



    public static boolean inRadiusCenter(BlockPos center, BlockPos checkPos, double radius) {
        return center.distanceSq(checkPos.getX()+0.5D,checkPos.getY()+0.5D,checkPos.getZ()+0.5D,true) < radius*radius;
    }

    public static void checkLog(ISeedReader reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config, Direction.Axis axis) {
        if (isAirOrLeaves(reader, pos)) {
            placeLogAt(reader, pos, rand, config, axis);
        }
    }

    public static void placeLogAt(IWorldWriter reader, BlockPos pos, Random rand, BaseTreeFeatureConfig config, Direction.Axis axis) {
        setLogState(reader, pos, config.trunkProvider.getBlockState(rand, pos).with(BlockStateProperties.AXIS, axis));
    }

    public static void setLogState(IWorldWriter reader, BlockPos pos, BlockState state) {
        reader.setBlockState(pos, state, 18);
    }

    public static void placeLeafAt(IWorldGenerationReader world, BlockPos pos, Random rand, BaseTreeFeatureConfig config) {
        if (isAirOrLeaves(world, pos)) {
            setLogState(world, pos, config.leavesProvider.getBlockState(rand, pos).with(LeavesBlock.DISTANCE, 1));
        }
    }

    public static boolean isAirOrLeaves(IWorldGenerationBaseReader reader, BlockPos pos) {
        if (reader instanceof net.minecraft.world.IWorldReader) {
            return reader.hasBlockState(pos, state -> state.canBeReplacedByLeaves((net.minecraft.world.IWorldReader) reader, pos));
        }
        return reader.hasBlockState(pos, (state) -> {
            return state.isAir() || state.isIn(BlockTags.LEAVES);
        });
    }

    public static boolean isAir(IWorldGenerationBaseReader reader, BlockPos pos) {
        if (!(reader instanceof net.minecraft.world.IBlockReader)) {
            return reader.hasBlockState(pos, BlockState::isAir);
        }
        else {
            return reader.hasBlockState(pos, state -> state.isAir((net.minecraft.world.IBlockReader) reader, pos));
        }
    }

}
