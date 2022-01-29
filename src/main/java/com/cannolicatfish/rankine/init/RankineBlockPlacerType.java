package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.world.gen.placers.CropsBlockPlacer;
import com.cannolicatfish.rankine.world.gen.placers.DoubleCropsBlockPlacer;
import com.mojang.serialization.Codec;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RankineBlockPlacerType<P extends BlockPlacer> extends net.minecraftforge.registries.ForgeRegistryEntry<BlockPlacerType<?>> {

    public static final DeferredRegister<BlockPlacerType<?>> BLOCK_PLACERS = DeferredRegister.create(ForgeRegistries.BLOCK_PLACER_TYPES, ProjectRankine.MODID);

    public static final RegistryObject<BlockPlacerType<CropsBlockPlacer>> SINGLE_CROP = BLOCK_PLACERS.register("single_crop_placer", () -> new BlockPlacerType<>(CropsBlockPlacer.CODEC));
    public static final RegistryObject<BlockPlacerType<DoubleCropsBlockPlacer>> DOUBLE_CROP = BLOCK_PLACERS.register("double_crop_placer", () -> new BlockPlacerType<>(DoubleCropsBlockPlacer.CODEC));
    public static final RegistryObject<BlockPlacerType<DoubleCropsBlockPlacer>> TRIPLE_CROP = BLOCK_PLACERS.register("triple_crop_placer", () -> new BlockPlacerType<>(DoubleCropsBlockPlacer.CODEC));
    private final Codec<P> codec;

    public RankineBlockPlacerType(Codec<P> codec) {
        this.codec = codec;
    }

    public Codec<P> getCodec() {
        return this.codec;
    }

}
