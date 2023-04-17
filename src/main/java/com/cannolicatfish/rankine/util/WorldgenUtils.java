package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public static List<Block> DRIPSTONES = new ArrayList<>();
    public static List<Block> GRAVELS = new ArrayList<>();
    public static List<Block> SANDS = new ArrayList<>();
    public static List<Block> SANDSTONES = new ArrayList<>();

    public static List<Block> ORE_STONES = new ArrayList<>();
    public static List<String> ORE_TEXTURES = new ArrayList<>();

    public static List<String> ORES = Arrays.asList("minecraft:stone", "minecraft:granite", "minecraft:diorite", "minecraft:andesite", "minecraft:deepslate|minecraft:deepslate", "minecraft:sandstone|minecraft:sandstone_bottom", "minecraft:red_sandstone|minecraft:red_sandstone_bottom", "minecraft:netherrack", "minecraft:blackstone", "minecraft:basalt|minecraft:basalt_top", "minecraft:end_stone", "minecraft:obsidian", "rankine:pegmatite", "rankine:gray_granite", "rankine:rhyolite", "rankine:comendite", "rankine:granodiorite", "rankine:red_porphyry", "rankine:purple_porphyry", "rankine:hornblende_andesite", "rankine:black_dacite", "rankine:red_dacite", "rankine:tholeiitic_basalt", "rankine:diabase","rankine:gabbro", "rankine:anorthosite", "rankine:dunite", "rankine:harzburgite", "rankine:lherzolite", "rankine:wehrlite", "rankine:troctolite", "rankine:kimberlite", "rankine:komatiite","rankine:shonkinite", "rankine:norite", "rankine:pyroxenite", "rankine:rose_marble", "rankine:white_marble", "rankine:gray_marble", "rankine:black_marble", "rankine:gneiss", "rankine:mica_schist", "rankine:phyllite", "rankine:slate", "rankine:quartzite","rankine:mariposite","rankine:eclogite","rankine:limestone", "rankine:dolostone", "rankine:chalk", "rankine:soapstone", "rankine:shale", "rankine:siltstone", "rankine:itacolumite", "rankine:arkose", "rankine:mudstone","rankine:serpentinite","rankine:marlstone", "rankine:graywacke", "rankine:blueschist", "rankine:greenschist", "rankine:whiteschist","rankine:sommanite","rankine:post_perovskite","rankine:bridgmanham","rankine:ringwoodine","rankine:wadsleyone","rankine:honeystone", "rankine:meteorite", "rankine:frozen_meteorite", "rankine:enstatite_chondrite", "rankine:soul_sandstone|rankine:soul_sandstone_bottom", "rankine:white_sandstone|rankine:white_sandstone_bottom", "rankine:black_sandstone|rankine:black_sandstone_bottom", "rankine:desert_sandstone|rankine:desert_sandstone_bottom", "rankine:episyenite", "rankine:nepheline_syenite", "rankine:phonolite");

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
            ORE_STONES.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(ORE.split("\\|")[0])));
        }

        for (List<Object> L : Config.BIOME_GEN.BIOME_SETTINGS.get()) {
            String biomeToAdd = (String) L.get(0);
            List<String> biomeName = Arrays.asList(biomeToAdd.split(":"));
            if (biomeName.size() > 1) {
                Block gravel = ResourceLocation.tryParse((String) L.get(5)) == null ? Blocks.AIR : ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse((String) L.get(5)));
                Block sand = ResourceLocation.tryParse((String) L.get(6)) == null ? Blocks.AIR : ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse((String) L.get(6)));
                Block sandstone = ResourceLocation.tryParse((String) L.get(7)) == null ? Blocks.AIR : ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse((String) L.get(7)));
                Block dripstone = ResourceLocation.tryParse((String) L.get(8)) == null ? Blocks.AIR : ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse((String) L.get(8)));
                populateLists(ResourceLocation.tryParse(biomeToAdd),(List<String>) L.get(1),(List<String>) L.get(2),(List<String>) L.get(3),(List<String>) L.get(4), gravel, sand, sandstone, dripstone);
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
                tempIB.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(entry.split("\\|")[0])));
                tempIW.add(Float.parseFloat(entry.split("\\|")[1]));
                tempIO.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(entry.split("\\|")[2])));
                tempIC.add(Float.parseFloat(entry.split("\\|")[3]));
                col.add(tempIW.get(ind),tempIB.get(ind).defaultBlockState());
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
                tempVB.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(entry.split("\\|")[0])));
                tempVW.add(Float.parseFloat(entry.split("\\|")[1]));
                col.add(tempVW.get(ind),tempVB.get(ind).defaultBlockState());
                ind += 1;
            }
            VEGETATION_COLLECTIONS.add(col);
        }

    }

    private static void populateLists(ResourceLocation BIOME, List<String> SOILS, List<String> INTRUSIONS, List<String> STONES, List<String> VEGETATION, Block GRAVEL, Block SAND, Block SANDSTONE, Block DRIPSTONE) {
        GEN_BIOMES.add(BIOME);
        if (SOILS.isEmpty()) {
            O1.add(Blocks.AIR);
            A1.add(Blocks.AIR);
            B1.add(Blocks.AIR);
            O2.add(Blocks.AIR);
            A2.add(Blocks.AIR);
            B2.add(Blocks.AIR);
        } else {
            O1.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(SOILS.get(0))));
            A1.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(SOILS.get(1))));
            B1.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(SOILS.get(2))));
            O2.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(SOILS.get(3))));
            A2.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(SOILS.get(4))));
            B2.add(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(SOILS.get(5))));
        }
        INTRUSION_LISTS.add(INTRUSIONS);
        LAYER_LISTS.add(STONES);
        VEGETATION_LISTS.add(VEGETATION);
        GRAVELS.add(GRAVEL);
        SANDS.add(SAND);
        SANDSTONES.add(SANDSTONE);
        DRIPSTONES.add(DRIPSTONE);

    }




    public static boolean isWet(ChunkAccess chunk, BlockPos pos) {
        for(BlockPos POS : BlockPos.betweenClosed(pos.offset(-2,0,-2),pos.offset(2,1,2))) {
            if (Math.abs(POS.getX() - pos.getX()) <= 2 && Math.abs(POS.getZ() - pos.getZ()) <= 2 && chunk.getFluidState(POS).is(FluidTags.WATER)) {
                return true;
            }
        }
        return false;
    }

    public static Direction randomHorizontalDirection(RandomSource rand) {
        List<Direction> dirs = Arrays.asList(Direction.NORTH,Direction.EAST,Direction.WEST,Direction.SOUTH);
        return dirs.get(rand.nextInt(dirs.size()));
    }

    public static BlockPos eightBlockDirection(BlockPos pos, int type, int mult) {
        switch (type % 8) {
            case 0:
                return pos.offset(-1*mult,0,-1*mult);
            case 1:
                return pos.offset(-1*mult,0,0);
            case 2:
                return pos.offset(-1*mult,0, mult);
            case 7:
                return pos.offset(0,0,-1*mult);
            case 3:
                return pos.offset(0,0, mult);
            case 6:
                return pos.offset(mult,0,-1*mult);
            case 5:
                return pos.offset(mult,0,0);
            case 4:
                return pos.offset(mult,0, mult);
            default:
                return pos;
        }
    }

    public static boolean inInfiniteSource(Level levelIn, BlockPos posIn) {
        int i = 0;
        for (Direction dir : Direction.values()) {
            if (dir.getAxis().isVertical()) continue;
            FluidState fluid = levelIn.getFluidState(posIn.relative(dir));
            if (levelIn.getFluidState(posIn.relative(dir)).is(Fluids.WATER)) {
                i++;
                if (i>1) return true;
            }
        }
        return false;
    }

    public static int waterTableHeight(Level levelIn, BlockPos posIn) {
        if (!Config.GENERAL.DISABLE_WATER.get()) return levelIn.getMaxBuildHeight();
        int surface = levelIn.getHeight(Heightmap.Types.WORLD_SURFACE_WG,posIn.getX(),posIn.getZ());
        return levelIn.getSeaLevel() + Math.max(0, Math.round((surface-levelIn.getSeaLevel()) * (1)));
    }

    public static boolean inArea(BlockPos b, double radius, boolean center, BlockPos... targets) {
        for (BlockPos target : targets) {
            if (center) {
                if (b.getCenter().distanceToSqr(target.getCenter()) < Math.pow(radius, 2) + 0.5) {
                    return true;
                }
            } else {
                if (b.distSqr(target) < Math.pow(radius, 2) + 0.5) {
                    return true;
                }
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


    public static Block getCeillingBlock(Level worldIn, BlockPos pos, Direction dir, int height) {
        int i = 1;
        while (i<=height) {
            if (!worldIn.isEmptyBlock(pos.relative(dir,i)) && !(worldIn.getBlockState(pos.above(i)).canBeReplaced(Fluids.WATER))) {
                return worldIn.getBlockState(pos.above(i)).getBlock();
            }
            ++i;
        }
        return Blocks.AIR;
    }


// TODO: fix inRadiusCenter to account for center [checkPos.getX()+0.5D]
    public static boolean inRadiusCenter(BlockPos center, BlockPos checkPos, double radius) {
        return center.distSqr(new Vec3i(checkPos.getX(),checkPos.getY(),checkPos.getZ())) < radius*radius;
    }

    public static void checkLog(WorldGenLevel reader, BlockPos pos, RandomSource rand, TreeConfiguration config, Direction.Axis axis) {
        if (isAirOrLeaves(reader, pos)) {
            placeLogAt(reader, pos, rand, config, axis);
        }
    }

    public static void placeLogAt(WorldGenLevel reader, BlockPos pos, RandomSource rand, TreeConfiguration config, Direction.Axis axis) {
        setLogState(reader, pos, config.trunkProvider.getState(rand, pos).setValue(BlockStateProperties.AXIS, axis));
    }

    public static void setLogState(WorldGenLevel reader, BlockPos pos, BlockState state) {
        reader.setBlock(pos, state, 18);
    }

    public static void placeLeafAt(WorldGenLevel world, BlockPos pos, RandomSource rand, TreeConfiguration config) {
        if (isAirOrLeaves(world, pos)) {
            setLogState(world, pos, config.foliageProvider.getState(rand, pos).setValue(LeavesBlock.DISTANCE, 1));
        }
    }

    public static boolean isAirOrLeaves(WorldGenLevel levelIn, BlockPos pos) {
        /*/if (reader instanceof net.minecraft.world.level.LevelReader) {
            return reader.isStateAtPosition(pos, state -> state.canBeReplacedByLeaves((net.minecraft.world.level.LevelReader) reader, pos));
        }*/
        return levelIn.isStateAtPosition(pos, (state) -> state.isAir() || state.is(BlockTags.LEAVES));
    }

    public static boolean isAir(WorldGenLevel reader, BlockPos pos) {
        if (!(reader instanceof net.minecraft.world.level.BlockGetter)) {
            return reader.isStateAtPosition(pos, BlockState::isAir);
        }
        else {
            return reader.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::isAir);
        }
    }

    public static boolean isGasOrAir(Level reader, BlockPos pos) {
        return RankineLists.GAS_BLOCKS.contains(reader.getBlockState(pos).getBlock()) || reader.getBlockState(pos).isAir();
    }

}
